package logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;


/**
 * Class for managing the Connections game.
 * Handles game state, word selections, guesses, and category checking.
 * Words are grouped into 4 color-based categories (yellow, green, blue, purple).
 * Each group contains 4 words that can be revealed through other games (Wordle, Spelling Bee).
 * 
 * The board is shuffled at the start. Players submit guesses of 4 words. 
 * A guess is correct if all 4 words belong to the same category.
 * 
 * Tracks revealed words, categories completed, number of guesses left, and game result stats.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */

public class Connections {

    private ArrayList<String> board, categoriesCompleted;
    public ArrayList<Integer> currentGuess; // takes indexes from the board array
    private String[] yellowWords, greenWords, blueWords, purpleWords;
    private int guessesLeft;
    private String yellowCategory, greenCategory, blueCategory, purpleCategory; // yellowCategory = "Rooms in rekugjhselkfgj seg"
    private HashMap<String, String> wordToCategory; // wordToCategory.get("hall") -> "yellow"
    private ArrayList<String[]> results; // for the ascii results you copy paste after a game
    private HashMap<String, Boolean> revealed; // .get("hall") -> false .......... true

    /**
     * Constructor for new game. Takes in data about each category.
     * 
     * @param yellowWords // array of yellow words
     * @param yellowCategory // category ("answer") for yellow
     * @param greenWords // etc.
     * @param greenCategory
     * @param blueWords
     * @param blueCategory
     * @param purpleWords
     * @param purpleCategory
     */

    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory) {
        this.wordToCategory = new HashMap<>();
        this.revealed = new HashMap<>();
        this.yellowWords = yellowWords;
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        this.purpleCategory = purpleCategory;
        this.board = new ArrayList<String>();
        for (int index = 0; index < 4; ++index) {
            this.board.add(yellowWords[index]); // add each word to board
            this.board.add(greenWords[index]);
            this.board.add(blueWords[index]);
            this.board.add(purpleWords[index]);
            this.wordToCategory.put(yellowWords[index], "yellow"); // assigns each word to their colour's character
            this.wordToCategory.put(greenWords[index], "green");
            this.wordToCategory.put(blueWords[index], "blue");
            this.wordToCategory.put(purpleWords[index], "purple");
            this.revealed.put(yellowWords[index], false); // assigns each word as unrevealed
            this.revealed.put(greenWords[index], false);
            this.revealed.put(blueWords[index], false);
            this.revealed.put(purpleWords[index], false);
        }
        this.categoriesCompleted = new ArrayList<String>();
        this.guessesLeft = 4;
        this.currentGuess = new ArrayList<Integer>();
        this.results = new ArrayList<String[]>();
    }

    /**
     * Constructor for game in progress.
     * 
     * @param yellowWords
     * @param yellowCategory
     * @param greenWords
     * @param greenCategory
     * @param blueWords
     * @param blueCategory
     * @param purpleWords
     * @param purpleCategory
     * @param guessesLeft // guesses that the player has left
     * @param revealed // hashmap to track which words have already been revealed through Wordle or Spelling Bee
     * @param results
     */


     /*    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory, int guessesLeft, ArrayList<String[]> results, HashMap<String, Boolean> revealed) {
        this.wordToCategory = new HashMap<>();
        this.revealed = new HashMap<>();
        this.yellowWords = yellowWords;
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        this.purpleCategory = purpleCategory;
        this.board = new ArrayList<String>();
        for (int index = 0; index < 4; ++index) {
            this.board.add(yellowWords[index]); // add each word to board
            this.board.add(greenWords[index]);
            this.board.add(blueWords[index]);
            this.board.add(purpleWords[index]);
            this.wordToCategory.put(yellowWords[index], "yellow"); // assigns each word to their colour's character
            this.wordToCategory.put(greenWords[index], "green");
            this.wordToCategory.put(blueWords[index], "blue");
            this.wordToCategory.put(purpleWords[index], "purple");
        }
        this.categoriesCompleted = new ArrayList<String>();
        this.guessesLeft = guessesLeft;
        this.currentGuess = new ArrayList<Integer>();
        this.results = results;
        this.revealed = revealed;
    }
    */
    /**
    * Gets the array of yellow category words.
     *
    * @return an array of 4 yellow words
    */
    public String[] getYellowWords() {
        return this.yellowWords;
    }
    /**
    * Gets the name of the yellow category.
     *
    * @return the yellow category name
    */
    public String getYellowCategory() {
        return this.yellowCategory;
    }
     /**
    * Gets the array of green category words.
     *
    * @return an array of 4 greem words
    */
    public String[] getGreenWords() {
        return this.greenWords;
    }
    /**
    * Gets the name of the green category.
     *
    * @return the green category name
    */
    public String getGreenCategory() {
        return this.greenCategory;
    }
    /**
    * Gets the array of blue category words.
    *
    * @return an array of 4 blue words
    */
    public String[] getBlueWords() {
        return this.blueWords;
    }
    /**
    * Gets the name of the blue category.
     *
    * @return the blue category name
    */
    public String getBlueCategory() {
        return this.blueCategory;
    }
    /**
    * Gets the array of purple category words.
    *
    * @return an array of 4 purple words
    */
    public String[] getPurpleWords() {
        return this.purpleWords;
    }
    /**
    * Gets the name of the purple category.
    *
    * @return the purple category name
    */
    public String getPurpleCategory() {
        return this.purpleCategory;
    }
    /**
    * Gets the list of guess results.
    * Each element is an array of 4 category strings submitted in a guess.
    *
    * @return an ArrayList of submitted guess category results
    */
    public ArrayList<String[]> getResults() {
        return this.results;
    }

    /**
     * Shuffles the board.
     */

    public void shuffle() {
        ArrayList<String> shuffledBoard = new ArrayList<String>(); // initialize empty shuffled board
        Random random = new Random();
        while (this.board.size() > 0) shuffledBoard.add(this.board.remove(random.nextInt(this.board.size()))); // keep adding elements from this.board to shuffledBoard at random indexes, removing from this.board each time
        this.board = shuffledBoard; // assign shuffledBoard to this.board
    }

   /**
     * Selects or deselects a word at the given index.
     * Does not allow more than 4 selections.
     * 
     * @param index index of the word to toggle in the guess
     * @return null (future use could return the word if unrevealed)
     */
    public String selectWord(int index) {// 
       // you can't do this. + redundant if (!this.revealed.get(this.board.get(index))) return this.board.get(index); // if not revealed, return the word to be wordled/spelling beed
        if (this.currentGuess.contains(index)) this.currentGuess.remove(Integer.valueOf(index)); // deselects index if already in guess //can't do this
        else if (this.currentGuess.size() < 4) this.currentGuess.add(Integer.valueOf(index));
        return null;
    }
    
    /**
     * Update the revealed hashmap to reveal a word that was successfully revealed through Wordle or Spelling Bee.
     * 
     * @param word word to be revealed
     */

    public void revealWord(String word) {
        this.revealed.replace(word, true);
    }

    /**
     * Deselects all words for the current guess.
     */

    public void deselectAll() {
        this.currentGuess.clear();
    }

    /**
     * Submits a guess.
     * Checks if the guess is valid.
     * 
     * @return "not enough words" if not enough words in guess, "game over" if wrong and game-ending (no guesses left), "wrong" if wrong but not game-ending, name of category matched otherwise
     */

    public String submitGuess() {
        System.out.println("Guess size: " + currentGuess.size() + " → " + currentGuess);
        if (this.currentGuess.size() < 4) return "not enough words";
        String category1 = this.wordToCategory.get(this.board.get(this.currentGuess.get(0))), category2 = this.wordToCategory.get(this.board.get(this.currentGuess.get(1))), category3 = this.wordToCategory.get(this.board.get(this.currentGuess.get(2))), category4 = this.wordToCategory.get(this.board.get(this.currentGuess.get(3)));
        this.results.add(new String[]{category1, category2, category3, category4}); // add the stats of the submitted guess to the results
        this.currentGuess.sort(null); // so that the indexes of the guess are in order to remove properly
        if (category1.equals(category2) && category2.equals(category3) && category3.equals(category4)) { // if a category is completed
            this.board.remove((int) this.currentGuess.get(3));
            this.board.remove((int) this.currentGuess.get(2));
            this.board.remove((int) this.currentGuess.get(1));
            this.board.remove((int) this.currentGuess.get(0));
            this.categoriesCompleted.add(category1); // category successfully guessed
            return category1;
        }
        if (--this.guessesLeft == 0) return "game over"; // if the player has no guesses left and fails the game (-- decrements the amount of guesses left)
        return "wrong"; // player didn't fail the game since the above line didn't run
    }
    
}