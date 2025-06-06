/**
 * Class for the Wordle game.
 * The class includes getters for some variables and methods to run the game (adding letters, submitting guesses, etc.).
 * Currently takes in a word of any length to be guessed. Always gives the user 6 guesses (words of length 3 or below not recommended).
 * Does not currently make use of a dictionary API to validate guesses (to be added).
 * 
 * @author @FranklinZhu1
 */

import java.util.Arrays;
import java.util.ArrayList;

public class Wordle {

    private char[] word;
    private String[] guessData, overallGuessData; // guessData is local to each guess, overallGuessData is a 26 length array for each character to track overall (think of the on-screen keyboard)
    private ArrayList<Character> currentGuess;
    private int wordLength, guessCount;
    private boolean win;
    private int[] wordLetterCount; // will have size 26

    /**
     * Default constructor to make new wordle game with every stat at default.
     * 
     * @param word solution word
     */

    public Wordle(char[] word) {
        this.word = word;
        this.wordLength = this.word.length;
        this.guessCount = 0;
        this.guessData = new String[this.wordLength];
        this.overallGuessData = new String[26];
        this.currentGuess = new ArrayList<Character>();
        this.win = false;
        this.wordLetterCount = new int[26];
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 97];
        }
    }

    /**
     * Constructor to initialize an already-started game, assigns each variable
     * 
     * @param word solution word
     * @param guessCount the number of guesses already made
     * @param overallGuessData the data for each letter already discovered
     */

    public Wordle(char[] word, int guessCount, String[] overallGuessData) {
        this.word = word;
        this.wordLength = this.word.length;
        this.guessCount = guessCount;
        this.guessData = new String[this.wordLength];
        this.overallGuessData = overallGuessData;
        this.currentGuess = new ArrayList<Character>();
        this.win = false;
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 97];
        }
    }

    public int getWordLength() {
        return this.wordLength;
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

    public ArrayList<Character> getCurrentGuess() {
        return this.currentGuess;
    }

    public boolean getWin() {
        return this.win;
    }

    /**
     * Types a letter into the current guess.
     * 
     * @param letter
     * @return false if too many letters
     */

    public boolean inputLetter(char letter) {
        if (this.currentGuess.size() >= this.wordLength || !Character.isLetter(letter)) return false;
        this.currentGuess.add((letter < 97) ? (char) (letter + 32) : letter); // convert to lowercase
        return true;
    }

    /**
     * Deletes the last letter from the current guess. Doesn't do anything if the guess is empty.
     */

    public void deleteLetter() {
        if (this.currentGuess.size() == 0) return;
        this.currentGuess.remove(this.currentGuess.size() - 1);
    }

    /**
     * Submits the current guess and tracks the guess data.
     * CAN CHANGE this.win, getWin() SHOULD ALWAYS BE RAN AFTER USING THIS METHOD.
     * 
     * @return -1 if not enough letters to guess, 0 if guess is not a word, 1 if the guess went through
     */

    public int submitGuess() {
        if (this.currentGuess.size() < this.wordLength) return -1;
        // if it ain't a word return 0 WE NEED A DICTIONARY API
        // Below here logs the word as a guess and tracks the stats
        this.win = true; // assume the player wins first
        int[] lettersUsed = Arrays.copyOf(this.wordLetterCount, 26); // copy of word letter count to track how many letters have been used (e.g., what if letters repeat?)
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            if (this.word[letterIndex] == this.currentGuess.get(letterIndex)) { // green case
                --lettersUsed[this.currentGuess.get(letterIndex)];
                this.guessData[letterIndex] = "green"; // nth letter marked green
                this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = "green"; // letter green on keyboard
            }
            else if (--lettersUsed[this.currentGuess.get(letterIndex)] >= 0) { // yellow case (the -- decrements the repeated letters left until grey, i.e., if there are two Es in the word and this iteration assesses a third, this clause will not run)
                this.win = false; // the player has not won
                this.guessData[letterIndex] = "yellow"; // nth letter marked yellow
                if (this.overallGuessData[this.currentGuess.get(letterIndex) - 97] != "green") this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = "yellow"; // if it's not already green on that character, mark it yellow
            }
            else { // grey case
                this.win = false; // the player has not won
                this.guessData[letterIndex] = "grey";
                if (this.overallGuessData[this.currentGuess.get(letterIndex) - 97] == "") this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = "grey"; // if there's no colour on that character (EMPTY STRING IN ARRAY), mark it grey
            }
        }
        ++guessCount; // one more guess was made
        return 1;
    }

}