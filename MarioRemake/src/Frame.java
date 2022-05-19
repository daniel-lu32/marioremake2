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
	
	// Static Variables
	public static int vy = 0;
	public static int acceleration = 1;
	public static final int originalPlatform = 580;
	public static int platform = 580;
	
	// Tracker Variables
	private int lives = 7;
	private int coins = 0;
	private int score = 0;
	private int highScore = 0;
	private int time = 1800;
	private int frameTracker = 0;
	private boolean lost = false;
	
	// Collision Tracker Variables
	private boolean onShort = false;
	private boolean onLong = false;
	private boolean onBlock1 = false;
	private boolean onBlock2 = false;
	private boolean onBlock3 = false;
	private boolean onBlock4 = false;
	
	// Objects
	Background background = new Background(0, -435);
	Character mario = new Character(10, originalPlatform);
	
	MarioObject flag = new Flag (880, originalPlatform);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);
	Spikes spikes1 = new Spikes(120, originalPlatform);
	
	Goomba goomba = new Goomba(background.getX() + 550, originalPlatform);
	PowerUp big = new PowerUp(600, originalPlatform, "Big Mushroom");
	PowerUp ice = new PowerUp(700, originalPlatform, "Ice Flower");
	PowerUp fire = new PowerUp(800, originalPlatform, "Fire Flower");
	PowerUp oneup = new PowerUp(900, originalPlatform, "1-UP");

	MarioObject key = new Key(0, 600);
	int keyX = (((Key)key).getRandomX(400, 800));
	int keyY = (((Key)key).getRandomY(900, 1100));
	Door door = new Door(background.getX() + 950, background.getY() + 890);
	PowerUp livesicon = new PowerUp(8, 10, "1-UP");
	Coin coinicon = new Coin(5, 50);

	Block block1 = new Block(background.getX() + 800, background.getY() + 435 + 480, "Normal", false);
	Block block2 = new Block(background.getX() + 840, 480, "Normal", false);
	Block block3 = new Block(background.getX() + 880, background.getY() + 435 + 480, "Normal", false);
	Block block4 = new Block(background.getX() + 920, 480, "Normal", false);
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// Paint the Background
		background.paint(g);
		// Paint Other Objects
		door.paint(g);
		key.paint(g);
		keyDisp.paint(g);
		spikes1.paint(g);
		
		// Update Mario's Y Position
		mario.setY(mario.getY() + vy);
		vy += acceleration;
		
		// Establish a Floor 
		if (mario.getY() >= platform && !(mario.getJumping())) {
			mario.setY(platform);
			vy = 0;
		}
		if (mario.getY() >= platform) {
			mario.setJumping(false);
		}
		
//		// Update the Background
//		if (background.getY() < -440) {
//			background.setY(-440);
//		}
//		if (background.getY() + 1200 >= 765) {
//				background.setVY(-vy * 0.4);
//		} else {
//			background.setVY(0);
//		}

		// Paint Mario
		mario.paint(g);
		
		// Key Mechanics
		g.drawRect(key.getX(), key.getY(), key.getWidth(), key.getHeight());
		key.setX(background.getX() + keyX);
		key.setY(background.getY() + keyY);
				
		// Key Display Mechanics
		if (((Key)key).getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key.getX() && mario.getX() <= key.getX() + key.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key.getY() && mario.getY() <= key.getY() + key.getHeight()) {
					System.out.println("KEY HIT" + (keyDisp.getState()));
					keyDisp.setState(keyDisp.getState() + 1);
					((Key)key).setAvailable(false);
				}
			}
		}
				
		// Door Mechanics
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
		
		// Pipe
		MarioObject shortPipe = new Pipe(background.getX() + 400, background.getY() + 1005, false, false);
		MarioObject longPipe = new Pipe(background.getX() + 600, background.getY() + 865, true, false);	
		shortPipe.setX(background.getX() + 400);
		shortPipe.setY(background.getY() + 1005);
		longPipe.setX(background.getX() + 600);
		longPipe.setY(background.getY() + 865);
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
		
		// Spikes
		spikes1.setX(background.getX() + 120);
		spikes1.setY(background.getY() + 1025);
//		if (mario.getX() + mario.getWidth() >= spikes1.getX() && mario.getX() <= spikes1.getX() + spikes1.getWidth()) {
//			if (mario.getY() + mario.getHeight() >= spikes1.getY() && mario.getY() <= spikes1.getY() + spikes1.getHeight()) {
//				spikes1.setHit(true);
//			}
//		}
//		if (spikes1.getHit() == true) {
//			mario.setState(mario.getState()-1);
//		}
		if (mario.collide(spikes1) && !spikes1.getHit()) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			System.out.println("spikes hit");
			spikes1.setHit(true);
		}
		
		// Prevent Mario from Going Off-Screen
		if (mario.getX() <= 10) {	
			mario.setX(10);
		}
				
		if (mario.getX() >= 1120) {	
			mario.setX(1120);
		}
		
		// Goomba Mechanics
		goomba.setX(goomba.getX() + goomba.getVX());
		goomba.paint(g);
		if (goomba.collide(shortPipe)) {
			goomba.setVX(goomba.getVX() * -1);
			if (goomba.getX() <= shortPipe.getX()) {
				goomba.setX(shortPipe.getX() - goomba.getWidth());
			} else {
				goomba.setX(shortPipe.getX() + shortPipe.getWidth());
			}
		}
		
		if (goomba.collide(longPipe)) {
			goomba.setVX(goomba.getVX() * -1);
			if (goomba.getX() <= longPipe.getX()) {
				goomba.setX(longPipe.getX() - goomba.getWidth());
			} else {
				goomba.setX(longPipe.getX() + longPipe.getWidth());
			}
		}
		
		if (mario.collide(goomba) && !goomba.getHit()) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba.setHit(true);
		}
		
		if (mario.getState() < 0) {
			lives--;
			mario.setState(0);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba.setHit(false);
			resetPosition();
			spikes1.setHit(false);
		}
		
		// PowerUp
		big.setX(big.getX() + big.getVX());
		ice.setX(ice.getX() + big.getVX());
		fire.setX(fire.getX() + big.getVX());
		oneup.setX(oneup.getX() + big.getVX());
		
		// Paint the Power Ups only if they have not been collected yet
		if (!big.getHit()) {
			big.paint(g);
		}
		if (!ice.getHit()) {
			ice.paint(g);
		}
		if (!fire.getHit()) {
			fire.paint(g);
		}
		if (!oneup.getHit()) {
			oneup.paint(g);
		} 
		
		if (mario.collide(big) && !big.getHit()) {
			big.setHit(true);
			if (!mario.getHasAbility()) {
				mario.setState(1);
			}
		}
		if (mario.collide(ice) && !ice.getHit()) {
			ice.setHit(true);
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Ice");
		}
		if (mario.collide(fire) && !fire.getHit()) {
			fire.setHit(true);
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Fire");
		}
		if (mario.collide(oneup) && !oneup.getHit()) {
			oneup.setHit(true);
			lives++;
		}
		
		block1.setX(background.getX() + 800);
		block2.setX(background.getX() + 840);
		block3.setX(background.getX() + 880);
		block4.setX(background.getX() + 920);
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
		
		// Top Text Display (Lives, Score, Coins, and Timer)
		g.setFont(new Font("Unknown", Font.BOLD, 20));
		
		// Check if the game should be ended
		endGame();
		
		// Update the Timer
		frameTracker++;
		if (frameTracker % 35 == 0) {
			time--;
			if (frameTracker % 350 == 0) {
				goomba.setHit(false);
				spikes1.setHit(false);
			}
		}
		// Paint the Lives and Coin Icons
		livesicon.paint(g);
		coinicon.paint(g);
		
		// Paint the Number of Lives Remaining
		g.drawString("" + lives, 60, 35);
		if (lives <= 0) {
			lost = true;
		}
		
		// Paint the Number of Coins Obtained
		if (coins < 10) {
			g.drawString("0" + coins, 60, 85);
		} else {
			g.drawString("" + coins, 60, 85);
		}
		if (coins > 99) {
			lives++;
			coins = 0;
		}
		// Paint the Score and High Score
		g.drawString("Score: " + score, 200, 30);
		g.drawString("High Score: " + score, 200, 80);
		if (score > highScore) {
			highScore = score;
		}
		
		if (time <= 0) {
			time = 0;
		}
		if (time <= 60) {
			g.setColor(Color.RED);
		}
		if (time % 60 == 0 || time % 60 < 10) {
			g.drawString("" + time / 60 + ":" + "0" + time % 60, 1100, 30);		//CHANGED VALUES TO FIT KEYDISP
		} else {
			g.drawString("" + time / 60 + ":" + time % 60, 1100, 30);			//VALUES
		}
		if (lost) {
			g.drawString("Game Over. Press R to Restart!", 400, 200);
		}
		
	} // end of paint method
	
	public void resetPosition() {
		mario.setX(10);
		mario.setY(originalPlatform);
		background.setX(0);
		background.setY(-435);
	}
	
	public void reset() {
		lives = 7;
		coins = 0;
		score = 0;
		time = 1800;
		frameTracker = 0;
		platform = originalPlatform;
		lost = false;
	}
	public void endGame() {
		if (lost) {
			onShort = false;
			onLong = false;
			onBlock1 = false;
			onBlock2 = false;
			onBlock3 = false;
			onBlock4 = false;
			goomba.setHit(false);
			spikes1.setHit(false);
			big.setHit(false);
			ice.setHit(false);
			fire.setHit(false);
			oneup.setHit(false);
			background = new Background(0, -435);
			mario = new Character(10, originalPlatform);
			flag = new Flag (880, originalPlatform);
			keyDisp = new KeyDisplay(450, 20);
			spikes1 = new Spikes(120, originalPlatform);
			goomba = new Goomba(550, originalPlatform);
			big = new PowerUp(600, originalPlatform, "Big Mushroom");
			ice = new PowerUp(700, originalPlatform, "Ice Flower");
			fire = new PowerUp(800, originalPlatform, "Fire Flower");
			oneup = new PowerUp(900, originalPlatform, "1-UP");
			key = new Key(0, 600);
			keyX = (((Key)key).getRandomX(400, 800));
			keyY = (((Key)key).getRandomY(900, 1100));
			door = new Door(background.getX() + 950, background.getY() + 890);
		}
	}
	
	// Frame Class Runner
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	// Frame Constructor
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
		if (arg0.getKeyCode() == 39 && !lost) {
			mario.setImage(false, true);
			mario.rightPressed(true);
			if (background.getX() + 1800 >= 1200) {		 
				background.slide(false, true);	
			}
		}
		else {											
			background.slide(false, false);
		}
		if (arg0.getKeyCode() == 37 && !lost) {
			mario.setImage(true, false);
			mario.leftPressed(true);
			if (background.getX() <= -10) {				
				background.slide(true, false);	
			}
		}
		else {											
			background.slide(false, false);
		}
		if (arg0.getKeyCode() == 38 && !mario.getJumping() && !lost && mario.getY() + mario.getHeight() >= platform) {
			mario.setJumping(true);
//			if (mario.getJumping() == true) {
//				background.slideVertical(true);
//			}
//			else {
//				background.slideVertical(false);
//			}
			vy = -20;
		}
		
		if (arg0.getKeyCode() == 10) {
			if (door.getInRange() == true) {
				door.setStateLocked(false);
				door.chooseImage();
			}
		}
		if (arg0.getKeyCode() == 82 && lost) {
			reset();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == 39 && !lost) {
			mario.setImage(false, false);
			mario.rightPressed(false);
		}
		if (arg0.getKeyCode() == 37 && !lost) {
			mario.setImage(false, false);
			mario.leftPressed(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}



