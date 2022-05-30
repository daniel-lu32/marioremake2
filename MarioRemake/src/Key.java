import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Key extends MarioObject {
	
	// attributes
	private boolean available;
	private int spawnX;
	private int spawnY;
	
	// constructor
	public Key(int x, int y) {
		super(x, y);
		spawnX = x;
		spawnY = y;
		available = true;
		height = 48;
		width = 36;
		scaleX = 0.16;
		scaleY = 0.16;
		img = getImage("/imgs/key2.gif");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
	}
	
	/*
	 * SpawnX and SpawnY are the original x and y values of the keys to which they should return when the game is over or reset
	 */
	public int getSpawnX() {
		return spawnX;
	}
	
	public int getSpawnY() {
		return spawnY;
	}
	
	/*
	 * These random methods aren't used anymore but used to form a random X and Y value for the keys
	 */
	public int getRandomX(int min, int max) {
		int x = (int)(Math.random()*(min-max+1)) + min;
		return x;
	}
	
	public int getRandomY(int min, int max) {
		int y = (int)(Math.random()*(min-max+1)) + min;
		return y;
	}
	
	// getters
	public boolean getAvailable() {
		return available;
	}
	
	// setters
	public void setAvailable(boolean boolParam) {
		this.available = boolParam;
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
			URL imageURL = Key.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
