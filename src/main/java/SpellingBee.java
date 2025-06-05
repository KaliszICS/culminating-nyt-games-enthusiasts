/**
 * a class for spelling bee
 * 
 * should we add the points ranking? it would need to be hard-coded prob (or we could grab from api and make it based on # of words)
 * 19 letters is the max
 */

import java.util.ArrayList;
import java.util.Random;

public class SpellingBee {
    
    private String keyword, currentWord; // keyword is the pangram that the user needs to get to progress the connections board
    private ArrayList<Character> letters;
    private char goldenLetter;
    private int score;
    private ArrayList<String> wordsFound;

    public SpellingBee(String keyword, char goldenLetter) {
        this.keyword = keyword;
        for (int letterIndex = 0; letterIndex < this.keyword.length(); ++letterIndex) {
            if (!this.letters.contains(this.keyword.charAt(letterIndex))) this.letters.add(this.keyword.charAt(letterIndex)); // if letters doesn't have it yet, add it
        }
        this.goldenLetter = goldenLetter;
        this.currentWord = "";
        this.score = 0;
        this.wordsFound = new ArrayList<String>();
    }

    public SpellingBee(String keyword, char goldenLetter, String currentWord, int score, ArrayList<String> wordsFound) {
        this.keyword = keyword;
        for (int letterIndex = 0; letterIndex < this.keyword.length(); ++letterIndex) {
            if (!this.letters.contains(this.keyword.charAt(letterIndex))) this.letters.add(this.keyword.charAt(letterIndex)); // if letters doesn't have it yet, add it
        }
        this.goldenLetter = goldenLetter;
        this.currentWord = currentWord;
        this.score = score;
        this.wordsFound = wordsFound;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public ArrayList<Character> getLetters() {
        return this.letters;
    }

    public String getCurrentWord() {
        return this.currentWord;
    }

    public int getScore() {
        return this.score;
    }

    public ArrayList<String> getWordsFound() {
        return this.wordsFound;
    }

    /**
     * input a letter into the current word
     * 
     * @param letter letter to be inputted
     * @return -1 if not a letter, 0 if too long (>19 characters), 1 if successful
     */

    public int inputLetter(char letter) {
        if (!Character.isLetter(letter)) return -1;
        if (this.currentWord.length() == 19) return 0;
        this.currentWord += letter;
        return 1;
    }

    /**
     * deletes a letter from the current word
     */

    public void deleteLetter() {
        if (this.currentWord.length() > 0) this.currentWord = this.currentWord.substring(0, this.currentWord.length() - 1);
    }

    /**
     * shuffles the letters
     * uses arraylists to do it
     * might not work cuz it didn't work in my unit 4 problem set?????
     */

    public void shuffle() {
        ArrayList<Character> shuffledLetters = new ArrayList<Character>(); // initialize empty shuffled letters
        Random random = new Random();
        while (this.letters.size() > 0) shuffledLetters.add(this.letters.remove(random.nextInt(this.letters.size()))); // keep adding elements from newLetters to shuffledLetters at random indexes, removing from newLetters each time
        this.letters = shuffledLetters; // assign shuffledLetters to this.letters
    }

    /**
     * submits the current word
     * 
     * @return -2 IF TOO SHORT, -1 IF MISSING CENTER LETTER, 0 IF NOT A WORD, POINTS VALUE IF SUCCESSFUL
     */

    public int submitWord() {
        if (this.currentWord.length() < 4) return -2;
        if (!this.currentWord.contains(String.valueOf(this.goldenLetter))) return -1;
        // if it's not a word WE NEED A DICTIONARY API return 0
        int pointValue = (this.currentWord.length() == 4) ? 1 : this.currentWord.length(); // if the word has 4 letters, the point value is 1, otherwise it's the number of the word's letters
        ArrayList<Character> uniqueLetters = new ArrayList<Character>();
        for (int letterIndex = 0; letterIndex < this.currentWord.length(); ++letterIndex) {
            if (!uniqueLetters.contains(this.currentWord.charAt(letterIndex))) uniqueLetters.add(this.currentWord.charAt(letterIndex)); // if uniqueLetters doesn't have it yet, add it
        }
        if (uniqueLetters.size() == this.letters.size()) pointValue += this.letters.size(); // if pangram, add the number of available letters to pointValue
        this.score += pointValue;
        return pointValue;
    }

}
