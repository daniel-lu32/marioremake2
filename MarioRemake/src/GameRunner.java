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

public class GameRunner extends JPanel implements ActionListener, MouseListener, KeyListener {
	
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
	private int time = 300;
	private int frameTracker = 0;
	private boolean lost = false;
	
	private int spawnX = 10;
	private int spawnY = originalPlatform;
	
	private boolean summonIce = false;
	private boolean summonFire = false;
	private boolean goombaIsAlive = true;
	
	// Collision Tracker Variables
	private boolean onShort1 = false;
	private boolean onShort2 = false;
	private boolean onLong = false;
	private boolean onBlock1 = false;
	private boolean onBlock2 = false;
	private boolean onBlock3 = false;
	private boolean onBlock4 = false;
	private boolean onBlock5 = false;
	private boolean onBlock6 = false;
	private boolean onBlock7 = false;
	private boolean onBlock8 = false;
	private boolean onBlock9 = false;
	private boolean onBlock10 = false;
	private boolean onBlock11 = false;
	private boolean onBlock12 = false;
	private boolean onPrison = false;	//ADDED

	boolean shortPipeInRange = false;	//ADDED
	boolean longPipeInRange = false;	//ADDED
	private boolean otherAudioPlaying = false; 	//ADDED2
	
	private boolean onMystBlock1 = false;
	
	//ADDED
	Music theme = new Music("Super Mario Bros. Theme Song.wav", true);
	Music coinSound = new Music("coin.wav", false);
	Music pipeSound = new Music("pipe.wav", false);
	Music jumpSound = new Music("jump.wav", false);
	Music mystSound = new Music("mysteryBlock.wav", false);
	Music levelClear = new Music("level-clear.wav", false);
	Music lifeLost = new Music("life-lost.wav", false);
	Music gameOver = new Music("game-over.wav", true);	//ADDED2/CHANGED
	Music powerUp = new Music("power-up.wav", false);
	Music powerUpAppears = new Music("power-up-appears.wav", false);
	Music keySound = new Music("keySound.wav", false);	//ADDED2
	
	// Fonts
	Font keyFont = new Font("Unknown", Font.BOLD, 14);
	Font displayFont = new Font("Unknown", Font.BOLD, 20);
	
	// Objects
	Background background = new Background(0, -435);
	Character2 mario = new Character2(spawnX, originalPlatform);
	
	Flag flag = new Flag (background.getX() + 780, 440);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);
	
	Goomba goomba1 = new Goomba(background.getX() + 450, originalPlatform);
	
	
	
	Spikes spikes1 = new Spikes(120, originalPlatform + 10);
	Spikes spikes2 = new Spikes(800, originalPlatform + 10);
	Spikes spikes3 = new Spikes(885, originalPlatform + 10);
	
	
	
	Key key1 = new Key(0, 0);
	Key key2 = new Key(0, 0);
	Key key3 = new Key(0, 0);
	Peach peach = new Peach(background.getX() + 1350, 200);
	
	PowerUp livesicon = new PowerUp(8, 10, "1-UP");
	Coin coinicon = new Coin(10, 55);

	Pipe shortPipe1 = new Pipe(background.getX() + 250, 570, false, false);
	Pipe shortPipe2 = new Pipe(background.getX() + 550, 570, true, false);
	Pipe longPipe = new Pipe(background.getX() + 1600, 350, true, false);	
	Block block1 = new Block(background.getX() + 700, 480, "Normal", false);
	Block block2 = new Block(background.getX() + 740, 480, "Normal", false);
	Block block3 = new Block(background.getX() + 780, 480, "Normal", false);
	Block block4 = new Block(background.getX() + 820, 480, "Normal", false);
	Block block5 = new Block(background.getX() + 1000, 480, "Normal", false);
	Block block6 = new Block(background.getX() + 1080, 440, "Normal", false);
	Block block7 = new Block(background.getX() + 1160, 400, "Normal", false);
	Block block8 = new Block(background.getX() + 1240, 360, "Normal", false);
	Block block9 = new Block(background.getX() + 1280, 360, "Normal", false);
	Block block10 = new Block(background.getX() + 1320, 360, "Normal", false);
	Block block11 = new Block(background.getX() + 1360, 360, "Normal", false);
	Block block12 = new Block(background.getX() + 1400, 360, "Normal", false);
	Block mystBlock1 = new Block(background.getX() + 900, 481, "Mystery", true);
	
	Projectile iceball = new Projectile(mario.getX() + 20, originalPlatform, "Iceball");
	Projectile fireball = new Projectile(mario.getX() + 20, originalPlatform, "Fireball");
	PowerUp big = new PowerUp(500, originalPlatform, "Big Mushroom");
	PowerUp ice = new PowerUp(block1.getX(), block1.getY() - 36, "Ice Flower");
	PowerUp fire = new PowerUp(block2.getX(), block2.getY() - 36, "Fire Flower");
	PowerUp oneup = new PowerUp(700, originalPlatform, "1-UP");
	
	
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		//ADDED2 -- COPY OVER THIS SECTION
				if (levelClear.isRunning() || lifeLost.isRunning() || gameOver.isRunning()) {
					otherAudioPlaying = true;
				}
				else {
					otherAudioPlaying = false;
				}
				
				if (lost == false && otherAudioPlaying == false) {	//ADDED
					theme.start();
				}
				if (lost || otherAudioPlaying) {
					theme.stop();
					if (lost) {
						gameOver.start();
					}
					else {
						gameOver.stop();
					}
				}
//		// mass produce?
//		Coin[] x3 = massProduceCoins();
//		for (int i = 0; i < x3.length; i++) {
//			if (mario.collide(x3[i])) {
//				x3[i].setCollided(true);
//				coinSound.play();		//ADDED
//				coins++;
//			}
//			if (!x3[i].getCollided()) {
//				x3[i].paint(g);
//			}
//		}
		
		// Paint the Background
		background.paint(g);
		
		// Paint Important Objects
		key1.paint(g);
		key2.paint(g);
		key3.paint(g);
		keyDisp.paint(g);
		flag.paint(g);
		peach.paint(g);

		// Paint Mario
		mario.paint(g);
		
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
		
		// Key Mechanics
		// Key1
		if (key1.getAvailable() == true) {
			key1.setX(background.getX() + 100);
			key1.setY(405);
		} else {
			key1.setX(-500);
		}
		// Key2
		if (key2.getAvailable() == true) {
			key2.setX(background.getX() + 680);
			key2.setY(205);
		} else {
			key2.setX(-500);
		}
		// Key3
		if (key3.getAvailable() == true) {
			key3.setX(background.getX() + 1700);
			key3.setY(200);
		} else {
			key3.setX(-500);
		}
		
		// Key Display Mechanics
		// Key1
		if (key1.getAvailable() == true) {
			if (mario.collide(key1)) {
				System.out.println("KEY HIT" + (keyDisp.getState()));
				keyDisp.setState(keyDisp.getState() + 1);
				key1.setAvailable(false);
				keySound.play();	//ADDED2
				score += 500;
			} else {					//ADDED2
				keySound.stop();	//ADDED2
			}
		}
		// Key2
		if (key2.getAvailable() == true) {
			if (mario.collide(key2)) {
				System.out.println("KEY HIT" + (keyDisp.getState()));
				keyDisp.setState(keyDisp.getState() + 1);
				key2.setAvailable(false);
				score += 500;
				keySound.play();	//ADDED2
			} else {					//ADDED2
				keySound.stop();	//ADDED2
			}
		}
		// Key3
		if (key3.getAvailable() == true) {
			if (mario.collide(key3)) {
				System.out.println("KEY HIT" + (keyDisp.getState()));
				keyDisp.setState(keyDisp.getState() + 1);
				key3.setAvailable(false);
				score += 500;
				keySound.play();	//ADDED2
			} else {					//ADDED2
				keySound.stop();	//ADDED2
			}
		}
				
		// Peach Mechanics
		peach.setX(background.getX() + 1350);
		if (mario.getX() + mario.getWidth() >= peach.getX() - 30 && mario.getX() <= peach.getX() + peach.getWidth() + 30) {
			if (mario.getY() + mario.getHeight() >= peach.getY() - 30 && mario.getY() <= peach.getY() + peach.getHeight() + 30) {
				if (keyDisp.getState() <= 2) {
					g.setFont(keyFont);
					if (keyDisp.getState() == 2) {
						g.drawString(("Collect " + (3 - keyDisp.getState()) + " more key to open!"), peach.getX() - 55, peach.getY() - 30);
					} else {
						g.drawString(("Collect " + (3 - keyDisp.getState()) + " more keys to open!"), peach.getX() - 55, peach.getY() - 30);
					}
				}
				if (keyDisp.getState() >= 3) {
					peach.setInRange(true);
					g.setFont(keyFont);
					g.drawString(("Press enter to open!"), peach.getX() - 30, peach.getY() - 30);
				}
			}
		}
		
		// Pipe
		shortPipe1.setX(background.getX() + 250);
		shortPipe1.paint(g);
		shortPipe2.setX(background.getX() + 550);
		shortPipe2.paint(g);
		longPipe.setX(background.getX() + 1600);
		longPipe.paint(g);
		
		if (mario.getX() + mario.getWidth() >= shortPipe2.getX() + 30 && mario.getX() <= shortPipe2.getX() + shortPipe2.getWidth() - 30) {
			if (mario.getY() + mario.getHeight() >= shortPipe2.getY() - 60 && mario.getY() <= shortPipe2.getY()) {
				shortPipeInRange = true;
				g.setFont(keyFont);
				g.drawString("Press down to enter!", shortPipe2.getX() - 10, shortPipe2.getY() - 50);
			}	
		}
		else {
			shortPipeInRange = false;
		}
		
		if (mario.getX() + mario.getWidth() >= longPipe.getX() + 30 && mario.getX() <= longPipe.getX() + longPipe.getWidth() - 30) {
			if (mario.getY() + mario.getHeight() >= longPipe.getY() - 60 && mario.getY() <= longPipe.getY()) {
				longPipeInRange = true;
				g.setFont(keyFont);
				g.drawString("press down to enter", longPipe.getX() - 10, longPipe.getY() - 50);
			}	
		}
		else {
			longPipeInRange = false;
		}
		
		
		
		// Flag
		flag.setImage();
		flag.setX(background.getX() + 780);
		flag.setY(440 - flag.getHeight() + mario.getHeight());
		if (mario.collide(flag) && flag.getState() != 2) {
			flag.setState(2);
			score += 500;
			spawnX = background.getX() + flag.getX() + 10;
			spawnY = flag.getY() + flag.getHeight() - mario.getHeight();
		}
		
		// Spikes
		spikes1.setX(background.getX() + 120);
		spikes2.setX(background.getX() + 800);
		spikes3.setX(background.getX() + 885);
		spikes1.paint(g);
		spikes2.paint(g);
		spikes3.paint(g);
		
		if (mario.collide(spikes1) && !spikes1.getHit()) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			spikes1.setHit(true);
			score -= 100;
		}
		if (mario.collide(spikes2) && !spikes2.getHit()) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			spikes2.setHit(true);
			score -= 100;
		}
		if (mario.collide(spikes3) && !spikes3.getHit()) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			spikes3.setHit(true);
			score -= 100;
		}
		
		
		if (fireball.getOnScreen()) {
			fireball.paint(g);
			fireball.setX(fireball.getX() + fireball.getVX());
		} else {
			fireball.setX(mario.getX() + 20);
			fireball.setY(mario.getY());
		}
		if (iceball.getOnScreen()) {
			iceball.paint(g);
			iceball.setX(iceball.getX() + iceball.getVX());
		} else {
			iceball.setX(mario.getX() + 20);
			iceball.setY(mario.getY());
		}
		if (fireball.getX() >= mario.getX() + 400) {
			fireball.setOnScreen(false);
		}
		if (iceball.getX() >= mario.getX() + 400) {
			iceball.setOnScreen(false);
		}
		if (fireball.getOnScreen() && fireball.collideObjects(goomba1)) {
			goombaIsAlive = false;
			fireball.setOnScreen(false);
			score += 1000;
		}

		if (iceball.getOnScreen() && iceball.collideObjects(goomba1)) {
			goombaIsAlive = false;
			iceball.setOnScreen(false);
			score += 1000;
		}
		
		// Prevent Mario from Going Off-Screen
		if (mario.getX() <= 10) {	
			mario.setX(10);
		}
		
		if (mario.getX() >= 1120) {	
			mario.setX(1120);
		}
		
		// Goomba Mechanics
		goomba1.setX(goomba1.getX() + goomba1.getVX());
		if (goombaIsAlive) {
			goomba1.paint(g);
		}
		if (goomba1.collide(shortPipe1)) {
			goomba1.setVX(goomba1.getVX() * -1);
			if (goomba1.getX() <= shortPipe1.getX()) {
				goomba1.setX(shortPipe1.getX() - goomba1.getWidth());
			} else {
				goomba1.setX(shortPipe1.getX() + shortPipe1.getWidth());
			}
		}
		
		if (goomba1.collide(shortPipe2)) {
			goomba1.setVX(goomba1.getVX() * -1);
			if (goomba1.getX() <= shortPipe2.getX()) {
				goomba1.setX(shortPipe2.getX() - goomba1.getWidth());
			} else {
				goomba1.setX(shortPipe2.getX() + shortPipe2.getWidth());
			}
		}
		if (mario.collide(goomba1) && !goomba1.getHit() && goombaIsAlive) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba1.setHit(true);
			score -= 300;
		}
		
		if (mario.getState() < 0) {
			lives--;
			if (lives != 0) {		//ADDED
				lifeLost.play();	//ADDED
			}
			mario.setState(0);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba1.setHit(false);
			spikes1.setHit(false);
			spikes2.setHit(false);
			spikes3.setHit(false);
			resetPosition();
		}
		
		// PowerUp
		big.setX(big.getX() + big.getVX());
		oneup.setX(oneup.getX() + oneup.getVX());
		if (big.collide(shortPipe1)) {
			big.setVX(big.getVX() * -1);
			if (big.getX() <= shortPipe1.getX()) {
				big.setX(shortPipe1.getX() - big.getWidth());
			} else {
				big.setX(shortPipe1.getX() + shortPipe1.getWidth());
			}
		}
		
		if (big.collide(shortPipe2)) {
			big.setVX(big.getVX() * -1);
			if (big.getX() <= shortPipe2.getX()) {
				big.setX(shortPipe2.getX() - big.getWidth());
			} else {
				big.setX(shortPipe2.getX() + shortPipe2.getWidth());
			}
		}
		ice.setX(block1.getX());
		fire.setX(block2.getX());
		
		if (oneup.collide(longPipe)) {
			oneup.setVX(oneup.getVX() * -1);
			if (oneup.getX() <= longPipe.getX()) {
				oneup.setX(longPipe.getX() - oneup.getWidth());
			} else {
				oneup.setX(longPipe.getX() + longPipe.getWidth());
			}
		}
		
		if (oneup.collide(shortPipe2)) {
			oneup.setVX(oneup.getVX() * -1);
			if (oneup.getX() <= shortPipe2.getX()) {
				oneup.setX(shortPipe2.getX() - oneup.getWidth());
			} else {
				oneup.setX(shortPipe2.getX() + shortPipe2.getWidth());
			}
		}
		
		// Paint the Power Ups only if they have not been collected yet
		if (!big.getHit()) {
			big.paint(g);
		}
		if (!ice.getHit() && summonIce) {
			ice.paint(g);
		}
		if (!fire.getHit() && summonFire) {
			fire.paint(g);
		}
		if (!oneup.getHit()) {
			oneup.paint(g);
		} 
		
		if (mario.collide(big) && !big.getHit()) {
			big.setHit(true);
			score += 200;
			powerUp.play();		//ADDED
			if (!mario.getHasAbility()) {
				mario.setState(1);
			}
		}
		
		if (mario.collide(ice) && !ice.getHit() && summonIce) {
			ice.setHit(true);
			score += 400;
			powerUp.play();		//ADDED
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Ice");
		}
		if (mario.collide(fire) && !fire.getHit() && summonFire) {
			fire.setHit(true);
			score += 400;
			powerUp.play();		//ADDED
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Fire");
		}
		if (mario.collide(oneup) && !oneup.getHit()) {
			oneup.setHit(true);
			score += 200;
			powerUp.play();		//ADDED
			lives++;
		}

		
		mystBlock1.paint(g);
		
		// 4 Blocks
		block1.setX(background.getX() + 700);
		block2.setX(background.getX() + 740);
		block3.setX(background.getX() + 780);
		block4.setX(background.getX() + 820);
		block5.setX(background.getX() + 1000);
		block6.setX(background.getX() + 1080);
		block7.setX(background.getX() + 1160);
		block8.setX(background.getX() + 1240);
		block9.setX(background.getX() + 1280);
		block10.setX(background.getX() + 1320);
		block11.setX(background.getX() + 1360);
		block12.setX(background.getX() + 1400);

		block1.paint(g);
		block2.paint(g);
		block3.paint(g);
		block4.paint(g);
		block5.paint(g);
		block6.paint(g);
		block7.paint(g);
		block8.paint(g);
		block9.paint(g);
		block10.paint(g);
		block11.paint(g);
		block12.paint(g);
		
		mystBlock1.setX(background.getX() + 450);
		if (mystBlock1.getHasCoin()) {
			mystBlock1.setY(481);
		} else {
			mystBlock1.setY(421);
		}
		
		onShort1 = mario.aboveObject(shortPipe1, onShort1);
		onShort2 = mario.aboveObject(shortPipe2, onShort2);
		onLong = mario.aboveObject(longPipe, onLong);
		onBlock1 = mario.aboveObject(block1, onBlock1);
		onBlock2 = mario.aboveObject(block2, onBlock2);
		onBlock3 = mario.aboveObject(block3, onBlock3);
		onBlock4 = mario.aboveObject(block4, onBlock4);
		onBlock5 = mario.aboveObject(block5, onBlock5);
		onBlock6 = mario.aboveObject(block6, onBlock6);
		onBlock7 = mario.aboveObject(block7, onBlock7);
		onBlock8 = mario.aboveObject(block8, onBlock8);
		onBlock9 = mario.aboveObject(block9, onBlock9);
		onBlock10 = mario.aboveObject(block10, onBlock10);
		onBlock11 = mario.aboveObject(block11, onBlock11);
		onBlock12 = mario.aboveObject(block12, onBlock12);
		onMystBlock1 = mario.aboveMystBlock(mystBlock1, onMystBlock1, mystBlock1.getAvailable());	//CHANGED
		
		if (peach.getStateLocked() == false) {				//ADDED
			onPrison = false;								//ADDED
		}													//ADDED
		else {												//ADDED
			onPrison = mario.aboveObject(peach, onPrison);	//ADDED
		}
		
		if (!onLong && !onShort1 && !onShort2 && !onBlock1 && !onBlock2 && !onBlock3 && !onBlock4 && !onMystBlock1 && !onBlock5 && !onBlock6 && !onBlock7 && !onBlock8 && !onBlock9 && !onBlock10 && !onBlock11 && !onBlock12) {		//CHANGED
			platform = originalPlatform;
		}
		
		if (mario.hittingObjectFromBelow(block1)) {
			vy = 4;
			if (!summonIce) {
				powerUpAppears.play();	//ADDED
			}
			summonIce = true;
		}
		if (mario.hittingObjectFromBelow(block2)) {
			vy = 4;
			if (!summonFire) {
				powerUpAppears.play();	//ADDED
			}
			summonFire = true;
		}
		if (mario.hittingObjectFromBelow(block3) || mario.hittingObjectFromBelow(block4) || mario.hittingObjectFromBelow(block5) || mario.hittingObjectFromBelow(block6) || mario.hittingObjectFromBelow(block7) || mario.hittingObjectFromBelow(block8) || mario.hittingObjectFromBelow(block9) || mario.hittingObjectFromBelow(block10) || mario.hittingObjectFromBelow(block11) || mario.hittingObjectFromBelow(block12)) {
			vy = 4;
		}
		
		if (mario.hittingMystBlockFromBelow(mystBlock1)) { //CHANGED
			vy = 4;
		}
		if (mystBlock1.getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= mystBlock1.getX() && mario.getX() <= mystBlock1.getX() + mystBlock1.getWidth()) {
				if (mario.getY() <= mystBlock1.getY() + 40 + 10 && mario.getY() >= mystBlock1.getY() + 40 - 10) {
					System.out.println("block hit");
					int coinsToBeAdded = (int) (Math.random() * 8 + 1);
					coins += coinsToBeAdded * 10;
					score += coinsToBeAdded * 100;
					mystBlock1.mystHit();
				}
			}
		}
		// Top Text Display (Lives, Score, Coins, and Timer)
		g.setFont(displayFont);
		
		// Check if the game should be ended
		endGame();
		
		// Update the Timer
		frameTracker++;
		if (frameTracker % 35 == 0) {
			time--;
			if (frameTracker % 350 == 0) {
				goomba1.setHit(false);
				spikes1.setHit(false);
				spikes2.setHit(false);
				spikes3.setHit(false);
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
		g.drawString("High Score: " + highScore, 200, 80);
		if (score > highScore) {
			highScore = score;
		}
		if (score < 0) {
			score = 0;
		}
		
		if (time <= 0) {
			time = 0;
			lives = 0;
		}
		if (time <= 60) {
			g.setColor(Color.RED);
		}
		if (time % 60 == 0 || time % 60 < 10) {
			g.drawString("" + time / 60 + ":" + "0" + time % 60, 1100, 30);
		} else {
			g.drawString("" + time / 60 + ":" + time % 60, 1100, 30);
		}
		if (lost) {
			Font gameOverFont = new Font("Unknown", Font.BOLD, 36);		//ADDED2/CHANGED
			g.setFont(gameOverFont);										//ADDED2/CHANGED
			g.drawString("Game Over. Press R to Restart!", 320, 200);	//CHANGED
		}
		
	} // end of paint method
	
	public void resetPosition() {
		mario.setX(spawnX);
		mario.setY(spawnY);
		background.setX(0);
		background.setY(-435);
	}
	
	// DANIEL ADDED
	public void updateShoot(int zeroFireOneIce) {
		if (zeroFireOneIce == 0) {
			fireball.setOnScreen(true);
			fireball.setVX(3);
		}
		if (zeroFireOneIce == 1) {
			iceball.setOnScreen(true);
			iceball.setVX(3);
		}
	}
	// END
	
	public void reset() {
		lives = 7;
		coins = 0;
		score = 0;
		time = 300;
		frameTracker = 0;
		platform = originalPlatform;
		lost = false;
		spawnX = 10;
		spawnY = originalPlatform;

		otherAudioPlaying = false; 	//ADDED2
		
		summonIce = false;
		summonFire = false;
		goombaIsAlive = true;
		onShort1 = false;
		onShort2 = false;
		onLong = false;
		onBlock1 = false;
		onBlock2 = false;
		onBlock3 = false;
		onBlock4 = false;
		onBlock5 = false;
		onBlock6 = false;
		onBlock7 = false;
		onBlock8 = false;
		onBlock9 = false;
		onBlock10 = false;
		onBlock11 = false;
		onBlock12 = false;
		onPrison = false;
		shortPipeInRange = false;
		longPipeInRange = false;
		onMystBlock1 = false;
		background = new Background(0, -435);
		mario = new Character2(spawnX, originalPlatform);
		flag = new Flag (background.getX() + 780, 440);
		keyDisp = new KeyDisplay(450, 20);
		goomba1 = new Goomba(background.getX() + 450, originalPlatform);
		spikes1 = new Spikes(120, originalPlatform + 10);
		spikes2 = new Spikes(800, originalPlatform + 10);
		spikes3 = new Spikes(885, originalPlatform + 10);
		key1 = new Key(0, 0);
		key2 = new Key(0, 0);
		key3 = new Key(0, 0);
		peach = new Peach(background.getX() + 1350, 200);
		livesicon = new PowerUp(8, 10, "1-UP");
		coinicon = new Coin(10, 55);

		shortPipe1 = new Pipe(background.getX() + 250, 570, false, false);
		shortPipe2 = new Pipe(background.getX() + 550, 570, true, false);
		longPipe = new Pipe(background.getX() + 1600, 350, true, false);	
		block1 = new Block(background.getX() + 700, 480, "Normal", false);
		block2 = new Block(background.getX() + 740, 480, "Normal", false);
		block3 = new Block(background.getX() + 780, 480, "Normal", false);
		block4 = new Block(background.getX() + 820, 480, "Normal", false);
		block5 = new Block(background.getX() + 1000, 480, "Normal", false);
		block6 = new Block(background.getX() + 1080, 440, "Normal", false);
		block7 = new Block(background.getX() + 1160, 400, "Normal", false);
		block8 = new Block(background.getX() + 1240, 360, "Normal", false);
		block9 = new Block(background.getX() + 1280, 360, "Normal", false);
		block10 = new Block(background.getX() + 1320, 360, "Normal", false);
		block11 = new Block(background.getX() + 1360, 360, "Normal", false);
		block12 = new Block(background.getX() + 1400, 360, "Normal", false);
		mystBlock1 = new Block(background.getX() + 900, 481, "Mystery", true);
		
		iceball = new Projectile(mario.getX() + 20, originalPlatform, "Iceball");
		fireball = new Projectile(mario.getX() + 20, originalPlatform, "Fireball");
		big = new PowerUp(background.getX() + 500, originalPlatform, "Big Mushroom");
		ice = new PowerUp(block1.getX(), block1.getY() - 36, "Ice Flower");
		fire = new PowerUp(block2.getX(), block2.getY() - 36, "Fire Flower");
		oneup = new PowerUp(background.getX() + 700, originalPlatform, "1-UP");
				
	}
	public void endGame() {
		if (lost) {
			//theme.stop();		//ADDED
			gameOver.start();	//ADDED
			spawnX = 10;
			spawnY = originalPlatform;
			
			resetPosition();
		}
	}
	
	// GameRunner Class Runner
	public static void main(String[] arg) {
		GameRunner f = new GameRunner();
			
	}
	
	// GameRunner Constructor
	public GameRunner() {
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
			vy = -20;
		}
		
		if (arg0.getKeyCode() == 10) {
			if (peach.getInRange() == true) {
				peach.setStateLocked(false);
				peach.chooseImage();
				score += 1000;
				score += time * 10;
				score += lives * 100;
			}
		}
		if (arg0.getKeyCode() == 82 && lost) {
			reset();
		}
		
		// emergency reset: Press \
		if (arg0.getKeyCode() == 92) {
			reset();
		}
		
		
		// DANIEL ADDED
		if (arg0.getKeyCode() == 32) {
			if (mario.getAbility().equals("Fire") && !fireball.getOnScreen()) {
				updateShoot(0);
			}
			if (mario.getAbility().equals("Ice") && !fireball.getOnScreen()) {
				updateShoot(1);
			}
		}
		// END
		if (arg0.getKeyCode() == 40 && shortPipeInRange == true) {
			background.setX(background.getX() - Math.abs(longPipe.getX() - shortPipe2.getX()));
			if (background.outOfBoundsLeft() == true || background.outOfBoundsRight() == true) {
				if (background.outOfBoundsLeft() == true) {
					background.setX(0);
				}
				if (background.outOfBoundsRight() == true) {
					background.setX(1200 - background.getWidth());
				}
				//ADD IF STATEMENT HERE FOR DIFFERENT LEVELS, CHANGING THE X VALUE TO BE SET TO
				if (longPipe.getX() + 20 >= 1200) {
					mario.setX(1050);
				}
				else {
					mario.setX(longPipe.getX() + 20);
				}
			}
			else {
				mario.setX(mario.getX());
			}
			
			//ADD IF STATEMENT HERE FOR DIFF LEVELS
			platform = longPipe.getY() - 1;
			background.setX(1200 - background.getWidth());
			mario.setX(1050);
			mario.setY(longPipe.getY() - mario.getHeight() - 1);
			shortPipeInRange = false;
		}
		
		else if (arg0.getKeyCode() == 40 && longPipeInRange == true) {
			System.out.println("Got to 1");
			background.setX(background.getX() + Math.abs(longPipe.getX() - shortPipe2.getX()));
			if (background.outOfBoundsLeft() == true || background.outOfBoundsRight() == true) {
				if (background.outOfBoundsLeft() == true) {
					background.setX(0);
				}
				if (background.outOfBoundsRight() == true) {
					background.setX(1600 - background.getWidth());
				}
				//ADD IF STATEMENT HERE FOR DIFFERENT LEVELS, CHANGING THE X VALUE TO BE SET TO
				if (shortPipe2.getX() + 20 <= 0) {
					mario.setX(620);
				}
				else {
					mario.setX(background.getX() + shortPipe2.getX() + 20);
				}
			}
			else {
				mario.setX(mario.getX());
			}
			System.out.println("Got to 2");
			
			//ADD IF STATEMENT HERE FOR DIFF LEVELS
			platform = shortPipe2.getY() - 1;
			background.setX(0);
			mario.setX(620);
			mario.setY(shortPipe2.getY() - mario.getHeight() - 1);
			longPipeInRange = false;
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