package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Button extends JLabel {
	private BufferedImage image;
	
	public Button(BufferedImage image) {
		this.image = image;
		this.setIcon(new ImageIcon(image));
	}

//In development, trying to make it so there's a tint on the hover	
	public void hover(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(Color.red);
		g2D.drawRect(100, 100, 100, 100);
		g2D.dispose();
		//repaint();
	}
	public void unhover(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.white);
		g2D.clearRect(100, 100, 100, 100);
		//repaint();
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setIcon(new ImageIcon(image));
	}
	
	
}
