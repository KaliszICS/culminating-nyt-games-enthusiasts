// package logic;

// /**
//  * A class to store local scores for players.
//  * Writes local scores for each user to their own text file. Stores usernames/passwords in a text file as well (I don't know how to encrypt text files).
//  * 
//  * @author @FranklinZhu1
//  */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*; // scanner and filewriter
import java.io.File; // file i/o
import java.io.IOException;

// public class Leaderboard {

//     private String username, password; // user username to store data
//     private HashMap<String, String> passwords; // to let users log into local accounts
//     // all instance variables below map the user username to the appropriate stat
//     private int[] wordleStats, connectionsStats; // wordleStats: indexes 0 to 5 store number of games won with index + 1 guesses, connectionsStats: indexes 0 to 3 store number of games won with index mistakes
//     private ArrayList<Integer> spellingBeeStats; // each integer is the number of total points earned on some unique spelling bee board. note that spellingBeeAttempts is just this.spellingBeeStats.size()
//     private int wordleAttempts, connectionsAttempts; // total games played for both

    /**
     * Default constructor to be ran upon launching the program.
     * Attempts to copy all username/password pairs from passwords.txt to the passwords hashmap.
     * Doesn't assign any instance variables (they are all assigned under login).
     */

//     public Leaderboard() {
//         Scanner scanner = null;
//         this.passwords = new HashMap<String, String>();
//         try {
//             scanner = new Scanner(new BufferedReader(new FileReader("passwords.txt")));
//             while (scanner.hasNext()) this.passwords.put(scanner.next(), scanner.next()); // read "username password" over and over again
//         } catch (FileNotFoundException e) {
//             System.out.println("Error while reading the file.");
//         } finally {
//             if (scanner != null) scanner.close();
//         }
//     }

//     public String getUsername() {
//         return this.username;
//     }

//     public String getPassword() {
//         return this.password;
//     }

    /**
     * Returns this.wordleStats if the user is logged in.
     * OTHER GETTERS BELOW THIS ONE ARE CODED LIKEWISE.
     * 
     * @return null if the user is logged out, this.wordleStats if user is logged in
     */

//     public int[] getWordleStats() {
//         if (username == null) return null;
//         return this.wordleStats;
//     }

//     public int[] getConnectionsStats() {
//         if (username == null) return null;
//         return this.connectionsStats;
//     }

//     public ArrayList<Integer> getSpellingBeeStats() {
//         if (username == null) return null;
//         return this.spellingBeeStats;
//     }

    // Next two getters return -1 instead of null if the user is logged out.

//     public int getWordleAttempts() {
//         if (username == null) return -1;
//         return this.wordleAttempts;
//     }

//     public int getConnectionsAttempts() {
//         if (username == null) return -1;
//         return this.connectionsAttempts;
//     }

    /**
     * Saves the login info for a user to both passwords.txt and this.passwords.
     * Should be ran if the user wishes to create an account.
     * 
     * @param username username
     * @param password password for the user
     */

    public void saveLoginInfo(String username, String password) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("passwords.txt");
            pw.printf("%s %s\n", username, password); // write username and password onto passwords.txt
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading the file.");
        } finally {
            if (pw != null) pw.close();
        }
        this.passwords.put(username, password); // store user username and password in hashmap
    }

    /**
     * Lets the user log into an existent account.
     * 
     * @param username username
     * @param password password for the username
     * @return true if password matches username, false if not or if the username isn't registered
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
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(new BufferedReader(new FileReader(this.username + ".txt")));
                        this.wordleStats = new int[]{scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()}; // first 6 integers of user file should be the wordle stats
                        this.connectionsStats = new int[]{scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()}; // next 4 integers should be connections stats
                        this.wordleAttempts = scanner.nextInt(); // next integer should be wordle attempts
                        this.connectionsAttempts = scanner.nextInt(); // next integer should be connections attempts
                        while (scanner.hasNext()) this.spellingBeeStats.add(scanner.nextInt()); // since spelling bee scores can vary, remaining integers should all be added to spelling bee stats
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (scanner != null) scanner.close();
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

//     public boolean addWordleAttempt(int score) {
//         if (username == null) return false;
//         ++this.wordleAttempts; // increase user's wordle attempts by 1
//         if (score != -1) ++this.wordleStats[score - 1]; // if user passed wordle, grab the array from the hashmap and increment it at the appropriate index
//         return true;
//     }

    // score is number of MISTAKES - if user failed, this should be -1

//     public boolean addConnectionsAttempt(int score) {
//         if (username == null) return false;
//         ++this.connectionsAttempts; // increase user's connections attempts by 1
//         if (score != -1) ++this.connectionsStats[score]; // if user passed connections, grab the array from the hashmap and increment it at the appropriate index
//         return true;
//     }

    // score is NUMBER OF POINTS OBTAINED PER BOARD. there is no fail condition; the lowest score is 0

    public boolean addSpellingBeeAttempt(int score) {
        if (username == null) return false;
        this.spellingBeeStats.add(score); // add the score to the spelling bee hashmap
        this.spellingBeeStats.sort(null); // sort the hashmap
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

//     public Object[] getUserData() {
//         if (this.username == null) return null;
//         Object[] userData = {this.wordleStats, this.connectionsStats, this.spellingBeeStats, this.wordleAttempts, this.connectionsAttempts};
//         return userData;
//     }

    /**
     * Writes all user data to the user file.
     * Writes user data by creating a temporary file, writing it there, then renaming that temp file to the user file.
     */

    public void saveUserData() {
        PrintWriter pw = null;
        try {
            File userFile = new File(this.username + ".txt"), tempUserFile = new File("temp.txt");
            pw = new PrintWriter(tempUserFile);
            pw.printf("%d %d %d %d %d %d\n", this.wordleStats[0], this.wordleStats[1], this.wordleStats[2], this.wordleStats[3], this.wordleStats[4], this.wordleStats[5]);
            pw.printf("%d %d %d %d\n", this.connectionsStats[0], this.connectionsStats[1], this.connectionsStats[2], this.connectionsStats[3]);
            pw.printf("%d\n", this.wordleAttempts);
            pw.printf("%d\n", this.connectionsAttempts);
            for (int index = 0; index < this.spellingBeeStats.size(); ++index) pw.print(this.spellingBeeStats.get(index));
            pw.println();
            userFile.delete(); // delete original user file, if it existed
            tempUserFile.renameTo(userFile); // rename temp file to original user file's name
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) pw.close();
        }
    }

// }