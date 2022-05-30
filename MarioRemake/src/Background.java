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
	
	/* if there is white space showing between the background and the screen (if the left side of the background has an X value greater than 0 or if the
	 * right side has an X less than the screen display width 1200), then it returns true. If not, returns false.
	 */
	public boolean outOfBounds() {
		if (this.getX() > 0 || this.getX() + this.getWidth() < 1200) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* 
	 * If the background is showing white space on the left side (using the same logic as above), returns true. Returns false otherwise.
	 */
	public boolean outOfBoundsLeft() {
		if (this.getX() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* 
	 * If the background is showing white space on the right side (using the same logic as above), returns true. Returns false otherwise.
	 */
	public boolean outOfBoundsRight() {
		if (this.getX() + this.getWidth() < 1200) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* 
	 * Variable vx is used as the horizontal velocity for the background. SetVX sets the vx value to the parameter inputted
	 */
	public void setVX (double newVX) {
		this.vx = newVX;
	}
	
	/* Variable vy is used as the vertical velocity for the background. SetVY sets the vy value to the parameter inputted. vy is not used anymore 
	 * as we are no longer scrolling the backgorund vertically
	 */
	public void setVY (double newVY) {
		this.vy = newVY;
	}
	
	/* The slide method is what scrolls the backgound horizontally when the Mario character moves. Two booleans are inputted for if Mario is 
	 * running left (when left arrow key pressed) or running right (right arrow pressed). If Mario is running left, then the background needs
	 * to scroll to the opposite direction to make it seem like Mario is travelling across the background, so the vx is set to a positive value of
	 * 4, then added to the current X value of background. When Mario is running right, the background should move left, so the vx is set to -4. If
	 * neither running left or right is true, then the vx is set to 0 so the background doesn't move.
	 */
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
	
	/* This method was used for vertical background scrolling and is no longer used in the code.
	 * 
	 */
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


