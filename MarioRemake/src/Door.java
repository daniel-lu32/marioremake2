import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Door extends MarioObject {
	
	// attributes
	private boolean stateLocked;
	private boolean inRange;
	
	public Door(int x, int y) {
		super(x, y);
		stateLocked = true;
		inRange = false;
		height = 154;
		width = 81;
		scaleX = 0.6;
		scaleY = 0.6;
		img = getImage("/imgs/doorLocked.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public void chooseImage() {
		if (this.stateLocked == true) {
			img = getImage("/imgs/doorLocked.png");
		}
		else {
			img = getImage("/imgs/doorUnlocked.png");
		}
	}
	
	// getters
	public boolean getStateLocked() {
		return stateLocked;
	}
	
	public boolean getInRange() {
		return inRange;
	}
	
	// setters
	public void setStateLocked(boolean boolParam) {
		this.stateLocked = boolParam;
		chooseImage();
	}
	
	public void setInRange(boolean boolParam) {
		this.inRange = boolParam;;
	}
	
	// graphics settings
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		update();
		g2.drawImage(img, tx, null);
	}
	private void update() {
		chooseImage();
		tx.setToTranslation(x, y);
		tx.scale(scaleX, scaleY);
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(0.75, 0.75);
	}
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Key.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
