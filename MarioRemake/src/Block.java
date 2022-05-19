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
	private boolean available;
	
	public Block(int x, int y, String brickType, boolean hasCoin) {
		super(x, y);
		if (brickType.equals("Mystery")) {
			this.brickType = "Mystery";
			this.hasCoin = true;
			available = true;
			height = 40;
			width = 40;
			scaleX = 1.67;
			scaleY = 1.67;
			int rnd = (int)(Math.random()*5) + 1;
			coinValue = rnd*100;
			img = getImage("/imgs/mysteryBlockFINAL.gif");
		}
		else {
			height = 40;
			width = 40;
			available = false;
			this.brickType = "Normal";
			this.hasCoin = false;
			scaleX = 1.7;
			scaleY = 1.7;
			img = getImage("/imgs/brick.png");
		}
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	public void chooseImage() {
		if (brickType.equals("Mystery") && hasCoin == true && available == true) {
			img = getImage("/imgs/mysteryBlockFINAL.gif");
		}
		if (brickType.equals("Mystery") && hasCoin == false && available == true) {
			this.setY(this.y - 60);
			this.setHeight(100);
			hasCoin = false;
			available = false;
			img = getImage("/imgs/mysteryBlockHitFINAL.gif");
		}
		if (brickType.equals("Normal")) {
			img = getImage("/imgs/brick.png");
			this.setHeight(40);
		}
	}
	
	public void mystHit() {
		hasCoin = false;
		chooseImage();
	}
	
	// getters
	public String getBrickType() {
		return brickType;
	}
	public boolean getAvailable() {
		return available;
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
		//chooseImage();
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
