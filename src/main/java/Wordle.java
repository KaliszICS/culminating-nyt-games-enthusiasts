import java.util.Arrays;
import java.util.ArrayList;

/**
 * this is a class for wordle
 * it includes methods to play the game like putting in guesses
 * ALL LETTERS ARE IN LOWERCASE!
 * we need a dictionary api
 * 
 * guessData is local to each guess, overallGuessData is a 26 length array for each character to track overall (think of the on-screen keyboard)
 * 
 * author @FranklinZhu1
 */

public class Wordle {

    private char[] word, guessData, overallGuessData; // guessData: g for green, y for yellow, 0 for grey
    private ArrayList<Character> currentGuess;
    private int wordLength, guessCount;
    private boolean win;
    private int[] wordLetterCount; // will have size 26

    /**
     * default constructor to make new wordle game with every stat at default
     * 
     * @param wordLength
     */

    public Wordle(char[] word) {
        this.word = word;
        this.wordLength = this.word.length;
        this.guessCount = 0;
        this.guessData = new char[this.wordLength];
        this.overallGuessData = new char[26];
        this.currentGuess = new ArrayList<Character>();
        this.win = false;
        this.wordLetterCount = new int[26];
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            ++wordLetterCount[this.word[letterIndex] - 97];
        }
    }

    /**
     * constructor to initialize an already-started game, assigns each variable
     * 
     * @param wordLength
     * @param guessCount
     */

    public Wordle(char[] word, int guessCount, char[] guessData) {
        this.word = word;
        this.wordLength = this.word.length;
        this.guessCount = guessCount;
        this.guessData = guessData;
        this.overallGuessData = new char[26];
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

    public char[] getGuessData() {
        return this.guessData;
    }

    public boolean getWin() {
        return this.win;
    }

    /**
     * types a letter into the guess
     * 
     * @param letter
     * @return FALSE IF TOO MANY LETTERS
     */

    public boolean inputLetter(char letter) {
        if (this.currentGuess.size() >= this.wordLength || !Character.isLetter(letter)) return false;
        this.currentGuess.add((letter < 97) ? (char) (letter + 32) : letter); // convert to lowercase
        return true;
    }

    /**
     * submits the current guess. modifies board as needed
     * MODIFIES this.win, getWin() SHOULD ALWAYS BE RAN AFTER USING THIS METHOD
     * 
     * @return -1 IF NOT ENOUGH LETTERS, 0 IF NOT A WORD, 1 IF GOOD
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
                this.guessData[letterIndex] = 'g'; // nth letter marked green
                this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = 'g'; // letter green on keyboard
            }
            else if (--lettersUsed[this.currentGuess.get(letterIndex)] >= 0) { // yellow case (the -- decrements the repeated letters left until grey, i.e., if there are two Es in the word and this iteration assesses a third, this clause will not run)
                this.win = false; // the player has not won
                this.guessData[letterIndex] = 'y'; // nth letter marked yellow
                if (this.overallGuessData[this.currentGuess.get(letterIndex) - 97] != 'g') this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = 'y'; // if it's not already green on that character, mark it yellow
            }
            else { // grey case
                this.win = false; // the player has not won
                this.guessData[letterIndex] = '0';
                if (this.overallGuessData[this.currentGuess.get(letterIndex) - 97] == '\0') this.overallGuessData[this.currentGuess.get(letterIndex) - 97] = '0'; // if there's no colour on that character (NULL CHAR IN ARRAY), mark it grey
            }
        }
        ++guessCount; // one more guess was made
        return 1;
    }

}