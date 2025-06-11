package logic;

/**
 * A class to handle leaderboards, including display and ranking.
 * Extends Player class.
 * Makes use of wordle.txt, connections.txt, spellingbee.txt leaderboard text files in the filepath specified by the final variable.
 * 
 * ??????????????
 * SCRAP PROBABLY
 * 
 * @author @FranklinZhu1
 */

/**
 * write all stats onto each gamemode's leaderboard
 * get each gamemode's leaderboard
 */

import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Leaderboard extends Player {

    Scanner sc = null;
    final String filepath = "src/main/java/logic/leaderboards/"; // filepath for user files
    private ArrayList<Integer> wordleScores;
    private ArrayList<Integer> connectionsScores;
    private ArrayList<Integer> spellingBeeScores;

    public Leaderboard() {
        super();
    }

    public void saveAllStats() {

    }
    
}
