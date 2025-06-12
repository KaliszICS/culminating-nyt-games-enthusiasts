package logic;

import java.io.*;

/**
 * Represents a player with login credentials and game statistics.
 * 
 * Supports saving and loading login info and game win counts from a user file.
 * 
 * The player stats tracked are wins for Wordle, Spelling Bee, and Connections games.
 * 
 * The user data is stored in a text file named after the username in the folder:
 * "src/main/java/logic/user files/"
 * 
 * Format of user file:
 * password: <password>
 * wordle_wins: <int>
 * spelling_bee_wins: <int>
 * connections_wins: <int>
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class Player {
    /** Player's username */
    private String username;

    /** Player's password */
    private String password;

    /** Number of Wordle wins */
    private int wordleWins;

    /** Number of Spelling Bee wins */
    private int spellingBeeWins;

    /** Number of Connections wins */
    private int connectionsWins;

    /** Directory path where user files are saved */
    private final String filePath = "src/main/java/logic/user files/";

    /**
     * Default constructor initializes empty username and zero stats.
     */
    public Player() {
        this.username = "";
        this.password = "";
        this.wordleWins = 0;
        this.spellingBeeWins = 0;
        this.connectionsWins = 0;
    }

    /**
     * Saves a new user's login info and initializes their stats to zero.
     * Creates or overwrites the user's file with initial data.
     * 
     * @param username the player's username
     * @param password the player's password
     */
    public void saveLoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
        this.wordleWins = 0;
        this.spellingBeeWins = 0;
        this.connectionsWins = 0;

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath + username + ".txt"))) {
            pw.println("password: " + password);
            pw.println("wordle_wins: 0");
            pw.println("spelling_bee_wins: 0");
            pw.println("connections_wins: 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to login a user by verifying the username and password.
     * 
     * Loads player stats from file if credentials are correct.
     * 
     * @param username the username to login
     * @param password the password provided
     * @return -1 if user file does not exist or on error,
     *          0 if password is incorrect,
     *          1 if login is successful
     */
    public int login(String username, String password) {
        File file = new File(filePath + username + ".txt");
        if (!file.exists()) return -1; // User does not exist

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read and validate password line
            String storedPasswordLine = br.readLine();
            if (storedPasswordLine == null) return -1;

            String storedPassword = storedPasswordLine.split(": ")[1];
            if (!storedPassword.equals(password)) return 0; // Wrong password

            // Set username and password fields
            this.username = username;
            this.password = storedPassword;

            // Read and parse win counts
            this.wordleWins = Integer.parseInt(br.readLine().split(": ")[1]);
            this.spellingBeeWins = Integer.parseInt(br.readLine().split(": ")[1]);
            this.connectionsWins = Integer.parseInt(br.readLine().split(": ")[1]);

            return 1; // Login success
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1; // Error during reading/parsing
        }
    }

    /**
     * Saves the current player's stats and password to their user file.
     * Overwrites the file with current data.
     */
    public void saveStats() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath + username + ".txt"))) {
            pw.println("password: " + password);
            pw.println("wordle_wins: " + wordleWins);
            pw.println("spelling_bee_wins: " + spellingBeeWins);
            pw.println("connections_wins: " + connectionsWins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the Wordle wins count by one and saves the stats.
     */
    public void incrementWordleWins() {
        wordleWins++;
        saveStats();
    }

    /**
     * Increments the Spelling Bee wins count by one and saves the stats.
     */
    public void incrementSpellingBeeWins() {
        spellingBeeWins++;
        saveStats();
    }

    /**
     * Increments the Connections wins count by one and saves the stats.
     */
    public void incrementConnectionsWins() {
        connectionsWins++;
        saveStats();
    }

    /**
     * Gets the player's username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the player's Wordle wins count.
     * 
     * @return number of Wordle wins
     */
    public int getWordleWins() {
        return wordleWins;
    }

    /**
     * Gets the player's Spelling Bee wins count.
     * 
     * @return number of Spelling Bee wins
     */
    public int getSpellingBeeWins() {
        return spellingBeeWins;
    }

    /**
     * Gets the player's Connections wins count.
     * 
     * @return number of Connections wins
     */
    public int getConnectionsWins() {
        return connectionsWins;
    }
}
