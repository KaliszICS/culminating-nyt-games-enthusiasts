package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.StatsButton;
import kalisz.KaliszTimes;

/**
 * The {@code GameSelectionPanel} class represents the main game selection screen
 * where the player can choose to start playing the Connections game or access
 * other UI components such as viewing stats or returning to the previous screen.
 * <p>
 * This panel includes UI elements like a back button, the KaliszGames logo,
 * a stats button, and a play button. It handles user input on the play button to
 * navigate to the Connections game panel.
 * 
 * </p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class GameSelectionPanel extends TemplatePanel {

    /**
     * Constructs a new {@code GameSelectionPanel}.
     * <p>
     * Sets up the preferred size of the panel, initializes the layout,
     * adds UI buttons (back, logo, stats, play), and registers event listeners
     * for user interaction.
     * </p>
     */
    public GameSelectionPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
        setLayout(null);

        // Add back button to navigate to the previous screen
        add(new BackButton(GUIConstants.backButtonImage));

        // Add the KaliszGames logo centered near the top
        int refKaliszX = 1920 / 2 - 250;
        int refKaliszY = 10;
        add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

        // Add the stats button to view leaderboard and stats
        add(new StatsButton(GUIConstants.viewStatsButtonImage));

        // Create the play button for starting the Connections game
        Button playButton = new Button(GUIConstants.playButtonImage);
        int refX = 842;
        int refY = 775;
        int refWidth = 286;
        int refHeight = 93;
        playButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));

        // Add mouse listener to the play button to handle clicks
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
     * Paints the background and any additional graphics of this panel.
     *
     * @param g the {@code Graphics} object used for drawing
     */
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);

        // Draw the background image scaled to the window size
        graphics.drawImage(GUIConstants.connections_start_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT, this);
    }

    /**
     * Requests focus for this panel so it can receive keyboard input.
     * Focus request is done asynchronously on the event dispatch thread.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
}
