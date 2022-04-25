import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Flag {
	
	// attributes
	private int x, y, height, width, reachHeight;
	private boolean reached;
	private double scaleX, scaleY;
	private Image img;
	private AffineTransform tx;
	
	private int state;
	
	public Flag (int x, int y) {
		this.x = x;
		this.y = y;
		height = 200;
		width = 20;
		reachHeight = 0;
		reached = false;
		scaleX = 1.5;
		scaleY = 1.5;
		state = 0;
		img = getImage("/imgs/flag gif.gif");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public void setImage() {
		if (state == 0) {
			img = getImage("/imgs/flag gif.gif");
		}
		else if (state == 1){
			img = getImage("/imgs/flag raise.gif");
			state = 2;
		}
		else {
			img = getImage("/imgs/flag raised.gif");
		}
	}
	
	public void setState(int stateParam) {
		state = stateParam;
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
	public int getReachHeight() {
		return reachHeight;
	}
	public boolean getReacher() {
		return reached;
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
	public void setReachHeight(int reachHeight) {
		this.reachHeight = reachHeight;
	}
	public void setReached(boolean reached) {
		this.reached = reached;
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
		tx.scale(1.5, 1.5);
	}
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Flag.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
