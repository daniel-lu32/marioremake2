import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Goomba extends MarioObject {
	
	// attributes
	private int vx;
	private boolean hit;
	
	public Goomba(int x, int y) {
		super(x, y);
		height = 36;
		width = 32;
		vx = -1;
		hit = false;
		scaleX = 0.072;
		scaleY = 0.072;
		img = getImage("/imgs/goombagif2.gif");
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
	public boolean getHit() {
		return hit;
	}
	
	// setters
	public void setVX(int vx) {
		this.vx = vx;
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
			URL imageURL = Goomba.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
