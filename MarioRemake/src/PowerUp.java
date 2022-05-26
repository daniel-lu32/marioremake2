import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PowerUp extends MarioObject {
	
	// attributes
	private int vx;
	private String type;
	private boolean hit;
	
	public PowerUp(int x, int y, String type) {
		super(x, y);
		height = 36;
		this.type = type;
		hit = false;
		if (type.equals("Big Mushroom")) {
			vx = -1;
			img = getImage("/imgs/redmushroom2.png");
			width = 36;
			scaleX = 0.2;
			scaleY = 0.2;
		} else if (type.equals("Ice Flower")) {
			vx = 0;
			img = getImage("/imgs/iceflower2.png");
			width = 32;
			scaleX = 0.07;
			scaleY = 0.075;
		} else if (type.equals("Fire Flower")) {
			vx = 0;
			img = getImage("/imgs/fireflower2.png");
			width = 34;
			scaleX = 0.07;
			scaleY = 0.07;
		} else if (type.equals("1-UP")) {
			vx = -1;
			img = getImage("/imgs/greenmushroom2.png");
			width = 36;
			scaleX = 0.2;
			scaleY = 0.2;
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	public boolean collide(MarioObject other) {
		if (this.getX() + this.getWidth() >= other.getX() && this.getX() <= other.getX() + other.getWidth()) {
			if (this.getY() + this.getWidth() >= other.getY() && this.getY() <= other.getY() + other.getHeight()) {
				return true;
			}
		}
		return false;
	}
	// getters
	public int getVX() {
		return vx;
	}
	public String getType() {
		return type;
	}
	public boolean getHit() {
		return hit;
	}
	
	// setters
	public void setVX(int vx) {
		this.vx = vx;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	// graphics settings
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		update();
		g2.drawImage(img, tx, null);
	}
	private void update() {
		tx.setToTranslation(x, y);
		tx.scale(scaleX, scaleY);
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(2.7, 2.5);
	}
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = PowerUp.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}


