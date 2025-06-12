package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import kalisz.KaliszTimes;
import logic.LeaderboardHandler;

public class LeaderboardPanel extends TemplatePanel {

    private int currentGameIndex = 0;
    private final String[] gameModeNames = {"😊 wordle", "😲 connections", "🐝 spelling bee"};
    private final String[] gameModes = {"wordle", "connections", "spellingbee"};
    private ArrayList<String> leaderboardEntries = new ArrayList<>();
    private final LeaderboardHandler leaderboard = new LeaderboardHandler();

    public LeaderboardPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        // Add back button
        add(new BackButton(GUIConstants.backButtonImage));

        // Add logo
        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        // Add navigation buttons
        int refY = 200;
        Button prevStats = new Button(GUIConstants.stats_prev_button_image, GUIConstants.scaleX(1920 / 2 - 500), GUIConstants.scaleY(refY));
        prevStats.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentGameIndex = (currentGameIndex - 1 + gameModes.length) % gameModes.length;
                loadLeaderboard();
            }
        });
        add(prevStats);

        Button nextStats = new Button(GUIConstants.stats_next_button_image, GUIConstants.scaleX(1920 / 2 + 500), GUIConstants.scaleY(refY));
        nextStats.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentGameIndex = (currentGameIndex + 1) % gameModes.length;
                loadLeaderboard();
            }
        });
        add(nextStats);

        loadLeaderboard();
        repaint();
    }

    private void loadLeaderboard() {
        leaderboardEntries = leaderboard.getTopScores(gameModes[currentGameIndex], 10);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(graphics);

        graphics.drawImage(GUIConstants.stats_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - 25, this);

        graphics.setFont(new Font("SansSerif", Font.BOLD, GUIConstants.scaleY(30)));
        graphics.setColor(Color.black);

        // Draw game mode title
        String title = gameModeNames[currentGameIndex].toUpperCase() + " ALL-TIME LEADERBOARD";
        int titleX = GUIConstants.scaleX(1920 / 2 - 250);
        int titleY = GUIConstants.scaleY(280);
        graphics.drawString(title, titleX, titleY);

        // Draw leaderboard entries
        int startX = GUIConstants.scaleX(1920 / 2 - 250);
        int startY = GUIConstants.scaleY(350);
        int lineHeight = GUIConstants.scaleY(50);

        for (int i = 0; i < leaderboardEntries.size(); i++) {
            graphics.drawString(leaderboardEntries.get(i), startX, startY + i * lineHeight);
        }
        //Label text
		String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
		int refLabelX = 250;
		int refLabelY = 75;

		graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
		graphics.setColor(Color.black); // or whatever color you want
		graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));
    }

    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }
    public void reloadFrame() {
        repaint();
        revalidate();
    }
}
