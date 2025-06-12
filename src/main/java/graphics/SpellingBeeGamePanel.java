package graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeButton;
import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.StatsButton;
import kalisz.KaliszTimes;

import logic.SpellingBee;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

/**
 * Panel for displaying and managing the Spelling Bee game UI.
 * <p>
 * Handles input from mouse and keyboard, displays letters, word list,
 * and manages interactions with the underlying SpellingBee logic object.
 * </p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 * 
 */
public class SpellingBeeGamePanel extends TemplatePanel implements KeyListener {

    /** Listeners for keyboard click events in the spelling bee game. */
    private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<>();

    /** Buttons representing the letters for the spelling bee game. */
    private ArrayList<Button> letterButtons = new ArrayList<>();

    /** The main spelling bee game logic instance backing this panel. */
    public SpellingBee spellingBeeGame;

    /** Reference to the originating SpellingBeeEnterButton that launched this panel. */
    SpellingBeeEnterButton origin;

    /**
     * Constructs a new SpellingBeeGamePanel based on the provided origin button.
     * Initializes UI components, event listeners, and loads the spelling bee game state.
     *
     * @param origin the SpellingBeeEnterButton that triggered the game panel creation
     */
    public SpellingBeeGamePanel(SpellingBeeEnterButton origin) {
        this.origin = origin;
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        System.out.println(origin.getKeyWord());
        spellingBeeGame = new SpellingBee(origin.getKeyWord());

        // Debug mode auto-win logic
        if (KaliszTimes.debugMode) {
            KaliszTimes.popup("Congratulations! " + spellingBeeGame.getKeyword() + " was the correct word!");
            origin.setFinished();
            origin.repaint();

            // Properly remove panel from parent
            getPanel().setFocusable(false);
            getPanel().setVisible(false);

            Container parent = getPanel().getParent();
            if (parent != null) {
                parent.remove(getPanel());
                parent.revalidate();
                parent.repaint();
            }

            GraphicsHandler.activeSpellingBeeInstances.remove(origin); // cleanup
            KaliszTimes.getGraphicsHandler().jump("Connections Panel"); // navigate
        }

        // Setup keyboard listener for physical keyboard input
        addKeyListener(this);
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        // UI Components
        add(new BackButton(GUIConstants.backButtonImage));

        // Add KaliszGames logo
        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        // Add View Stats button
        add(new StatsButton(GUIConstants.viewStatsButtonImage));

        // Add Delete button with mouse listener to fire BACKSPACE event
        int refDeleteX = 337;
        int refDeleteY = 753;
        Button deleteButton = new Button(GUIConstants.spelling_bee_delete_button_image, GUIConstants.scaleX(refDeleteX), GUIConstants.scaleY(refDeleteY));
        deleteButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                EventHandler.fireSpellingBeeClickEvent(getPanel(), ' ', KeyboardClickEvent.BACKSPACE);
            }
        });
        add(deleteButton);

        // Add Shuffle button with mouse listener to shuffle letters
        int refShuffleX = 485;
        int refShuffleY = 753;
        Button shuffleButton = new Button(GUIConstants.spelling_bee_shuffle_button_image, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY));
        shuffleButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                spellingBeeGame.shuffle();
                // Update letter buttons except the golden letter at index 0
                for (int i = 0; i < 6; i++) {
                    SpellingBeeButton b = (SpellingBeeButton) letterButtons.get(i + 1);
                    b.setLetter(spellingBeeGame.getLetters().get(i));
                }
                revalidate();
                repaint();

                KaliszTimes.getGraphicsHandler().getFrame().revalidate();
                KaliszTimes.getGraphicsHandler().getFrame().repaint();

                KaliszTimes.popup("Successfully shuffled letters!");
            }
        });
        add(shuffleButton);

        // Add Enter/Submit button
        int refEnterX = 586;
        int refEnterY = 753;
        Button submitButton = new Button(GUIConstants.spelling_bee_enter_button_image, GUIConstants.scaleX(refEnterX), GUIConstants.scaleY(refEnterY));
        submitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                EventHandler.fireSpellingBeeClickEvent(getPanel(), ' ', KeyboardClickEvent.ENTER);
            }
        });
        add(submitButton);

        System.out.println("SIZE " + spellingBeeGame.getLetters().size() + " " + spellingBeeGame.getKeyword());

        // Create letter buttons: golden letter and six normal letters
        // Golden letter
        int refX1 = 455;
        int refY1 = 465;
        Button goldenLetter = new SpellingBeeButton(GUIConstants.golden_letter_image, spellingBeeGame.getGoldenLetter(), GUIConstants.scaleX(refX1), GUIConstants.scaleY(refY1), getPanel());
        letterButtons.add(goldenLetter);

        // Other letters positions
        int[][] letterPositions = {
            {340, 400},
            {575, 400},
            {340, 532},
            {575, 532},
            {455, 330},
            {455, 598}
        };

        for (int i = 0; i < letterPositions.length; i++) {
            Button b = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(i + 1), GUIConstants.scaleX(letterPositions[i][0]), GUIConstants.scaleY(letterPositions[i][1]), getPanel());
            letterButtons.add(b);
        }

        // Add all letter buttons to panel
        for (Button b : letterButtons) {
            add(b);
        }

        // Add custom keyboard listener to handle spelling bee input events
        addKeyboardListener(new KeyboardClickEventListener() {
            @Override
            public void handleClick(KeyboardClickEvent e) {
                if (e.getClickType() == KeyboardClickEvent.NORMAL_KEY) {
                    spellingBeeGame.inputLetter(e.getKeyClicked());
                    System.out.println("Clicked");
                    repaint();
                }
                if (e.getClickType() == KeyboardClickEvent.BACKSPACE) {
                    spellingBeeGame.deleteLetter();
                    repaint();
                }
                if (e.getClickType() == KeyboardClickEvent.ENTER) {
                    int code = spellingBeeGame.submitWord();
                    repaint();
                    switch (code) {
                        case -3:
                            KaliszTimes.popup("Too short: below 4 characters!");
                            break;
                        case -2:
                            KaliszTimes.popup("Doesn't contain the golden letter!");
                            break;
                        case -1:
                            KaliszTimes.popup("Invalid word!");
                            break;
                        case 0:
                            KaliszTimes.popup("Already guessed this word!");
                            break;
                    }
                    if (spellingBeeGame.getWin()) {
                        KaliszTimes.popup("Congratulations! " + spellingBeeGame.getKeyword() + " was the correct word!");
                        origin.setFinished();
                        origin.repaint();

                        // Update stats on win
                        spellingBeeGame.winEvent();

                        // Properly remove panel from parent
                        getPanel().setFocusable(false);
                        getPanel().setVisible(false);

                        Container parent = getPanel().getParent();
                        if (parent != null) {
                            parent.remove(getPanel());
                            parent.revalidate();
                            parent.repaint();
                        }

                        GraphicsHandler.activeSpellingBeeInstances.remove(origin); // cleanup
                        KaliszTimes.getGraphicsHandler().goBack(); // navigate back
                    }
                }
            }
        });

        repaint();
    }

    /**
     * Paints the component, including the background, letter buttons,
     * the current input word with color highlighting, and the list of words found.
     *
     * @param g the Graphics context to use for painting
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);

        int SHIFT_UP = 25;
        graphics.drawImage(GUIConstants.spellingbee_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
        int reFFontSize = 30;

        // Display logged in username
        String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
        int refLabelX = 250;
        int refLabelY = 75;

        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.black);
        graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));

        graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(reFFontSize)));

        int refX = 1920 / 4 - 150;
		int refY = 200;

		String word = spellingBeeGame.getCurrentWord();
		char goldenChar = spellingBeeGame.getGoldenLetter();
		int goldenIndex = word.indexOf(goldenChar);

		// Use anti-aliasing for cleaner text

		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Draw the full word in black
		graphics.setColor(Color.BLACK);
		graphics.drawString(word, GUIConstants.scaleX(refX), GUIConstants.scaleY(refY));

		// Now draw the golden letter in gold at the correct position
		if (goldenIndex != -1) {
			FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());

			// Measure width up to golden letter
			int goldenXOffset = metrics.stringWidth(word.substring(0, goldenIndex));

			graphics.setColor(new Color(176, 170, 4)); // gold
			graphics.drawString(
				String.valueOf(goldenChar),
				GUIConstants.scaleX(refX + goldenXOffset),
				GUIConstants.scaleY(refY)
			);
		}

        // Draw the list of words found with golden highlight for the keyword
        graphics.setColor(Color.black);
        for (int i = 0; i < spellingBeeGame.getWordsFound().size(); i++) {
            int VERTICAL_OFFSET = 100 * i;
            int refWordListX = 1000;
            int refWordListY = 250;
            if (spellingBeeGame.getWordsFound().get(i).equals(spellingBeeGame.getKeyword()))
                graphics.setColor(new Color(176, 170, 4)); // gold
            else
                graphics.setColor(Color.BLACK);

            graphics.drawString(spellingBeeGame.getWordsFound().get(i), GUIConstants.scaleX(refWordListX), GUIConstants.scaleY(refWordListY + VERTICAL_OFFSET));
        }

        // Draw number of words found
        int refFontSize = 20;
        graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));
        int refNumOfWordsX = 1150;
        int refNumOfWordsY = 210;
        graphics.drawString("" + spellingBeeGame.getWordsFound().size(), GUIConstants.scaleX(refNumOfWordsX), GUIConstants.scaleY(refNumOfWordsY));
    }

    /**
     * Registers a listener for keyboard click events.
     *
     * @param listener the listener to add
     */
    public void addKeyboardListener(KeyboardClickEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a previously registered keyboard click event listener.
     *
     * @param listener the listener to remove
     */
    public void removeKeyboardListener(KeyboardClickEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the list of registered keyboard click event listeners.
     *
     * @return the list of listeners
     */
    public ArrayList<KeyboardClickEventListener> getListeners() {
        return listeners;
    }

    /**
     * Handles the KeyTyped event. Not used here.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles physical keyboard key press events.
     * Converts key presses to spelling bee click events that this panel can handle.
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isLetter(e.getKeyChar())) {
            EventHandler.fireSpellingBeeClickEvent(this, Character.toUpperCase(e.getKeyChar()), KeyboardClickEvent.NORMAL_KEY);
        } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            EventHandler.fireSpellingBeeClickEvent(this, ' ', KeyboardClickEvent.ENTER);
        } else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
            EventHandler.fireSpellingBeeClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
        }
    }

    /**
     * Handles the KeyReleased event. Not used here.
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Returns this panel instance.
     *
     * @return this SpellingBeeGamePanel instance
     */
    public SpellingBeeGamePanel getPanel() {
        return this;
    }

    /**
     * Requests focus for this panel to receive keyboard input.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
}
