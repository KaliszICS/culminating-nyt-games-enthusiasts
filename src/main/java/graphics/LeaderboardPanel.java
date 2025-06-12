package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import kalisz.KaliszTimes;
import logic.LeaderboardHandler;

/**
 * LeaderboardPanel displays the all-time leaderboard for
 * different game modes: Wordle, Connections, and Spelling Bee.
 * 
 * It allows navigation between leaderboards for each game mode,
 * displays the top scores, and shows the signed-in player's username.
 * 
 * The panel uses custom buttons for navigation and a back button to
 * return to the previous menu.
 * 
 * This class extends TemplatePanel and overrides painting methods
 * to render the leaderboard UI.
 * 
 * Dependencies:
 * - GUIConstants for layout and scaling constants and images.
 * - LeaderboardHandler for fetching leaderboard data.
 * - KaliszTimes for accessing player info and graphics handler.
 * 
 * Executes without errors, has clear user feedback, and updates smoothly
 * when navigation buttons are pressed.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 */
public class LeaderboardPanel extends TemplatePanel {

    /**
     * Index of the currently displayed game mode leaderboard.
     */
    private int currentGameIndex = 0;

    /**
     * Display names of the game modes with emojis for fun.
     */
    private final String[] gameModeNames = {"😊 wordle", "😲 connections", "🐝 spelling bee"};

    /**
     * Internal string identifiers for game modes, used to fetch leaderboard data.
     */
    private final String[] gameModes = {"wordle", "connections", "spellingbee"};

    /**
     * Holds the current leaderboard entries to display.
     */
    private ArrayList<String> leaderboardEntries = new ArrayList<>();

    /**
     * Handler responsible for retrieving leaderboard data.
     */
    private final LeaderboardHandler leaderboard = new LeaderboardHandler();

    /**
     * Constructs the LeaderboardPanel.
     * Sets preferred size, layout, adds back and navigation buttons,
     * loads initial leaderboard data, and repaints the panel.
     */
    public LeaderboardPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        // Add back button at fixed location
        add(new BackButton(GUIConstants.backButtonImage));

        // Add Kalisz Games logo centered at top
        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        // Add previous leaderboard navigation button
        int refY = 200;
        Button prevStats = new Button(GUIConstants.stats_prev_button_image, GUIConstants.scaleX(1920 / 2 - 500), GUIConstants.scaleY(refY));
        prevStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Cycle backward through game modes circularly
                currentGameIndex = (currentGameIndex - 1 + gameModes.length) % gameModes.length;
                loadLeaderboard();
            }
        });
        add(prevStats);

        // Add next leaderboard navigation button
        Button nextStats = new Button(GUIConstants.stats_next_button_image, GUIConstants.scaleX(1920 / 2 + 500), GUIConstants.scaleY(refY));
        nextStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Cycle forward through game modes circularly
                currentGameIndex = (currentGameIndex + 1) % gameModes.length;
                loadLeaderboard();
            }
        });
        add(nextStats);

        // Load initial leaderboard data
        loadLeaderboard();
        repaint();
    }

    /**
     * Loads the leaderboard data for the currently selected game mode.
     * Fetches top 10 scores and triggers a repaint to update the display.
     */
    private void loadLeaderboard() {
        leaderboardEntries = leaderboard.getTopScores(gameModes[currentGameIndex], 10);
        repaint();
    }

    /**
     * Paints the leaderboard panel, including background image,
     * game mode title, leaderboard entries, and signed-in username.
     *
     * @param g Graphics object for rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(graphics);

        // Draw background image filling the panel except bottom 25 pixels
        graphics.drawImage(GUIConstants.stats_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - 25, this);

        // Set font and color for title
        graphics.setFont(new Font("SansSerif", Font.BOLD, GUIConstants.scaleY(30)));
        graphics.setColor(Color.black);

        // Draw game mode leaderboard title, centered-ish near top
        String title = gameModeNames[currentGameIndex].toUpperCase() + " ALL-TIME LEADERBOARD";
        int titleX = GUIConstants.scaleX(1920 / 2 - 250);
        int titleY = GUIConstants.scaleY(280);
        graphics.drawString(title, titleX, titleY);

        // Draw leaderboard entries starting below title, spaced vertically
        int startX = GUIConstants.scaleX(1920 / 2 - 250);
        int startY = GUIConstants.scaleY(350);
        int lineHeight = GUIConstants.scaleY(50);

        for (int i = 0; i < leaderboardEntries.size(); i++) {
            graphics.drawString(leaderboardEntries.get(i), startX, startY + i * lineHeight);
        }

        // Draw label showing currently signed-in username in smaller font near top-left
        String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
        int refLabelX = 250;
        int refLabelY = 75;
        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.black);
        graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));
    }

    /**
     * Requests focus on this panel to ensure it can receive input events.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    /**
     * Reloads the frame by repainting and revalidating.
     * Useful if external changes require the panel to refresh.
     */
    public void reloadFrame() {
        repaint();
        revalidate();
    }
}
