import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;

public class FrameTester extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	/**
	 * Game header
	 * 
	 */
	
	//comment
	int vx = 0;
	int vy = 0;
	int acceleration = 1;
	int platform = 665;
	boolean onShort = false;
	boolean onLong = false;
	
	// colors and fonts
	Color red = new Color(210, 20, 4);
	Color yellow = new Color(252, 226, 5);
	Color green = new Color(3, 172, 19);
	Color brown = new Color(92, 51, 23);
	Color black = new Color(0, 0, 0);
	Color purple = new Color(106, 13, 173);
	Color white = new Color(255, 255, 255);
	Color cyan = new Color(0, 255, 255);
	Font courierTiny = new Font("Courier", Font.BOLD, 15);
	Font courierSmall = new Font("Courier", Font.BOLD, 40);
	Font courierMedium = new Font("Courier", Font.BOLD, 80);
	Font courierBig = new Font("Courier", Font.BOLD, 120);
	
	// create the background
	Background background = new Background(0, 0);

	// variables and trackers

	Character mario = new Character(10, 665);
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// paint the background
		background.paint(g);
		
		// paint mario
		mario.paint(g);
		
		// update mario's position
		mario.setX(mario.getX() + vx);
		mario.setY(mario.getY() + vy);
		vy += acceleration;
		
		// establishes a floor 
		if (mario.getY() >= platform && !(mario.getJumping())) {
			mario.setY(platform);
			vy = 0;
		}
		if (mario.getY() >= platform) {
			mario.setJumping(false);
		}
		
		/* Hitboxes */
		g.setColor(Color.black);
		
		// Mario
		g.drawRect(10, 665, 26, 39);
		
		// Goomba
		Goomba goomba = new Goomba(100, 100);
		goomba.paint(g);
		g.drawRect(100, 100, 32, 36);
		
		// PowerUp
		PowerUp big = new PowerUp(200, 100, "Big Mushroom");
		PowerUp ice = new PowerUp(300, 100, "Ice Flower");
		PowerUp fire = new PowerUp(400, 100, "Fire Flower");
		PowerUp oneup = new PowerUp(500, 100, "1-UP");
		big.paint(g);
		ice.paint(g);
		fire.paint(g);
		oneup.paint(g);
		g.drawRect(200, 100, 36, 36); 
		g.drawRect(300, 100, 32, 36);
		g.drawRect(400, 100, 34, 36);
		g.drawRect(500, 100, 36, 36);
		
		// Key
		Key key = new Key(600, 100);
		key.paint(g);
		g.drawRect(600, 100, 36, 48);
		
		// Block
		Block normalBlock = new Block(700, 100, "Normal", false);
		normalBlock.paint(g);
		g.drawRect(700, 100, 40, 40);
		
		// Pipe
		Pipe shortPipe = new Pipe(400, 660, false, false);
		Pipe longPipe = new Pipe(600, 510, true, false);
		shortPipe.paint(g);
		longPipe.paint(g);
		g.drawRect(400, 660, 115, 110);
		g.drawRect(600, 510, 115, 260);
		
		// Short Pipe Collision
		
		// First, test if Mario is within the X range of the Pipe.
		if (mario.getX() + mario.getWidth() >= shortPipe.getX() && mario.getX() <= shortPipe.getX() + shortPipe.getWidth()) {
			
			// Next, test if Mario is above the Y value of the Pipe.
			if (mario.getY() + mario.getHeight() < shortPipe.getY() && mario.getJumping()) {
				onShort = true;
				if (onShort) {
					platform = shortPipe.getY() - mario.getHeight();
				}
			}
			// Otherwise, set Mario's X position so that the Pipe is a barrier.
			if (platform == 665 && !(mario.getY() + mario.getHeight() < shortPipe.getY())) {
				if (mario.getX() <= shortPipe.getX()) {
					mario.setX(shortPipe.getX() - mario.getWidth());
				} else {
					mario.setX(shortPipe.getX() + shortPipe.getWidth());
				}
			}
		}
		if (!(mario.getX() + mario.getWidth() >= shortPipe.getX() && mario.getX() <= shortPipe.getX() + shortPipe.getWidth())) {
			onShort = false;
		}
		
		// Long Pipe Collision
		if (mario.getX() + mario.getWidth() >= longPipe.getX() && mario.getX() <= longPipe.getX() + longPipe.getWidth()) {
			if (mario.getY() + mario.getHeight() < longPipe.getY() && mario.getJumping()) {
				onLong = true;
				if (onLong) {
					platform = longPipe.getY() - mario.getHeight();
				}
			}
			if (platform == 665 && !(mario.getY() + mario.getHeight() < longPipe.getY())) {
				if (mario.getX() <= longPipe.getX()) {
					mario.setX(longPipe.getX() - mario.getWidth());
				} else {
					mario.setX(longPipe.getX() + longPipe.getWidth());
				}
			}
		}
		if (!(mario.getX() + mario.getWidth() >= longPipe.getX() && mario.getX() <= longPipe.getX() + longPipe.getWidth())) {
			onLong = false;
		}
		
		if (!onShort && !onLong) {
			platform = 665;
		}
		
	}
	
	// creates a Frame object, makes class runnable
	public static void main(String[] arg) {
		FrameTester f = new FrameTester();
	}
	
	// Frame constructor
	public FrameTester() {
		JFrame f = new JFrame("Mario Remake Tester");
		f.setSize(new Dimension(1200, 800));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(true);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 39) {
			mario.setImage(false, true);
			mario.rightPressed(true);
		}
		if (arg0.getKeyCode() == 37) {
			mario.setImage(true, false);
			mario.leftPressed(true);
		}
		if (arg0.getKeyCode() == 38 && !mario.getJumping()) {
			mario.setJumping(true);
			vy = -20;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == 39) {
			mario.setImage(false, false);
			mario.rightPressed(false);
		}
		if (arg0.getKeyCode() == 37) {
			mario.setImage(false, false);
			mario.leftPressed(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}

