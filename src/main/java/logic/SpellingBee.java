package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the Spelling Bee game.
 * Contains getters for most variables and methods to run the game (e.g., adding letters to the guess, submitting the guesses).
 * Currently takes in a keyword of any length to be used on the board, as long as it is at LEAST 4 letters long. (Below that breaks the scoring system.)
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-02-25
 * @author @julie-lin-ics4u1-02-25
 * @author aksayan-nirmalan-ics4u1-2-2025
 */

public class SpellingBee {
    
    private String keyword, currentWord; // keyword is the pangram that the user needs to get to progress the connections board
    private ArrayList<Character> letters;
    private char goldenLetter;
    private int score;
    private ArrayList<String> wordsFound;
    private boolean win;

    /**
     * A default constructor for a new game.
     * Takes in the keyword that the player needs to guess, sets the golden letter to the first character, and gives all other instance variables default values.
     * 
     * @param keyword Keyword that the player needs to guess
     */

    public SpellingBee(String keyword) {
        this.keyword = keyword.toUpperCase();
        this.goldenLetter = Character.toUpperCase(keyword.charAt(0));
        this.letters = new ArrayList<Character>();
        for (int letterIndex = 0; letterIndex < this.keyword.length(); ++letterIndex) {
            this.letters.add(this.keyword.charAt(letterIndex)); //Golden letter is always at index 0.
        }
        this.currentWord = "";
        this.score = 0;
        this.wordsFound = new ArrayList<String>();
        this.win = false;
    }

    /**
     * Constructor for game in progress.
     * Sets instance variables equal to parameters.
     * 
     * @param keyword Keyword that the player needs to guess
     * @param goldenLetter Letter that the player needs to include in each of their guesses
     * @param currentWord Current guess that the player has typed
     * @param score Score that the player has achieved thus far
     * @param wordsFound Number of words that the player has found thus far
     */

    public SpellingBee(String keyword, String currentWord, int score, ArrayList<String> wordsFound) {
        this.keyword = keyword;
        this.letters = new ArrayList<Character>(); 
        this.goldenLetter = Character.toUpperCase(keyword.charAt(0));
        for (int letterIndex = 0; letterIndex < this.keyword.length(); ++letterIndex) {
            this.letters.add(this.keyword.charAt(letterIndex));     
        }
        this.currentWord = currentWord;
        this.score = score;
        this.wordsFound = wordsFound;
        this.win = false;
    }

    /**
     * Gets the keyword to be guessed.
     * 
     * @return Keyword to be guessed
     */

    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Gets the list of letters available to use.
     * 
     * @return The list of letters available to use
     */

    public ArrayList<Character> getLetters() {
        return this.letters;
    }

    /**
     * Gets the golden letter to be used in guesses.
     * 
     * @return The golden letter to be used in guesses
     */

    public char getGoldenLetter() {
        return this.goldenLetter;
    }

    /**
     * Gets the current word that the user has typed.
     * 
     * @return The current word that the user has typed
     */

    public String getCurrentWord() {
        return this.currentWord;
    }
    
    /**
     * Gets the score that the user has currently achieved.
     * 
     * @return The score that the user has currently achieved
     */

    public int getScore() {
        return this.score;
    }

    /**
     * Gets the list of words found by the user thus far.
     * 
     * @return The list of words found by the user thus far
     */

    public ArrayList<String> getWordsFound() {
        return this.wordsFound;
    }

    /**
     * Gets the win boolean, which indicates if the player has won or not (gotten the keyword or not).
     * 
     * @return The win boolean
     */

    public boolean getWin() {
        return this.win;
    }

    /**
     * Input a letter into the current word.
     * 
     * @param letter letter to be inputted
     * @return -1 if not a valid letter/not a letter at all, 0 if too long (>19 characters), 1 if successful
     */

    public int inputLetter(char letter) {
        letter = Character.toUpperCase(letter);
        if (!(this.letters.contains(letter) || letter == this.goldenLetter) || !Character.isLetter(letter)) return -1;
        if (this.currentWord.length() == 19) return 0;
        this.currentWord += letter;
        return 1;
    }

    /**
     * Deletes the last letter from the current word. Doesn't do anything if the word is empty.
     */

    public void deleteLetter() {
        if (this.currentWord.length() > 0) this.currentWord = this.currentWord.substring(0, this.currentWord.length() - 1);
    }

    /**
     * Shuffles the letters.
     */

    public void shuffle() {
        Random random = new Random();
        for (int i = this.letters.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1); // random index from 0 to i
            // Swap letters[i] and letters[j]
            char temp = this.letters.get(i);
            this.letters.set(i, this.letters.get(j));
            this.letters.set(j, temp);
        }
    }

    // public void shuffle() {
    //     ArrayList<Character> shuffledLetters = new ArrayList<Character>(); // initialize empty shuffled letters
    //     Random random = new Random();
    //     while (this.letters.size() > 0) shuffledLetters.add(this.letters.remove(random.nextInt(this.letters.size()))); // keep adding elements from newLetters to shuffledLetters at random indexes, removing from newLetters each time
    //     this.letters = shuffledLetters; // assign shuffledLetters to this.letters
    // }

    /**
     * Submits the current word.
     * Calculates the points value if the word is valid and adds it to this.score.
     * If keyword is reached, this.win is set to true.
     * 
     * @return -3 if too short, -2 if missing center letter, -1 if not a word, 0 if word already found, points value if otherwise valid
     */

    public int submitWord() {
        DictionaryChecker dictionaryChecker = new DictionaryChecker();
        if (this.currentWord.length() < 4) {
            this.currentWord = "";
            return -3;
        }
        if (!this.currentWord.contains(String.valueOf(this.goldenLetter))) {
            this.currentWord = "";
            return -2;
        }
        if (!dictionaryChecker.checkWord(this.currentWord)) {
            this.currentWord = "";
            return -1;
        }
        if (this.wordsFound.contains(this.currentWord)) {
            this.currentWord = "";
            return 0;
        }
        // below here assumes it is a valid word
        this.wordsFound.add(this.currentWord);
        if (this.currentWord.equals(keyword)) this.win = true; // set this.win to true if current word is the keyword
        int pointValue = (this.currentWord.length() == 4) ? 1 : this.currentWord.length(); // if the word has 4 letters, the point value is 1, otherwise it's the number of the word's letters
        ArrayList<Character> uniqueLetters = new ArrayList<Character>(); // for checking if the word is a pangram
        for (int letterIndex = 0; letterIndex < this.currentWord.length(); ++letterIndex) {
            char c = this.currentWord.charAt(letterIndex);
            if (!uniqueLetters.contains(c)) uniqueLetters.add(c); // if uniqueLetters doesn't have it yet, add it
        }
        if (uniqueLetters.size() == this.letters.size()) pointValue += this.letters.size(); // if word is a pangram, add extra score
        this.score += pointValue;
        this.currentWord = "";
        return pointValue;
    }

}