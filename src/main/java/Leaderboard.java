/**
 * A class to store local scores for players.
 * Does not make use of a database. Instead, all player data is stored LOCALLY in HashMaps that correlate to player username.
 * 
 * @author @FranklinZhu1
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Leaderboard {

    private String username, password; // user username to store data
    private HashMap<String, String> passwords; // to let users log into local accounts
    // all instance variables below map the user username to the appropriate stat
    private HashMap<String, Integer[]> wordleStats, connectionsStats; // wordleStats: indexes 0 to 5 store number of games won with index + 1 guesses, connectionsStats: indexes 0 to 3 store number of games won with index mistakes
    private HashMap<String, ArrayList<Double>> spellingBeeStats; // each double is the % of total points completed on some unique spelling bee board
    private HashMap<String, Integer> wordleAttempts, connectionsAttempts, spellingBeeAttempts; // total games played for both

    /**
     * default constructor to be ran upon launching the program, sets all HashMaps to empty HashMaps
     */

    public Leaderboard() {
        this.passwords = new HashMap<String, String>();
        this.wordleStats = new HashMap<String, Integer[]>();
        this.connectionsStats = new HashMap<String, Integer[]>();
        this.spellingBeeStats = new HashMap<String, ArrayList<Double>>();
        this.wordleAttempts = new HashMap<String, Integer>();
        this.connectionsAttempts = new HashMap<String, Integer>();
        this.spellingBeeAttempts = new HashMap<String, Integer>();
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

    public Integer[] getWordleStats() {
        if (username == null) return null;
        return this.wordleStats.get(username);
    }

    public Integer[] getConnectionsStats() {
        if (username == null) return null;
        return this.connectionsStats.get(username);
    }

    // this method also sorts the spelling bee scores in ascending order before returning it

    public ArrayList<Double> getSpellingBeeStats() {
        if (username == null) return null;
        this.spellingBeeStats.get(this.username).sort(null); // sort the spelling bee attempts in ascending order
        return this.spellingBeeStats.get(username);
    }

    /**
     * the next few getters all return -1 if the user is logged out
     * 
     * @return -1 if logged out, wordle attempts otherwise
     */

    public int getWordleAttempts() {
        if (username == null) return -1;
        return this.wordleAttempts.get(username);
    }

    public int getConnectionsAttempts() {
        if (username == null) return -1;
        return this.connectionsAttempts.get(username);
    }

    public int getSpellingBeeAttempts() {
        if (username == null) return -1;
        return this.spellingBeeAttempts.get(username);
    }

    /**
     * save login info for a user (should be ran if user wishes to create an account)
     * 
     * @param username username
     * @param password password for the user
     */

    public void saveLoginInfo(String username, String password) {
        this.passwords.put(username, password); // store user username and password
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
            return true;
        }
        return false;
    }

    /**
     * lets user log out of current account
     * SETS BOTH this.username AND this.password TO NULL
     * note that this.password actually holds no relevance since all other methods checks if this.username is null or not to see if the user is signed in or not
     */

    public void logout() {
        this.username = null;
        this.password = null;
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
        this.wordleAttempts.put(this.username, this.wordleAttempts.get(this.username) + 1); // increase user's wordle attempts by 1
        if (score != -1) ++this.wordleStats.get(this.username)[score - 1]; // if user passed wordle, grab the array from the hashmap and increment it at the appropriate index
        return true;
    }

    // param score is number of MISTAKES. if failed, -1

    public boolean addConnectionsAttempt(int score) {
        if (username == null) return false;
        this.connectionsAttempts.put(this.username, this.connectionsAttempts.get(this.username) + 1); // increase user's connections attempts by 1
        if (score != -1) ++this.connectionsStats.get(this.username)[score]; // if user passed connections, grab the array from the hashmap and increment it at the appropriate index
        return true;
    }

    // param score is PERCENTAGE OF SCORE OBTAINED OUT OF MAXIMUM POSSIBLE SCORE PER BOARD. there is no fail condition; the lowest score is 0%

    public boolean addSpellingBeeAttempt(double score) {
        if (username == null) return false;
        this.spellingBeeAttempts.put(this.username, this.spellingBeeAttempts.get(this.username) + 1); // increase user's spelling bee attempts by 1
        this.spellingBeeStats.get(this.username).add(score); // grab the hashmap from the hashmap and add the score to it
        return true;
    }

    /**
     * returns ALL user data in the form of an object array, in order of:
     * wordleStats - Integer[]
     * connectionsStats - Integer[]
     * spellingBeeStats - Double[]
     * wordleAttempts - Integer
     * connectionsAttempts - Integer
     * spellingBeeAttempts - Integer
     * 
     * @return null if user is not signed in. otherwise, an Object[] array of the objects above, in that order
     */

    public Object[] getUserData() {
        if (this.username == null) return null;
        Object[] userData = {this.wordleStats.get(this.username), this.connectionsStats.get(this.username), this.spellingBeeStats.get(this.username), this.wordleAttempts.get(this.username), this.connectionsAttempts.get(this.username), this.spellingBeeAttempts.get(this.username)};
        return userData;
    }

}