import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class MarioObject {
	
	// Attributes that all MarioObjects have
	protected int x, y, height, width;
	protected double scaleX, scaleY;
	protected Image img;
	protected AffineTransform tx;

	// MarioObject Superclass Constructor
	public MarioObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Generic Collision Detection Method
	public boolean collideObjects(MarioObject other) {
		if (this.getX() + this.getWidth() >= other.getX() && this.getX() <= other.getX() + other.getWidth()) {
			if (this.getY() + this.getWidth() >= other.getY() && this.getY() <= other.getY() + other.getHeight()) {
				return true;
			}
		}
		return false;
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
			URL imageURL = MarioObject.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	// Subclass methods
	public void setImage() {
		
	}
	public void setState(int x) {
		
	}
	
}


