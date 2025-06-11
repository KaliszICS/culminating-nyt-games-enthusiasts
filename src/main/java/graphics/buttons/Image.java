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

/**
 * A JLabel-based component for displaying a scalable image in the Kalisz Times game UI.
 * <p>
 * This class wraps a {@link BufferedImage} and uses {@link StretchIcon} to maintain aspect ratio while scaling.
 * The image is automatically positioned and scaled based on GUI constants.
 */
public class Image extends JLabel {
	private BufferedImage image;
	private int x, y;
	
	/**
     * Constructs an Image label using the given image and position.
     * The image is scaled according to the GUI resolution using {@link GUIConstants}.
     *
     * @param image the {@code BufferedImage} to display
     * @param x     the x-coordinate for placement
     * @param y     the y-coordinate for placement
     */
	public Image(BufferedImage image, int x, int y) {
		this.image = image;
	
		this.x = x;
		this.y = y;
		this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
		this.setIcon(new StretchIcon(image, true));
		
	}
	/**
     * Returns the image currently displayed by this label.
     *
     * @return the image associated with this component
     */
	public BufferedImage getImage() {
		return image;
	}
	/**
     * Updates the image displayed by this label and refreshes the icon.
     *
     * @param image the new image to display
     */
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setIcon(new StretchIcon(image, true));
	}


}
