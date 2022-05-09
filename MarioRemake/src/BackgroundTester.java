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

public class BackgroundTester extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	/**
	 * Game header
	 * 
	 */
	
	// variables and trackers
	int vx = 0;
	int vy = 0;
	int acceleration = 1;
	int platform = 575;
	boolean onShort = false;
	boolean onLong = false;
	private int lives = 7;
	private int score = 0;
	private int time = 3600;
	private int frameTracker;
	
	// colors and fonts
	Color red = new Color(210, 20, 4);
	Color yellow = new Color(252, 226, 5);
	Color green = new Color(3, 172, 19);
	Color brown = new Color(92, 51, 23);
	Color black = new Color(0, 0, 0);
	Color purple = new Color(106, 13, 173);
	Color white = new Color(255, 255, 255);
	Color cyan = new Color(0, 255, 255);
	
	// create the background and character
	Background background = new Background(0, -440);	 //** EDITED COORDINATES **//
	Character mario = new Character(10, 575);  			 //** EDITED COORDINATES **//
	MarioObject flag = new Flag (880, 585);
	MarioObject goomba2 = new Goomba(500, 665);
	
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// paint the background
		background.paint(g);
		
		// paint mario
		mario.paint(g);
		
		// update mario's position
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
		
		//update background
		if (background.getY() <= -10 && background.getY() + 1200 >= 758) {
				background.setVY(-vy);
		}
		else {
			background.setVY(0);
		}

		
		/* Hitboxes */
		g.setColor(Color.black);
		
		// Mario
		g.drawRect(10, 665, 26, 39);
		g.drawRect(mario.getX(), mario.getY(), 26, 39);		 //** CHANGED TO FOLLOW MARIO **//
		
		// Goomba
		Goomba goomba = new Goomba(100, 100);
		goomba.paint(g);
		g.drawRect(100, 100, 32, 36);
		
		// PowerUp
		MarioObject big = new PowerUp(200, 100, "Big Mushroom");
		MarioObject ice = new PowerUp(300, 100, "Ice Flower");
		MarioObject fire = new PowerUp(400, 100, "Fire Flower");
		MarioObject oneup = new PowerUp(500, 100, "1-UP");
		big.paint(g);
		ice.paint(g);
		fire.paint(g);
		oneup.paint(g);
		g.drawRect(200, 100, 36, 36); 
		g.drawRect(300, 100, 32, 36);
		g.drawRect(400, 100, 34, 36);
		g.drawRect(500, 100, 36, 36);
		
		// Key
		MarioObject key = new Key(background.getX() + 200, background.getY() + 990);
		key.paint(g);
		g.drawRect(600, 100, 36, 48);
		
		// Block
		MarioObject normalBlock = new Block(700, 100, "Normal", false);
		normalBlock.paint(g);
		g.drawRect(700, 100, 40, 40);
		
		// Pipe
		MarioObject shortPipe = new Pipe(background.getX() + 400, 530, false, false);		//changed x so pipe moves with background, fix y
		MarioObject longPipe = new Pipe(background.getX() + 600, 400, true, false);			//changed x so pipe moves with background, fix y
		shortPipe.paint(g);
		longPipe.paint(g);
		g.drawRect(400, 660, 115, 110);
		g.drawRect(600, 510, 115, 260);
		
		// Flag
		flag.paint(g);
		flag.setImage();
		g.drawRect(flag.getX(), flag.getY(), 55, 120);	
		if (mario.getX() + mario.getWidth() >= flag.getX() && mario.getX() <= flag.getX() + flag.getWidth()) {
			if (mario.getY() + mario.getHeight() >= flag.getY() && mario.getY() <= flag.getY() + flag.getHeight()) {
				flag.setState(2);
			}
		}
		if (mario.getX() <= 10) {	 //** ADDED TO PREVENT GOING OFFSCREEN **//
			mario.setX(10);
		}
		
		if (mario.getX() >= 1120) {	 //** ADDED TO PREVENT GOING OFFSCREEN **//
			mario.setX(1120);
		}
		// Short Pipe Collision
		
		// First, test if Mario is within the X range of the Pipe.
		if (mario.getX() + mario.getWidth() >= shortPipe.getX() && mario.getX() <= shortPipe.getX() + shortPipe.getWidth()) {
			
			// Next, test if Mario is above the Y value of the Pipe.
			if (mario.getY() + mario.getHeight() < shortPipe.getY()) {
				onShort = true;
				if (onShort) {
					platform = shortPipe.getY() - mario.getHeight();
				}
			}
			// Otherwise, set Mario's X position so that the Pipe is a barrier.
			if (platform == 575 && !(mario.getY() + mario.getHeight() < shortPipe.getY())) {
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
			if (mario.getY() + mario.getHeight() < longPipe.getY()) {
				onLong = true;
				if (onLong) {
					platform = longPipe.getY() - mario.getHeight();
				}
			}
			if (platform == 575 && !(mario.getY() + mario.getHeight() < longPipe.getY())) {
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
			platform = 575;
		}
		
		// Goomba collision
		goomba2.setX(goomba2.getX() - 1);
		goomba2.paint(g);
		if (mario.collide(goomba2)) {
			time = 0;
		}
		
		
		// Top Text Display (Lives, Score, and Timer)
		frameTracker++;
		if (frameTracker % 35 == 0) {
			time--;
		}
		g.setFont(new Font("Unknown", Font.BOLD, 20));
		g.drawString("Lives: " + lives, 10, 30);
		g.drawString("Score: " + score, 310, 30);
		if (time <= 0) {
			time = 0;
		}
		if (time % 60 == 0 || time < 10) {
			g.drawString("Time Remaining: " + time / 60 + ":" + "0" + time % 60, 610, 30);
		} else {
			g.drawString("Time Remaining: " + time / 60 + ":" + time % 60, 610, 30);
		}
		
		
	} // end of paint method
	
	// creates a FrameTester object, makes class runnable
	public static void main(String[] arg) {
		BackgroundTester f = new BackgroundTester();
	}
	
	// Frame constructor
	public BackgroundTester() {
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
			if (background.getX() + 1800 >= 1200) {		 //** ADDED IF **//
				background.slide(false, true);	
			}
		}
		else {											//** ADDED ELSE **//
			background.slide(false, false);
		}
		if (arg0.getKeyCode() == 37) {
			mario.setImage(true, false);
			mario.leftPressed(true);
			if (background.getX() <= -10) {				//** ADDED IF **//
				background.slide(true, false);	
			}
		}
		else {											//** ADDED ELSE **//
			background.slide(false, false);
		}
		if (arg0.getKeyCode() == 38 && !mario.getJumping()) {
			mario.setJumping(true);
			if (mario.getJumping() == true) {
				background.slideVertical(true);
			}
			else {
				background.slideVertical(false);
			}
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

