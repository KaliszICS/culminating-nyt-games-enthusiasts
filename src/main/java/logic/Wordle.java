package logic;

import java.util.ArrayList;

/**
 * Class for the Wordle game.
 * The class includes getters for some variables and methods to run the game (adding letters, submitting guesses, etc.).
 * Currently takes in a word of any length to be guessed. Always gives the user 6 guesses (words of length 3 or below not recommended).
 * Does not currently make use of a dictionary API to validate guesses (to be added).
 * 
 * @author @FranklinZhu1
 */

import java.util.Arrays;

public class Wordle {

    private char[] word;
    private String[] guessData, overallGuessData; // guessData is local to each guess, overallGuessData is a 26 length array for each character to track overall (think of the on-screen keyboard)
    private String currentGuess;
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
        this.currentGuess = "";
        this.win = false;
        this.wordLetterCount = new int[26];
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 'A'];
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
        this.currentGuess = "";
        this.win = false;
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 'A'];
        }
        this.results = new ArrayList<String[]>();
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
     * Clears the current guess, removing all its letters.
     */

    public void clearGuess() {
        this.currentGuess = "";
    }

    /**
     * Submits the current guess and tracks the guess data.
     * CAN CHANGE this.win, charAtWin() SHOULD ALWAYS BE RAN AFTER USING THIS METHOD.
     * 
     * @return -1 if not enough letters to guess, 0 if guess is not a word, 1 if the guess went through
     */

     public int submitGuess() {
        DictionaryChecker dictionaryChecker = new DictionaryChecker();
        if (this.currentGuess.length() < this.word.length) return -1;
        if (!dictionaryChecker.checkWordExists(this.currentGuess)) return 0;

        this.win = true;
        int[] lettersUsed = Arrays.copyOf(this.wordLetterCount, 26);

        for (int i = 0; i < word.length; ++i) {
            char answerLetter = this.word[i];
            char guessLetter = this.currentGuess.charAt(i);

            if (answerLetter == guessLetter) {
                --lettersUsed[guessLetter - 'A'];
                this.guessData[i] = "green";
                this.overallGuessData[guessLetter - 'A'] = "green";
            } else if (--lettersUsed[guessLetter - 'A'] >= 0) {
                this.win = false;
                this.guessData[i] = "yellow";
                if (!"green".equals(this.overallGuessData[guessLetter - 'A'])) {
                    this.overallGuessData[guessLetter - 'A'] = "yellow";
                }
            } else {
                this.win = false;
                this.guessData[i] = "grey";
                if (this.overallGuessData[guessLetter - 'A'] == null || this.overallGuessData[guessLetter - 'A'].isEmpty()) {
                    this.overallGuessData[guessLetter - 'A'] = "grey";
                }
            }
        }
        ++guessCount; // one more guess was made
        this.results.add(guessData); // add submitted guess to results
        return 1;
    }
}