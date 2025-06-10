package logic;

/**
 * Class for the Connections game.
 * Contains getters for some variables and methods to help run the game (selecting words, submitting guesses, etc.).
 * All words to set up the game are hidden by default. Whether or not they have been revealed is tracked using the "revealed" hashmap.
 * Does not currently have a win boolean. Wins should be tracked based on the return value of submitGuess and the number of categories completed.
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
    private ArrayList<String[]> results; // for the ascii results you copy paste after a game
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

    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory, int guessesLeft, ArrayList<String[]> results, HashMap<String, Boolean> revealed) {
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
        this.results = results;
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
        String category1 = this.wordToCategory.get(this.board.get(this.currentGuess.get(0))), category2 = this.wordToCategory.get(this.board.get(this.currentGuess.get(1))), category3 = this.wordToCategory.get(this.board.get(this.currentGuess.get(2))), category4 = this.wordToCategory.get(this.board.get(this.currentGuess.get(3)));
        this.results.add(new String[]{category1, category2, category3, category4}); // add the stats of the submitted guess to the results
        this.currentGuess.sort(null); // so that the indexes of the guess are in order to remove properly
        if (category1.equals(category2) && category2.equals(category3) && category3.equals(category4)) { // if a category is completed
            String categoryCompleted = this.wordToCategory.get(this.board.get(this.currentGuess.get(0))); //(category successfully guessed)
            this.board.remove((int) this.currentGuess.get(3));
            this.board.remove((int) this.currentGuess.get(2));
            this.board.remove((int) this.currentGuess.get(1));
            this.board.remove((int) this.currentGuess.get(0));
            this.categoriesCompleted.add(categoryCompleted);
            return categoryCompleted;
        }
        if (--this.guessesLeft == 0) return "game over"; // if the player has no guesses left and fails the game (-- decrements the amount of guesses left)
        return "wrong"; // player didn't fail the game since the above line didn't run
    }
    
}