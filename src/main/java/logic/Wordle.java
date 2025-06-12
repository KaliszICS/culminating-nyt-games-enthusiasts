package logic;

import java.util.ArrayList;
import java.util.Arrays;
import kalisz.KaliszTimes;

/**
 * Class for the Wordle game.
 * Handles input, guess submission, win tracking, and per-letter result calculation for the game.
 * Designed to handle words of any length (though lengths shorter than 4 are not recommended).
 * This implementation assumes all input and solution words are uppercase.
 *
 * <p>Authors:
 * <ul>
 *   <li>@FranklinZhu1</li>
 *   <li>@elliot-chan-ics4u1-2-2025</li>
 *   <li>@julie-lin-ics4u1-2-2025</li>
 *   <li>@aksayan-nirmalan-ics4u1-2-2025</li>
 * </ul>
 *
 */
public class Wordle {

    private String word;
    private String currentGuess;
    private String[] guessData, overallGuessData;
    private int guessCount;
    private boolean win;
    private int[] wordLetterCount;
    private ArrayList<String[]> results;

    /**
     * Constructs a new Wordle game.
     *
     * @param word The target solution word (must be in uppercase)
     */
    public Wordle(String word) {
        this.word = word;
        this.guessCount = 0;
        this.guessData = new String[this.word.length()];
        this.overallGuessData = new String[26];
        Arrays.fill(this.overallGuessData, "");
        this.currentGuess = "";
        this.win = false;
        this.wordLetterCount = new int[26];
        for (int i = 0; i < word.length(); ++i) {
            ++wordLetterCount[word.charAt(i) - 'A'];
        }
        this.results = new ArrayList<>();
    }

    /** @return the target word to be guessed */
    public String getWord() {
        return this.word;
    }

    /** @return the length of the target word */
    public int getWordLength() {
        return this.word.length();
    }

    /** @return the number of guesses the player has used */
    public int getGuessCount() {
        return this.guessCount;
    }

    /** @return the most recent guess result array (green/yellow/grey) */
    public String[] getGuessData() {
        return this.guessData;
    }

    /** @return the current global keyboard guess state for each letter */
    public String[] getOverallGuessData() {
        return this.overallGuessData;
    }

    /** @return the currently typed (incomplete) guess */
    public String getCurrentGuess() {
        return this.currentGuess;
    }

    /** @return true if the player has correctly guessed the word */
    public boolean getWin() {
        return this.win;
    }

    /** @return list of all guess result arrays from each submitted guess */
    public ArrayList<String[]> getResults() {
        return this.results;
    }

    /**
     * Appends a letter to the current guess if it doesn't exceed word length.
     *
     * @param letter The letter to add
     * @return true if the letter was added, false if invalid or too long
     */
    public boolean inputLetter(char letter) {
        if (this.currentGuess.length() >= this.word.length() || !Character.isLetter(letter)) return false;
        this.currentGuess += Character.toUpperCase(letter);
        return true;
    }

    /**
     * Deletes the last letter from the current guess.
     * Does nothing if the guess is empty.
     */
    public void deleteLetter() {
        if (this.currentGuess.length() > 0)
            this.currentGuess = this.currentGuess.substring(0, this.currentGuess.length() - 1);
    }

    /**
     * Submits the current guess for evaluation.
     * Compares letters to the solution and updates guess data.
     * Updates win condition and keyboard color state.
     *
     * @return -1 if guess too short, 0 if not a word, 1 if valid submission
     */
    public int submitGuess() {
        DictionaryChecker dictionaryChecker = new DictionaryChecker();
        if (this.currentGuess.length() < this.word.length()) {
            this.currentGuess = "";
            return -1;
        }
        if (!dictionaryChecker.checkWord(this.currentGuess)) {
            this.currentGuess = "";
            return 0;
        }

        this.guessData = new String[this.word.length()];
        Arrays.fill(this.guessData, "");
        this.win = true;
        int[] lettersUsed = Arrays.copyOf(this.wordLetterCount, 26);

        for (int i = 0; i < this.word.length(); ++i) {
            int code = this.currentGuess.charAt(i) - 'A';
            if (this.word.charAt(i) == this.currentGuess.charAt(i)) {
                if (lettersUsed[code] == 0) {
                    for (int j = this.word.length() - 1; j >= 0; --j) {
                        if (this.guessData[j].equals("yellow")) {
                            this.guessData[j] = "grey";
                            break;
                        }
                    }
                } else --lettersUsed[code];
                this.guessData[i] = "green";
                this.overallGuessData[code] = "green";
            } else if (lettersUsed[code] > 0) {
                --lettersUsed[code];
                this.guessData[i] = "yellow";
                this.win = false;
                if (!"green".equals(this.overallGuessData[code])) this.overallGuessData[code] = "yellow";
            } else {
                this.guessData[i] = "grey";
                this.win = false;
                if ("".equals(this.overallGuessData[code])) this.overallGuessData[code] = "grey";
            }
        }

        ++guessCount;
        this.results.add(this.guessData);
        this.currentGuess = "";
        return 1;
    }

    /**
     * Handles logic to be run on win condition (saving player data and refreshing leaderboard).
     */
    public void winEvent() {
        KaliszTimes.player.incrementWordleWins();
        KaliszTimes.player.saveStats();

        LeaderboardHandler leaderboard = new LeaderboardHandler();
        leaderboard.saveAllStats();

        KaliszTimes.getGraphicsHandler().reloadLeaderboardFrame();
    }
}
