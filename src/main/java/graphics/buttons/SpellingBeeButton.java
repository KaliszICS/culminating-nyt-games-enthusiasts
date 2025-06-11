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
 * Represents an interactive button used in the Spelling Bee game interface.
 * This button displays a single letter and sends a click event to the associated
 * {@link SpellingBeeGamePanel} when pressed.
 * 
 * @see Button
 * @see EventHandler
 * @see SpellingBeeGamePanel
 */
public class SpellingBeeButton extends Button {
    char letter;
    SpellingBeeGamePanel panel;

    /**
     * Constructs a new Spelling Bee button with the given image, letter, position, and game panel.
     *
     * @param image  the background image for the button
     * @param letter the letter to display on the button
     * @param x      the x-position of the button
     * @param y      the y-position of the button
     * @param panel  the game panel that handles button click events
     */
    public SpellingBeeButton(BufferedImage image, char letter, int x, int y, SpellingBeeGamePanel panel) {
        super(image);
        this.letter = letter;
        this.panel = panel;
        this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
        
    }
    /**
     * Handles mouse press events by firing a Spelling Bee click event.
     *
     * @param e the {@link MouseEvent} object describing the interaction
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse clicked");
		EventHandler.fireSpellingBeeClickEvent(panel, letter, KeyboardClickEvent.NORMAL_KEY);
		
	}
    /**
     * Paints the button and its letter centered on the image.
     *
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the image from the Button class
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(new Font("Arial", Font.BOLD, 25));

        // Draw character centered
        int textWidth = graphics.getFontMetrics().stringWidth("" + letter);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + letter, textX, textY);
    }
    /**
     * Returns the letter associated with this button.
     *
     * @return the letter displayed on the button
     */
    public char getLetter() {
        return this.letter;
    }
    /**
     * Sets the letter associated with this button.
     *
     * @param letter the new letter to display and send on click
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }
}
