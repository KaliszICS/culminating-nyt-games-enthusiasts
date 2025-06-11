package logic;

import java.util.ArrayList;

/**
 * Class for the Wordle game.
 * The class includes getters for some variables and methods to run the game (adding letters, submitting guesses, etc.).
 * Currently takes in a word of any length to be guessed. Always gives the user 6 guesses (words of length 3 or below not recommended).
 * 
 * CURRENTLY USES UpperCase (change to uppercase for Elliot)
 * Edited to take in strings, edit back to char array to match GUI 
 * 
 * @author @FranklinZhu1
 */

import java.util.Arrays;

public class Wordle {

    private char[] word;
    private String currentGuess;
    private String[] guessData, overallGuessData; // guessData is local to each guess, overallGuessData is a 26 length array for each character to track overall (think of the on-screen keyboard)
    private int guessCount;
    private boolean win;
    private int[] wordLetterCount; // will have size 26
    private ArrayList<String[]> results; // for the ascii results you copy paste after a game

    /**
     * Default constructor to make new Wordle game with every stat at default.
     * 
     * @param word solution word
     */

    public Wordle(char[] word) {
        this.word = word;
        this.guessCount = 0;
        this.guessData = new String[this.word.length];
        this.overallGuessData = new String[26];
        Arrays.fill(this.overallGuessData, "");
        this.currentGuess = "";
        this.win = false;
        this.wordLetterCount = new int[26];
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 97];
        }
        this.results = new ArrayList<String[]>();
    }

    /**
     * Constructor to initialize an already-started game which assigns each variable.
     * 
     * @param word solution word
     * @param guessCount the number of guesses already made
     * @param overallGuessData the data for each letter already discovered
     */

    public Wordle(char[] word, int guessCount, String[] overallGuessData) {
        this.word = word;
        this.guessCount = guessCount;
        this.guessData = new String[this.word.length];
        this.overallGuessData = overallGuessData;
        Arrays.fill(this.overallGuessData, "");
        this.currentGuess = "";
        this.win = false;
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 97]; // {1, 0, 0, 1, 2, 0, 0, 0, 0...}
        }
        this.results = new ArrayList<String[]>();
    }

    public char[] getWord() {
        return this.word;
    }

    public int getWordLength() {
        return this.word.length;
    }

    public int getGuessCount() {
        return this.guessCount;
    }

    public String[] getGuessData() {
        return this.guessData;
    }

    public String[] getOverallGuessData() {
        return this.overallGuessData;
    }

    public String getCurrentGuess() {
        return this.currentGuess;
    }

    public boolean getWin() {
        return this.win;
    }

    public ArrayList<String[]> getResults() {
        return this.results;
    }

    /**
     * Types a letter into the current guess.
     * 
     * @param letter
     * @return false if too many letters
     */

    public boolean inputLetter(char letter) {
        if (this.currentGuess.length() >= this.word.length || !Character.isLetter(letter)) return false;
        this.currentGuess += Character.toUpperCase(letter);
        return true;
    }

    /**
     * Deletes the last letter from the current guess. Doesn't do anything if the guess is empty.
     */

    public void deleteLetter() {
        if (this.currentGuess.length() > 0) this.currentGuess = this.currentGuess.substring(0, this.currentGuess.length() - 1);
    }

    /**
     * Submits the current guess and tracks the guess data.
     * CAN CHANGE this.win, charAtWin() SHOULD ALWAYS BE RAN AFTER USING THIS METHOD.
     * 
     * @return -1 if not enough letters to guess, 0 if guess is not a word, 1 if the guess went through
     */

     public int submitGuess() {
        DictionaryChecker dictionaryChecker = new DictionaryChecker();
        if (this.currentGuess.length() < this.word.length) {
            this.currentGuess = ""; // clear guess after submission
            return -1;
        }
        if (!dictionaryChecker.checkWord(this.currentGuess)) {
            this.currentGuess = ""; // clear guess after submission
            return 0;
        }
        // Below here logs the word as a guess and tracks the stats
        this.guessData = new String[this.word.length];
        Arrays.fill(this.guessData, ""); // reset guessData to empty strings 
        this.win = true; // assume the player wins first
        int[] lettersUsed = Arrays.copyOf(this.wordLetterCount, 26); // copy of word letter count to track how many letters have been used (e.g., what if letters repeat?)
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            int letterCode = this.currentGuess.charAt(letterIndex) - 97; // 0 = 'a', 1 = 'b', etc.
            if (this.word[letterIndex] == this.currentGuess.charAt(letterIndex)) { // green case
                if (lettersUsed[letterCode] == 0) { // letter already marked yellow previously
                    for (int revIndex = this.word.length - 1; revIndex >= 0; --revIndex) { // search backwards through guessData to find the last yellow
                        if (this.guessData[revIndex].equals("yellow")) {
                            this.guessData[revIndex] = "grey"; // turn it grey instead
                            break;
                        }
                    }
                } else --lettersUsed[letterCode]; // otherwise "use up" another letter
                this.guessData[letterIndex] = "green"; // nth letter marked green
                this.overallGuessData[letterCode] = "green"; // letter green on keyboard
            }
            else if (lettersUsed[letterCode] > 0) { // yellow case (if there are two Es in the word and this iteration assesses a third, this clause will not run)
                --lettersUsed[letterCode];
                this.win = false; // the player has not won
                this.guessData[letterIndex] = "yellow"; // nth letter marked yellow
                if (this.overallGuessData[letterCode] != "green") this.overallGuessData[letterCode] = "yellow"; // if it's not already green on that character, mark it yellow
            }
            else { // grey case
                this.win = false; // the player has not won
                this.guessData[letterIndex] = "grey";
                if (this.overallGuessData[letterCode].equals("")) this.overallGuessData[letterCode] = "grey"; // if there's no colour on that character (EMPTY STRING IN ARRAY), mark it grey
            }
        }
        ++guessCount; // one more guess was made
        this.results.add(guessData); // add submitted guess to results
        this.currentGuess = ""; // clear guess after submission
        return 1;
    }
}