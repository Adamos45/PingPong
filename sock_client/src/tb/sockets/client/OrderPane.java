package tb.sockets.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class OrderPane extends JPanel implements ActionListener,KeyListener {
	
	static Image image;
	static Graphics2D device;
	static Graphics2D buffer;
	static int width, height;
	static char c;
	static Rakietka rocket;
	static Rakietka rocket2;
	static Pilka pilka;
	Timer timer;
	
	public OrderPane() {
		setBackground(new Color(255, 255, 240));
		timer = new Timer(10,this);
		addKeyListener(this);
	}
	void initialize()
	{
		width = getWidth();
		height = getHeight();
		rocket = new Rakietka(true);
		rocket2 = new Rakietka(false);
		pilka = new Pilka();
		Thread t = new Thread(rocket);
		Thread t2 = new Thread(rocket2);
		Thread t3 = new Thread(pilka);
		timer.addActionListener(rocket);
		timer.addActionListener(rocket2);
		timer.addActionListener(pilka);
		timer.start();
		t.start();
		t2.start();
		t3.start();
		
		image = createImage(width, height);
		buffer = (Graphics2D) image.getGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		device = (Graphics2D) getGraphics();
		device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		device.drawImage(image, 0, 0, null);
		buffer.clearRect(0, 0, width, height);
		buffer.setBackground(Color.WHITE);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(c=='a')
			rocket.setObjLocation(-5);
		else
		if(c=='d')
			rocket.setObjLocation(5);	
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		c = arg0.getKeyChar();
		if(c=='a')
			rocket.setObjLocation(-15);
		else
		if(c=='d')
			rocket.setObjLocation(15);
	}
}