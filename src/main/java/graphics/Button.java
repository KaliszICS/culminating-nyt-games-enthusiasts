package graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import kalisz.KaliszTimes;


public class Button extends JLabel implements MouseListener {
	private BufferedImage image;
	
	public Button(BufferedImage image) {
		this.image = image;
		this.setIcon(new StretchIcon(image, true));
		this.addMouseListener(this);
	}


	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setIcon(new StretchIcon(image, true));
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	//	KaliszTimes.getAudioHandler().playSound("resources/button_sound.wav");
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
}
