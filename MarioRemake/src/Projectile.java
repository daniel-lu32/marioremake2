import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Projectile extends MarioObject {
	
	// Projectile Attributes
	private int vx; // Velocity of the Projectile
	private String type; // Fireball or Iceball
	private boolean hit, onScreen; // Whether or not the Projectile has hit something and whether or not it is on the screen
	
	// Projectile Constructor
	public Projectile(int x, int y, String type) {
		// Call the Superclass Constructor
		super(x, y);
		// Default velocity is set to 0
		vx = 0;
		this.type = type;
		hit = false;
		onScreen = false;
		// Obtain the correct image file depending on whether the Projectile is a fireball or an iceball
		if (type.equals("Fireball")) {
			height = 18;
			width = 32;
			img = getImage("/imgs/fireball.png");
			scaleX = 0.15;
			scaleY = 0.15;
		} else if (type.equals("Iceball")) {
			height = 25;
			width = 25;
			img = getImage("/imgs/iceball.png");
			scaleX = 0.15;
			scaleY = 0.15;
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
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
	public boolean getOnScreen() {
		return onScreen;
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
	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
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
			URL imageURL = Projectile.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}



