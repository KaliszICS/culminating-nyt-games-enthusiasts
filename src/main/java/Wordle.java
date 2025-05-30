/**
 * how do i write javadocs
 * this is a class for wordle
 * it includes methods to play the game like putting in guesses
 * ALL LETTERS ARE IN LOWERCASE!
 */

import java.util.Arrays;

public class Wordle {

    private char[] word, guessData, currentGuess; // guessData: g for green, y for yellow, 0 for grey
    private int wordLength, guessCount, currentGuessLetterCount;
    private boolean win;
    private int[] wordLetterCount;

    /**
     * default constructor to make new wordle game with every stat at default
     * 
     * @param wordLength
     */

    public Wordle(char[] word) {
        this.word = word;
        this.wordLength = this.word.length;
        this.guessCount = 0;
        this.currentGuessLetterCount = 0;
        this.guessData = new char[this.wordLength];
        this.currentGuess = new char[this.wordLength];
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
        this.currentGuessLetterCount = 0;
        this.guessData = guessData;
        this.currentGuess = new char[this.wordLength];
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
        if (this.currentGuessLetterCount >= this.wordLength || !Character.isLetter(letter)) return false;
        this.currentGuess[this.currentGuessLetterCount++] = (letter < 97) ? letter + 32 : letter; // convert to lowercase
        return true;
    }

    /**
     * submits the current guess. modifies board as needed
     * 
     * @return FALSE IF IT AIN'T A WORD
     */

    public boolean submitGuess() {
        // if it ain't a word return false WE NEED A DICTIONARY API
        char[] counterthing = Arrays.copyOf(wordLetterCount);
        for (int letterIndex = 0; letterIndex < 5; ++letterIndex) {
            if (word[letterIndex] == currentGuess[letterIndex]) {
                --counterthing[currentGuess[letterIndex]];
                guessData[letterIndex] = 'g';
            }
            else if (--counterthing[currentGuess[letterIndex]] >= 0) {
                guessData[letterIndex] = 'y';
            }
            else {
                guessData[letterIndex] = '0';
            }
        }
        return true;
    }

}