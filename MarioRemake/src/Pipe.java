import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Pipe extends MarioObject {
	
	// attributes
	private boolean isLong, enterable;
	
	public Pipe(int x, int y, boolean isLong, boolean enterable) {
		super(x, y);
		if (isLong) {
			height = 260;
			img = getImage("/imgs/longpipe.png");
		} else {
			height = 110;
			img = getImage("/imgs/pipe.png");
		}
		width = 115;
		this.enterable = enterable;
		scaleX = 0.5;
		scaleY = 0.5;
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	// getters
	public boolean getIsLong() {
		return isLong;
	}
	public boolean getEnterable() {
		return enterable;
	}
	
	// setters
	public void setIsLong(boolean isLong) {
		this.isLong = isLong;
	}
	public void setEnterable(boolean enterable) {
		this.enterable = enterable;
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
			URL imageURL = Pipe.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
