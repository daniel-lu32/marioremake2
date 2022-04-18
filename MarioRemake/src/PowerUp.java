import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PowerUp {
	private int x, y, height, width;
	private String type; // Big Mushroom, Ice Flower, Fire Flower, 1-UP
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	public PowerUp(int x, int y, String type) {
		this.x = x;
		this.y = y;
		height = 30;
		width = 20;
		this.type = type;
		if (type.equals("Big Mushroom")) {
			img = getImage("/imgs/redmushroom.png");
			scaleX = 0.2;
			scaleY = 0.2;
		} else if (type.equals("Ice Flower")) {
			img = getImage("/imgs/iceflower.png");
			scaleX = 0.08;
			scaleY = 0.08;
		} else if (type.equals("Fire Flower")) {
			img = getImage("/imgs/fireflower.png");
			scaleX = 0.08;
			scaleY = 0.08;
		} else if (type.equals("1-UP")) {
			img = getImage("/imgs/greenmushroom.png");
			scaleX = 0.2;
			scaleY = 0.2;
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
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
	public String getType() {
		return type;
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
	public void setType(String type) {
		this.type = type;
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
			URL imageURL = PowerUp.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
