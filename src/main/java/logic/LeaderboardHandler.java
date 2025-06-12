package logic;

import java.io.*;
import java.util.*;

import kalisz.KaliszTimes;

/**
 * Handles leaderboard management including saving scores and retrieving top scores
 * for multiple game modes. Scores are persisted to text files named by game mode.
 * 
 * This class performs full sync of player stats across all supported leaderboards.
 * 
 * Each leaderboard file contains entries in the format: username score
 * 
 * @author elliot-chan-ics4u1-2-2025
 */
public class LeaderboardHandler {

    /** 
     * Directory path where leaderboard files are stored.
     * No trailing slash is included since it is added in method calls.
     */
    private final String filepath = "src/main/java/logic/leaderboards/";

    /**
     * Saves or updates a single player's score for a given game mode.
     * 
     * Reads existing leaderboard entries from file (if exists), updates the player's
     * score or adds a new entry, then overwrites the file with updated scores.
     * 
     * @param gameMode the game mode to save the score for (e.g., "wordle")
     * @param username the player's username
     * @param score the player's score to save
     */
    public void saveScore(String gameMode, String username, int score) {
        File file = new File(filepath + gameMode + ".txt");
        Map<String, Integer> scores = new HashMap<>();

        // Step 1: Read existing scores from leaderboard file
        if (file.exists()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNext()) {
                    String user = sc.next();
                    int oldScore = sc.nextInt();
                    scores.put(user, oldScore);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Step 2: Update or add the player's score in the map
        scores.put(username, score);

        // Step 3: Write all scores back to file (overwrite)
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                pw.printf("%s %d%n", entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top N scores for a given game mode.
     * 
     * Reads all leaderboard entries from the file, sorts them descending by score,
     * then returns formatted strings for the top entries.
     * 
     * @param gameMode the game mode leaderboard to query (e.g., "spellingbee")
     * @param topN the maximum number of top scores to return
     * @return a list of formatted strings representing the top scores, 
     *         each formatted as "rank. username: score win(s)"
     */
    public ArrayList<String> getTopScores(String gameMode, int topN) {
        File file = new File(filepath + gameMode + ".txt");
        ArrayList<PlayerEntry> playerEntries = new ArrayList<>();

        // Read leaderboard entries from file
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String user = sc.next();
                int score = sc.nextInt();
                playerEntries.add(new PlayerEntry(user, score));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Sort entries descending by score
        playerEntries.sort((a, b) -> Integer.compare(b.score, a.score));

        // Prepare formatted top scores list
        ArrayList<String> topScores = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, playerEntries.size()); i++) {
            PlayerEntry entry = playerEntries.get(i);
            String winText = entry.score == 1 ? "win" : "wins";
            topScores.add((i + 1) + ". " + entry.username + ": " + entry.score + " " + winText);
        }

        return topScores;
    }

    /**
     * Synchronizes the current player's stats by saving them to all leaderboards.
     * 
     * Saves wordle wins, spelling bee wins, and connections wins for the player.
     * This method appends/updates the leaderboard files accordingly.
     */
    public void saveAllStats() {
        saveScore("wordle", KaliszTimes.player.getUsername(), KaliszTimes.player.getWordleWins());
        saveScore("spellingbee", KaliszTimes.player.getUsername(), KaliszTimes.player.getSpellingBeeWins());
        saveScore("connections", KaliszTimes.player.getUsername(), KaliszTimes.player.getConnectionsWins());
    }

    /**
     * Private internal class representing a player's leaderboard entry.
     * Used for sorting and storing username-score pairs.
     */
    private static class PlayerEntry {
        String username;
        int score;

        PlayerEntry(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }
}
