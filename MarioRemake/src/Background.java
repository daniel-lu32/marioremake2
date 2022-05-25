import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background{
	
	// attributes
	private int x, y;
	private Image img; 	
	private AffineTransform tx;
	private double vx, vy;
	private int yAccel = -1;
	private int width;

	// constructor
	public Background(int x, int y) {
		vx = 0;
		vy = 0;
		width = 1800;
		this.x = x;
		this.y = y;
		img = getImage("/imgs/background.png"); 
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				

	} 
	
	public boolean outOfBounds() {
		if (this.getX() > 0 || this.getX() + this.getWidth() < 1200) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean outOfBoundsLeft() {
		if (this.getX() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean outOfBoundsRight() {
		if (this.getX() + this.getWidth() < 1200) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setVX (double newVX) {
		this.vx = newVX;
	}
	
	public void setVY (double newVY) {
		this.vy = newVY;
	}
	
	public void slide(boolean moveLeft, boolean moveRight) {
		if (moveLeft == true) {
			vx = 4;
			this.x += vx;
		}
		else if (moveRight == true) {
			vx = -4;
			this.x += vx;
		}
		else {
			vx = 0;
		}
	}
	
	public void slideVertical(boolean jumping) {
		if (jumping == true) {
			vy = 2;
			this.y += vy;
		}
		else {
			if (y <= 10 && y + 1200 >= 800) {
				vy = -2;
				this.y += vy;
			}
		}
	}
	
	// getters
	public double getVX() {
		return vx;
	}
	
	public double getVY() {
		return vy;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	// setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	// graphics settings
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		update();
		g2.drawImage(img, tx, null);
	}
	private void update() {
		y += vy;
		if (y + 1200 >= 800) {
			y += yAccel;
		}
		tx.setToTranslation(x, y);
		tx.scale(3.0, 3.0);
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(3.0, 3.0);
	}
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}


