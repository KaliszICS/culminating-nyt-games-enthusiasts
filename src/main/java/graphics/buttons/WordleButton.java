package graphics.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.ConnectionsPanel;
import graphics.GUIConstants;
import graphics.GraphicsHandler;
import graphics.WordleGamePanel;

import kalisz.KaliszTimes;

/**
 * A button that launches or resumes a Wordle game instance.
 * <p>
 * When clicked, this button either creates a new Wordle game panel or
 * brings an existing one to focus. Once the Wordle game linked to this button
 * is finished, clicking the button instead sends the answer word to the
 * Connections game for selection.
 * </p>
 * <p>
 * This class ensures consistent UI feedback, including color-coding for
 * revealed words and indication when the game is in progress.
 * </p>
 * 
 * @see Button
 * @see WordleGamePanel
 * @see ConnectionsPanel
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class WordleButton extends Button {
    
    private static final int COLOR_RECT_SIZE = 100;          // Size of color fill rectangle
    private static final int FONT_SIZE_IN_PROGRESS = 15;     // Font size for "In Progress" text
    private static final int FONT_SIZE_FINISHED = 25;        // Font size for finished answer display
    private static final int IN_PROGRESS_TEXT_Y_DIVISOR = 4; // Divisor to position "In Progress" text vertically
    private static final int FINISHED_TEXT_VERTICAL_OFFSET = 4; // Offset to vertically center finished text
    
    private WordleGamePanel wordleInstance;
    private boolean finished = false;
    private String wordleAnswer;
    private int uniqueID;

    /**
     * Constructs a WordleButton with a specified image, unique identifier, and answer word.
     * 
     * @param image        The background image of the button.
     * @param uniqueID     Unique identifier correlating this button to a word in the Connections game.
     * @param wordleAnswer The answer word revealed after the Wordle game is completed.
     */
    public WordleButton(BufferedImage image, int uniqueID, String wordleAnswer) {
        super(image);
        this.uniqueID = uniqueID;
        this.wordleAnswer = wordleAnswer;
    }
    
    /**
     * Handles mouse press events.
     * <p>
     * If no Wordle instance is active for this button, this method creates a new one,
     * tracks it, and adds its panel to the graphics handler. Otherwise, it focuses on
     * the existing game panel associated with this button.
     * </p>
     * 
     * @param e The mouse event triggering the press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!GraphicsHandler.activeWordleInstances.containsKey(this)) {
            // Create and track new Wordle game panel instance
            wordleInstance = new WordleGamePanel(this);
            GraphicsHandler.activeWordleInstances.put(this, wordleInstance);
            KaliszTimes.getGraphicsHandler().addPanel(wordleInstance, "Wordle Game Panel " + uniqueID);
        } else {
            // Focus existing Wordle game panel
            wordleInstance = GraphicsHandler.activeWordleInstances.get(this);
            KaliszTimes.getGraphicsHandler().jump("Wordle Game Panel " + uniqueID);
        }
        wordleInstance.focus();
    }

    /**
     * Paints the button's appearance.
     * <p>
     * - If the associated answer word is revealed, the background is colored
     *   based on the word's category (blue, green, yellow, purple).
     * - If the button's word is currently guessed in Connections, the background
     *   is greyed out.
     * - When the Wordle game is in progress, displays "In Progress" in red.
     * - When finished, displays the answer word centered in black.
     * </p>
     * 
     * @param g The Graphics context used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        // Color background if word is revealed with category color
        if (ConnectionsPanel.connectionsGame.isWordRevealed(wordleAnswer)) {
            String color = ConnectionsPanel.connectionsGame.getWordColor(wordleAnswer);
            if ("blue".equals(color)) {
                graphics.setColor(Color.BLUE);
            } else if ("green".equals(color)) {
                graphics.setColor(Color.GREEN);
            } else if ("yellow".equals(color)) {
                graphics.setColor(Color.YELLOW);
            } else if ("purple".equals(color)) {
                graphics.setColor(Color.MAGENTA);
            } else {
                graphics.setColor(Color.LIGHT_GRAY);
            }
            graphics.fillRect(0, 0, COLOR_RECT_SIZE, COLOR_RECT_SIZE);
        }

        // Grey out if currently selected in Connections guess
        if (ConnectionsPanel.connectionsGame.currentGuess.contains(uniqueID)) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(0, 0, COLOR_RECT_SIZE, COLOR_RECT_SIZE);
        }

        if (!finished) {
            super.paintComponent(g);
            if (wordleInstance != null && wordleInstance.wordleGame != null && !wordleInstance.wordleGame.getWin()) {
                graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(FONT_SIZE_IN_PROGRESS)));
                int textWidth = graphics.getFontMetrics().stringWidth("In Progress");
                int textHeight = graphics.getFontMetrics().getAscent();
                int textX = (getWidth() - textWidth) / 2;
                int textY = (getHeight() + textHeight) / IN_PROGRESS_TEXT_Y_DIVISOR;
                graphics.setColor(Color.RED);
                graphics.drawString("In Progress", GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
            }
        } else {
            graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(FONT_SIZE_FINISHED)));
            graphics.setColor(Color.BLACK);

            // Draw answer word centered
            int textWidth = graphics.getFontMetrics().stringWidth(wordleAnswer);
            int textHeight = graphics.getFontMetrics().getAscent();
            int textX = (getWidth() - textWidth) / 2;
            int textY = (getHeight() + textHeight) / 2 - FINISHED_TEXT_VERTICAL_OFFSET;

            graphics.drawString(wordleAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
        }
    }

    /**
     * Returns the Wordle game panel instance associated with this button.
     * 
     * @return The {@link WordleGamePanel} instance linked to this button.
     */
    public WordleGamePanel getWordleInstance() {
        return wordleInstance;
    }

    /**
     * Marks this button as finished.
     * <p>
     * Removes the current mouse listener and adds a new one that, upon click,
     * selects this word in the Connections game and repaints the button.
     * </p>
     */
    public void setFinished() {
        finished = true;
        removeMouseListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ConnectionsPanel.connectionsGame.selectWord(uniqueID);
                repaint();
            }
        });
    }

    /**
     * Returns the answer word for this Wordle button.
     * 
     * @return The answer word as a String.
     */
    public String getWordleAnswer() {
        return wordleAnswer;
    }
}
