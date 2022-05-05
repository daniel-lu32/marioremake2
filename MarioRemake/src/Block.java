import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Block extends MarioObject {
	
	// attributes
	private String brickType;
	private boolean hasCoin;
	
	public Block(int x, int y, String brickType, boolean hasCoin) {
		super(x, y);
		height = 40;
		width = 40;
		this.brickType = brickType;
		this.hasCoin = hasCoin;
		if (brickType.equals("Normal")) {
			scaleX = 1.7;
			scaleY = 1.7;
			img = getImage("/imgs/brick.png");
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	// getters
	public String getBrickType() {
		return brickType;
	}
	public boolean getHasCoin() {
		return hasCoin;
	}
	
	// setters
	public void setBrickType(String brickType) {
		this.brickType = brickType;
	}
	public void setHasCoin(boolean hasCoin) {
		this.hasCoin = hasCoin;
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
			URL imageURL = Block.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
