package logic;

import java.util.ArrayList;
import java.util.Random;

import kalisz.KaliszTimes;

/**
 * Manages the logic for the Spelling Bee game.
 * 
 * A keyword (pangram) is used to generate the letter board. Players must submit valid words using the letters,
 * always including the golden letter (first character of the keyword).
 * 
 * Words must be at least 4 letters long, and scoring is based on word length with bonuses for pangrams.
 * 
 * This class handles inputting letters, validating words, tracking score, and triggering win events.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-02-25
 * @author @julie-lin-ics4u1-02-25
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class SpellingBee {
    
    /** The keyword (pangram) required to win the game */
    private String keyword;

    /** The current word the player is constructing */
    private String currentWord;

    /** List of all letters available for forming words (includes golden letter) */
    private ArrayList<Character> letters;

    /** The central letter that must appear in every submitted word */
    private char goldenLetter;

    /** Total score the player has accumulated */
    private int score;

    /** List of valid words the player has found so far */
    private ArrayList<String> wordsFound;

    /** Indicates whether the player has found the keyword and won */
    private boolean win;

    /**
     * Constructs a new game with a keyword.
     * Golden letter is automatically set as the first letter of the keyword.
     * 
     * @param keyword the keyword (pangram) that the player needs to find
     */
    public SpellingBee(String keyword) {
        this.keyword = keyword.toUpperCase();
        this.goldenLetter = Character.toUpperCase(keyword.charAt(0));
        this.letters = new ArrayList<>();
        for (int i = 0; i < this.keyword.length(); ++i) {
            this.letters.add(this.keyword.charAt(i));
        }
        this.currentWord = "";
        this.score = 0;
        this.wordsFound = new ArrayList<>();
        this.win = false;
    }

    /**
     * Constructs a Spelling Bee game in progress.
     * Used when loading a game with saved state.
     * 
     * @param keyword the keyword being used
     * @param currentWord the current word the user is typing
     * @param score the score so far
     * @param wordsFound list of words found so far
     */
    public SpellingBee(String keyword, String currentWord, int score, ArrayList<String> wordsFound) {
        this.keyword = keyword;
        this.letters = new ArrayList<>();
        this.goldenLetter = Character.toUpperCase(keyword.charAt(0));
        for (int i = 0; i < this.keyword.length(); ++i) {
            this.letters.add(this.keyword.charAt(i));
        }
        this.currentWord = currentWord;
        this.score = score;
        this.wordsFound = wordsFound;
        this.win = false;
    }

    /** @return the keyword to be found */
    public String getKeyword() {
        return this.keyword;
    }

    /** @return list of letters available to use */
    public ArrayList<Character> getLetters() {
        return this.letters;
    }

    /** @return the golden letter that must appear in every word */
    public char getGoldenLetter() {
        return this.goldenLetter;
    }

    /** @return the current word being typed */
    public String getCurrentWord() {
        return this.currentWord;
    }

    /** @return the player's current score */
    public int getScore() {
        return this.score;
    }

    /** @return list of valid words found so far */
    public ArrayList<String> getWordsFound() {
        return this.wordsFound;
    }

    /** @return whether the player has found the keyword and won */
    public boolean getWin() {
        return this.win;
    }

    /**
     * Attempts to add a letter to the current word.
     * 
     * @param letter the character to input
     * @return 1 if successful, 0 if word length limit reached, -1 if invalid letter
     */
    public int inputLetter(char letter) {
        letter = Character.toUpperCase(letter);
        if (!(this.letters.contains(letter) || letter == this.goldenLetter) || !Character.isLetter(letter)) return -1;
        if (this.currentWord.length() == 19) return 0;
        this.currentWord += letter;
        return 1;
    }

    /**
     * Deletes the last letter from the current word.
     * Does nothing if the current word is already empty.
     */
    public void deleteLetter() {
        if (this.currentWord.length() > 0)
            this.currentWord = this.currentWord.substring(0, this.currentWord.length() - 1);
    }

    /**
     * Randomly shuffles the available letters.
     */
    public void shuffle() {
        Random random = new Random();
        for (int i = this.letters.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = this.letters.get(i);
            this.letters.set(i, this.letters.get(j));
            this.letters.set(j, temp);
        }
    }

    /**
     * Attempts to submit the current word.
     * 
     * Validates word length, presence of golden letter, dictionary existence, and uniqueness.
     * Calculates points based on length and pangram status.
     * 
     * @return point value if valid, or a negative code:
     *         -3 = too short, -2 = missing golden letter, -1 = not in dictionary, 0 = already found
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

        // Word is valid
        this.wordsFound.add(this.currentWord);
        if (this.currentWord.equals(keyword)) this.win = true;

        int pointValue = (this.currentWord.length() == 4) ? 1 : this.currentWord.length();

        // Bonus if it's a pangram
        ArrayList<Character> uniqueLetters = new ArrayList<>();
        for (int i = 0; i < this.currentWord.length(); ++i) {
            char c = this.currentWord.charAt(i);
            if (!uniqueLetters.contains(c)) uniqueLetters.add(c);
        }
        if (uniqueLetters.size() == this.letters.size()) pointValue += this.letters.size();

        this.score += pointValue;
        this.currentWord = "";
        return pointValue;
    }

    /**
     * Triggers the win event logic after the player wins:
     * - Updates player stats
     * - Updates leaderboard
     * - Refreshes leaderboard UI
     */
    public void winEvent() {
        KaliszTimes.player.incrementSpellingBeeWins();
        KaliszTimes.player.saveStats();

        LeaderboardHandler leaderboard = new LeaderboardHandler();
        leaderboard.saveAllStats();

        KaliszTimes.getGraphicsHandler().reloadLeaderboardFrame();
    }
}
