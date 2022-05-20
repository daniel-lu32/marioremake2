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
	
	// Static Variables
	public static int vy = 0;
	public static int acceleration = 1;
	public static final int originalPlatform = 580;
	public static int platform = 580;
	
	// Tracker Variables
	private int lives = 7;
	private int score = 0;
	private int time = 1800;
	private int frameTracker = 0;
	
	// Collision Tracker Variables
	private boolean onShort = false;
	private boolean onLong = false;
	private boolean onBlock1 = false;
	private boolean onBlock2 = false;
	private boolean onBlock3 = false;
	private boolean onBlock4 = false;
	private boolean onMystBlock1 = false;
	boolean goombaCollided = false;
	boolean bigCollided = false;
	boolean iceCollided = false;
	boolean fireCollided = false;
	boolean oneupCollided = false;
	
	// Objects
	Background background = new Background(0, -435);
	Character mario = new Character(10, originalPlatform);
	
	MarioObject flag = new Flag (880, originalPlatform);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);
	Spikes spikes1 = new Spikes(120, originalPlatform);
	
	MarioObject goomba = new Goomba(500, originalPlatform);
	MarioObject big = new PowerUp(600, originalPlatform, "Big Mushroom");
	MarioObject ice = new PowerUp(700, originalPlatform, "Ice Flower");
	MarioObject fire = new PowerUp(800, originalPlatform, "Fire Flower");
	MarioObject oneup = new PowerUp(900, originalPlatform, "1-UP");
	
	Block mystBlock1 = new Block(background.getX() + 300, background.getY() + 860, "Mystery", true);

	MarioObject key1 = new Key(0, 600);
	int key1X = (((Key)key1).getRandomX(400, 800));
	int key1Y = (((Key)key1).getRandomY(900, 1100));
	
	MarioObject key2 = new Key(40, 600);					//ADDED keys 2 and 3
	int key2X = (((Key)key2).getRandomX(400, 800));
	int key2Y = (((Key)key2).getRandomY(900, 1100));
	
	MarioObject key3 = new Key(100, 500);
	int key3X = (((Key)key3).getRandomX(400, 800));
	int key3Y = (((Key)key3).getRandomY(900, 1100));
	
	Door door = new Door(background.getX() + 950, background.getY() + 890);
	
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// Paint the Background
		background.paint(g);

		// Paint Other Objects
		door.paint(g);
		key1.paint(g);
		key2.paint(g);	//ADDED
		key3.paint(g);	//ADDED
		keyDisp.paint(g);
		spikes1.paint(g);
		mystBlock1.paint(g); //ADDED
		
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
		
		// Update the Background
		/*if (background.getY() < -440) {
			background.setY(-440);
		}
		if (background.getY() + 1200 >= 765) {
				background.setVY(-vy * 0.4);
		} else {
			background.setVY(0);
		}*/
		background.setVY(0);
		background.setY(-435);

		// Paint Mario
		mario.paint(g);
		
		// Key Mechanics -- ADDED 2 NEW KEYS, COPY OVER
		//Key1
		g.drawRect(key1.getX(), key1.getY(), key1.getWidth(), key1.getHeight());
		if (((Key)key1).getAvailable() == true) {
			key1.setX(background.getX() + key1X);
			key1.setY(background.getY() + key1Y);
		}
		else {
			((Key)key1).setX(-500);
		}
		//Key2
		g.drawRect(key2.getX(), key2.getY(), key2.getWidth(), key2.getHeight());
		if (((Key)key2).getAvailable() == true) {
			key2.setX(background.getX() + key2X);
			key2.setY(background.getY() + key2Y);
		}
		else {
			((Key)key2).setX(-500);
		}

		//Key3
		g.drawRect(key3.getX(), key3.getY(), key3.getWidth(), key3.getHeight());
		if (((Key)key3).getAvailable() == true) {
			key3.setX(background.getX() + key3X);
			key3.setY(background.getY() + key3Y);
		}
		else {
			((Key)key3).setX(-500);
		}

		
		// Key Display Mechanics -- ADDED 2 NEW KEYS, COPY OVER
		//Key1
		if (((Key)key1).getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key1.getX() && mario.getX() <= key1.getX() + key1.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key1.getY() && mario.getY() <= key1.getY() + key1.getHeight()) {
					System.out.println("KEY HIT" + (keyDisp.getState()));
					keyDisp.setState(keyDisp.getState() + 1);
					((Key)key1).setAvailable(false);
				}
			}
		}
		//Key2
		if (((Key)key2).getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key2.getX() && mario.getX() <= key2.getX() + key2.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key2.getY() && mario.getY() <= key2.getY() + key2.getHeight()) {
					System.out.println("KEY HIT" + (keyDisp.getState()));
					keyDisp.setState(keyDisp.getState() + 1);
					((Key)key2).setAvailable(false);
				}
			}
		}
		//Key3
		if (((Key)key3).getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key3.getX() && mario.getX() <= key3.getX() + key3.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key3.getY() && mario.getY() <= key3.getY() + key3.getHeight()) {
					System.out.println("KEY HIT" + (keyDisp.getState()));
					keyDisp.setState(keyDisp.getState() + 1);
					((Key)key3).setAvailable(false);
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
		
		// Spikes
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
		}
		
		// Prevent Mario from Going Off-Screen
		if (mario.getX() <= 10) {	
			mario.setX(10);
		}
				
		if (mario.getX() >= 1120) {	
			mario.setX(1120);
		}
		
		// Goomba Mechanics
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
		
		// Paint the Power Ups only if they have not been collected yet
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
		
		mystBlock1.setX(background.getX() + 300);			//ADDED
		if (mystBlock1.getHasCoin() == true) {				//
			mystBlock1.setY(background.getY() + 860);		//
		}													//
		else {												//
			mystBlock1.setY(background.getY() + 800);		//
		}

		onShort = mario.aboveObject(shortPipe, onShort);
		onLong = mario.aboveObject(longPipe, onLong);
		onBlock1 = mario.aboveObject(block1, onBlock1);
		onBlock2 = mario.aboveObject(block2, onBlock2);
		onBlock3 = mario.aboveObject(block3, onBlock3);
		onBlock4 = mario.aboveObject(block4, onBlock4);
		onMystBlock1 = mario.aboveMystBlock(mystBlock1, onMystBlock1);	//CHANGED
		
		if (!onShort && !onLong && !onBlock1 && !onBlock2 && !onBlock3 && !onBlock4 && !onMystBlock1) {		//CHANGED
			platform = originalPlatform;
		}
		if (mario.hittingObjectFromBelow(block1) || mario.hittingObjectFromBelow(block2) || mario.hittingObjectFromBelow(block3) || mario.hittingObjectFromBelow(block4) || mario.hittingObjectFromBelow(mystBlock1)) { //CHANGED
			vy = 4;
		}
		
		if (mystBlock1.getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= mystBlock1.getX() && mario.getX() <= mystBlock1.getX() + mystBlock1.getWidth()) {
				if (mario.getY() <= mystBlock1.getY() + 40 + 10 && mario.getY() >= mystBlock1.getY() + 40 - 10) {
					System.out.println("block hit");
					mystBlock1.mystHit();
				}
			}
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
		if (time <= 60) {
			g.setColor(Color.RED);
		}
		if (time % 60 == 0 || time % 60 < 10) {
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
	
	// Frame Class Runner
	public static void main(String[] arg) {
		BackgroundTester f = new BackgroundTester();
	}
	
	// Frame Constructor
	public BackgroundTester() {
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



