/**
 * a class for spelling bee
 * 
 * should we add the points ranking? it would need to be hard-coded prob (or we could grab from api and make it based on # of words)
 */

public class SpellingBee {
    
    private String keyword, currentGuess;
    private char[] letters;
    private int score, wordsFound;

    public SpellingBee(String keyword, char[] letters) {
        this.keyword = keyword;
        this.letters = letters;
        this.currentGuess = "";
        this.score = 0;
        this.wordsFound = 0;
    }

    public SpellingBee(String keyword, char[] letters, String currentGuess, int score, int wordsFound) {
        
    }

}
