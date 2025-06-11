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
 * The base button class for the Kalisz Times game UI.
 * 
 * This class extends {@link JLabel} and implements {@link MouseListener} to provide
 * visual components that behave like interactive buttons using custom images.
 * 
 * The button supports scaling, custom cursor changes on hover, and can be subclassed
 * to add specific behaviors on mouse interaction.
 */
public class Button extends JLabel implements MouseListener {
	private BufferedImage image;
	private int x, y;
	
	/**
     * Constructs a new Button using the given image.
     * The button will be sized to the image and displayed with a stretchable icon.
     *
     * @param image the image used for the button's appearance
     */
	public Button(BufferedImage image) {
		this.image = image;
		this.setSize(image.getWidth(), image.getHeight());
		this.setIcon(new StretchIcon(image, true));
		this.addMouseListener(this);
	}

	/**
     * Constructs a new Button with the given image and position.
     * Uses {@link GUIConstants} to scale the size according to the GUI resolution.
     *
     * @param image the image used for the button's appearance
     * @param x     the x-coordinate for the button's position
     * @param y     the y-coordinate for the button's position
     */
	public Button(BufferedImage image, int x, int y) {
		this.image = image;
		
		this.x = x;
		this.y = y;
		

		this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
		this.setIcon(new StretchIcon(image, true));
		this.addMouseListener(this);
	}

	/**
     * Constructs a new Button using the given image and associates an action command.
     * Currently the action command is unused, but can be useful for future expansion.
     *
     * @param image          the image used for the button's appearance
     * @param actionCommand  an optional string command identifier
     */
	public Button(BufferedImage image, String actionCommand) {
		this.image = image;
		this.setSize(image.getWidth(), image.getHeight());
		this.setIcon(new StretchIcon(image, true));
		this.addMouseListener(this);
	}

	/**
     * Returns the image used for this button.
     *
     * @return the button's image
     */
	public BufferedImage getImage() {
		return image;
	}

	/**
     * Updates the image used by this button and sets a new icon.
     *
     * @param image the new image to display
     */
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setIcon(new StretchIcon(image, true));
	}

	/**
     * Invoked when the mouse has been clicked on the button.
     * Currently contains commented-out audio functionality.
     *
     * @param e the mouse event
     */
	@Override
	public void mouseClicked(MouseEvent e) {
	//	KaliszTimes.getAudioHandler().playSound("resources/button_sound.wav");
	}

	/**
     * Invoked when the mouse is pressed down on the button.
     * Intended to be overridden by subclasses.
     *
     * @param e the mouse event
     */
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/**
     * Invoked when the mouse is released after a press on the button.
     * Currently does nothing by default.
     *
     * @param e the mouse event
     */
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/**
     * Invoked when the mouse enters the button area.
     * Changes the cursor to a hand to indicate interactivity.
     *
     * @param e the mouse event
     */
	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}

	/**
     * Invoked when the mouse exits the button area.
     * Currently does nothing by default.
     *
     * @param e the mouse event
     */
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
}
