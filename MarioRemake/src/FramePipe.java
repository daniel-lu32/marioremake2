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

public class FramePipe extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	/**
	 * THIS IS JUST A TESTER CLASS.
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
	
	private int spawnX = 10;
	
	private boolean summonBig = false;
	private boolean summonIce = false;
	private boolean summonFire = false;
	private boolean summonOneup = false;
	
	// Collision Tracker Variables
	private boolean onShort = false;
	private boolean onLong = false;
	private boolean onBlock1 = false;
	private boolean onBlock2 = false;
	private boolean onBlock3 = false;
	private boolean onBlock4 = false;
	private boolean onPrison = false;	//ADDED
	private boolean onMystBlock1 = false;
	
	boolean shortPipeInRange = false;	//ADDED
	boolean longPipeInRange = false;	//ADDED
	
	private boolean otherAudioPlaying = false; 	//ADDED2
	
	// Objects
	Background background = new Background(0, -435);
	Character mario = new Character(spawnX, originalPlatform);
	
	Flag flag = new Flag (background.getX() + 880, originalPlatform);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);
	Spikes spikes1 = new Spikes(120, originalPlatform + 10);
	Spikes spikes2 = new Spikes(1050, originalPlatform + 10);	//ADDED
	
	Goomba goomba = new Goomba(background.getX() + 450, originalPlatform);
	PowerUp big = new PowerUp(500, originalPlatform, "Big Mushroom");
	PowerUp ice = new PowerUp(600, originalPlatform, "Ice Flower");
	PowerUp fire = new PowerUp(700, originalPlatform, "Fire Flower");
	PowerUp oneup = new PowerUp(800, originalPlatform, "1-UP");
	
	PowerUp bigFromBlock = new PowerUp(0, originalPlatform, "Big Mushroom");
	PowerUp iceFromBlock = new PowerUp(0, originalPlatform, "Ice Flower");
	PowerUp fireFromBlock = new PowerUp(0, originalPlatform, "Fire Flower");
	PowerUp oneupFromBlock = new PowerUp(0, originalPlatform, "1-UP");
	MarioObject key1 = new Key(100, 405);	//CHANGED
	MarioObject key2 = new Key(680, 205);	//CHANGED
	MarioObject key3 = new Key(1600, 305);	//CHANGED
	Peach peach = new Peach(background.getX() + 950, platform - 47);
	PowerUp livesicon = new PowerUp(8, 10, "1-UP");
	Coin coinicon = new Coin(10, 55);

	MarioObject shortPipe = new Pipe(background.getX() + 400, background.getY() + 1005, false, false);
	MarioObject longPipe = new Pipe(background.getX() + 1600, background.getY() + 865, true, false);	
	Block block1 = new Block(background.getX() + 700, background.getY() + 435 + 480, "Normal", false);
	Block block2 = new Block(background.getX() + 740, background.getY() + 435 + 480, "Normal", false);
	Block block3 = new Block(background.getX() + 780, background.getY() + 435 + 480, "Normal", false);
	Block block4 = new Block(background.getX() + 820, background.getY() + 435 + 480, "Normal", false);
	Block mystBlock1 = new Block(background.getX() + 280, background.getY() + 860, "Mystery", true);		//ADDED
	
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
	
	public Coin[] massProduceCoins() {
		Coin[] x1 = new Coin[10];
		int x2 = 800;
		for (int i = 0; i < 10; i++) {
			Coin coin = new Coin(x2, originalPlatform);
			x1[i] = coin;
			coin.setScaleX(0.15);
			coin.setScaleY(0.15);
			x2 += (int) (coin.getWidth() * 1.3);
		}
		return x1;
	}
	public Block[] massProduceBlocks() {
		Block[] x1 = new Block[10];
		int x2 = 900;
		for (int i = 0; i < 10; i++) {
			Block block = new Block(background.getX() + x2, 480, "Normal", false);
			x1[i] = block;
			x2 += block.getWidth();
		}
		return x1;
	}
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
		
		// Paint the Background
		background.paint(g);
		// Paint Other Objects
		peach.paint(g);
		key1.paint(g);
		key2.paint(g);	
		key3.paint(g);	
		keyDisp.paint(g);
		spikes1.paint(g);
		spikes2.paint(g);
		mystBlock1.paint(g); 
		
		// mass produce?
		Coin[] x3 = massProduceCoins();
		for (int i = 0; i < x3.length; i++) {
			if (mario.collide(x3[i])) {
				x3[i].setCollided(true);
				coinSound.play();		//ADDED
				coins++;
			}
			if (!x3[i].getCollided()) {
				x3[i].paint(g);
			}
		}
		Block[] x4 = massProduceBlocks();
		for (int i = 0; i < x4.length; i++) {
			x4[i].paint(g);
		}

		bigFromBlock.setX(block1.getX());
		bigFromBlock.setY(block1.getY() - bigFromBlock.getHeight());
		iceFromBlock.setX(block2.getX());
		iceFromBlock.setY(block2.getY() - iceFromBlock.getHeight());
		fireFromBlock.setX(block3.getX());
		fireFromBlock.setY(block3.getY() - fireFromBlock.getHeight());
		oneupFromBlock.setX(block4.getX());
		oneupFromBlock.setY(block4.getY() - oneupFromBlock.getHeight());
		if (summonBig) {
			bigFromBlock.paint(g);
		}
		if (summonIce) {
			iceFromBlock.paint(g);
		}
		if (summonFire) {
			fireFromBlock.paint(g);
		}
		if (summonOneup) {
			oneupFromBlock.paint(g);
		}
		
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
		background.setVY(0);
		background.setY(-435);

		// Paint Mario
		mario.paint(g);
		
		// Key Mechanics -- CHANGED, COPY OVER
		//Key1
		g.drawRect(key1.getX(), key1.getY(), key1.getWidth(), key1.getHeight());
		if (((Key)key1).getAvailable() == true) {
			key1.setX(background.getX() + 100);
			key1.setY(background.getY() + 840);
		}
		else {
			if (lost == false) {
				key1.setX(-500);
			}
			else {
				key1.setX(((Key)key1).getSpawnX());
				key1.setY(((Key)key1).getSpawnY());
				
			}
		}
		//Key2
		g.drawRect(key2.getX(), key2.getY(), key2.getWidth(), key2.getHeight());
		if (((Key)key2).getAvailable() == true) {
			key2.setX(background.getX() + 680);
			key2.setY(background.getY() + 640);
		}
		else {
			if (lost == false) {
				key2.setX(-500);
			}
			else {
				key2.setX(((Key)key2).getSpawnX());
				key2.setY(((Key)key2).getSpawnY());
			}
		}

		//Key3
		g.drawRect(key3.getX(), key3.getY(), key3.getWidth(), key3.getHeight());
		if (((Key)key3).getAvailable() == true) {
			key3.setX(background.getX() + 1600);
			key3.setY(background.getY() + 740);
		}
		else {
			if (lost == false) {
				key3.setX(-500);
			}
			else {
				key3.setX(((Key)key3).getSpawnX());
				key3.setY(((Key)key3).getSpawnY());
			}
		}
				
		// Key Display Mechanics -- CHANGED, COPY OVER
		//Key1
		if (((Key)key1).getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key1.getX() && mario.getX() <= key1.getX() + key1.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key1.getY() && mario.getY() <= key1.getY() + key1.getHeight()) {
					System.out.println("KEY HIT" + (keyDisp.getState()));
					keyDisp.setState(keyDisp.getState() + 1);
					((Key)key1).setAvailable(false);
					keySound.play();	//ADDED2
				}
				else {					//ADDED2
					keySound.stop();	//ADDED2
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
					keySound.play();	//ADDED2
				}
				else {					//ADDED2
					keySound.stop();	//ADDED2
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
					keySound.play();	//ADDED2
				}
				else {					//ADDED2
					keySound.stop();	//ADDED2
				}
			}
		}
				
		// Peach Mechanics
		peach.setX(background.getX() + 950);
		peach.setY(background.getY() + 968);
		Font keyFont = new Font("keyFont", Font.BOLD, 14);
		if (mario.getX() + mario.getWidth() >= peach.getX() - 30 && mario.getX() <= peach.getX() + peach.getWidth() + 30) {
			if (mario.getY() + mario.getHeight() >= peach.getY() - 30 && mario.getY() <= peach.getY() + peach.getHeight() + 30) {
				System.out.println("in peach range");	//remove later
				if (keyDisp.getState() <= 2) {
					g.setFont(keyFont);
					g.drawString(("collect " + (3 - keyDisp.getState()) + " more keys to open"), peach.getX() - 55, peach.getY() - 30);
				}
				if (keyDisp.getState() >= 3) {
					peach.setInRange(true);
					g.setFont(keyFont);
					g.drawString(("press enter to open"), peach.getX() - 30, peach.getY() - 30);
				}
			}
		}
		
		// Pipe -- CHANGED
		shortPipe.setX(background.getX() + 350);
		shortPipe.setY(background.getY() + 1005);
		longPipe.setX(background.getX() + 1600);
		longPipe.setY(background.getY() + 865);
		shortPipe.paint(g);
		longPipe.paint(g);
		g.drawRect(shortPipe.getX(), shortPipe.getY(), shortPipe.getWidth(), shortPipe.getHeight());
		g.drawRect(longPipe.getX(), longPipe.getY(), longPipe.getWidth(), longPipe.getHeight());
		
		if (mario.getX() + mario.getWidth() >= shortPipe.getX() + 30 && mario.getX() <= shortPipe.getX() + shortPipe.getWidth() - 30) {
			if (mario.getY() + mario.getHeight() >= shortPipe.getY() - 60 && mario.getY() <= shortPipe.getY()) {
				shortPipeInRange = true;
				g.setFont(keyFont);
				g.drawString("press down to enter", shortPipe.getX() - 10, shortPipe.getY() - 50);
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

		flag.setX(background.getX() + 880);
		flag.setY(originalPlatform - flag.getHeight() + mario.getHeight());
		if (mario.collide(flag)) {
			flag.setState(2);
			spawnX = background.getX() + flag.getX();
		}
		
		// Spikes
		spikes1.setX(background.getX() + 120);
		spikes1.setY(background.getY() + 1025);
		spikes2.setX(background.getX() + 1050);		//ADDED
		spikes2.setY(background.getY() + 1025);		//ADDED
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
		
		if (mario.collide(spikes2) && !spikes2.getHit()) {		//ADDED METHOD
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			System.out.println("spikes hit");
			spikes2.setHit(true);
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
			lives--;	//ADDED
			if (lives != 0) {		//ADDED
				lifeLost.play();	//ADDED
			}
			mario.setState(0);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba.setHit(false);
			resetPosition();
			spikes1.setHit(false);
			spikes2.setHit(false);		//ADDED
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
			powerUp.play();		//ADDED
			if (!mario.getHasAbility()) {
				mario.setState(1);
			}
		}
		if (mario.collide(ice) && !ice.getHit()) {
			ice.setHit(true);
			powerUp.play();		//ADDED
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Ice");
		}
		if (mario.collide(fire) && !fire.getHit()) {
			fire.setHit(true);
			powerUp.play();		//ADDED
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Fire");
		}
		if (mario.collide(oneup) && !oneup.getHit()) {
			oneup.setHit(true);
			powerUp.play();		//ADDED
			lives++;
		}
		
		block1.setX(background.getX() + 700);
		block2.setX(background.getX() + 740);
		block3.setX(background.getX() + 780);
		block4.setX(background.getX() + 820);
		block1.paint(g);
		block2.paint(g);
		block3.paint(g);
		block4.paint(g);
		
		mystBlock1.setX(background.getX() + 280);		//ADDED		
		if (mystBlock1.getHasCoin() == true) {		
			mystBlock1.setY(background.getY() + 860);		
		}													
		else {												
			mystBlock1.setY(background.getY() + 800);		
		}
		
		onShort = mario.aboveObject(shortPipe, onShort);
		onLong = mario.aboveObject(longPipe, onLong);
		onBlock1 = mario.aboveObject(block1, onBlock1);
		onBlock2 = mario.aboveObject(block2, onBlock2);
		onBlock3 = mario.aboveObject(block3, onBlock3);
		onBlock4 = mario.aboveObject(block4, onBlock4);
		if (peach.getStateLocked() == false) {				//ADDED
			onPrison = false;								//ADDED
		}													//ADDED
		else {												//ADDED
			onPrison = mario.aboveObject(peach, onPrison);	//ADDED
		}
		onMystBlock1 = mario.aboveMystBlock(mystBlock1, onMystBlock1, mystBlock1.getAvailable());	

		if (!onShort && !onLong && !onBlock1 && !onBlock2 && !onBlock3 && !onBlock4 && !onMystBlock1 && !onPrison) {	//ADDED PRISON
			platform = originalPlatform;
		}
		
		if (mario.hittingObjectFromBelow(block1)) {
			vy = 4;	
			summonBig = true;
			powerUpAppears.play();	//ADDED
			
		}
		if (mario.hittingObjectFromBelow(block2)) {
			vy = 4;
			summonIce = true;
			powerUpAppears.play();	//ADDED
		}
		if (mario.hittingObjectFromBelow(block3)) {
			vy = 4;
			summonFire = true;
			powerUpAppears.play();	//ADDED
		}
		if (mario.hittingObjectFromBelow(block4)) {
			vy = 4;
			summonOneup = true;
			powerUpAppears.play();	//ADDED
		}
		if (mario.hittingMystBlockFromBelow(mystBlock1)) { 
			if (mystBlock1.getHasCoin() == true) {	//ADDED
				mystBlock1.mystHit();
				mystSound.play();
			}
			vy = 4;
		}
		/*if (mystBlock1.getAvailable() == true) {		//DELETE
			if (mario.getX() + mario.getWidth() >= mystBlock1.getX() && mario.getX() <= mystBlock1.getX() + mystBlock1.getWidth()) {
				if (mario.getY() <= mystBlock1.getY() + 40 + 10 && mario.getY() >= mystBlock1.getY() + 40 - 10) {
					System.out.println("block hit");
					mystBlock1.mystHit();
				}
			}
		}*/
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
				spikes2.setHit(false);		//ADDED
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
			g.drawString("" + time / 60 + ":" + "0" + time % 60, 1100, 30);
		} else {
			g.drawString("" + time / 60 + ":" + time % 60, 1100, 30);
		}
		if (lost) {
			Font gameOverFont = new Font("gameOverFont", Font.BOLD, 36);		//ADDED2/CHANGED
			g.setFont(gameOverFont);										//ADDED2/CHANGED
			g.drawString("Game Over. Press R to Restart!", 320, 200);	//CHANGED
		}
		
	} // end of paint method
	
	public void resetPosition() {
		mario.setX(spawnX);
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
			//theme.stop();		//ADDED2/cHANGED
			gameOver.start();	//ADDED
			onShort = false;
			onLong = false;
			onBlock1 = false;
			onBlock2 = false;
			onBlock3 = false;
			onBlock4 = false;
			((Key)key1).setAvailable(true);		//ADDED
			((Key)key2).setAvailable(true);		//ADDED
			((Key)key3).setAvailable(true);		//ADDED
			onMystBlock1 = false;	//ADDED
			mystBlock1.setHasCoin(true);	//ADDED
			mystBlock1.setAvailable(true);	//ADDED
			mystBlock1.chooseImage();	//ADDED
			onPrison = false;	//ADDED
			goomba.setHit(false);
			spikes1.setHit(false);
			spikes2.setHit(false);		//ADDED
			big.setHit(false);
			ice.setHit(false);
			fire.setHit(false);
			oneup.setHit(false);
			background = new Background(0, -435);
			mario = new Character(spawnX, originalPlatform);
			flag = new Flag (880, originalPlatform);
			keyDisp = new KeyDisplay(450, 20);
			spikes1 = new Spikes(120, originalPlatform + 10);
			spikes2 = new Spikes(1050, originalPlatform + 10);	//ADDED
			goomba = new Goomba(550, originalPlatform);
			big = new PowerUp(600, originalPlatform, "Big Mushroom");
			ice = new PowerUp(700, originalPlatform, "Ice Flower");
			fire = new PowerUp(800, originalPlatform, "Fire Flower");
			oneup = new PowerUp(900, originalPlatform, "1-UP");
			peach = new Peach(background.getX() + 950, platform - 47);
		}

	}
	
	// Frame Class Runner
	public static void main(String[] arg) {
		FramePipe f = new FramePipe();
	}
	
	// Frame Constructor
	public FramePipe() {
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
			jumpSound.play();		//ADDED
//			if (mario.getJumping() == true) {
//				background.slideVertical(true);
//			}
//			else {
//				background.slideVertical(false);
//			}
			vy = -20;
		}
		
		if (arg0.getKeyCode() == 10) {
			if (peach.getInRange() == true) {
				peach.setStateLocked(false);
				levelClear.play();		//ADDED
				peach.chooseImage();
			}
		}
		if (arg0.getKeyCode() == 82) {	//CHANGED INTO NESTED, BUT NOT NEEDED
			if (lost) {
				reset();
			}
		}
		
		if (arg0.getKeyCode() == 40 && shortPipeInRange == true) {
			/*background.setX(background.getX() - Math.abs(longPipe.getX() - shortPipe.getX()));
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
			}*/
			
			//ADD IF STATEMENT HERE FOR DIFF LEVELS
			platform = longPipe.getY() - 1;
			background.setX(1200 - background.getWidth());
			mario.setX(1050);
			mario.setY(longPipe.getY() - mario.getHeight() - 1);
			shortPipeInRange = false;
			pipeSound.play();		//ADDED
		}
		
		else if (arg0.getKeyCode() == 40 && longPipeInRange == true) {
			/*System.out.println("Got to 1");
			background.setX(background.getX() + Math.abs(longPipe.getX() - shortPipe.getX()));
			if (background.outOfBoundsLeft() == true || background.outOfBoundsRight() == true) {
				if (background.outOfBoundsLeft() == true) {
					background.setX(0);
				}
				if (background.outOfBoundsRight() == true) {
					background.setX(1600 - background.getWidth());
				}
				//ADD IF STATEMENT HERE FOR DIFFERENT LEVELS, CHANGING THE X VALUE TO BE SET TO
				if (shortPipe.getX() + 20 <= 0) {
					mario.setX(400);
				}
				else {
					mario.setX(shortPipe.getX() + 20);
				}
			}
			else {
				mario.setX(mario.getX());
			}
			System.out.println("Got to 2"); */
			
			//ADD IF STATEMENT HERE FOR DIFF LEVELS
			platform = shortPipe.getY() - 1;
			background.setX(0);
			mario.setX(400);
			mario.setY(shortPipe.getY() - mario.getHeight() - 1);
			longPipeInRange = false;
			pipeSound.play();	//ADDED
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
