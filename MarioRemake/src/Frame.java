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

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	/**
	 * Game header
	 * 
	 */
	
	// variables and trackers
	int vx = 0;
	public static int vy = 0;
	int acceleration = 1;
	public static final int originalPlatform = 580;
	public static int platform = 580;
	private boolean onShort = false;
	private boolean onLong = false;
	private boolean onBlock1 = false;
	private boolean onBlock2 = false;
	private boolean onBlock3 = false;
	private boolean onBlock4 = false;
	private int lives = 7;
	private int score = 0;
	private int time = 3600;
	private int frameTracker;
	int blockmaker = 100;

	boolean goombaCollided = false;
	boolean bigCollided = false;
	boolean iceCollided = false;
	boolean fireCollided = false;
	boolean oneupCollided = false;
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
	Background background = new Background(0, -435);	 //** EDITED COORDINATES **//
	Character mario = new Character(10, originalPlatform);  			 //** EDITED COORDINATES **//
	MarioObject flag = new Flag (880, 585);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);		//ADDED//				//was created later in the code, but all objects should be made earlier?//
	Spikes spikes1 = new Spikes(120, 585);				//ADDED//
	MarioObject goomba = new Goomba(500, originalPlatform);
	MarioObject big = new PowerUp(600, originalPlatform, "Big Mushroom");
	MarioObject ice = new PowerUp(700, originalPlatform, "Ice Flower");
	MarioObject fire = new PowerUp(800, originalPlatform, "Fire Flower");
	MarioObject oneup = new PowerUp(900, originalPlatform, "1-UP");

	MarioObject key = new Key(0, 600);					//ADDED//
	int keyX = (((Key)key).getRandomX(400, 800));		//ADDED//
	int keyY = (((Key)key).getRandomY(900, 1100));		//ADDED//
	Door door = new Door(background.getX() + 950, background.getY() + 890);		//ADDED//
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// paint the background
		background.paint(g);


		door.paint(g);		//ADDED//
		goomba.paint(g);
		key.paint(g);
		keyDisp.paint(g);	//ADDED
		spikes1.paint(g);	//ADDED//
		
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
		//update background							//*ADDED
		if (background.getY() < -440) {				//
			background.setY(-440);					//
		}
		if (background.getY() + 1200 >= 765) {		//
				background.setVY(-vy * 0.4);		//
		}											//
		else {										//
			background.setVY(0);					//
		}											//*

		// paint mario
		mario.paint(g);
		
		// Key	-- ADDED A LOT -- COPY OVER
				g.drawRect(key.getX(), key.getY(), key.getWidth(), key.getHeight());
				key.setX(background.getX() + keyX);
				key.setY(background.getY() + keyY);
				
				// Key Display --ADDED -- COPY OVER
				if (((Key)key).getAvailable() == true) {
					if (mario.getX() + mario.getWidth() >= key.getX() && mario.getX() <= key.getX() + key.getWidth()) {
						if (mario.getY() + mario.getHeight() >= key.getY() && mario.getY() <= key.getY() + key.getHeight()) {
							System.out.println("KEY HIT" + (keyDisp.getState()));
							keyDisp.setState(keyDisp.getState() + 1);
							((Key)key).setAvailable(false);
						}
					}
				}
				
				// Door -- ADDED -- COPY OVER (maybe not yet if we are changing the door though)
				door.setX(background.getX() + 950);
				door.setY(background.getY() + 902);
				Font keyFont = new Font("keyFont", Font.BOLD, 14);
				if (mario.getX() + mario.getWidth() >= door.getX() - 30 && mario.getX() <= door.getX() + door.getWidth() + 30) {
					if (mario.getY() + mario.getHeight() >= door.getY() - 30 && mario.getY() <= door.getY() + door.getHeight() + 30) {
						System.out.println("in door range");	//remove later
						if (keyDisp.getState() <= 2) {
							g.setFont(keyFont);
							g.drawString(("collect " + (3 - keyDisp.getState()) + " more keys to open"), door.getX() - 55, door.getY() - 30);
						}
						if (keyDisp.getState() >= 3) {
							door.setInRange(true);
							g.setFont(keyFont);
							g.drawString(("press enter to open"), door.getX() - 30, door.getY() - 30);
						}
					}
				}
		
		// moving mario hitbox
		g.setColor(black);
		g.drawRect(mario.getX(), mario.getY(), mario.getWidth(), mario.getHeight());
		
		// Pipe
		MarioObject shortPipe = new Pipe(background.getX() + 400, background.getY() + 965, false, false);
		MarioObject longPipe = new Pipe(background.getX() + 600, background.getY() + 835, true, false);	
		shortPipe.setX(background.getX() + 400);
		shortPipe.setY(background.getY() + 965);
		longPipe.setX(background.getX() + 600);
		longPipe.setY(background.getY() + 835);
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
		
		// Spikes -- ADDED -- COPY OVER
				spikes1.setX(background.getX() + 120);
				spikes1.setY(background.getY() + 1025);
				if (mario.getX() + mario.getWidth() >= spikes1.getX() && mario.getX() <= spikes1.getX() + spikes1.getWidth()) {
					if (mario.getY() + mario.getHeight() >= spikes1.getY() && mario.getY() <= spikes1.getY() + spikes1.getHeight()) {
						System.out.println("spikes hit");
						spikes1.setHit(true);
					}
				}
				if (spikes1.getHit() == true) {
					mario.setState(mario.getState()-1);
//					//mario character change image to flashing one with a timer
//					mario.setX(mario.getX() - (spikes1.getWidth() + 40));
//					mario.setY(mario.getY() - (spikes1.getHeight() + 20));
				}
				// spikes up to here
				if (mario.getX() <= 10) {	
					mario.setX(10);
				}
				
				if (mario.getX() >= 1120) {	
					mario.setX(1120);
				}
		// Goomba collision
		goomba.setX(goomba.getX() - 1);
		goomba.paint(g);
		if (mario.collide(goomba) && !goombaCollided) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goombaCollided = true;
		}
		if (goomba.getX() < -50) {
			goomba.setX(500);
			goombaCollided = false;
		}
		
		if (mario.getState() < 0) {
			lives--;
			mario.setState(0);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goombaCollided = false;
			resetPosition();
			spikes1.setHit(false);
		}
		
		// PowerUp
		big.setX(big.getX() - 1);
		ice.setX(ice.getX() - 1);
		fire.setX(fire.getX() - 1);
		oneup.setX(oneup.getX() - 1);
		if (!bigCollided) {
			big.paint(g);
		}
		if (!iceCollided) {
			ice.paint(g);
		}
		if (!fireCollided) {
			fire.paint(g);
		}
		if (!oneupCollided) {
			oneup.paint(g);
		}
		if (mario.collide(big) && !bigCollided) {
			bigCollided = true;
			if (!mario.getHasAbility()) {
				mario.setState(1);
			}
		}
		if (mario.collide(ice) && !iceCollided) {
			iceCollided = true;
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Ice");
		}
		if (mario.collide(fire) && !fireCollided) {
			fireCollided = true;
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Fire");
		}
		if (mario.collide(oneup) && !oneupCollided) {
			oneupCollided = true;
			lives++;
		}
		
		Block block1 = new Block(800, 550, "Normal", false);
		Block block2 = new Block(840, 550, "Normal", false);
		Block block3 = new Block(880, 550, "Normal", false);
		Block block4 = new Block(920, 550, "Normal", false);
		block1.paint(g);
		block2.paint(g);
		block3.paint(g);
		block4.paint(g);
		
		onShort = mario.aboveObject(shortPipe, onShort);
		onLong = mario.aboveObject(longPipe, onLong);
		onBlock1 = mario.aboveObject(block1, onBlock1);
		onBlock2 = mario.aboveObject(block2, onBlock2);
		onBlock3 = mario.aboveObject(block3, onBlock3);
		onBlock4 = mario.aboveObject(block4, onBlock4);
		if (!onShort && !onLong && !onBlock1 && !onBlock2 && !onBlock3 && !onBlock4) {
			platform = originalPlatform;
		}
		if (mario.hittingObjectFromBelow(block1) || mario.hittingObjectFromBelow(block2) || mario.hittingObjectFromBelow(block3) || mario.hittingObjectFromBelow(block4)) {
			vy = 4;
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
			g.drawString("Time Remaining: " + time / 60 + ":" + "0" + time % 60, 900, 30);		//CHANGED VALUES TO FIT KEYDISP
		} else {
			g.drawString("Time Remaining: " + time / 60 + ":" + time % 60, 900, 30);			//VALUES
		}
		
		
	} // end of paint method
	
	public void resetPosition() {
		mario.setX(10);
		mario.setY(originalPlatform);
		background.setX(0);
		background.setY(-435);
	}
	
	// creates a FrameTester object, makes class runnable
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	// Frame constructor
	public Frame() {
		JFrame f = new JFrame("Mario Remake");
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
			if (background.getX() + 1800 >= 1200) {		 
				background.slide(false, true);	
			}
		}
		else {											
			background.slide(false, false);
		}
		if (arg0.getKeyCode() == 37) {
			mario.setImage(true, false);
			mario.leftPressed(true);
			if (background.getX() <= -10) {				
				background.slide(true, false);	
			}
		}
		else {											
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
		
		if (arg0.getKeyCode() == 10) {		//ADDED STATEMENT
			if (door.getInRange() == true) {
				door.setStateLocked(false);
				door.chooseImage();
			}
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



