package graphics.buttons;

import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import graphics.GUIConstants;
import graphics.utils.StretchIcon;

/**
 * A base class for image-based buttons in the Kalisz Times game UI.
 *
 * <p>This class extends {@link JLabel} and implements {@link MouseListener}
 * to create scalable, interactive buttons with hover cursor behavior.
 * Buttons are displayed using {@link StretchIcon} to allow resolution-independent scaling.</p>
 *
 * <p>This class is intended to be subclassed for buttons that implement custom behavior
 * on mouse events such as clicks or presses.</p>
 *
 * <p>Supports multiple constructors for positioning and optional action commands
 * (currently unused).</p>
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class Button extends JLabel implements MouseListener {
    private BufferedImage image;
    private int x, y;

    /**
     * Creates a button with the given image.
     * Automatically sets the size to match the image dimensions.
     *
     * @param image the image to use for this button
     */
    public Button(BufferedImage image) {
        this.image = image;
        this.setSize(image.getWidth(), image.getHeight());
        this.setIcon(new StretchIcon(image, true));
        this.addMouseListener(this);
    }

    /**
     * Creates a button with the given image and screen position.
     * Scales the image dimensions based on {@link GUIConstants}.
     *
     * @param image the image to use for this button
     * @param x the x-coordinate of the button
     * @param y the y-coordinate of the button
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
     * Creates a button with the given image and optional action command.
     * Currently, the action command is unused but can support future features.
     *
     * @param image the image to use for this button
     * @param actionCommand a string identifier for button actions
     */
    public Button(BufferedImage image, String actionCommand) {
        this.image = image;
        this.setSize(image.getWidth(), image.getHeight());
        this.setIcon(new StretchIcon(image, true));
        this.addMouseListener(this);
    }

    /**
     * Returns the current image used by this button.
     *
     * @return the button's image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets a new image for this button and updates the icon.
     *
     * @param image the new image to use
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        this.setIcon(new StretchIcon(image, true));
    }

    /**
     * Called when the mouse is clicked on this button.
     * Default implementation does nothing.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // No default action
    }

    /**
     * Called when the mouse is pressed down on this button.
     * Intended to be overridden by subclasses.
     *
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // To be overridden by subclass
    }

    /**
     * Called when the mouse is released on this button.
     * Default implementation does nothing.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No default action
    }

    /**
     * Called when the mouse enters the button area.
     * Changes the cursor to a hand to indicate interactivity.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Called when the mouse exits the button area.
     * Default implementation does nothing.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // No default action
    }
}
