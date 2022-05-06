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
	private int vx, vy;
	private int yAccel = -1;

	// constructor
	public Background(int x, int y) {
		vx = 0;
		vy = 0;
		this.x = x;
		this.y = y;
		img = getImage("/imgs/background.png"); 
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				

	} 
	
	public void setVX (int newVX) {
		this.vx = newVX;
	}
	
	public void setVY (int newVY) {
		this.vy = newVY;
	}
	
	public void slide(boolean moveLeft, boolean moveRight) {
		if (moveLeft == true) {
			vx = 3;
			this.x += vx;
		}
		else if (moveRight == true) {
			vx = -3;
			this.x += vx;
		}
		else {
			vx = 0;
		}
	}
	
	public void slideVertical(boolean jumping) {
		if (jumping == true) {
			vy = 6;
			this.y += vy;
		}
		else {
			vy = 0;
		}
	}
	
	// getters
	public int getVX() {
		return vx;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
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


