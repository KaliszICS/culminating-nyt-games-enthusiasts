package logic;

import java.io.*;
import java.util.*;

public class Player {
    private String username;
    private String password;
    private int wordleWins;
    private int spellingBeeWins;
    private int connectionsWins;
    private String filePath = "src/main/java/logic/user files/";

    public Player() {
        this.username = "";
        this.password = "";
        this.wordleWins = 0;
        this.spellingBeeWins = 0;
        this.connectionsWins = 0;
    }

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

    public int login(String username, String password) {
        File file = new File(filePath + username + ".txt");
        if (!file.exists()) return -1; // user does not exist

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String storedPassword = br.readLine().split(": ")[1];
            if (!storedPassword.equals(password)) return 0; // wrong password

            this.username = username;
            this.password = storedPassword;
            this.wordleWins = Integer.parseInt(br.readLine().split(": ")[1]);
            this.spellingBeeWins = Integer.parseInt(br.readLine().split(": ")[1]);
            this.connectionsWins = Integer.parseInt(br.readLine().split(": ")[1]);
            return 1; // login success
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

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
    
    public void incrementWordleWins() {
        wordleWins++;
        saveStats();
    }

    public void incrementSpellingBeeWins() {
        spellingBeeWins++;
        saveStats();
    }

    public void incrementConnectionsWins() {
        connectionsWins++;
        saveStats();
    }

    public String getUsername() {
        return username;
    }

    public int getWordleWins() {
        return wordleWins;
    }

    public int getSpellingBeeWins() {
        return spellingBeeWins;
    }

    public int getConnectionsWins() {
        return connectionsWins;
    }
}