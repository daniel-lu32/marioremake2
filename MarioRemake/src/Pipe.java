import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Pipe {
	private int x, y, height, width;
	private boolean isLong, enterable;
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	public Pipe(int x, int y, boolean isLong, boolean enterable) {
		this.x = x;
		this.y = y;
		if (isLong) {
			height = 200;
			img = getImage("/imgs/longpipe.png");
		} else {
			height = 100;
			img = getImage("/imgs/pipe.png");
		}
		width = 30;
		this.enterable = enterable;
		scaleX = 0.7;
		scaleY = 0.7;
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
	public boolean getIsLong() {
		return isLong;
	}
	public boolean getEnterable() {
		return enterable;
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
	public void setIsLong(boolean isLong) {
		this.isLong = isLong;
	}
	public void setEnterable(boolean enterable) {
		this.enterable = enterable;
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
			URL imageURL = Pipe.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
