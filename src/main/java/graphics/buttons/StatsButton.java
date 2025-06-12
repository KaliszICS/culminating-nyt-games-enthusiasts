package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;

import kalisz.KaliszTimes;

/**
 * A button that navigates the user to the Leaderboard Panel when clicked.
 * 
 * It is positioned based on predefined coordinates and scaled according to GUIConstants.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class StatsButton extends Button {

    /**
     * Creates a StatsButton with the given background image.
     * Sets the button's position and size based on reference coordinates.
     *
     * @param image the background image for the button
     */
    public StatsButton(BufferedImage image) {
        super(image);

        int refX = 1600;
        int refY = 20;
        int refWidth = getWidth();
        int refHeight = getHeight();

        setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));
    }

    /**
     * Handles mouse press events by jumping to the Leaderboard Panel.
     *
     * @param e the mouse event triggering the press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        KaliszTimes.getGraphicsHandler().jump("Leaderboard Panel");
    }
}
