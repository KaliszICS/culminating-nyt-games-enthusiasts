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
import java.io.*;
import java.io.File; // file i/o
import java.io.IOException;

public class Player {

    private String username, password; // user username to store data
    private HashMap<String, String> passwords; // to let users log into local accounts
    // all instance variables below map the user username to the appropriate stat
    private int[] wordleStats, connectionsStats; // wordleStats: indexes 0 to 5 store number of games won with index + 1 guesses, connectionsStats: indexes 0 to 3 store number of games won with index mistakes
    private ArrayList<Integer> spellingBeeStats; // each integer is the number of total points earned on some unique spelling bee board. note that spellingBeeAttempts is just this.spellingBeeStats.size()
    private int wordleAttempts, connectionsAttempts; // total games played for both
    private Scanner sc; // scanner to scan from passwords and user files
    private PrintWriter pw; // printwriter to write onto passwords and user files

    /**
     * Default constructor to be ran upon launching the program.
     * Attempts to copy all username/password pairs from passwords.txt to the passwords hashmap.
     * Doesn't assign any instance variables (they are all assigned under login).
     */

    public Player() {
        this.passwords = new HashMap<String, String>();
        try {
            this.sc = new Scanner(new BufferedReader(new FileReader("src/main/java/logic/passwords.txt")));
            while (this.sc.hasNext()) this.passwords.put(this.sc.next(), this.sc.next()); // read "username password" over and over again
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * Returns this.wordleStats if the user is logged in.
     * OTHER GETTERS BELOW THIS ONE ARE CODED LIKEWISE.
     * 
     * @return null if the user is logged out, this.wordleStats if user is logged in
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

    // Next two getters return -1 instead of null if the user is logged out.

    public int getWordleAttempts() {
        if (username == null) return -1;
        return this.wordleAttempts;
    }

    public int getConnectionsAttempts() {
        if (username == null) return -1;
        return this.connectionsAttempts;
    }

    /**
     * Saves the login info for a user to both passwords.txt and this.passwords.
     * Should be ran if the user wishes to create an account.
     * 
     * @param username username
     * @param password password for the user
     */

    public void saveLoginInfo(String username, String password) {
        try {
            this.pw = new PrintWriter(new FileWriter("src/main/java/logic/passwords.txt", true));
            this.pw.printf("%s %s\n", username, password); // write username and password onto passwords.txt
            this.passwords.put(username, password); // store user username and password in hashmap
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.pw != null) this.pw.close();
        }
    }

    /**
     * Lets the user log into an existent account.
     * 
     * @param username username
     * @param password password for the username
     * @return -1 if the username isn't registered, 0 if the password is incorrect, 1 if the login is successful
     */

    public int login(String username, String password) {
        if (this.passwords.get(username) == null) return -1; // if the username doesn't exist
        if (this.passwords.get(username).equals(password)) {
            this.username = username; // user is now logged in (instance variables set equal to whatever the user provided here)
            this.password = password;
            try {
                File userFile = new File("src/main/java/logic/" + this.username + ".txt");
                if (userFile.createNewFile()) { // if user file didn't already exist (one was just created), initialize all instance variables (like a default constructor)
                    this.wordleStats = new int[6];
                    this.connectionsStats = new int[4];
                    this.spellingBeeStats = new ArrayList<Integer>();
                    this.wordleAttempts = 0;
                    this.connectionsAttempts = 0;
                    saveUserData(); // initialize empty user file
                } else {
                    try {
                        this.sc = new Scanner(new BufferedReader(new FileReader("src/main/java/logic/" + this.username + ".txt")));
                        this.wordleStats = new int[]{this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt()}; // first 6 integers of user file should be the wordle stats
                        this.connectionsStats = new int[]{this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt(), this.sc.nextInt()}; // next 4 integers should be connections stats
                        this.wordleAttempts = this.sc.nextInt(); // next integer should be wordle attempts
                        this.connectionsAttempts = this.sc.nextInt(); // next integer should be connections attempts
                        this.spellingBeeStats = new ArrayList<Integer>(); // initialize stats arraylist before adding scores
                        while (this.sc.hasNext()) this.spellingBeeStats.add(this.sc.nextInt()); // since spelling bee scores can vary, remaining integers should all be added to spelling bee stats
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }
        return 0;
    }

    /**
     * Lets the user log out of their current account.
     * Sets all instance variables to null or -1.
     */

    public void logout() {
        this.username = null;
        this.password = null;
        this.wordleStats = null;
        this.connectionsStats = null;
        this.spellingBeeStats = null;
        this.wordleAttempts = -1;
        this.connectionsAttempts = -1;
    }

    /**
     * Checks if the user is logged in or not.
     * 
     * @return true if the user is logged in, false otherwise (if the username is null)
     */

    public boolean isLoggedIn() {
        return !(this.username == null);
    }

    /**
     * Adds a wordle attempt to this.wordleStats.
     * OTHER ADD METHODS BELOW THIS ONE ARE CODED LIKEWISE.
     * 
     * @param score wordle score to be recorded. If the player failed out, this should be -1
     * @return false if the player isn't logged in. True otherwise
     */

    public boolean addWordleAttempt(int score) {
        if (username == null) return false;
        ++this.wordleAttempts; // increase user's wordle attempts by 1
        if (score != -1) ++this.wordleStats[score - 1]; // if user passed wordle, grab the array from the hashmap and increment it at the appropriate index
        saveUserData(); // autosave user data
        return true;
    }

    // score is number of MISTAKES - if user failed, this should be -1

    public boolean addConnectionsAttempt(int score) {
        if (username == null) return false;
        ++this.connectionsAttempts; // increase user's connections attempts by 1
        if (score != -1) ++this.connectionsStats[score]; // if user passed connections, grab the array from the hashmap and increment it at the appropriate index
        saveUserData(); // autosave user data
        return true;
    }

    // score is NUMBER OF POINTS OBTAINED PER BOARD. there is no fail condition; the lowest score is 0

    public boolean addSpellingBeeAttempt(int score) {
        if (username == null) return false;
        this.spellingBeeStats.add(score); // add the score to the spelling bee hashmap
        this.spellingBeeStats.sort(null); // sort the hashmap
        saveUserData(); // autosave user data
        return true;
    }

    /**
     * Returns ALL user data in the form of an object array, in order of:
     * wordleStats - int[]
     * connectionsStats - int[]
     * spellingBeeStats - int[]
     * wordleAttempts - int
     * connectionsAttempts - int
     * 
     * @return null if user is not signed in. Otherwise, an Object[] array of the objects above, in that order
     */

    public Object[] getUserData() {
        if (this.username == null) return null;
        Object[] userData = {this.wordleStats, this.connectionsStats, this.spellingBeeStats, this.wordleAttempts, this.connectionsAttempts};
        return userData;
    }

    /**
     * Writes all user data to the user file.
     * Writes user data by creating a temporary file, writing it there, then renaming that temp file to the user file.
     * 
     * @return -1 if file I/O error occurred, 0 if user doesn't exist, 1 otherwise
     */

    public int saveUserData() {
        if (this.username == null) return 0; // user doesn't exist
        try {
            File userFile = new File("src/main/java/logic/" + this.username + ".txt"), tempUserFile = new File("src/main/java/logic/temp.txt");
            this.pw = new PrintWriter(new FileWriter(tempUserFile, true));
            this.pw.printf("%d %d %d %d %d %d\n", this.wordleStats[0], this.wordleStats[1], this.wordleStats[2], this.wordleStats[3], this.wordleStats[4], this.wordleStats[5]);
            this.pw.printf("%d %d %d %d\n", this.connectionsStats[0], this.connectionsStats[1], this.connectionsStats[2], this.connectionsStats[3]);
            this.pw.printf("%d\n", this.wordleAttempts);
            this.pw.printf("%d\n", this.connectionsAttempts);
            for (int index = 0; index < this.spellingBeeStats.size(); ++index) this.pw.print(this.spellingBeeStats.get(index) + " "); // yes it will end in a space (shouldn't matter)
            this.pw.println();
            userFile.delete(); // delete original user file, if it existed
            tempUserFile.renameTo(userFile); // rename temp file to original user file's name
            return 1; // successfully saved
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // save failed
        } finally {
            if (this.pw != null) this.pw.close();
        }
    }

}