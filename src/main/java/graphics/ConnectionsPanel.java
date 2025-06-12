package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.StatsButton;
import graphics.buttons.WordleButton;
import graphics.utils.GameDataHandler;
import javafx.application.Platform;
import kalisz.KaliszTimes;
import logic.Connections;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

/**
 * Panel for the "Connections" game UI. 
 * This panel manages all the visual elements including buttons,
 * word tiles, event handling, and the game state display.
 * 
 * <p>It handles displaying Wordle and Spelling Bee buttons, user interactions,
 * and communicates with the underlying game logic.</p>
 * 
 * <p>Extends {@link TemplatePanel} for shared layout and styling.</p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class ConnectionsPanel extends TemplatePanel {

    /** List of WordleButton objects representing 5-letter word tiles */
    ArrayList<WordleButton> wordleButtons = new ArrayList<>();

    /** List of SpellingBeeEnterButton objects representing 7-letter word tiles */
    ArrayList<SpellingBeeEnterButton> spellingBeeButtons = new ArrayList<>();

    /** List of keyboard click event listeners registered to this panel */
    private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<>();

    /** Game data handler for accessing persistent game data */
    static GameDataHandler data;

    /** The main Connections game logic instance */
    public static Connections connectionsGame;

    /**
     * Constructs a new ConnectionsPanel.
     * Sets up the UI components, initializes the game,
     * registers event listeners, and lays out the buttons.
     */
    public ConnectionsPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        // Initialize the connections game with categories and words from GameDataHandler
        connectionsGame = new Connections(
            GameDataHandler.yellowWords, GameDataHandler.yellowCategory,
            GameDataHandler.greenWords, GameDataHandler.greenCategory,
            GameDataHandler.blueWords, GameDataHandler.blueCategory,
            GameDataHandler.purpleWords, GameDataHandler.purpleCategory
        );

        // Add keyboard event listener for handling Enter and Deselect All actions
        addKeyboardListener(new KeyboardClickEventListener() {
            @Override
            public void handleClick(KeyboardClickEvent e) {
                int clickType = e.getClickType();
                switch(clickType) {
                    case KeyboardClickEvent.ENTER:
                        String code = connectionsGame.submitGuess();
                        if(code.equals("not enough words")) {
                            KaliszTimes.popup("You have not selected enough words to make a connection!");
                        } else if(code.equals("wrong")){
                            KaliszTimes.popup("That is the incorrect connection! -1 guess!");
                        } else if(code.equals("game over")) {
                            KaliszTimes.popup("You lost :(");
                            KaliszTimes.adPopup("To receive 3 more guesses, please watch this ad!\nReminder: Kalisz Time Games is a freemium service. By watching this ad, you directly support the developers.\nWe appreciate your support truly.");
                            Platform.runLater(() -> {
                                KaliszTimes.showVideoPopup("Ad.mp4", () -> {});
                            });
                            connectionsGame.guessesLeft += 3;
                        } else {
                            // Successful guess: mark words as revealed and update UI
                            KaliszTimes.popup("You successfully guessed the category: " + connectionsGame.convertCategoryColorToName(code));
                            for(WordleButton wordleButton : wordleButtons)
                                wordleButton.repaint();
                            for(SpellingBeeEnterButton spellingBeeEnterButton : spellingBeeButtons)
                                spellingBeeEnterButton.repaint();

                            if(connectionsGame.getWin()) {
                                KaliszTimes.popup("You have successfully completed today's The Kalisz Times Game!\nCongratulations!\nFeel free to see your stats in the leaderboard.");
                                connectionsGame.winEvent();
                            }
                        }
                        connectionsGame.deselectAll();
                        repaint();
                        break;

                    case KeyboardClickEvent.DESELECT_ALL:
                        connectionsGame.deselectAll();
                        repaint();
                        break;
                }
            }
        });

        // Add navigation and control buttons
        add(new BackButton(GUIConstants.backButtonImage));

        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        add(new StatsButton(GUIConstants.viewStatsButtonImage));

        Button shuffleButton = new Button(GUIConstants.shuffleButtonImage, GUIConstants.scaleX(720), GUIConstants.scaleY(925));
        shuffleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                KaliszTimes.popup("Shuffled connections board!");
                shuffleBoardUI();
            }
        });
        add(shuffleButton);

        Button deselectAllButton = new Button(GUIConstants.deselectAllButtonImage, GUIConstants.scaleX(878), GUIConstants.scaleY(925));
        deselectAllButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                EventHandler.fireConnectionsClickEvent(getPanel(), ' ', KeyboardClickEvent.DESELECT_ALL);
            }
        });
        add(deselectAllButton);

        Button submitButton = new Button(GUIConstants.submitButtonImage, GUIConstants.scaleX(1088), GUIConstants.scaleY(925));
        submitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                EventHandler.fireConnectionsClickEvent(getPanel(), ' ', KeyboardClickEvent.ENTER);
            }
        });
        add(submitButton);

        // Create and position Wordle (5-letter) and Spelling Bee (7-letter) buttons
        List<String> fullBoard = connectionsGame.getBoard();

        List<Integer> wordleIndices = new ArrayList<>();
        List<Integer> spellingBeeIndices = new ArrayList<>();

        for (int i = 0; i < fullBoard.size(); i++) {
            String word = fullBoard.get(i);
            if (word.length() == 5) {
                wordleIndices.add(i);
            } else if (word.length() == 7) {
                spellingBeeIndices.add(i);
            }
        }

        // Wordle buttons layout (2 rows, 4 columns)
        for (int i = 0; i < wordleIndices.size(); i++) {
            int boardIndex = wordleIndices.get(i);
            String word = fullBoard.get(boardIndex);
            WordleButton wordleButton = new WordleButton(GUIConstants.wordleButtonImage, boardIndex, word.toUpperCase());

            int refX = 695 + ((i % 4) * 153);
            int refY = 250 + ((i / 4) * 153);

            wordleButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY),
                GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH), GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT));

            wordleButtons.add(wordleButton);
            add(wordleButton);
        }

        // Spelling Bee buttons layout (2 rows, 4 columns)
        for (int i = 0; i < spellingBeeIndices.size(); i++) {
            int boardIndex = spellingBeeIndices.get(i);
            String word = fullBoard.get(boardIndex);
            SpellingBeeEnterButton spellingBeeButton = new SpellingBeeEnterButton(GUIConstants.spellingBeeButtonImage, boardIndex, word.toUpperCase());

            int refX = 695 + ((i % 4) * 153);
            int refY = 550 + ((i / 4) * 153);

            spellingBeeButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY),
                GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH), GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT));

            spellingBeeButtons.add(spellingBeeButton);
            add(spellingBeeButton);
        }

        shuffleBoardUI();
        repaint();
    }

    /**
     * Paints the panel including background and text labels such as username and mistakes made.
     * 
     * @param g Graphics object for drawing
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);

        int SHIFT_UP = 25;
        graphics.drawImage(GUIConstants.connections_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);

        // Draw user info label
        String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
        int refLabelX = 250;
        int refLabelY = 75;

        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.black);
        graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));

        // Draw mistakes made label
        String mistakesMadeText = "Mistakes made: " + connectionsGame.guessesLeft + "/4";
        refLabelX = 883;
        refLabelY = 880;

        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.black);
        graphics.drawString(mistakesMadeText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));
    }

    /**
     * Returns the list of Wordle buttons on the panel.
     * 
     * @return ArrayList of WordleButton objects
     */
    public ArrayList<WordleButton> getWordleButtons() {
        return this.wordleButtons;
    }

    /**
     * Requests focus for this panel.
     * Ensures it can receive keyboard input.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    /**
     * Registers a KeyboardClickEventListener to receive keyboard events.
     * 
     * @param listener the listener to add
     */
    public void addKeyboardListener(KeyboardClickEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a KeyboardClickEventListener.
     * 
     * @param listener the listener to remove
     */
    public void removeKeyboardListener(KeyboardClickEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the list of registered KeyboardClickEventListeners.
     * 
     * @return list of listeners
     */
    public ArrayList<KeyboardClickEventListener> getListeners() {
        return listeners;
    }

    /**
     * Returns this panel instance.
     * 
     * @return the current ConnectionsPanel instance
     */
    public ConnectionsPanel getPanel() {
        return this;
    }

    /**
     * Shuffles the UI buttons on the board and repositions them in a 4x4 grid.
     * This affects both Wordle and Spelling Bee buttons.
     */
    private void shuffleBoardUI() {
        // Combine all buttons into one list and shuffle
        List<Button> allButtons = new ArrayList<>();
        allButtons.addAll(wordleButtons);
        allButtons.addAll(spellingBeeButtons);

        Collections.shuffle(allButtons);

        // Reposition buttons in grid layout
        for (int i = 0; i < allButtons.size(); i++) {
            Button button = allButtons.get(i);

            int refX = 695 + ((i % 4) * 153); // column offset
            int refY = 250 + ((i / 4) * 153); // row offset

            button.setBounds(
                GUIConstants.scaleX(refX),
                GUIConstants.scaleY(refY),
                GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH),
                GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT)
            );
        }

        revalidate();
        repaint();
    }

    /**
     * Helper class to track a word and its original index on the board.
     */
    class WordWithIndex {
        /** The word string */
        String word;

        /** The index of the word on the board */
        int index;

        /**
         * Constructs a WordWithIndex instance.
         * 
         * @param word the word
         * @param index the index in the board list
         */
        public WordWithIndex(String word, int index) {
            this.word = word;
            this.index = index;
        }
    }
}
