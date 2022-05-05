import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Flag extends MarioObject {
	
	// attributes
	private int reachHeight;
	private boolean reached;
	
	private int state;
	
	public Flag (int x, int y) {
		super(x, y);
		height = 120;
		width = 55;
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
	public int getReachHeight() {
		return reachHeight;
	}
	public boolean getReached() {
		return reached;
	}
	
	// setters
	public void setReachHeight(int reachHeight) {
		this.reachHeight = reachHeight;
	}
	public void setReached(boolean reached) {
		this.reached = reached;
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
