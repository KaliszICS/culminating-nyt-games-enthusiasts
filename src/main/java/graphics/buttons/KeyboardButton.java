package graphics.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import graphics.WordleGamePanel;
import logic.events.EventHandler;

/**
 * A clickable button representing a key on the Wordle game keyboard.
 * Displays a letter or special key, and notifies the game panel when clicked.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class KeyboardButton extends Button {
    BufferedImage image;
    private char character;
    int x, y;
    int clickType;
    private WordleGamePanel panel;
    private Color color;

    /**
     * Creates a button for a letter key.
     *
     * @param image      the background image
     * @param character  the key's character
     * @param x          x-position on screen
     * @param y          y-position on screen
     * @param clickType  type of click event
     * @param panel      the Wordle game panel
     */
    public KeyboardButton(BufferedImage image, char character, int x, int y, int clickType, WordleGamePanel panel) {
        super(image);
        this.panel = panel;
        this.color = Color.gray;
        this.x = x;
        this.y = y;
        this.clickType = clickType;
        this.character = character;

        int HEIGHT_OFFSET = 25;
        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y),
                GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));
    }

    /**
     * Creates a button for a special key (e.g. Enter or Backspace).
     *
     * @param image      the background image
     * @param x          x-position on screen
     * @param y          y-position on screen
     * @param clickType  type of click event
     * @param panel      the Wordle game panel
     */
    public KeyboardButton(BufferedImage image, int x, int y, int clickType, WordleGamePanel panel) {
        super(image);
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.character = ' ';
        this.clickType = clickType;
        this.color = Color.gray;

        int HEIGHT_OFFSET = 25;
        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y),
                GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));
    }

    /**
     * Paints the button and its character. Fills the background based on game state.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        String color = panel.getKeyColours().get(getCharacter());
        if (color != null) {
            if (color.equals("green")) {
                graphics.setColor(Color.green);
            } else if (color.equals("yellow")) {
                graphics.setColor(new Color(176, 170, 4));
            } else if (color.equals("grey")) {
                graphics.setColor(Color.gray);
            }
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }

        graphics.setColor(Color.black);
        graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(25)));

        int textWidth = graphics.getFontMetrics().stringWidth("" + character);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + character, textX, textY);
    }

    /**
     * Triggers a Wordle click event when the button is pressed.
     *
     * @param e the mouse press event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        EventHandler.fireWordleClickEvent(panel, this.character, this.clickType);
    }

    /**
     * Gets the character associated with the key.
     *
     * @return the key character
     */
    public char getCharacter() {
        return this.character;
    }
}
