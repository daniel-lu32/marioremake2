import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Spikes extends MarioObject {
	
	// attributes
	private boolean hit;
	
	public Spikes(int x, int y) {
		super(x, y);
		height = 32;
		width = 85;
		scaleX = 1.4;
		scaleY = 1.4;
		hit = false;
		img = getImage("/imgs/spikes.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	// getters
	public boolean getHit() {
		return hit;
	}
	
	// setters
	public void setHit(boolean param) {
		hit = param;
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
