import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Character {
	private int x, y, height, width, vx = 0;
	private boolean big, small, hasAbility, jumping;
	private String ability;
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	public Character(int x, int y) {
		this.x = x;
		this.y = y;
		height = 80;
		width = 25;
		vx = 0;
		big = false;
		small = false;
		hasAbility = false;
		jumping = false;
		ability = "";
		scaleX = 0.3;
		scaleY = 0.3;
		img = getImage("/imgs/mariostanding.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public void setImage(boolean run) {
		if (run == true) {
			img = getImage("/imgs/mariorunninggif.gif");
		}
		else {
			img = getImage("/imgs/mariostanding.png");
		}
	}
	
//	public void upPressed(boolean upPressed) {	
//		if (upPressed == true) {
//			vy = -12;
//			upPressed = false;
//		}
//		else {
//			vy = 3;
//		}
//		
//	}
//	
//	public void updatePosition(boolean jump) {
//		if (jump) {
//			y -= vy;
//			vy -= ay;
//		}
//	}
//	public void upReleased() {
//		vy = 3;
//	}
	
	public void rightPressed(boolean rightPressed) {
		if (rightPressed == true) {
			vx = 5;
			rightPressed = false;
		}
		else {
			vx = 0;
		}
	}
	
	public void leftPressed(boolean leftPressed) {
		if (leftPressed == true) {
			vx = -5;
			leftPressed = false;
		}
		else {
			vx = 0;
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
	public boolean getBig() {
		return big;
	}
	public boolean getSmall() {
		return small;
	}
	public boolean getHasAbility() {
		return hasAbility;
	}
	public boolean getJumping() {
		return jumping;
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
	public void setBig(boolean big) {
		this.big = big;
	}
	public void setSmall(boolean small) {
		this.small = small;
	}
	public void setHasAbility(boolean hasAbility) {
		this.hasAbility = hasAbility;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
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
		tx.setToTranslation(x, y);
		tx.scale(scaleX, scaleY);
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(2.7, 2.5);
	}
	public void setImage(boolean runLeft, boolean runRight) {
		if (runRight == false && runLeft == false) {
			img = getImage("/imgs/mariostanding.png");
		}
		else if (runRight == true) {
			img = getImage("/imgs/mariorunninggif.gif");
		}
		else if (runLeft == true){
			img = getImage("/imgs/mariorunleft.gif");
		}
		else {
			img = getImage("/imgs/mariostanding.png");
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
