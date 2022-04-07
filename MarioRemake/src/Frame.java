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
	
	//comment
	
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
	
	
	// main method with code and movement that is called 60 times per second
	public void paint(Graphics g) {
		super.paintComponent(g);
		Character c = new Character(200, 200);
		bg.paint(g);
		c.paint(g);
		
		
	}
	
	// creates a Frame object, makes class runnable
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	// Frame constructor
	public Frame() {
		JFrame f = new JFrame("Mario Remake");
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
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}