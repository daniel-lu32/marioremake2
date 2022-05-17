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
	private int coinValue;
	
	public Block(int x, int y, String brickType, boolean hasCoin) {
		super(x, y);
		if (brickType.equals("Normal")) {
			height = 40;
			width = 40;
			this.brickType = brickType;
			this.hasCoin = hasCoin;
			scaleX = 1.7;
			scaleY = 1.7;
			img = getImage("/imgs/brick.png");
		}
		if (brickType.equals("Mystery")) {
			height = 100;
			width = 40;
			scaleX = 1.67;
			scaleY = 1.67;
			hasCoin = true;
			int rnd = (int)(Math.random()*5) + 1;
			coinValue = rnd*100;
			img = getImage("/imgs/mysteryLongFINAL.gif");
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public void mystHit() {
		if (hasCoin == true) {
			img = getImage("/imgs/mysteryBlockHitFINAL.gif");
		}
		hasCoin = false;
	}
	
	// getters
	public String getBrickType() {
		return brickType;
	}
	public boolean getHasCoin() {
		return hasCoin;
	}
	
	public int getCoinValue() {
		return coinValue;
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
