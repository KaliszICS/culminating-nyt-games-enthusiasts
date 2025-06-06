/**
 * Class for the Connections game.
 * Contains getters for some variables and methods to help run the game (selecting words, submitting guesses, etc.).
 * All words to set up the game are hidden by default. Whether or not they have been revealed is tracked using the "revealed" HashMap.
 * Does not currently have a this.win boolean. Wins should be tracked based on the return value of submitGuess and the number of categories completed.
 * 
 * @author @FranklinZhu1
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class Connections {

    private ArrayList<String> board, categoriesCompleted;
    private ArrayList<Integer> currentGuess; // takes indexes from the board array
    private String[] yellowWords, greenWords, blueWords, purpleWords;
    private int guessesLeft;
    private String yellowCategory, greenCategory, blueCategory, purpleCategory;
    private HashMap<String, String> wordToCategory;
    private HashMap<String, Boolean> revealed;

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
        this.yellowWords = yellowWords;
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        this.purpleCategory = purpleCategory;
        for (int index = 0; index < 4; ++index) {
            this.wordToCategory.put(yellowWords[index], "yellow"); // assigns each word to their colour's character
            this.wordToCategory.put(greenWords[index], "green");
            this.wordToCategory.put(blueWords[index], "blue");
            this.wordToCategory.put(purpleWords[index], "purple");
            this.revealed.put(yellowWords[index], false); // assigns each word as unrevealed
            this.revealed.put(greenWords[index], false);
            this.revealed.put(blueWords[index], false);
            this.revealed.put(purpleWords[index], false);
        }
        this.board = new ArrayList<String>();
        this.categoriesCompleted = new ArrayList<String>();
        this.guessesLeft = 4;
        this.currentGuess = new ArrayList<Integer>();
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
     * @param revealed // HashMap to track which words have already been revealed through Wordle or Spelling Bee
     */

    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory, int guessesLeft, HashMap<String, Boolean> revealed) {
        this.yellowWords = yellowWords;
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        this.purpleCategory = purpleCategory;
        for (int index = 0; index < 4; ++index) {
            this.wordToCategory.put(yellowWords[index], "yellow"); // assigns each word to their colour's character
            this.wordToCategory.put(greenWords[index], "green");
            this.wordToCategory.put(blueWords[index], "blue");
            this.wordToCategory.put(purpleWords[index], "purple");
        }
        this.board = new ArrayList<String>();
        this.categoriesCompleted = new ArrayList<String>();
        this.guessesLeft = guessesLeft;
        this.currentGuess = new ArrayList<Integer>();
        this.revealed = revealed;
    }

    public String[] getYellowWords() {
        return this.yellowWords;
    }

    public String getYellowCategory() {
        return this.yellowCategory;
    }

    public String[] getGreenWords() {
        return this.greenWords;
    }

    public String getGreenCategory() {
        return this.greenCategory;
    }

    public String[] getBlueWords() {
        return this.blueWords;
    }

    public String getBlueCategory() {
        return this.blueCategory;
    }

    public String[] getPurpleWords() {
        return this.purpleWords;
    }

    public String getPurpleCategory() {
        return this.purpleCategory;
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
     * Selects or deselects a word for the current guess.
     * Returns the word itself if it hasn't been revealed yet.
     * 
     * @param index index of word to be selected
     * @return the word to be revealed through Wordle or Spelling Bee, null if already revealed
     */

    public String selectWord(int index) {
        if (!this.revealed.get(this.board.get(index))) return this.board.get(index); // if not revealed, return the word to be wordled/spelling beed
        if (this.currentGuess.contains(index)) this.currentGuess.remove(index); // deselects index if already in guess
        else if (this.currentGuess.size() < 4) this.currentGuess.add(index);
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
        if (this.currentGuess.size() < 4) return "not enough words";
        this.currentGuess.sort(null); // so that the indexes of the guess are in order to remove properly
        if (this.wordToCategory.get(this.board.get(this.currentGuess.get(0))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(1))) && this.wordToCategory.get(this.board.get(this.currentGuess.get(1))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(2))) && this.wordToCategory.get(this.board.get(this.currentGuess.get(2))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(3)))) { // if a category is completed
            this.board.remove((int) this.currentGuess.get(3)); // remove all the words from the board
            this.board.remove((int) this.currentGuess.get(2));
            this.board.remove((int) this.currentGuess.get(1));
            this.board.remove((int) this.currentGuess.get(0));
            String categoryCompleted = this.wordToCategory.get(this.board.get(this.currentGuess.get(0))); // category successfully guessed
            this.categoriesCompleted.add(categoryCompleted);
            return categoryCompleted;
        }
        if (--this.guessesLeft == 0) return "game over"; // if the player has no guesses left and fails the game (-- decrements the amount of guesses left)
        return "wrong"; // player didn't fail the game since the above line didn't run
    }
    
}