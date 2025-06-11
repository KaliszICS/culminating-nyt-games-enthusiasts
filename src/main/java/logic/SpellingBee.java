package logic;

/**
 * Class for the Spelling Bee game.
 * Contains getters for most variables and methods to run the game (e.g., adding letters to the guess, submitting the guesses).
 * Currently takes in a keyword of any length to be used on the board, as long as it is at LEAST 4 letters long. (Below that breaks the scoring system.)
 * 
 * @author @FranklinZhu1
 */

import java.util.ArrayList;
import java.util.Random;

public class SpellingBee {
    
    private String keyword, currentWord; // keyword is the pangram that the user needs to get to progress the connections board
    private ArrayList<Character> letters;
    private char goldenLetter;
    private int score;
    private ArrayList<String> wordsFound;
    private boolean win;

    public SpellingBee(String keyword) {
        this.keyword = keyword.toUpperCase();
        this.goldenLetter = Character.toUpperCase(keyword.charAt(0));
        this.letters = new ArrayList<Character>();
        for (int letterIndex = 0; letterIndex < this.keyword.length(); ++letterIndex) {
            this.letters.add(this.keyword.charAt(letterIndex)); 
                 
                //Golden letter is always at index 0.
        }
     
        
        this.currentWord = "";
        this.score = 0;
        this.wordsFound = new ArrayList<String>();
        this.win = false;
    }

    public SpellingBee(String keyword, char goldenLetter, String currentWord, int score, ArrayList<String> wordsFound) {
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

    public String getKeyword() {
        return this.keyword;
    }

    public ArrayList<Character> getLetters() {
        return this.letters;
    }

    public char getGoldenLetter() {
        return this.goldenLetter;
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
        if (!this.currentWord.contains(String.valueOf(this.goldenLetter))){
            this.currentWord = "";
            return -2;
        } 
        if (!dictionaryChecker.checkWord(this.currentWord)){
            this.currentWord = "";
            return -1;
        } 
        if (this.wordsFound.contains(this.currentWord)){
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
