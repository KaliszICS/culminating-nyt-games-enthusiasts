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
import graphics.SpellingBeeGamePanel;

import kalisz.KaliszTimes;

/**
 * Button to start or resume a Spelling Bee game.
 * <p>
 * Clicking this button either launches a new Spelling Bee game panel,
 * resumes an existing one, or selects the answer word if the game is finished.
 * </p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class SpellingBeeEnterButton extends Button {

    /** Width and height of the colored background when a word is revealed */
    private static final int COLOR_BG_SIZE = 100;

    /** Font size when displaying "In Progress" text */
    private static final int IN_PROGRESS_FONT_SIZE = 15;

    /** Font size when displaying the answer word */
    private static final int ANSWER_FONT_SIZE = 20;

    /** Unique identifier correlating this button with the game */
    private final int uniqueID;

    /** The answer word tied to this button */
    private final String spellingBeeAnswer;

    /** Reference to the active Spelling Bee game panel for this button */
    private SpellingBeeGamePanel spellingBeeInstance;

    /** Flag indicating if the Spelling Bee game is finished */
    private boolean finished = false;

    /**
     * Constructs a Spelling Bee enter button.
     *
     * @param image             Background image for the button.
     * @param uniqueID          Unique identifier for this button.
     * @param spellingBeeAnswer The answer word associated with this button.
     */
    public SpellingBeeEnterButton(BufferedImage image, int uniqueID, String spellingBeeAnswer) {
        super(image);
        this.uniqueID = uniqueID;
        this.spellingBeeAnswer = spellingBeeAnswer;
    }

    /**
     * Handles mouse press event.
     * <p>
     * If no active Spelling Bee game exists for this button, creates one.
     * Otherwise, focuses on the existing game panel.
     * </p>
     *
     * @param e Mouse event triggered on click.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!GraphicsHandler.activeSpellingBeeInstances.containsKey(this)) {
            spellingBeeInstance = new SpellingBeeGamePanel(this);
            GraphicsHandler.activeSpellingBeeInstances.put(this, spellingBeeInstance);
            KaliszTimes.getGraphicsHandler().addPanel(spellingBeeInstance, "Spelling Bee Game Panel " + uniqueID);
        } else {
            spellingBeeInstance = GraphicsHandler.activeSpellingBeeInstances.get(this);
            KaliszTimes.getGraphicsHandler().jump("Spelling Bee Game Panel " + uniqueID);
        }

        spellingBeeInstance.focus();
    }

    /**
     * Custom paint method to display game status and answer word.
     * <p>
     * Shows colored background if the answer word is revealed,
     * grey overlay if currently selected,
     * "In Progress" text if game is ongoing,
     * and the answer word if finished.
     * </p>
     *
     * @param g Graphics context to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        // Draw colored background if word is revealed
        if (ConnectionsPanel.connectionsGame.isWordRevealed(spellingBeeAnswer)) {
            String colorKey = ConnectionsPanel.connectionsGame.getWordColor(spellingBeeAnswer);

            if ("blue".equals(colorKey)) {
                graphics.setColor(Color.BLUE);
            } else if ("green".equals(colorKey)) {
                graphics.setColor(Color.GREEN);
            } else if ("yellow".equals(colorKey)) {
                graphics.setColor(Color.YELLOW);
            } else if ("purple".equals(colorKey)) {
                graphics.setColor(Color.MAGENTA);
            }

            graphics.fillRect(0, 0, COLOR_BG_SIZE, COLOR_BG_SIZE);
        }

        // Overlay grey if this button is currently selected in guesses
        if (ConnectionsPanel.connectionsGame.currentGuess.contains(uniqueID)) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(0, 0, COLOR_BG_SIZE, COLOR_BG_SIZE);
        }

        if (!finished) {
            // Draw base button image
            super.paintComponent(g);

            // If game is in progress, show "In Progress" text
            if (spellingBeeInstance != null
                    && spellingBeeInstance.spellingBeeGame != null
                    && !spellingBeeInstance.spellingBeeGame.getWin()) {

                graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(IN_PROGRESS_FONT_SIZE)));

                String inProgressText = "In Progress";
                int textWidth = graphics.getFontMetrics().stringWidth(inProgressText);
                int textHeight = graphics.getFontMetrics().getAscent();

                int textX = (getWidth() - textWidth) / 2;
                int textY = (getHeight() + textHeight) / 4;

                graphics.setColor(Color.RED);
                graphics.drawString(inProgressText, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
            }
        } else {
            // If finished, display the answer word centered on the button
            graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(ANSWER_FONT_SIZE)));
            graphics.setColor(Color.BLACK);

            int textWidth = graphics.getFontMetrics().stringWidth(spellingBeeAnswer);
            int textHeight = graphics.getFontMetrics().getAscent();

            int textX = (getWidth() - textWidth) / 2;
            int textY = (getHeight() + textHeight) / 2 - 4;

            graphics.drawString(spellingBeeAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
        }
    }

    /**
     * Returns the active Spelling Bee game panel linked to this button.
     *
     * @return SpellingBeeGamePanel instance or null if none.
     */
    public SpellingBeeGamePanel getSpellingBeeInstance() {
        return spellingBeeInstance;
    }

    /**
     * Marks the button as finished and updates click behavior.
     * <p>
     * Removes the existing mouse listener and adds a new one
     * that selects the answer word in the Connections game.
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
     * Returns the answer word associated with this button.
     *
     * @return The answer word string.
     */
    public String getKeyWord() {
        return spellingBeeAnswer;
    }
}
