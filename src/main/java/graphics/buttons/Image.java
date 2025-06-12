package graphics.buttons;

import java.awt.image.BufferedImage;
import javax.swing.JLabel;

import graphics.GUIConstants;
import graphics.utils.StretchIcon;

/**
 * A JLabel that displays a scaled image for the Kalisz Times UI.
 * Uses {@link StretchIcon} for responsive resizing.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class Image extends JLabel {
    private BufferedImage image;

    /**
     * Creates an image label at the given position.
     * Scales the image using {@link GUIConstants}.
     *
     * @param image the image to display
     * @param x     x-coordinate for positioning
     * @param y     y-coordinate for positioning
     */
    public Image(BufferedImage image, int x, int y) {
        this.image = image;
        this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
        this.setIcon(new StretchIcon(image, true));
    }

    /**
     * Returns the current image.
     *
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets a new image and updates the display.
     *
     * @param image the new image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        this.setIcon(new StretchIcon(image, true));
    }
}
