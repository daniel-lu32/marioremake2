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
	
	// Game-specific static variables
	public static int vy = 0;
	public static int acceleration = 1;
	public static final int originalPlatform = 580;
	public static int platform = 580;
	
	// Variables to track statistics
	private int lives = 7;
	private int coins = 0;
	private int score = 0;
	private int highScore = 0;
	private int time = 300;
	private int frameTracker = 0;
	private boolean won = false;
	private boolean lost = false;
	
	// Variables to track where the Character spawns
	private int spawnX = 10;
	private int spawnY = originalPlatform;
	
	// Variables to track collisions
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
	private boolean onMystBlock1 = false;
	private boolean onPrison = false;
	private boolean shortPipeInRange = false;
	private boolean longPipeInRange = false;
	private boolean summonIce = false;
	private boolean summonFire = false;
	private boolean goombaIsAlive = true;
	
	// Create Music objects to store audio files
	Music theme = new Music("Super Mario Bros. Theme Song.wav", true);
	Music coinSound = new Music("coin.wav", false);
	Music pipeSound = new Music("pipe.wav", false);
	Music jumpSound = new Music("jump.wav", false);
	Music mystSound = new Music("mysteryBlock.wav", false);
	Music levelClear = new Music("level-clear.wav", false);
	Music lifeLost = new Music("life-lost.wav", false);
	Music gameOver = new Music("game-over.wav", true);
	Music powerUp = new Music("power-up.wav", false);
	Music powerUpAppears = new Music("power-up-appears.wav", false);
	Music keySound = new Music("keySound.wav", false);
	
	// Variable to track whether or not another audio file is being played
	private boolean otherAudioPlaying = false;
	
	// Fonts
	Font keyFont = new Font("Unknown", Font.BOLD, 14);
	Font displayFont = new Font("Unknown", Font.BOLD, 20);
	Font gameOverFont = new Font("Unknown", Font.BOLD, 36);
	
	// Create Background and Character objects
	Background background = new Background(0, -435);
	Character2 mario = new Character2(spawnX, originalPlatform);
	
	// Create obstacles (Spikes and Goombas)
	Spikes spikes1 = new Spikes(120, originalPlatform + 10);
	Spikes spikes2 = new Spikes(800, originalPlatform + 10);
	Spikes spikes3 = new Spikes(885, originalPlatform + 10);
	Goomba goomba1 = new Goomba(background.getX() + 450, originalPlatform);
	
	// Create Key objects and the KeyDisplay
	Key key1 = new Key(100, 405);
	Key key2 = new Key(680, 205);
	Key key3 = new Key(1600, 305);
	KeyDisplay keyDisp = new KeyDisplay(450, 20);

	// Create the Flag checkpoint and Princess Peach's cage
	Flag flag = new Flag (background.getX() + 780, 440);
	Peach peach = new Peach(background.getX() + 1350, 200);
	
	// Create a Coin object and a 1-UP object to display coins and lives
	PowerUp livesicon = new PowerUp(8, 10, "1-UP");
	Coin coinicon = new Coin(10, 55);
	
	// Create obstacles (Pipes and Blocks)
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
	
	// Create Projectiles and PowerUps
	Projectile iceball = new Projectile(mario.getX() + 20, originalPlatform, "Iceball");
	Projectile fireball = new Projectile(mario.getX() + 20, originalPlatform, "Fireball");
	PowerUp big = new PowerUp(500, originalPlatform, "Big Mushroom");
	PowerUp ice = new PowerUp(block1.getX(), block1.getY() - 36, "Ice Flower");
	PowerUp fire = new PowerUp(block2.getX(), block2.getY() - 36, "Fire Flower");
	PowerUp oneup = new PowerUp(700, originalPlatform, "1-UP");
	
	// Create collectible Coins
	Coin coin1 = new Coin(block5.getX() + 6, block5.getY() - 35);
	Coin coin2 = new Coin(block6.getX() + 6, block6.getY() - 35);
	Coin coin3 = new Coin(block7.getX() + 6, block7.getY() - 35);
	Coin coin4 = new Coin(block8.getX() + 6, block8.getY() - 35);
	Coin coin5 = new Coin(block12.getX() + 6, block12.getY() - 35);
	
	// Most important method with code and movement that is called 35 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// Determine whether or not the Mario theme should be played
		// If the game has not been lost and no other audio files are being played, then play the theme
		// If the game has been lost, then play the game over sound
		if (levelClear.isRunning() || lifeLost.isRunning() || gameOver.isRunning()) {
			otherAudioPlaying = true;
		}
		else {
			otherAudioPlaying = false;
		}
		
		if (lost == false && otherAudioPlaying == false && won == false) {
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
		
		// Paint the Keys, KeyDisplay, Flag, and Princess Peach
		key1.paint(g);
		key2.paint(g);
		key3.paint(g);
		keyDisp.paint(g);
		flag.paint(g);
		peach.paint(g);

		// Paint Mario
		mario.paint(g);
		
		// Update Mario's Y Position based on his current velocity and the constant acceleration
		mario.setY(mario.getY() + vy);
		vy += acceleration;
		
		// Establish a floor and make sure that Mario's Y Position does not go below the floor
		if (mario.getY() >= platform && !(mario.getJumping())) {
			mario.setY(platform);
			vy = 0;
		}
		if (mario.getY() >= platform) {
			mario.setJumping(false);
		}
		
		// Key Mechanics
		// If the Key has not yet been collected, then paint the Key
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
		// If Mario collides with a Key, then change the availability of the Key to false and update the score
		// Also, change the number of keys displayed at the top by KeyDisplay and play a key sound when the key is collected
		//Key1
		if (key1.getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key1.getX() && mario.getX() <= key1.getX() + key1.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key1.getY() && mario.getY() <= key1.getY() + key1.getHeight()) {
					keyDisp.setState(keyDisp.getState() + 1);
					key1.setAvailable(false);
					score += 500;
					keySound.play();
				}
				else {
					keySound.stop();
				}
			}
		}
		//Key2
		if (key2.getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key2.getX() && mario.getX() <= key2.getX() + key2.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key2.getY() && mario.getY() <= key2.getY() + key2.getHeight()) {
					keyDisp.setState(keyDisp.getState() + 1);
					key2.setAvailable(false);
					keySound.play();
					score += 500;
				}
				else {
					keySound.stop();
				}
			}
		}
		//Key3
		if (key3.getAvailable() == true) {
			if (mario.getX() + mario.getWidth() >= key3.getX() && mario.getX() <= key3.getX() + key3.getWidth()) {
				if (mario.getY() + mario.getHeight() >= key3.getY() && mario.getY() <= key3.getY() + key3.getHeight()) {
					keyDisp.setState(keyDisp.getState() + 1);
					key3.setAvailable(false);
					keySound.play();	//ADDED2
					score += 500;
				}
				else {					//ADDED2
					keySound.stop();	//ADDED2
				}
			}
		}
				
		// Peach Mechanics
		// If Mario is within a certain range of Princess Peach's cage, then display a message that tells the player
		// how many more keys need to be collected or how to win the game
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
		
		// Paint the Pipes and update their position so that their positions are consistent with the Background
		shortPipe1.setX(background.getX() + 250);
		shortPipe1.paint(g);
		shortPipe2.setX(background.getX() + 550);
		shortPipe2.paint(g);
		longPipe.setX(background.getX() + 1600);
		longPipe.paint(g);
		
		// Pipe Mechanics
		// If Mario is within a certain range of the second short Pipe, then display a message to tell the player
		// how to enter the pipe
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
		// If Mario is within a certain range of the long Pipe, then display a message to tell the player
		// how to enter the pipe
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
		// Make sure that Mario does not go past the longPipe
		if (mario.getX() > longPipe.getX() + longPipe.getWidth() && mario.getY() >= platform) {
			mario.setX(longPipe.getX());
			mario.setY(longPipe.getY() - mario.getHeight());
		}
		
		// Flag Mechanics
		flag.setImage();
		flag.setX(background.getX() + 780);
		flag.setY(440 - flag.getHeight() + mario.getHeight());
		// If Mario collides with the flag, set Mario's spawn point to the Flag's position and increase the score
		if (mario.collide(flag) && flag.getState() != 2) {
			flag.setState(2);
			score += 500;
			spawnX = background.getX() + flag.getX() + 10;
			spawnY = flag.getY() + flag.getHeight() - mario.getHeight();
		}
		
		// Spikes Mechanics
		// Paint the Spikes and update their positions accordingly
		// If Mario collides with a set of Spikes, then Mario's State decreases by 1
		// If Mario has an ability, he loses the ability
		// If Mario is big, he becomes small
		// If Mario is small, he loses a life
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
		
		// Paint the Projectiles only when the player has pressed space
		// Move the Projectiles if the player has shot the Projectiles
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
		// If a Projectile is far enough away from Mario, then it disappears
		if (fireball.getX() >= mario.getX() + 400) {
			fireball.setOnScreen(false);
		}
		if (iceball.getX() >= mario.getX() + 400) {
			iceball.setOnScreen(false);
		}
		// If a Projectile hits the Goomba, then the Goomba dies, and the score increases
		if (fireball.getOnScreen() && fireball.collideObjects(goomba1) && goombaIsAlive) {
			goombaIsAlive = false;
			fireball.setOnScreen(false);
			score += 1000;
		}

		if (iceball.getOnScreen() && iceball.collideObjects(goomba1) && goombaIsAlive) {
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
		
		// Paint the Goomba only if it is alive
		if (goombaIsAlive) {
			goomba1.paint(g);
		}
		
		// If Goomba hits a pipe, then it changes direction
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
		if (goomba1.getX() + goomba1.getWidth() < shortPipe1.getX()) {
			goomba1.setX(shortPipe1.getX() + shortPipe1.getWidth());
		}
		if (goomba1.getX() > shortPipe2.getX() + shortPipe2.getWidth()) {
			goomba1.setX(shortPipe1.getX() + shortPipe1.getWidth());
		}
		// Test for a collision with the Goomba if the Goomba has not already been hit and is alive
		// If a collision occurs, then Mario loses his ability, and the score decreases
		if (mario.collide(goomba1) && !goomba1.getHit() && goombaIsAlive) {
			mario.setState(mario.getState() - 1);
			mario.setHasAbility(false);
			mario.setAbility("None");
			goomba1.setHit(true);
			score -= 300;
		}
		// If Mario's state gets below 0, then he loses a life
		// The life lost audio file also plays, and his invincibility to all obstacles is removed
		if (mario.getState() < 0) {
			lives--;
			if (lives != 0) {
				lifeLost.play();
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
		
		// PowerUp Mechanics
		// Move the Big and 1-UP PowerUps
		big.setX(big.getX() + big.getVX());
		oneup.setX(oneup.getX() + oneup.getVX());
		// Make sure that the Red Mushroom stays within the short Pipes and changes directions upon collisions
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
		if (big.getX() + big.getWidth() < shortPipe1.getX()) {
			big.setX(shortPipe2.getX() - big.getWidth());
		}
		if (big.getX() > shortPipe2.getX() + shortPipe2.getWidth()) {
			big.setX(shortPipe2.getX() - big.getWidth());
		}

		// Make sure that the Green Mushroom stays between the second short Pipe and the long Pipe and changes directions upon collisions
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
		if (oneup.getX() + oneup.getWidth() < shortPipe2.getX()) {
			oneup.setX(shortPipe2.getX() + shortPipe2.getWidth());
		}
		if (oneup.getX() > longPipe.getX() + longPipe.getWidth()) {
			oneup.setX(shortPipe2.getX() + shortPipe2.getWidth());
		}
		// Set the positions of the Ice and Fire Flowers to the positions of the first 2 Blocks
		ice.setX(block1.getX());
		fire.setX(block2.getX());
		
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
		// Test for PowerUp collisions and update all variables accordingly
		if (mario.collide(big) && !big.getHit()) {
			big.setHit(true);
			score += 200;
			powerUp.play();
			if (!mario.getHasAbility()) {
				mario.setState(1);
			}
		}
		if (mario.collide(ice) && !ice.getHit() && summonIce) {
			ice.setHit(true);
			score += 400;
			powerUp.play();
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Ice");
		}
		if (mario.collide(fire) && !fire.getHit() && summonFire) {
			fire.setHit(true);
			score += 400;
			powerUp.play();
			mario.setHasAbility(true);
			mario.setState(2);
			mario.setAbility("Fire");
		}
		if (mario.collide(oneup) && !oneup.getHit()) {
			oneup.setHit(true);
			score += 200;
			powerUp.play();
			lives++;
		}
		
		// Set the positions of each of the 12 Blocks
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
		mystBlock1.setX(background.getX() + 450);
		
		// Paint the 12 blocks and the Mystery Block
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
		mystBlock1.paint(g);
		
		// Set the Y Position of the Mystery Block to the correct height depending on whether or not it has been hit
		if (mystBlock1.getHasCoin()) {
			mystBlock1.setY(481);
		} else {
			mystBlock1.setY(421);
		}
		
		// Update each variable to keep track of which objects Mario is above and whether or not the platform should be updated
		// If Mario is above any of these objects, then the platform will be updated accordingly
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
		onMystBlock1 = mario.aboveMystBlock(mystBlock1, onMystBlock1, mystBlock1.getAvailable());
		
		// Update whether or not Mario is on Princess Peach's cage
		if (peach.getStateLocked() == false) {				
			onPrison = false;								
		}													
		else {
			onPrison = mario.aboveObject(peach, onPrison);
		}
		
		// If Mario is not on any of these objects, then set the platform to what it was originally
		if (!onLong && !onShort1 && !onShort2 && !onBlock1 && !onBlock2 && !onBlock3 && !onBlock4 && !onMystBlock1 && !onBlock5 && !onBlock6 && !onBlock7 && !onBlock8 && !onBlock9 && !onBlock10 && !onBlock11 && !onBlock12) {
			platform = originalPlatform;
		}
		
		// If Mario hits a block from below, then change Mario's velocity in the Y direction
		// If Mario hits Blocks 1 or 2, then a PowerUp should appear
		if (mario.hittingObjectFromBelow(block1)) {
			vy = 4;
			if (!summonIce) {
				powerUpAppears.play();	
			}
			summonIce = true;
		}
		if (mario.hittingObjectFromBelow(block2)) {
			vy = 4;
			if (!summonFire) {
				powerUpAppears.play();	
			}
			summonFire = true;
		}
		if (mario.hittingObjectFromBelow(block3) || mario.hittingObjectFromBelow(block4) || mario.hittingObjectFromBelow(block5) || mario.hittingObjectFromBelow(block6) || mario.hittingObjectFromBelow(block7) || mario.hittingObjectFromBelow(block8) || mario.hittingObjectFromBelow(block9) || mario.hittingObjectFromBelow(block10) || mario.hittingObjectFromBelow(block11) || mario.hittingObjectFromBelow(block12)) {
			vy = 4;
		} 
		
		// If Mario hits the Mystery Block from below, then give a random number of coins to the player and update the Mystery Block
		if (mario.hittingMystBlockFromBelow(mystBlock1)) { 
			if (mystBlock1.getHasCoin() == true) {	
				mystBlock1.mystHit();
				mystSound.play();
				int coinsToBeAdded = (int) (Math.random() * 8 + 1);
				coins += coinsToBeAdded * 10;
				score += coinsToBeAdded * 100;
				mystBlock1.mystHit();
			}
			vy = 4;
		}
		
		// Coin Mechanics
		coin1.setX(block5.getX() + 8);
		coin2.setX(block6.getX() + 8);
		coin3.setX(block7.getX() + 8);
		coin4.setX(block8.getX() + 8);
		coin5.setX(block12.getX() + 8);
		// Paint the Coins if they have not yet been collected
		if (!coin1.getCollided()) {
			coin1.paint(g);
		}
		if (!coin2.getCollided()) {
			coin2.paint(g);
		}
		if (!coin3.getCollided()) {
			coin3.paint(g);
		}
		if (!coin4.getCollided()) {
			coin4.paint(g);
		}
		if (!coin5.getCollided()) {
			coin5.paint(g);
		}
		// If Mario collects a coin, then a random number of coins, from 10 to 80, will be added to the Coin counter
		if (mario.collide(coin1) && !coin1.getCollided()) {
			coin1.setCollided(true);
			int coinsToBeAdded = (int) (Math.random() * 8 + 1);
			coins += coinsToBeAdded * 10;
			score += coinsToBeAdded * 100;
		}
		if (mario.collide(coin2) && !coin2.getCollided()) {
			coin2.setCollided(true);
			int coinsToBeAdded = (int) (Math.random() * 8 + 1);
			coins += coinsToBeAdded * 10;
			score += coinsToBeAdded * 100;
		}
		if (mario.collide(coin3) && !coin3.getCollided()) {
			coin3.setCollided(true);
			int coinsToBeAdded = (int) (Math.random() * 8 + 1);
			coins += coinsToBeAdded * 10;
			score += coinsToBeAdded * 100;
		}
		if (mario.collide(coin4) && !coin4.getCollided()) {
			coin4.setCollided(true);
			int coinsToBeAdded = (int) (Math.random() * 8 + 1);
			coins += coinsToBeAdded * 10;
			score += coinsToBeAdded * 100;
		}
		if (mario.collide(coin5) && !coin5.getCollided()) {
			coin5.setCollided(true);
			int coinsToBeAdded = (int) (Math.random() * 8 + 1);
			coins += coinsToBeAdded * 10;
			score += coinsToBeAdded * 100;
		}
		
		// Top Text Display (Lives, Score, Coins, and Timer)
		g.setFont(displayFont);
		
		// Check if the game should be ended
		endGame();
		
		// Update the Timer
		if (!lost && !won) {
			frameTracker++;
		}
		// Every 35 frames, 1 second in real life passes
		if (frameTracker % 35 == 0 && !lost && !won) {
			time--;
			// Every 10 seconds, Mario's invincibility to every hurtful obstacle is removed
			if (frameTracker % 350 == 0) {
				goomba1.setHit(false);
				spikes1.setHit(false);
				spikes2.setHit(false);
				spikes3.setHit(false);
			}
		}
		
		// Paint the Lives and Coin Icons
		livesicon.paint(g);
		coinicon.setScaleX(0.2);
		coinicon.setScaleY(0.2);
		coinicon.paint(g);
		
		// Paint the Number of Lives Remaining
		g.drawString("" + lives, 60, 35);
		// If Mario has no more lives, then the player loses
		// Only if the player has lost will the endGame method actually do something
		if (lives <= 0) {
			lost = true;
		}
		
		// Paint the Number of Coins Obtained
		if (coins < 10) {
			g.drawString("0" + coins, 60, 85);
		} else {
			g.drawString("" + coins, 60, 85);
		}
		// If the number of coins is 100, then the player will gain a life in exchange for 100 coins
		if (coins > 99) {
			lives++;
			coins -= 100;
		}
		
		// Paint the Score and High Score
		g.drawString("Score: " + score, 200, 30);
		g.drawString("High Score: " + highScore, 200, 80);
		if (score > highScore) {
			highScore = score;
		}
		// Prevent negative scores
		if (score < 0) {
			score = 0;
		}
		// If the timer runs out, then the player loses
		if (time <= 0) {
			time = 0;
			lives = 0;
			lost = true;
		}
		// If the player is running out of time and only has a minute left, then the time will be displayed in red
		if (time <= 60) {
			g.setColor(Color.RED);
		}
		if (time % 60 == 0 || time % 60 < 10) {
			g.drawString("" + time / 60 + ":" + "0" + time % 60, 1100, 30);
		} else {
			g.drawString("" + time / 60 + ":" + time % 60, 1100, 30);
		}
		if (lost) {
			g.setFont(gameOverFont);
			g.drawString("Game Over :( Press R to Restart!", 320, 200);
		}
		if (won) {
			g.setFont(gameOverFont);
			g.drawString("You Won! :) Press R to try for a Better Score!", 260, 200);
		}
		// Paint Arrows to hint at where the hidden PowerUps are
		g.drawString("^^^^^^", block1.getX(), block1.getY() + 60);
		
		
	}
	
	// Method to reset Mario's position if he loses a life or restarts the game
	public void resetPosition() {
		mario.setX(spawnX);
		mario.setY(spawnY);
		background.setX(0);
		background.setY(-435);
	}
	
	// Determine which Projectile Mario has shot
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
	
	// Reset the entire game
	// This method basically recreates every object so that the game can be played again
	public void reset() {
		lives = 7;
		coins = 0;
		score = 0;
		time = 300;
		frameTracker = 0;
		platform = originalPlatform;
		won = false;
		lost = false;
		spawnX = 10;
		spawnY = originalPlatform;
		otherAudioPlaying = false;
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
		key1 = new Key(100, 405);
		key1.setAvailable(true);
		key2 = new Key(680, 205);
		key2.setAvailable(true);
		key3 = new Key(1600, 305);
		key3.setAvailable(true);
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
		coin1 = new Coin(block5.getX() + 6, block5.getY() - 35);
		coin2 = new Coin(block6.getX() + 6, block6.getY() - 35);
		coin3 = new Coin(block7.getX() + 6, block7.getY() - 35);
		coin4 = new Coin(block8.getX() + 6, block8.getY() - 35);
		coin5 = new Coin(block12.getX() + 6, block12.getY() - 35);
	}
	
	// This method is called 35 times per second, so as soon as the player has lost or won, the game will be ended
	public void endGame() {
		if (lost) {
			gameOver.start();
			spawnX = 10;
			spawnY = originalPlatform;
			resetPosition();
		}
		if (won) {
			spawnX = 10;
			spawnY = originalPlatform;
			resetPosition();
		}
	}
	
	// GameRunner Class Runner
	public static void main(String[] arg) {
		GameRunner marioRemake = new GameRunner();
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
		// System.out.println(arg0.getKeyCode());
		
		// If the Right Arrow Key is pressed, then Mario moves to the right along with the Background
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
		
		// If the Left Arrow Key is pressed, the mario moves to the left along with the Background
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
		
		// If the Up Arrow Key is pressed and Mario isn't already jumping, then Mario jumps. His Y velocity is set to a negative value
		if (arg0.getKeyCode() == 38 && !mario.getJumping() && !lost && mario.getY() + mario.getHeight() >= platform && !won) {
			mario.setJumping(true);
			jumpSound.play();
			vy = -20;
		}
		
		// If the Enter Key is pressed and Mario has collected every key and is in range of the prison, then the player wins
		if (arg0.getKeyCode() == 10) {
			if (peach.getInRange() == true && !won) {
				peach.setStateLocked(false);
				won = true;
				levelClear.play();
				peach.chooseImage();
				score += 2000;
				score += time * 10;
				score += lives * 200;
			}
		}
		
		// If the player lost or won, then the game can be reset by pressing R
		if (arg0.getKeyCode() == 82 && (lost || won)) {
			reset();
		}
		
		// The game can immediately be reset by pressing \ (Testing Purposes Only)
		if (arg0.getKeyCode() == 92) {
			reset();
		}
		
		// If the SPACE Key is pressed, then a Projectile is shot if Mario has the corresponding ability
		if (arg0.getKeyCode() == 32) {
			if (mario.getAbility().equals("Fire") && !fireball.getOnScreen()) {
				updateShoot(0);
			}
			if (mario.getAbility().equals("Ice") && !fireball.getOnScreen()) {
				updateShoot(1);
			}
		}
		
		// If the Down Arrow Key is pressed and Mario is in range of a Pipe that can be entered, then Mario
		// is teleported to the other Pipe
		if (arg0.getKeyCode() == 40 && shortPipeInRange == true) {
			background.setX(background.getX() - Math.abs(longPipe.getX() - shortPipe2.getX()));
			if (background.outOfBoundsLeft() == true || background.outOfBoundsRight() == true) {
				if (background.outOfBoundsLeft() == true) {
					background.setX(0);
				}
				if (background.outOfBoundsRight() == true) {
					background.setX(1200 - background.getWidth());
				}
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
			
			platform = longPipe.getY() - 1;
			background.setX(1200 - background.getWidth());
			mario.setX(1050);
			mario.setY(longPipe.getY() - mario.getHeight() - 1);
			shortPipeInRange = false;
			pipeSound.play();
		}
		
		else if (arg0.getKeyCode() == 40 && longPipeInRange == true) {
			background.setX(background.getX() + Math.abs(longPipe.getX() - shortPipe2.getX()));
			if (background.outOfBoundsLeft() == true || background.outOfBoundsRight() == true) {
				if (background.outOfBoundsLeft() == true) {
					background.setX(0);
				}
				if (background.outOfBoundsRight() == true) {
					background.setX(1600 - background.getWidth());
				}
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
			
			platform = shortPipe2.getY() - 1;
			background.setX(0);
			mario.setX(620);
			mario.setY(shortPipe2.getY() - mario.getHeight() - 1);
			longPipeInRange = false;
			pipeSound.play();
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


