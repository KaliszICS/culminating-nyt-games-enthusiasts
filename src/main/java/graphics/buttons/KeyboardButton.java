package graphics.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import graphics.GUIConstants;
import graphics.WordleGamePanel;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;


/**
 * A button representing a keyboard key in the Wordle game interface.
 * This button visually displays a character and sends an event to the Wordle game panel
 * when clicked. It is used for letter inputs as well as special keys like Enter or Backspace.
 * @see Button
 * @see logic.events.KeyboardClickEvent
 * @see logic.events.EventHandler
 */
public class KeyboardButton extends Button {
    BufferedImage image;
   private char character;
    int x, y;
    int clickType;
    private WordleGamePanel panel;
    
    /**
     * Constructs a keyboard button for a letter key.
     *
     * @param image      the button background image
     * @param character  the character displayed and sent on click
     * @param x          the x-position of the button
     * @param y          the y-position of the button
     * @param clickType  the type of click event to send
     * @param panel      the {@link WordleGamePanel} associated with the game
     */
    public KeyboardButton(BufferedImage image, char character, int x, int y, int clickType, WordleGamePanel panel) {
        super(image);
       
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.clickType = clickType;

        int HEIGHT_OFFSET = 25;
     

        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y), GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));
        this.character = character;

        

    }
    /**
     * Constructs a keyboard button for a special key (e.g., Enter, Backspace).
     *
     * @param image      the button background image
     * @param x          the x-position of the button
     * @param y          the y-position of the button
     * @param clickType  the type of click event to send
     * @param panel      the {@link WordleGamePanel} associated with the game
     */
     public KeyboardButton(BufferedImage image, int x, int y, int clickType, WordleGamePanel panel) {
        super(image);
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.character = ' ';
        this.clickType = clickType;

         int HEIGHT_OFFSET = 25;
         

    
        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y), GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));

        

    }
    /**
     * Paints the character onto the button.
     * This method centers the character in the middle of the button.
     *
     * @param g the {@link Graphics} context
     */
	 @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the image from the Button class
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(new Font("Arial", Font.BOLD, 25));

        // Draw character centered
        int textWidth = graphics.getFontMetrics().stringWidth("" + character);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + character, textX, textY);
    }
    //Sends character as an event
    /**
     * Sends a {@link logic.events.KeyboardClickEvent} to the game panel
     * when this button is clicked.
     *
     * @param e the mouse event that triggered the press
     */
    @Override
	public void mousePressed(MouseEvent e) {
		 EventHandler.fireWordleClickEvent(panel, this.character, this.clickType);
	}
        /**
     * Returns the character associated with this button.
     *
     * @return the button's character (or space if Enter/Backspace)
     */
    public char getCharacter() {
        return this.character;
    }
    
}
