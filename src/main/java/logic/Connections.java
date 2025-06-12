package logic;

import java.util.ArrayList;
import java.util.Random;

import kalisz.KaliszTimes;

import java.util.HashMap;

/**
 * Manages the Connections game logic, including the board setup, word categories,
 * guesses, revealed words, and game state.
 * <p>
 * The game board consists of 16 words divided equally into 4 color-coded categories:
 * yellow, green, blue, and purple. Each category has 4 associated words and a category name.
 * Players make guesses by selecting 4 words that they believe belong to the same category.
 * Correct guesses reveal those words. The game tracks revealed words, categories completed,
 * guesses remaining, and whether the player has won.
 * </p>
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class Connections {

    /**
     * The game board containing all words.
     */
    public ArrayList<String> board;

    /**
     * List of categories that have been completed.
     */
    public ArrayList<String> categoriesCompleted;

    /**
     * Current guess represented by indices of selected words on the board.
     */
    public ArrayList<Integer> currentGuess;

    private String[] yellowWords;
    private String[] greenWords;
    private String[] blueWords;
    private String[] purpleWords;

    /**
     * Number of guesses left for the player.
     */
    public int guessesLeft;

    private String yellowCategory;
    private String greenCategory;
    private String blueCategory;
    private String purpleCategory;

    /**
     * Maps a word to its color category ("yellow", "green", "blue", "purple").
     */
    private HashMap<String, String> wordToCategory;

    /**
     * Stores the results of each guess as arrays of category strings.
     */
    private ArrayList<String[]> results;

    /**
     * List of words that have been revealed.
     */
    public ArrayList<String> revealed;

    private boolean win;

    /**
     * Constructs a new Connections game instance.
     * Initializes the board and categories with the provided words and category names.
     * 
     * @param yellowWords Array of 4 words in the yellow category
     * @param yellowCategory Name of the yellow category
     * @param greenWords Array of 4 words in the green category
     * @param greenCategory Name of the green category
     * @param blueWords Array of 4 words in the blue category
     * @param blueCategory Name of the blue category
     * @param purpleWords Array of 4 words in the purple category
     * @param purpleCategory Name of the purple category
     */
    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, 
                       String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory) {
        this.win = false;
        this.wordToCategory = new HashMap<>();
        this.revealed = new ArrayList<>();
        this.yellowWords = yellowWords;
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        this.purpleCategory = purpleCategory;
        this.board = new ArrayList<>();
        for (int index = 0; index < 4; ++index) {
            this.board.add(yellowWords[index]);
            this.board.add(greenWords[index]);
            this.board.add(blueWords[index]);
            this.board.add(purpleWords[index]);
            this.wordToCategory.put(yellowWords[index], "yellow");
            this.wordToCategory.put(greenWords[index], "green");
            this.wordToCategory.put(blueWords[index], "blue");
            this.wordToCategory.put(purpleWords[index], "purple");
        }
        this.categoriesCompleted = new ArrayList<>();
        this.guessesLeft = 4;
        this.currentGuess = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    /**
     * Returns the array of yellow category words.
     * 
     * @return Array of 4 yellow words
     */
    public String[] getYellowWords() {
        return this.yellowWords;
    }

    /**
     * Returns the name of the yellow category.
     * 
     * @return Yellow category name
     */
    public String getYellowCategory() {
        return this.yellowCategory;
    }

    /**
     * Returns the array of green category words.
     * 
     * @return Array of 4 green words
     */
    public String[] getGreenWords() {
        return this.greenWords;
    }

    /**
     * Returns the name of the green category.
     * 
     * @return Green category name
     */
    public String getGreenCategory() {
        return this.greenCategory;
    }

    /**
     * Returns the array of blue category words.
     * 
     * @return Array of 4 blue words
     */
    public String[] getBlueWords() {
        return this.blueWords;
    }

    /**
     * Returns the name of the blue category.
     * 
     * @return Blue category name
     */
    public String getBlueCategory() {
        return this.blueCategory;
    }

    /**
     * Returns the array of purple category words.
     * 
     * @return Array of 4 purple words
     */
    public String[] getPurpleWords() {
        return this.purpleWords;
    }

    /**
     * Returns the name of the purple category.
     * 
     * @return Purple category name
     */
    public String getPurpleCategory() {
        return this.purpleCategory;
    }

    /**
     * Returns the list of guess results.
     * Each element is an array of 4 category strings corresponding to a submitted guess.
     * 
     * @return ArrayList of guess category arrays
     */
    public ArrayList<String[]> getResults() {
        return this.results;
    }

    /**
     * Shuffles the words on the board randomly.
     */
    public void shuffle() {
        ArrayList<String> shuffledBoard = new ArrayList<>();
        Random random = new Random();
        while (this.board.size() > 0) {
            shuffledBoard.add(this.board.remove(random.nextInt(this.board.size())));
        }
        this.board = shuffledBoard;
    }

    /**
     * Returns the color category of a given word.
     * 
     * @param word The word to look up
     * @return The color category ("yellow", "green", "blue", or "purple"),
     *         or null if the word is not found
     */
    public String getWordColor(String word) {
        return wordToCategory.get(word);
    }

    /**
     * Selects or deselects a word at the specified board index for the current guess.
     * Does not allow selection of more than 4 words.
     * Does not allow selection of already revealed words.
     * 
     * @param index The index of the word on the board to toggle selection
     * @return Always returns null (reserved for future use)
     */
    public String selectWord(int index) {
        if (this.currentGuess.contains(index)) {
            this.currentGuess.remove(Integer.valueOf(index));
        } else if (this.currentGuess.size() < 4 && !revealed.contains(board.get(index))) {
            this.currentGuess.add(Integer.valueOf(index));
        }
        return null;
    }

    /**
     * Converts a color category string ("yellow", "green", "blue", "purple") 
     * to its corresponding category name.
     * 
     * @param color The color string to convert
     * @return The category name corresponding to the color,
     *         or the input color string if it does not match known colors
     */
    public String convertCategoryColorToName(String color) {
        switch (color) {
            case "blue":
                return getBlueCategory();
            case "yellow":
                return getYellowCategory();
            case "green":
                return getGreenCategory();
            case "purple":
                return getPurpleCategory();
            default:
                return color;
        }
    }

    /**
     * Adds the specified word to the list of revealed words.
     * 
     * @param word The word to reveal
     */
    public void revealWord(String word) {
        this.revealed.add(word);
    }

    /**
     * Clears all currently selected words in the current guess.
     */
    public void deselectAll() {
        this.currentGuess.clear();
    }

    /**
     * Returns the current game board words.
     * 
     * @return The list of words on the board
     */
    public ArrayList<String> getBoard() {
        return board;
    }

    /**
     * Submits the current guess of 4 selected words.
     * Checks if the guess is valid, matches categories, updates revealed words,
     * and tracks guesses left and win condition.
     * 
     * @return The category name if the guess is correct,
     *         "not enough words" if fewer than 4 words are selected,
     *         "invalid selection" if any selected index is invalid,
     *         "game over" if no guesses remain,
     *         or "wrong" if the guess is incorrect
     */
    public String submitGuess() {
        System.out.println("Guess size: " + currentGuess.size() + " → " + currentGuess);
        if (this.currentGuess.size() < 4) return "not enough words";

        // Validate indices
        for (int index : currentGuess) {
            if (index < 0 || index >= board.size()) {
                currentGuess.clear();
                return "invalid selection";
            }
        }

        // Get categories of selected words
        String category1 = wordToCategory.get(board.get(currentGuess.get(0)));
        String category2 = wordToCategory.get(board.get(currentGuess.get(1)));
        String category3 = wordToCategory.get(board.get(currentGuess.get(2)));
        String category4 = wordToCategory.get(board.get(currentGuess.get(3)));

        results.add(new String[] { category1, category2, category3, category4 });

        // Check if all categories match
        if (category1.equals(category2) && category2.equals(category3) && category3.equals(category4)) {
            // Reveal words
            for (int index : currentGuess) {
                String word = board.get(index);
                revealWord(word);
            }

            categoriesCompleted.add(category1);
            currentGuess.clear();

            // Check win condition
            if (revealed.size() == board.size())
                win = true;

            return category1;
        }

        if (--guessesLeft == 0)
            return "game over";
        currentGuess.clear();
        return "wrong";
    }

    /**
     * Checks whether a word has been revealed.
     * 
     * @param word The word to check
     * @return true if the word is revealed, false otherwise
     */
    public boolean isWordRevealed(String word) {
        return revealed.contains(word);
    }

    /**
     * Returns whether the player has won the game.
     * 
     * @return true if the player has won, false otherwise
     */
    public boolean getWin() {
        return win;
    }

    /**
     * Called when the player wins.
     * Updates player statistics, saves data, and reloads the leaderboard UI.
     */
    public void winEvent() {
        // Update player stats
        KaliszTimes.player.incrementConnectionsWins();
        KaliszTimes.player.saveStats(); // writes to [username].txt

        // Update leaderboard
        LeaderboardHandler leaderboard = new LeaderboardHandler();
        leaderboard.saveAllStats(); // appends to Connections.txt

        KaliszTimes.getGraphicsHandler().reloadLeaderboardFrame();
    }
}
