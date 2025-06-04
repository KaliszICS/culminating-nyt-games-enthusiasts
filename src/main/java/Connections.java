import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.HashMap;

public class Connections {

    private String[] board;
    private int guessesLeft, categoriesLeft, wordsLeftToSelect;
    private String[] yellowWords, greenWords, blueWords, purpleWords;
    private String yellowCategory, greenCategory, blueCategory, purpleCategory;
    private char[] categoriesCompleted; // 0 IF INCOMPLETE
    private int[] currentGuess; // takes indexes from the board array
    private HashMap<String, Character> wordToCategory;

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
        for (int index = 0; index < 4; ++index) this.wordToCategory.put(yellowWords[index], 'y'); // assigns each word to their colour's character
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
        this.board = new String[16];
        this.guessesLeft = 4;
        this.categoriesLeft = 4;
        this.categoriesCompleted = new char[] {'0', '0', '0', '0'};
        this.currentGuess = new int[4];
        this.wordsLeftToSelect = 4;
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
        this.board = new String[this.categoriesLeft*4]; // 4 words per category
        this.guessesLeft = guessesLeft;
        this.categoriesLeft = categoriesLeft;
        this.categoriesCompleted = categoriesCompleted;
        this.currentGuess = new int[4];
        this.wordsLeftToSelect = 4;
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
     */

    public void shuffle() {
        ArrayList<String> newBoard = new ArrayList<String>(Arrays.asList(this.board)), shuffledBoard = new ArrayList<String>(); // copy this.board to a new board, initialize empty shuffled board
        Random random = new Random();
        while (newBoard.size() > 0) shuffledBoard.add(newBoard.remove(random.nextInt(newBoard.size()))); // keep adding elements from newBoard to shuffledBoard at random indexes, removing from newBoard each time
        this.board = shuffledBoard.toArray(new String[0]); // cast shuffledBoard to this.board
    }

    /**
     * selects a word for the current guess
     * 
     * @param index
     */

    public void selectWord(int index) {
        if (this.wordsLeftToSelect == 0) return;
        this.currentGuess[--this.wordsLeftToSelect] = index; // add index to current guess (going backwards)
    }

    /**
     * deselects all words for the current guess
     */

    public void deselectAll() {
        this.currentGuess = new int[4];
        this.wordsLeftToSelect = 4;
    }

    /**
     * removes correctly chosen words from the board and shrinks it
     * 
     * @param a index of first word
     * @param b index of second word
     * @param c index of third word
     * @param d index of fourth word
     */

    public void shrinkBoard(int a, int b, int c, int d) {
        ArrayList<String> newBoard = new ArrayList<String>(Arrays.asList(this.board));
        newBoard.remove(d);
        newBoard.remove(c);
        newBoard.remove(b);
        newBoard.remove(a);
        this.board = newBoard.toArray(new String[0]);
    }

    /**
     * submits a guess
     * 
     * @return '-' if not enough words in guess, '0' if wrong and game-ending (no guesses left), '1' if wrong but not game-ending, 'y'/'g'/'b'/'p' if category matched
     */

    public char submitGuess() {
        if (this.wordsLeftToSelect > 0) return '-';
        Arrays.sort(this.currentGuess); // so that the indexes of the guess are in order for shrinkBoard
        if (this.wordToCategory.get(this.board[this.currentGuess[0]]) == this.wordToCategory.get(this.board[this.currentGuess[1]]) && this.wordToCategory.get(this.board[this.currentGuess[1]]) == this.wordToCategory.get(this.board[this.currentGuess[2]]) && this.wordToCategory.get(this.board[this.currentGuess[2]]) == this.wordToCategory.get(this.board[this.currentGuess[3]])) { // if a category is completed
            shrinkBoard(this.currentGuess[0], this.currentGuess[1], this.currentGuess[2], this.currentGuess[3]); // remove the valid guesses from the board
            char categoryCompleted = this.wordToCategory.get(this.board[this.currentGuess[0]]); // category successfully guessed
            this.categoriesCompleted[4 - this.categoriesLeft--] = categoryCompleted; // 4 - this.categoriesLeft-- tracks the categories completed in order in categoriesCompleted (the -- decrements the number of categories left to guess)
            return categoryCompleted;
        }
        if (--this.guessesLeft == 0) return '0'; // if the player has no guesses left and fails the game (-- decrements the amount of guesses left)
        return '1'; // player didn't fail the game since the above line didn't run
    }
    
}