package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;

import kalisz.KaliszTimes;

/**
 * InstructionsPanel displays the game instructions with
 * a scrollable HTML pane, navigation buttons, and user info.
 * 
 * <p>This panel provides an interactive interface allowing
 * the user to read detailed game instructions and navigate
 * back or proceed to the gameplay screen.</p>
 * 
 * <p>Uses HTML formatting in a JEditorPane for rich text display.</p>
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 */
public class InstructionsPanel extends TemplatePanel {

    /** Reference X position for instructions pane before scaling */
    private static final int INSTRUCTIONS_REF_X = 1920 / 4 + 100;

    /** Reference Y position for instructions pane before scaling */
    private static final int INSTRUCTIONS_REF_Y = 200;

    /** Reference width for instructions pane before scaling */
    private static final int INSTRUCTIONS_REF_WIDTH = 800;

    /** Reference height for instructions pane before scaling */
    private static final int INSTRUCTIONS_REF_HEIGHT = 600;

    /** Reference X position for play button before scaling */
    private static final int PLAY_BUTTON_REF_X = 842;

    /** Reference Y position for play button before scaling */
    private static final int PLAY_BUTTON_REF_Y = 835;

    /** Reference width for play button before scaling */
    private static final int PLAY_BUTTON_REF_WIDTH = 286;

    /** Reference height for play button before scaling */
    private static final int PLAY_BUTTON_REF_HEIGHT = 93;

    /**
     * Constructs an InstructionsPanel.
     * 
     * <p>Sets up the layout, adds a back button, instructions text pane with
     * scroll bar, and a play button. Buttons have mouse listeners for navigation.</p>
     * 
     * <p>All components use scaled coordinates and sizes from GUIConstants.</p>
     */
    public InstructionsPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));

        setLayout(null);

        // Add the back button using provided image
        add(new BackButton(GUIConstants.backButtonImage));

        // Setup instructions text pane with HTML content
        JEditorPane instructionsPane = new JEditorPane();
        instructionsPane.setContentType("text/html");
        instructionsPane.setText(getInstructionsHTML());
        instructionsPane.setEditable(false);
        instructionsPane.setOpaque(true);
        instructionsPane.setBackground(Color.WHITE);
        instructionsPane.setCaretPosition(0); // Scroll to top

        // Wrap in scroll pane with vertical scrollbar as needed
        JScrollPane scrollPane = new JScrollPane(instructionsPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(
            GUIConstants.scaleX(INSTRUCTIONS_REF_X),
            GUIConstants.scaleY(INSTRUCTIONS_REF_Y),
            GUIConstants.scaleX(INSTRUCTIONS_REF_WIDTH),
            GUIConstants.scaleY(INSTRUCTIONS_REF_HEIGHT)
        );

        add(scrollPane);

        // Setup play button and position it using scaled bounds
        Button playButton = new Button(GUIConstants.playButtonImage);
        playButton.setBounds(
            GUIConstants.scaleX(PLAY_BUTTON_REF_X),
            GUIConstants.scaleY(PLAY_BUTTON_REF_Y),
            GUIConstants.scaleX(PLAY_BUTTON_REF_WIDTH),
            GUIConstants.scaleY(PLAY_BUTTON_REF_HEIGHT)
        );

        // Add click listener to transition to Connections Panel
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Play button pressed");
                KaliszTimes.getGraphicsHandler().jump("Connections Panel");
            }
        });

        add(playButton);

        repaint();
    }

    /**
     * Returns the instructions content as an HTML formatted string.
     * 
     * <p>Includes game overview, core concepts, gameplay rules, progression,
     * unlocking details, scoring, and a motivational closing message.</p>
     * 
     * @return a String containing complete HTML content for instructions
     */
    private String getInstructionsHTML() {
        return "<html>" +
            "<body style='font-family:sans-serif; font-size:12px; padding:10px;'>" +

            "<h1 style='color:#2C3E50;'>💻 The Kalisz Times Games: <i>Intertwined Edition</i></h1>" +

            "<p>This game reimagines the popular <b>New York Times Games</b> app as a streamlined yet deeply interconnected experience. " +
            "Instead of playing games independently, you'll embark on a <b>progressive puzzle journey</b> through <span style='color:#DAA520'><b>Connections</b></span>, the central hub and ultimate objective.</p>" +

            "<h2 style='color:#34495E;'>🎯 Core Concept</h2>" +
            "<ul>" +
            "<li><b>Connections</b> is the centerpiece of the game.</li>" +
            "<li>To solve it, players must first complete hidden <b>Wordle</b> and <b>Spelling Bee</b> puzzles.</li>" +
            "<li>This “game-within-a-game” format emphasizes mastery and strategy.</li>" +
            "</ul>" +

            "<h2 style='color:#34495E;'>🕹️ Gameplay & Rules</h2>" +

            "<h3>Main Page & Start</h3>" +
            "<ul>" +
            "<li>Launch the app to see the main menu.</li>" +
            "<li>Click the <b>App Logo</b> to enter the <b>Connections</b> board.</li>" +
            "</ul>" +

            "<h3>Connections: The Hub of Challenges</h3>" +
            "<ul>" +
            "<li>See a grid of <b>16 tiles</b>—your main puzzle board.</li>" +
            "<li><b>Twist:</b> Tiles are locked until mini-games are solved.</li>" +
            "<li>8 tiles hide <b>Wordle</b> games; 8 tiles hide <b>Spelling Bee</b> games.</li>" +
            "</ul>" +

            "<h3>Solving Mini-Games</h3>" +

            "<b>Wordle Tiles:</b>" +
            "<ul>" +
            "<li>Click a Wordle tile to enter the classic 5-letter guessing game.</li>" +
            "<li>Guess the word within limited attempts to unlock the tile.</li>" +
            "</ul>" +

            "<b>Spelling Bee Tiles:</b>" +
            "<ul>" +
            "<li>Click to see 7 letters with one central letter.</li>" +
            "<li>Reach the target score or find the pangram to solve.</li>" +
            "</ul>" +

            "<b>Progression:</b>" +
            "<ul>" +
            "<li><b>All 16 mini-games</b> must be completed to unlock the full Connections game.</li>" +
            "</ul>" +

            "<h3>Unlocking Connections</h3>" +
            "<ul>" +
            "<li>Once all tiles are solved, the words are revealed.</li>" +
            "<li>Group the words into <b>4 categories</b> of 4 words each based on themes.</li>" +
            "</ul>" +

            "<h3>🏁 Game Completion & Scoring</h3>" +
            "<ul>" +
            "<li>Complete the Connections board to finish the game.</li>" +
            "<li>Performance is tracked via a leaderboard based on:" +
            "<ul>" +
            "<li><b>⏱️ Total Time</b> to complete all mini-games + Connections</li>" +
            "<li><b>🔤 Wordle Attempts</b></li>" +
            "<li><b>🐝 Spelling Bee Score</b></li>" +
            "<li><b>❌ Mistakes</b> in Connections</li>" +
            "</ul>" +
            "</li>" +
            "</ul>" +

            "<p style='text-align:center; margin-top:20px;'>" +
            "<b><i>Are you ready to connect the words, unlock the puzzle, and rise to the top?</i></b><br>" +
            "🌟 <span style='color:#27AE60; font-weight:bold;'>Good luck!</span> 🌟" +
            "</p>" +

            "</body>" +
            "</html>";
    }

    /**
     * Paints the background and user info label on the panel.
     * 
     * <p>Overrides {@link TemplatePanel#paintComponent(Graphics)} to draw
     * a custom background image and display the current player's username.</p>
     * 
     * @param g the Graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);

        // Draw background image stretched to window size
        graphics.drawImage(
            GUIConstants.stats_background_image,
            0, 0,
            GUIConstants.WINDOW_WIDTH,
            GUIConstants.WINDOW_HEIGHT,
            this
        );

        // Compose label text with username
        String labelText = "Signed in as: " + KaliszTimes.player.getUsername();

        // Reference position for label
        final int refLabelX = 250;
        final int refLabelY = 75;

        // Set font and color, then draw label text with scaling
        graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
        graphics.setColor(Color.BLACK);
        graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));
    }

    /**
     * Requests focus for this panel asynchronously on the Event Dispatch Thread.
     * 
     * <p>Overrides focus behavior to ensure keyboard focus is set when
     * this panel is displayed.</p>
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
}
