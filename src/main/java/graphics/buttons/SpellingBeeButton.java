package graphics.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import graphics.SpellingBeeGamePanel;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;

/**
 * A button representing a letter key in the Spelling Bee game.
 * Displays a single character and notifies the game panel when clicked.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class SpellingBeeButton extends Button {
    char letter;
    SpellingBeeGamePanel panel;

    /**
     * Creates a Spelling Bee button with a given image, letter, position, and panel.
     *
     * @param image  button background image
     * @param letter the letter displayed on the button
     * @param x      x-coordinate of the button
     * @param y      y-coordinate of the button
     * @param panel  the Spelling Bee game panel for event handling
     */
    public SpellingBeeButton(BufferedImage image, char letter, int x, int y, SpellingBeeGamePanel panel) {
        super(image);
        this.letter = letter;
        this.panel = panel;
        this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
    }

    /**
     * Fires a click event to the Spelling Bee game panel when pressed.
     *
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse clicked");
        EventHandler.fireSpellingBeeClickEvent(panel, letter, KeyboardClickEvent.NORMAL_KEY);
    }

    /**
     * Paints the letter centered on the button image.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        int refFontSize = 25;
        graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));

        int textWidth = graphics.getFontMetrics().stringWidth("" + letter);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + letter, textX, textY);
    }

    /**
     * Returns the letter displayed by this button.
     *
     * @return the letter character
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Sets the letter displayed by this button.
     *
     * @param letter the new letter character
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }
}
