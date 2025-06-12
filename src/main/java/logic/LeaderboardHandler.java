package logic;

import java.io.*;
import java.util.*;

import kalisz.KaliszTimes;

public class LeaderboardHandler {

    private final String filepath = "src/main/java/logic/leaderboards/";

    /**
     * Save a single score to a leaderboard (appends).
     */
    public void saveScore(String gameMode, String username, int score) {
         File file = new File(filepath + gameMode + ".txt");
    Map<String, Integer> scores = new HashMap<>();

    // Step 1: Read existing scores
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

    // Step 2: Update or insert the player's score
    scores.put(username, score);

    // Step 3: Overwrite file with updated scores
    try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            pw.printf("%s %d%n", entry.getKey(), entry.getValue());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    /**
     * Gets top N scores from a leaderboard.
     */
    public ArrayList<String> getTopScores(String gameMode, int topN) {
        File file = new File(filepath + gameMode + ".txt");
        ArrayList<PlayerEntry> playerEntries = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String user = sc.next();
                int score = sc.nextInt();
                playerEntries.add(new PlayerEntry(user, score));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Sort descending
        playerEntries.sort((a, b) -> Integer.compare(b.score, a.score));

        ArrayList<String> topScores = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, playerEntries.size()); i++) {
            PlayerEntry entry = playerEntries.get(i);
            topScores.add((i + 1) + ". " + entry.username + ": " + entry.score + " " + (entry.score == 1 ? "win" : "wins"));
        }

        return topScores;
    }

    /**
     * Save a player's current stats to all leaderboards.
     * This is a full sync: appends the current stats.
     */
    public void saveAllStats() {
        
        saveScore("wordle", KaliszTimes.player.getUsername(),  KaliszTimes.player.getWordleWins());
        saveScore("spellingbee",  KaliszTimes.player.getUsername(),  KaliszTimes.player.getSpellingBeeWins());
        saveScore("connections",  KaliszTimes.player.getUsername(),  KaliszTimes.player.getConnectionsWins());
    }

    // Internal class for sorting entries
    private static class PlayerEntry {
        String username;
        int score;

        PlayerEntry(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }
}