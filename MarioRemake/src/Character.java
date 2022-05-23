import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Character {
	private int x, y, height, width, vx, state;
	private boolean big, hasAbility, jumping, onPlatform;
	private String ability;
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	public Character(int x, int y) {
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
	
	public void rightPressed(boolean rightPressed) {
		if (rightPressed == true) {
			vx = 5;
			rightPressed = false;
		} else {
			vx = 0;
		}
	}
	
	public void leftPressed(boolean leftPressed) {
		if (leftPressed == true) {
			vx = -5;
			leftPressed = false;
		} else {
			vx = 0;
		}
	}
	
	public boolean collide(MarioObject other) {
		if (this.getX() + this.getWidth() >= other.getX() && this.getX() <= other.getX() + other.getWidth()) {
			if (this.getY() + this.getWidth() >= other.getY() && this.getY() <= other.getY() + other.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
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
	
	public void moveX(MarioObject other, boolean current) {
		if (insideObjectX(other)) {
			if (PipeTester.platform == PipeTester.originalPlatform && !(y + height < other.getY()) && !belowObject(other)) {
				if (x <= other.getX()) {
					x = other.getX() - width;
				} else {
					x = other.getX() + other.getWidth();
				}
			}
		}
	}
	
	public boolean aboveObject(MarioObject other, boolean current) {
		boolean result = current;
		if (insideObjectX(other)) {
			if (y + height <= other.getY()) {
				result = true;
				if (result) {
					PipeTester.platform = other.getY() - height;
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
	
	public boolean aboveMystBlock(Block other, boolean current, boolean available) {
		boolean result = current;
		if (other.getBrickType().equals("Mystery") && available == false && insideObjectX(other)) {
			if (y + height <= other.getY() + 59) {
				result = true;
				if (result) {
					PipeTester.platform = other.getY() + 60 - height;
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
					PipeTester.platform = other.getY() - height;
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
	public boolean hittingObjectFromBelow(MarioObject other) {
		boolean result = false;
		if (insideObjectX(other)) {
			if (y + height >= other.getY() && y <= other.getY() + other.getHeight() + 1) {
				result = true;
			}
		}
		return result && PipeTester.vy < 0;
	}
	
	public boolean hittingMystBlockFromBelow(Block other) {
		boolean result = false;
		if (other.getBrickType().equals("Mystery") && insideObjectX(other)) {
			if (y + height >= other.getY() + 60 && y <= other.getY() + other.getHeight() + 1) {
				result = true;
			}
		}
		return result && PipeTester.vy < 0;
	}
	
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
			URL imageURL = Character.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}



