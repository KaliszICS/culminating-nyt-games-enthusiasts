import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

/**
 * connections class
 * 
 * i might need to add a win boolean since at the moment it relies on seeing if submitGuess returns a character AND there are no categories left (or if categoriesCompleted is filled with characters)
 */

public class Connections {

    private ArrayList<String> board;
    private ArrayList<Character> categoriesCompleted;
    private ArrayList<Integer> currentGuess; // takes indexes from the board array
    private String[] yellowWords, greenWords, blueWords, purpleWords;
    private int guessesLeft;
    private String yellowCategory, greenCategory, blueCategory, purpleCategory;
    private HashMap<String, Character> wordToCategory;
    private HashMap<String, Boolean> revealed;

    /**
     * constructor for new game
     * 
     * @param yellowWords
     * @param yellowCategory
     * @param greenWords
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
            this.wordToCategory.put(yellowWords[index], 'y'); // assigns each word to their colour's character
            this.wordToCategory.put(greenWords[index], 'g');
            this.wordToCategory.put(blueWords[index], 'b');
            this.wordToCategory.put(purpleWords[index], 'p');
            this.revealed.put(yellowWords[index], false); // assigns each word as unrevealed
            this.revealed.put(greenWords[index], false);
            this.revealed.put(blueWords[index], false);
            this.revealed.put(purpleWords[index], false);
        }
        this.board = new ArrayList<String>();
        this.guessesLeft = 4;
        this.categoriesCompleted = new ArrayList<Character>();
        this.currentGuess = new ArrayList<Integer>();
    }

    /**
     * constructor for game in progress
     * 
     * @param yellowWords
     * @param yellowCategory
     * @param greenWords
     * @param greenCategory
     * @param blueWords
     * @param blueCategory
     * @param purpleWords
     * @param purpleCategory
     */

    public Connections(String[] yellowWords, String yellowCategory, String[] greenWords, String greenCategory, String[] blueWords, String blueCategory, String[] purpleWords, String purpleCategory, int guessesLeft, int categoriesLeft, char[] categoriesCompleted) {
        this.yellowWords = yellowWords;
        for (int index = 0; index < 4; ++index) this.wordToCategory.put(yellowWords[index], 'y');
        this.yellowCategory = yellowCategory;
        this.greenWords = greenWords;
        for (int index = 0; index < 4; ++index) this.wordToCategory.put(greenWords[index], 'g');
        this.greenCategory = greenCategory;
        this.blueWords = blueWords;
        for (int index = 0; index < 4; ++index) this.wordToCategory.put(blueWords[index], 'b');
        this.blueCategory = blueCategory;
        this.purpleWords = purpleWords;
        for (int index = 0; index < 4; ++index) this.wordToCategory.put(purpleWords[index], 'p');
        this.purpleCategory = purpleCategory;
        this.board = new ArrayList<String>();
        this.guessesLeft = guessesLeft;
        this.categoriesCompleted = new ArrayList<Character>();
        this.currentGuess = new ArrayList<Integer>();
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
     * shuffles the board
     * uses arraylists to do it
     * might not work cuz it didn't work in my unit 4 problem set?????
     */

    public void shuffle() {
        ArrayList<String> shuffledBoard = new ArrayList<String>(); // initialize empty shuffled board
        Random random = new Random();
        while (this.board.size() > 0) shuffledBoard.add(this.board.remove(random.nextInt(this.board.size()))); // keep adding elements from this.board to shuffledBoard at random indexes, removing from this.board each time
        this.board = shuffledBoard; // assign shuffledBoard to this.board
    }

    /**
     * selects or deselects a word for the current guess
     * returns the word itself if it hasn't been revealed yet so that it can be wordled/spelling beed
     * 
     * @param index index of word to be selected
     * @return the word to be wordled/spelling beed, null if already revealed (if selected/deselected normally)
     */

    public String selectWord(int index) {
        if (!this.revealed.get(this.board.get(index))) return this.board.get(index); // if not revealed, return the word to be wordled/spelling beed
        if (this.currentGuess.contains(index)) this.currentGuess.remove(index); // deselects index if already in guess
        else if (this.currentGuess.size() < 4) this.currentGuess.add(index);
        return null;
    }

    /**
     * update the revealed hashmap to reveal a word that was successfully wordled/spelling beed
     * 
     * @param word word to be revealed
     */

    public void revealWord(String word) {
        this.revealed.replace(word, true);
    }

    /**
     * deselects all words for the current guess
     */

    public void deselectAll() {
        this.currentGuess.clear();
    }

    /**
     * submits a guess
     * 
     * @return '-' if not enough words in guess, '0' if wrong and game-ending (no guesses left), '1' if wrong but not game-ending, 'y'/'g'/'b'/'p' if category matched
     */

    public char submitGuess() {
        if (this.currentGuess.size() < 4) return '-';
        this.currentGuess.sort(null); // so that the indexes of the guess are in order to remove properly
        if (this.wordToCategory.get(this.board.get(this.currentGuess.get(0))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(1))) && this.wordToCategory.get(this.board.get(this.currentGuess.get(1))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(2))) && this.wordToCategory.get(this.board.get(this.currentGuess.get(2))) == this.wordToCategory.get(this.board.get(this.currentGuess.get(3)))) { // if a category is completed
            this.board.remove((int) this.currentGuess.get(3)); // remove all the words from the board
            this.board.remove((int) this.currentGuess.get(2));
            this.board.remove((int) this.currentGuess.get(1));
            this.board.remove((int) this.currentGuess.get(0));
            char categoryCompleted = this.wordToCategory.get(this.board.get(this.currentGuess.get(0))); // category successfully guessed
            this.categoriesCompleted.add(categoryCompleted);
            return categoryCompleted;
        }
        if (--this.guessesLeft == 0) return '0'; // if the player has no guesses left and fails the game (-- decrements the amount of guesses left)
        return '1'; // player didn't fail the game since the above line didn't run
    }
    
}