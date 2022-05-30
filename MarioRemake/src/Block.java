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
	private boolean available;
	
	// constructor
	/* If a mystery block needs to be created, then the input brickType is "Mystery" and hasCoin is true, so the instance variables
	 * are set to the according values. Mystery blocks have another variable called available which determines if the mystery block 
	 * can be hit or has been already hit. Available is initially set to true and the image is set to mystery block. 
	 * If a normal block needs to be created, the instance variables would be set to "Normal" and false for hasCoins. The image would be set to brick.
	 */
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
	
	/* This is the method that determines under what conditions each image should be shown. 
	 * If brickType is "Mystery" and hasCoin and available are true, then the image is set to the default
	 * mystery block gif. 
	 * If brickType is mystery, hasCoin is false, and available is true, this means the coin in the block has already been 
	 * collected, so available is set to false and the image is set to the gif of mystery block being hit. Since this image 
	 * has a different height compared to the default gif, the height and y value have to be changed.
	 * If brickType is "Normal", then the image is set to a brick image.
	 */
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
	
	/* This image is called when Mario hits the mystery block. It makes hasCoins false and calls the chooseImage method
	 * so it sets the image to mystery block hit gif.
	 */
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
	
	// setters
	public void setBrickType(String brickType) {
		this.brickType = brickType;
	}
	public void setHasCoin(boolean hasCoin) {
		this.hasCoin = hasCoin;
	}
	public void setAvailable(boolean avail) {
		available = avail;
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
