import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class KeyDisplay extends MarioObject {
	
	// attributes
	private int state;
	
	public KeyDisplay(int x, int y) {
		super(x, y);
		height = 48;
		width = 36;
		scaleX = 0.3;
		scaleY = 0.3;
		state = getState();
		img = getImage("/imgs/0keys.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	// getters
	public int getState() {
		return state;
	}
	
	// setters
	public void setState(int stateParam) {
		this.state = stateParam;
		if (state > 3) {
			state = 3;
		}
		chooseImage();
	}
	
	public void chooseImage() {
		if (this.state == 0) {
			img = getImage("/imgs/0keys.png");
		}
		else if (this.state == 1) {
			img = getImage("/imgs/1keys.png");
		}
		else if (this.state == 2) {
			img = getImage("/imgs/2keys.png");
		}
		else {
			img = getImage("/imgs/3keys.png");
		}
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
		tx.scale(0.3, 0.3);
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
