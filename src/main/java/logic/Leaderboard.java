package logic;

/**
 * A class to store local scores for players.
 * Writes local scores for each user to their own text file. Stores usernames/passwords in a text file as well (I don't know how to encrypt text files).
 * 
 * @author @FranklinZhu1
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*; // scanner and filewriter
import java.io.File; // file i/o
import java.io.IOException;

public class Leaderboard {

    private String username, password; // user username to store data
    private HashMap<String, String> passwords; // to let users log into local accounts
    // all instance variables below map the user username to the appropriate stat
    private int[] wordleStats, connectionsStats; // wordleStats: indexes 0 to 5 store number of games won with index + 1 guesses, connectionsStats: indexes 0 to 3 store number of games won with index mistakes
    private ArrayList<Integer> spellingBeeStats; // each integer is the number of total points earned on some unique spelling bee board. note that spellingBeeAttempts is just this.spellingBeeStats.size()
    private int wordleAttempts, connectionsAttempts; // total games played for both

    /**
     * default constructor to be ran upon launching the program, sets all HashMaps to empty HashMaps
     */

    public Leaderboard() {
        Scanner scanner = null;
        this.passwords = new HashMap<String, String>();
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("passwords.txt")));
            while (scanner.hasNext()) this.passwords.put(scanner.next(), scanner.next()); // read "username password" over and over again
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading the file.");
        } finally {
            if (scanner != null) scanner.close();
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * returns wordle stats integer array if the user is logged in
     * OTHER GETTERS BELOW THIS ONE ARE CODED LIKEWISE
     * 
     * @return null if the user is logged out, wordle stats array if user is logged in
     */

    public int[] getWordleStats() {
        if (username == null) return null;
        return this.wordleStats;
    }

    public int[] getConnectionsStats() {
        if (username == null) return null;
        return this.connectionsStats;
    }

    public ArrayList<Integer> getSpellingBeeStats() {
        if (username == null) return null;
        return this.spellingBeeStats;
    }

    /**
     * the next few getters all return -1 if the user is logged out
     * 
     * @return -1 if logged out, number of attempts otherwise
     */

    public int getWordleAttempts() {
        if (username == null) return -1;
        return this.wordleAttempts;
    }

    public int getConnectionsAttempts() {
        if (username == null) return -1;
        return this.connectionsAttempts;
    }

    /**
     * save login info for a user (should be ran if user wishes to create an account)
     * 
     * @param username username
     * @param password password for the user
     */

    public void saveLoginInfo(String username, String password) {
        PrinterWriter writer = null;
        try {
            writer = new PrintWriter("passwords.txt");
            writer.println("%s %s", username, password); // write username and password onto passwords.txt
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading the file.");
        } finally {
            if (scanner != null) scanner.close();
        }

        this.passwords.put(username, password); // store user username and password in hashmap
    }

    /**
     * let user log into existent account
     * 
     * @param username username
     * @param password password for the username
     * @return true if password matches username, false otherwise
     */

    public boolean login(String username, String password) {
        if (this.passwords.get(username).equals(password)) {
            this.username = username; // user is now logged in (instance variables set equal to whatever the user provided here)
            this.password = password;
            try {
                File userFile = new File(this.username + ".txt");
                if (userFile.createNewFile()) { // if user file didn't already exist (one was just created), initialize all instance variables (like a default constructor)
                    this.wordleStats = new int[6];
                    this.connectionsStats = new int[4];
                    this.spellingBeeStats = new ArrayList<Integer>();
                    this.wordleAttempts = 0;
                    this.connectionsAttempts = 0;
                } else {
                    try {
                        FileReader reader = new FileReader(new BufferedReader(new Scanner(this.username + ".txt")));
                        this.wordleStats = new int[]{reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt()}; // first 6 integers of user file should be the wordle stats
                        this.connectionsStats = new int[]{reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt()}; // next 4 integers should be connections stats
                        this.wordleAttempts = reader.nextInt(); // next integer should be wordle attempts
                        this.connectionsAttempts = reader.nextInt(); // next integer should be connections attempts
                        while (reader.hasNext()) this.spellingBeeStats.add(reader.nextInt()); // since spelling bee scores can vary, remaining integers should all be added to spelling bee stats
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * lets user log out of current account
     * sets all instance variables to null
     * note that this.password actually holds no relevance since all other methods checks if this.username is null or not to see if the user is signed in or not
     */

    public void logout() {
        this.username = null;
        this.password = null;
        this.wordleStats = null;
        this.connectionsStats = null;
        this.spellingBeeStats = null;
        this.wordleAttempts = null;
        this.connectionsAttempts = null;
    }

    /**
     * checks if user is logged in or not
     * 
     * @return true if the user is logged in, false otherwise (if the username is null)
     */

    public boolean isLoggedIn() {
        if (this.username == null) return false;
        return true;
    }

    /**
     * adds a wordle attempt to the wordle stats
     * OTHER ADD METHODS ARE CODED LIKEWISE
     * 
     * @param score wordle score to be recorded. if the player failed out, this should be -1
     * @return false if the player isn't logged in. true otherwise
     */

    public boolean addWordleAttempt(int score) {
        if (username == null) return false;
        ++this.wordleAttempts; // increase user's wordle attempts by 1
        if (score != -1) ++this.wordleStats[score - 1]; // if user passed wordle, grab the array from the hashmap and increment it at the appropriate index
        return true;
    }

    // param score is number of MISTAKES. if failed, -1

    public boolean addConnectionsAttempt(int score) {
        if (username == null) return false;
        ++this.connectionsAttempts; // increase user's connections attempts by 1
        if (score != -1) ++this.connectionsStats[score]; // if user passed connections, grab the array from the hashmap and increment it at the appropriate index
        return true;
    }

    // param score is NUMBER OF POINTS OBTAINED PER BOARD. there is no fail condition; the lowest score is 0

    public boolean addSpellingBeeAttempt(int score) {
        if (username == null) return false;
        this.spellingBeeStats.add(score); // grab the hashmap from the hashmap and add the score to it
        this.spellingBeeStats.sort(); // sort the hashmap
        return true;
    }

    /**
     * returns ALL user data in the form of an object array, in order of:
     * wordleStats - int[]
     * connectionsStats - int[]
     * spellingBeeStats - int[]
     * wordleAttempts - int
     * connectionsAttempts - int
     * 
     * @return null if user is not signed in. otherwise, an Object[] array of the objects above, in that order
     */

    public Object[] getUserData() {
        if (this.username == null) return null;
        Object[] userData = {this.wordleStats, this.connectionsStats, this.spellingBeeStats, this.wordleAttempts, this.connectionsAttempts};
        return userData;
    }

    /**
     * writes all user data to the user file. should probably be ran when the application exits.
     * writes user data by creating a temporary file, writing it there, then renaming that temp file to the user file.
     */

    public void saveUserData() {
        try {
            File userFile = new File(this.username + ".txt"), tempUserFile = new File("temp.txt");
            FileWriter fw = new FileWriter(tempUserFile);
            fw.println("%d %d %d %d %d %d", this.wordleStats[0], this.wordleStats[1], this.wordleStats[2], this.wordleStats[3], this.wordleStats[4], this.wordleStats[5]);
            fw.println("%d %d %d %d", this.connectionsStats[0], this.connectionsStats[1], this.connectionsStats[2], this.connectionsStats[3]);
            fw.println("%d", this.wordleAttempts);
            fw.println("%d", this.connectionsAttempts);
            for (int index = 0; index < this.spellingBeeStats.size(); ++index) fw.print(this.spellingBeeStats.get(index));
            fw.println();
            userFile.delete(); // delete original user file, if it existed
            tempUserFile.renameTo(userFile); // rename temp file to original user file's name
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}