package graphics.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import graphics.GUIConstants;
import graphics.utils.StretchIcon;
import kalisz.KaliszTimes;


public class Image extends JLabel {
	private BufferedImage image;
	private int x, y;
	
	public Image(BufferedImage image, int x, int y) {
		this.image = image;
	
		this.x = x;
		this.y = y;
		this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
		this.setIcon(new StretchIcon(image, true));
		
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setIcon(new StretchIcon(image, true));
	}


}
