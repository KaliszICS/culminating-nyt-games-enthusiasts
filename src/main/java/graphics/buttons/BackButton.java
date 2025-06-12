package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import kalisz.KaliszTimes;

/**
 * A button that navigates the user to the previous screen in the Kalisz Times game
 * when clicked.
 *
 * <p>This button uses a provided image and is positioned and sized using GUIConstants
 * scaling methods to ensure consistent layout across different screen sizes.</p>
 *
 * <p>When pressed, it calls {@code goBack()} on the graphics handler of the Kalisz Times
 * game to return to the previous screen.</p>
 *
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class BackButton extends Button {

    /**
     * Creates a BackButton with the given image.
     * The button's bounds are scaled using predefined reference values to
     * maintain a consistent appearance across different resolutions.
     *
     * @param image the image used to display the button
     */
    public BackButton(BufferedImage image) {
        super(image);

        int refX = 41;
        int refY = 35;
        int refWidth = 74;
        int refHeight = 59;

        setBounds(
            GUIConstants.scaleX(refX),
            GUIConstants.scaleY(refY),
            GUIConstants.scaleX(refWidth),
            GUIConstants.scaleY(refHeight)
        );
    }

    /**
     * Called when the mouse is pressed on this button.
     * Navigates to the previous screen using the graphics handler.
     *
     * @param e the mouse event triggered when the user clicks the button
     */
    @Override
    public void mousePressed(MouseEvent e) {
        KaliszTimes.getGraphicsHandler().goBack();
    }
}
