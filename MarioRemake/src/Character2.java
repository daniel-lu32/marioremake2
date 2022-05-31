import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Character2 {
	
	// instance variables
	private int x, y, height, width, vx, state;
	private boolean big, hasAbility, jumping, onPlatform;
	private String ability;
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	// constructor
	public Character2(int x, int y) {
		this.x = x;
		this.y = y;
		height = 39;
		width = 26;
		vx = 0;
		state = 0;
		big = false;
		hasAbility = false;
		jumping = false;
		onPlatform = false;
		ability = "None";
		scaleX = 0.3;
		scaleY = 0.3;
		img = getImage("/imgs/mariostanding.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	/* 
	 * is this method used or only the set image below?
	 */
	public void setImage(boolean run) {
		if (run == true) {
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariorunningright.gif");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariorunningright.gif");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariorunningright.gif");
			}
		}
		else {
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariostanding.png");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariostanding.png");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariostanding.png");
			}
		}
	}
	
	/* The parameter rightPressed boolean conveys if the right arrow key is being pressed
	 * If rightPressed is true, the vx (x velocity variable for Mario) is set to positive 5, so Mario moves right. If it is false, vx is 0 and Mario doesn't move
	 */
	public void rightPressed(boolean rightPressed) {
		if (rightPressed == true) {
			vx = 5;
			rightPressed = false;
		} else {
			vx = 0;
		}
	}
	
	/* The parameter leftPressed boolean conveys if the left arrow key is being pressed
	 * If leftPressed is true, the vx (x velocity variable for Mario) is set to negative 5, so Mario moves left. If it is false, vx is 0 and Mario doesn't move
	 */
	public void leftPressed(boolean leftPressed) {
		if (leftPressed == true) {
			vx = -5;
			leftPressed = false;
		} else {
			vx = 0;
		}
	}
	
	/* This is a modified version of the collide method defined below, specifically made for mystery blocks because it's height and y values change for each image
	 * It is used after the mystery block is hit and the image is changed to the mysteryBlockHit gif so it's y value which should be used for collision starts at its actual y
	 * value + 60.
	 */
	public boolean collideMystBlock(Block other) {
		if (other.getBrickType().equals("Mystery")) {
			if (this.getX() + this.getWidth() >= other.getX() && this.getX() <= other.getX() + other.getWidth()) {
				if (this.getY() + this.getHeight() >= other.getY() + 60 && this.getY() <= other.getY() + other.getHeight()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/* This is the default collision method
	 * It tests whether this Character2 object has x and y value within the x and y values of MarioObject other. If they do, the method returns true. If not, it returns false.
	 */
	public boolean collide(MarioObject other) {
		if (this.getX() + this.getWidth() >= other.getX() && this.getX() <= other.getX() + other.getWidth()) {
			if (this.getY() + this.getWidth() >= other.getY() && this.getY() <= other.getY() + other.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	/* This method changes the x value of Character2 so it's not colliding with object other.
	 * It first determines if this Character2 object is within a MarioObject other using the insideObjectX method. If it is inside object other, then the Character2 object is moved
	 * to either the left of the other object or to the right of it. If the current Character2 x is less than the other object's, then the Character2 x value is shifted so Character2 is to the
	 * left or other, and vice versa if it's x value is initially greater than object other's
	 */
	public void moveX(MarioObject other, boolean current) {
		if (insideObjectX(other)) {
			if (GameRunner.platform == GameRunner.originalPlatform && !(y + height < other.getY()) && !belowObject(other)) {
				if (x <= other.getX()) {
					x = other.getX() - width;
				} else {
					x = other.getX() + other.getWidth();
				}
			}
		}
	}
	
	/* This method determines if the Character2 object is above a MarioObject other and changes the boundary "platform" to the appropriate value
	 * It first checks if the x values of Character2 and object other are within each other by using the insideObjectX method. If it returns true, and Character2 is above other
	 * which is checked using it's y value and height, result is set to true, and the platform is set to a value so the Character2 object can't have a y value past the y of object other.
	 * If the x values of the objects weren't within each other, then result is set to false.
	 */
	public boolean aboveObject(MarioObject other, boolean current) {
		boolean result = current;
		if (insideObjectX(other)) {
			if (y + height <= other.getY()) {
				result = true;
				if (result) {
					GameRunner.platform = other.getY() - height;
				}
			}
			if (collide(other)) {
				moveX(other, current);
			}
		}
		if (!(x + width >= other.getX() && x <= other.getX() + other.getWidth())) {
			result = false;
		}
		return result;
	}
	
	/* This is a modified version of the aboveObject method, specifically used for mystery blocks.
	 * The only difference is that when available is false, the value used as the boundary to check whether the Character2 object is above object other is "other.getY() + 59" 
	 * instead of "other.getY()" because the mystery block in the image actually starts at a y of 60 rather than 0 due to empty space. When available is true, the block portion in the 
	 * image starts at 0, so the method is the same as in the default aboveObject method.
	 */
	public boolean aboveMystBlock(Block other, boolean current, boolean available) {
		boolean result = current;
		if (other.getBrickType().equals("Mystery") && available == false && insideObjectX(other)) {
			if (y + height <= other.getY() + 59) {
				result = true;
				if (result) {
					GameRunner.platform = other.getY() + 60 - height;
				}
			}
			if (collideMystBlock(other)) {
				moveX(other, current);
			}
		}
		
		if (other.getBrickType().equals("Mystery") && available == true && insideObjectX(other)) {
			if (y + height <= other.getY()) {
				result = true;
				if (result) {
					GameRunner.platform = other.getY() - height;
				}
			}
			if (collideMystBlock(other)) {
				moveX(other, current);
			}
		}
		if (!(x + width >= other.getX() && x <= other.getX() + other.getWidth())) {
			result = false;
		}
		return result;
	}
	
	/* This method determines if the Character2 object is below a MarioObject other
	 * It first checks if the x values of Character2 and object other are within each other by using the insideObjectX method. If it returns true, and Character2 is below other
	 * which is checked using it's y value and height, result is set to true. If the x values of the objects weren't within each other, then result is set to false.
	 */
	public boolean belowObject(MarioObject other) {
		boolean result = false;
		if (insideObjectX(other)) {
			if (y > other.getY() + other.getHeight()) {
				result = true;
			}
		}
		if (!insideObjectX(other)) {
			result = false;
		}
		return result;
	}
	
	/* This method returns if the Character2 object is hitting MarioObject other from below.
	 * It first tests if the Chracter2 object is inside other, and if its y value is within or just greater than the lower y value of other. In this case, Character2 is
	 * hitting object other from below and so it returns true.
	 */
	public boolean hittingObjectFromBelow(MarioObject other) {
		boolean result = false;
		if (insideObjectX(other)) {
			if (y + height >= other.getY() && y <= other.getY() + other.getHeight() + 3) {
				result = true;
			}
		}
		return result && GameRunner.vy < 0;
	}
	
	/* This method is a modified version of the hittingObjectFromBelow method defined above, specifically made for mystery blocks.
	 * The only difference is the "+60" when chekcing y values to account for the block portion of the image only starting at y=60 rather than y=0 and the "+10" to account for 
	 * quick velocity changes, so the code doesn't miss a quick hit
	 */
	public boolean hittingMystBlockFromBelow(Block other) {
		boolean result = false;
		if (other.getBrickType().equals("Mystery") && insideObjectX(other)) {
			if (y + height >= other.getY() + 60 && y <= other.getY() + other.getHeight() + 10) {
				result = true;
			}
		}
		return result && GameRunner.vy < 0;
	}
	
	/* 
	 * This method tests whether the x values of this Character2 object and the MarioObject other are within each other
	 */
	public boolean insideObjectX(MarioObject other) {
		return x + width >= other.getX() && x <= other.getX() + other.getWidth();
	}
	
	public void updateBig(boolean big) {
		if (big) {
			setScaleX(0.36);
			setScaleY(0.36);
			setHeight(47);
			setWidth(31);
			setBig(true);
		} else {
			setScaleX(0.3);
			setScaleY(0.3);
			setHeight(39);
			setWidth(26);
			setBig(false);
		}
	}
	
	// getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getState() {
		return state;
	}
	public boolean getBig() {
		return big;
	}
	public boolean getHasAbility() {
		return hasAbility;
	}
	public boolean getJumping() {
		return jumping;
	}
	public boolean getOnPlatform() {
		return onPlatform;
	}
	public String getAbility() {
		return ability;
	}
	public double getScaleX() {
		return scaleX;
	}
	public double getScaleY() {
		return scaleY;
	}
	
	// setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setState(int state) {
		this.state = state;
		if (state > 0) {
			updateBig(true);
		} else {
			updateBig(false);
		}
	}
	public void setBig(boolean big) {
		this.big = big;
	}
	public void setHasAbility(boolean hasAbility) {
		this.hasAbility = hasAbility;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	public void setOnPlatform(boolean onPlatform) {
		this.onPlatform = onPlatform;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
	
	// graphics settings
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		update();
		g2.drawImage(img, tx, null);
	}
	private void update() {
		x += vx;
		if (getAbility().equals("None") && vx == 0) {
			img = getImage("/imgs/mariostanding.png");
		}
		if (getAbility().equals("Ice") && vx == 0) {
			img = getImage("/imgs/icemariostanding.png");
		}
		if (getAbility().equals("Fire") && vx == 0) {
			img = getImage("/imgs/firemariostanding.png");
		}
		tx.setToTranslation(x, y);
		tx.scale(scaleX, scaleY);
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(2.7, 2.5);
	}
	
	/* Parameter booleans tell if Mario is running left (left arrow pressed) or running right (right arrow pressed) 
	 * For each condition (running left, running right, or standing), use the getAbility method to see if Mario currently has any abilities
	 * If not, the display is set to the default image/gif
	 * If Mario has Ice powers, the display is set to Ice version images/gifs
	 * If Mario has Fire powers, the display is set to Fire version images/gifs
	 */
	public void setImage(boolean runLeft, boolean runRight) {
		if (runRight == false && runLeft == false) {
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariostanding.png");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariostanding.png");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariostanding.png");
			}
		}
		else if (runRight == true) {
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariorunningright.gif");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariorunningright.gif");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariorunningright.gif");
			}
		}
		else if (runLeft == true){
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariorunningleft.gif");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariorunningleft.gif");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariorunningleft.gif");
			}
		}
		else {
			if (getAbility().equals("None")) {
				img = getImage("/imgs/mariostanding.png");
			}
			if (getAbility().equals("Ice")) {
				img = getImage("/imgs/icemariostanding.png");
			}
			if (getAbility().equals("Fire")) {
				img = getImage("/imgs/firemariostanding.png");
			}
		}
	}
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Character2.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}



