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
	boolean platform;
	
	// colors and fonts
	Background bg = new Background(0,0);
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

	// variables and trackers

	Character c = new Character(10, 665);
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		Pipe p = new Pipe(400, 400, false, false);
		Pipe p2 = new Pipe(600, 400, true, false);
		p.setScaleX(0.7);
		p.setScaleY(0.7);
		p2.setScaleX(0.7);
		p2.setScaleY(0.7);
		bg.paint(g);
		
		c.paint(g);
		c.setScaleX(0.3);
		c.setScaleY(0.3);

		p.paint(g);
		p2.paint(g);
		Key k1 = new Key(400, 100);
		k1.paint(g);
		Block b = new Block(300, 300, "", false);
		b.setScaleX(0.15);
		b.setScaleY(0.15);
		b.paint(g);
		Flag f = new Flag(100, 200);
		f.paint(g);
		
		if (c.getY() == 665) {
			platform = true;
		}
		
		else {
			platform = false;
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
		f.setResizable(false);
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
			c.setImage(true);
			c.rightPressed(true);
		}
		if (arg0.getKeyCode() == 37) {
			c.setImage(true);
			c.leftPressed(true);
		}
		if (arg0.getKeyCode() == 38) {
			c.upPressed(true);
			while (platform == false) {
				c.upPressed(false);
				break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == 39) {
			c.setImage(false);
			c.rightPressed(false);
		}
		if (arg0.getKeyCode() == 37) {
			c.setImage(false);
			c.leftPressed(false);
		}  
		if (arg0.getKeyCode() == 38) {
			c.upReleased();
		}
		

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}