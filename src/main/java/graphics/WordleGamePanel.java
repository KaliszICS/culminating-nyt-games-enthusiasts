package graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Image;
import graphics.buttons.KeyboardButton;
import graphics.buttons.StatsButton;
import graphics.buttons.WordleButton;

import javafx.application.Platform;

import kalisz.KaliszTimes;
import logic.Wordle;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

/**
 * WordleGamePanel manages the GUI and logic interaction for a single Wordle game instance.
 * 
 * This panel handles:
 * 
 *  Rendering the Wordle grid and keyboard
 *  Receiving input from both the physical and on-screen keyboard
 *  Displaying feedback with colors for guesses (green, yellow, grey)
 *  Managing game state transitions such as win/loss and moving between rows
 * 
 * 
 * It listens for keyboard events, handles input logic, and interacts with the
 * {@link logic.Wordle} game logic backend.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * 
 */
public class WordleGamePanel extends TemplatePanel implements KeyListener {

    /** The origin button that launched this Wordle game instance */
    WordleButton origin;

    /** On-screen keyboard buttons */
    ArrayList<KeyboardButton> keyboardButtons = new ArrayList<>();

    /** Listeners for keyboard click events from the on-screen keyboard */
    private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<>();

    /** Stores letters entered for each cell in the grid */
    private String[][] grid = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];

    /** Stores color feedback for each cell in the grid ("green", "yellow", "grey") */
    private String[][] gridColours = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];

    /** Queue of letters entered in the current guess row */
    private ArrayList<Character> lettersInQueue = new ArrayList<>();

    /** Tracks the current guess row number (0 to max rows - 1) */
    private int rowNumber = 0;

    /** Logic backend instance for Wordle game */
    public Wordle wordleGame;

	/** HashMap that stores maps each character to a colour for keyboard wordle integration */
    private HashMap<Character, String> keyColours = new HashMap<>();


    /**
     * Constructs a WordleGamePanel with the specified origin button.
     * Initializes the panel size, layout, UI components, and game backend.
     * Adds keyboard listeners for both physical and on-screen input.
     *
     * @param origin the WordleButton instance which launched this game
     */
    public WordleGamePanel(WordleButton origin) {
        this.origin = origin;
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        add(new BackButton(GUIConstants.backButtonImage));

        // Add KaliszGames Logo centered near top of panel
        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        // Add Stats button
        add(new StatsButton(GUIConstants.viewStatsButtonImage));

        // Load on-screen keyboard buttons
        loadKeyboard();

        // Add listener for physical keyboard input
        addKeyListener(this);

        // Initialize Wordle backend game instance with the correct answer
        this.wordleGame = new Wordle(origin.getWordleAnswer());

        // Debug mode: auto-win the game and close the panel
        if (KaliszTimes.debugMode) {
            KaliszTimes.popup("Congratulations! " + wordleGame.getWord() + " was the correct word!");
            origin.setFinished();
            origin.repaint();

            getPanel().setFocusable(false);
            getPanel().setVisible(false);

            Container parent = getPanel().getParent();
            if (parent != null) {
                parent.remove(getPanel());
                parent.revalidate();
                parent.repaint();
            }

            GraphicsHandler.activeWordleInstances.remove(origin); // cleanup
            KaliszTimes.getGraphicsHandler().jump("Connections Panel"); // navigate back
        }

        // Add listener for custom keyboard click events from on-screen keyboard buttons
        addKeyboardListener(new KeyboardClickEventListener() {
            @Override
            public void handleClick(KeyboardClickEvent e) {
                handleKeyboardClick(e);
            }
        });

        repaint();
    }

    /**
     * Handles keyboard click events from on-screen keyboard buttons.
     * Processes normal key input, backspace, and enter submission.
     * Updates grid letters, colors, and game state accordingly.
     *
     * @param e the KeyboardClickEvent triggered by button click
     */
    private void handleKeyboardClick(KeyboardClickEvent e) {
        if (rowNumber > 5) {
            return; // max guesses reached
        }

        char letter = e.getKeyClicked();

        switch (e.getClickType()) {
            case KeyboardClickEvent.NORMAL_KEY:
                if (lettersInQueue.size() < 5) {
                    grid[rowNumber][lettersInQueue.size()] = String.valueOf(letter);
                    lettersInQueue.add(letter);
                    wordleGame.inputLetter(letter);
                    repaint();
                }
                break;

            case KeyboardClickEvent.BACKSPACE:
                if (!lettersInQueue.isEmpty()) {
                    grid[rowNumber][lettersInQueue.size() - 1] = null;
                    lettersInQueue.remove(lettersInQueue.size() - 1);
                    wordleGame.deleteLetter();
                    repaint();
                }
                break;

            case KeyboardClickEvent.ENTER:
                if (lettersInQueue.size() == 5) {
                    int result = wordleGame.submitGuess();
                    if (result == 1) { // valid guess submitted
                        String[] guessResult = wordleGame.getGuessData();
                        updateKeyColours(guessResult, new ArrayList<>(lettersInQueue));

                        for (int i = 0; i < 5; i++) {
                            gridColours[rowNumber][i] = guessResult[i];
                        }
                        repaint();

                        lettersInQueue.clear();
                        rowNumber++;

                        if (wordleGame.getWin()) {
                            handleWin();
                        } else if (rowNumber == 6) {
                            handleLoss();
                        }
                    } else if (result == 0) {
                        KaliszTimes.popup("Word does not exist in our library!");
                    }
                } else {
                    KaliszTimes.popup("Please submit a valid word!");
                }
                break;
        }
    }

    /**
     * Handles the user winning the game.
     * Displays a congratulatory popup, updates stats, and closes this panel.
     */
    private void handleWin() {
        KaliszTimes.popup("Congratulations! " + wordleGame.getWord() + " was the correct word!\nYou did it in "
                + wordleGame.getGuessCount() + " attempt" + (wordleGame.getGuessCount() == 1 ? "!" : "s!"));

        wordleGame.winEvent();

        origin.setFinished();
        origin.repaint();

        closePanel();
        GraphicsHandler.activeWordleInstances.remove(origin);
        KaliszTimes.getGraphicsHandler().goBack();
    }

    /**
     * Handles the user losing the game after exhausting all attempts.
     * Prompts user to watch an ad for continuation.
     */
    private void handleLoss() {
        KaliszTimes.adPopup("Uh oh! You exceeded the max number of guesses without guessing the right word!\n"
                + "Watch an ad to continue!\n(Reminder: Kalisz Times Games is a freemium model. By watching an ad, "
                + "you directly support the developers of this game. We thank you for your support and contributions)");

        Platform.runLater(() -> {
            KaliszTimes.showVideoPopup("Ad.mp4", () -> {
                KaliszTimes.popup("Your ad is over! You have passed this level, but did not receive the win.\nThe correct answer was "
                        + wordleGame.getWord() + ".");

                origin.setFinished();
                origin.repaint();

                closePanel();
                GraphicsHandler.activeWordleInstances.remove(origin);
                KaliszTimes.getGraphicsHandler().goBack();
            });
        });
    }

    /**
     * Closes this panel by removing it from its parent container and hiding it.
     */
    private void closePanel() {
        getPanel().setFocusable(false);
        getPanel().setVisible(false);

        Container parent = getPanel().getParent();
        if (parent != null) {
            parent.remove(getPanel());
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Paints the Wordle game grid, colored feedback squares, letters, and other UI components.
     * <p>
     * Draws colored squares for guesses based on {@link #gridColours} and letters from {@link #grid}.
     * Also draws the logged-in username at the top.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);

        int SHIFT_UP = 25;
        graphics.drawImage(GUIConstants.wordle_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH,
                GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);

        // Draw username label
        String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
        int refLabelX = 250;
        int refLabelY = 75;

        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.black);
        graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));

        // Draw colored feedback squares behind letters
        for (int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
            for (int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
                if (gridColours[row][column] != null) {
                    String colour = gridColours[row][column];

                    switch (colour) {
                        case "green":
                            graphics.setColor(Color.green);
                            break;
                        case "yellow":
                            graphics.setColor(new Color(176, 170, 4)); // gold color
                            break;
                        case "grey":
                            graphics.setColor(Color.gray);
                            break;
                    }

                    int HORIZONTAL_OFFSET = GUIConstants.scaleX(-200 + (column * 82));
                    int VERTICAL_OFFSET = GUIConstants.scaleY(-63 + row * 82);

                    int refX = GUIConstants.WINDOW_WIDTH / 2 + HORIZONTAL_OFFSET;
                    int refY = GUIConstants.WINDOW_HEIGHT / 5 + VERTICAL_OFFSET;

                    int squareWidth = 68;

                    graphics.fillRect(refX, refY, GUIConstants.scaleX(squareWidth), GUIConstants.scaleY(squareWidth));

                    // Draw black border
                    graphics.setColor(Color.black);
                    graphics.drawRect(refX, refY, GUIConstants.scaleX(squareWidth), GUIConstants.scaleY(squareWidth));
                }
            }
        }

        // Draw letters on top of colored squares
        int refFontSize = 40;
        graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));

        for (int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
            for (int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
                if (grid[row][column] != null) {
                    int HORIZONTAL_OFFSET = GUIConstants.scaleX(-180 + (column * 82));
                    int VERTICAL_OFFSET = GUIConstants.scaleY(-10 + row * 82);

                    int refX = GUIConstants.WINDOW_WIDTH / 2 + HORIZONTAL_OFFSET;
                    int refY = GUIConstants.WINDOW_HEIGHT / 5 + VERTICAL_OFFSET;

                    if (gridColours[row][column] != null)
                        graphics.setColor(Color.white);
                    else
                        graphics.setColor(Color.black);

                    graphics.drawString(grid[row][column], refX, refY);
                }
            }
        }
    }

    /**
     * Loads and positions the on-screen keyboard buttons on the panel.
     * <p>
     * Creates keys for top, middle, and bottom rows, plus enter and backspace keys.
     * Adds the buttons to the panel and to the internal keyboard button list.
     */
    private void loadKeyboard() {
        char[] topRow = new char[] { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' };
        for (int i = 0; i < 10; i++) {
            int refX = 466 + (i * 99);
            int refY = 639;
            KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, topRow[i], refX, refY,
                    KeyboardClickEvent.NORMAL_KEY, this);
            keyboardButtons.add(button);
        }

        char[] middleRow = new char[] { 'A', 'S', 'D', 'F', 'G', 'H', 'I', 'K', 'L' };
        for (int i = 0; i < 9; i++) {
            int refX = 512 + (i * 100);
            int refY = 772;
            KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, middleRow[i], refX, refY,
                    KeyboardClickEvent.NORMAL_KEY, this);
            keyboardButtons.add(button);
        }

        char[] bottomRow = new char[] { 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
        for (int i = 0; i < 7; i++) {
            int refX = 610 + (i * 100);
            int refY = 900;
            KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, bottomRow[i], refX, refY,
                    KeyboardClickEvent.NORMAL_KEY, this);
            keyboardButtons.add(button);
        }

        // Enter and Backspace keys
        int refX = 467;
        int refY = 900;
        KeyboardButton enterButton = new KeyboardButton(GUIConstants.keyboard_enter_button_image, refX, refY,
                KeyboardClickEvent.ENTER, this);
        keyboardButtons.add(enterButton);

        int refX2 = 1318;
        int refY2 = 900;
        KeyboardButton backspaceButton = new KeyboardButton(GUIConstants.keyboard_backspace_button_image, refX2, refY2,
                KeyboardClickEvent.BACKSPACE, this);
        keyboardButtons.add(backspaceButton);

        // Add all keyboard buttons to the panel UI
        for (KeyboardButton button : keyboardButtons) {
            add(button);
        }
    }

    /**
     * Updates the colors of the on-screen keyboard keys based on the latest guess results.
     * <p>
     * Prioritizes colors in order: green > yellow > grey.
     * Updates internal keyColours map and repaints keys to reflect changes.
     *
     * @param guessResult array of color strings corresponding to each letter in the guess
     * @param guessLetters list of characters guessed in the current attempt
     */
    public void updateKeyColours(String[] guessResult, ArrayList<Character> guessLetters) {
        for (int i = 0; i < guessResult.length; i++) {
            char letter = guessLetters.get(i);
            String newColour = guessResult[i];

            String currentColour = keyColours.get(letter);

            // Prioritize: green > yellow > grey
            if (currentColour == null || (currentColour.equals("grey") && !newColour.equals("grey"))
                    || (currentColour.equals("yellow") && newColour.equals("green"))) {
                keyColours.put(letter, newColour);
            }
        }

        // Repaint all keyboard buttons to reflect color changes
        for (KeyboardButton button : keyboardButtons) {
            button.repaint();
        }
    }

    /**
     * Returns the map of current key colors.
     *
     * @return a HashMap mapping each letter to its current color string
     */
    public HashMap<Character, String> getKeyColours() {
        return keyColours;
    }

    /**
     * Adds a listener to receive keyboard click events from the on-screen keyboard.
     *
     * @param listener the KeyboardClickEventListener to add
     */
    public void addKeyboardListener(KeyboardClickEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a keyboard click event listener.
     *
     * @param listener the KeyboardClickEventListener to remove
     */
    public void removeKeyboardListener(KeyboardClickEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns a list of currently registered keyboard click event listeners.
     *
     * @return list of KeyboardClickEventListeners
     */
    public ArrayList<KeyboardClickEventListener> getListeners() {
        return listeners;
    }

    /**
     * Handles keyTyped events from the physical keyboard.
     * Not used but required by the KeyListener interface.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**
     * Handles keyPressed events from the physical keyboard.
     * Converts physical key presses into keyboard click events and fires them
     * so that the same input handling code can process them.
     *
     * @param e the KeyEvent triggered by a physical key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Hi");
        if (Character.isLetter(e.getKeyChar())) {
            EventHandler.fireWordleClickEvent(this, Character.toUpperCase(e.getKeyChar()), KeyboardClickEvent.NORMAL_KEY);
        } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.ENTER);
        } else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
            EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
        }
    }

    /**
     * Handles keyReleased events from the physical keyboard.
     * Not used but required by the KeyListener interface.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    /**
     * Returns this WordleGamePanel instance.
     *
     * @return this panel instance
     */
    public WordleGamePanel getPanel() {
        return this;
    }
  /**
     * Requests focus for this panel to enable key events.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
}