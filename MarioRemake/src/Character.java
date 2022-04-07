import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Character {
	private int x;
	private int y;
	private int height;
	private int width;
	private Image img;
	private AffineTransform tx;
	private boolean big;
	private boolean small;
	private boolean hasAbility;
	private String ability;
	private double scaleX, scaleY;
	
	public Character(int x, int y) {
		this.x = x;
		this.y = y;
		height = 80;
		width = 25;
		img = getImage("/imgs/mariorun.gif");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y);
		scaleX = 1.0;
		scaleY = 1.0;
		big = false;
		small = false;
		hasAbility = false;
		ability = "";
		
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
			URL imageURL = Character.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
