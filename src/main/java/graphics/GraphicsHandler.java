package graphics;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.*;

import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.WordleButton;

/**
 * The {@code GraphicsHandler} class manages the main JFrame and the switching
 * of different game panels in the application using a CardLayout.
 * <p>
 * It serves as the centralized controller for navigating between panels such as the
 * title screen, game selection, individual game panels (e.g., Connections),
 * leaderboard, and instructions.
 * </p>
 * <p>
 * The class also keeps track of active instances of Wordle and Spelling Bee games
 * via HashMaps keyed on their respective buttons for game state management.
 * </p>
 * <p>
 * Panel navigation history is tracked with a stack to allow going back to previous panels.
 * </p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class GraphicsHandler {

    /** The main application window frame */
    private static JFrame frame = null;

    /** The CardLayout used for switching between panels in the main panel */
    private static CardLayout layout = null;

    /** The main container JPanel holding all sub-panels */
    private static JPanel mainPanel = null;

    /** Active Wordle game instances mapped by their associated WordleButton */
    public static HashMap<WordleButton, WordleGamePanel> activeWordleInstances = new HashMap<>();

    /** Active Spelling Bee game instances mapped by their associated SpellingBeeEnterButton */
    public static HashMap<SpellingBeeEnterButton, SpellingBeeGamePanel> activeSpellingBeeInstances = new HashMap<>();

    /** Singleton instance of the ConnectionsPanel */
    private static ConnectionsPanel connectionsPanel;

    /** Singleton instance of the LeaderboardPanel */
    private static LeaderboardPanel leaderboardPanel;

    /** Tracks the currently displayed panel's name */
    public static String currentPanel;

    /** Stack storing panel names to allow "back" navigation */
    private static Stack<String> panelHistory = new Stack<>();

    /**
     * Initializes the JFrame and all the core panels, sets up the CardLayout,
     * and displays the initial title screen panel.
     * <p>
     * This method must be called once to set up the entire GUI framework.
     * </p>
     */
    public void initiate() {
        frame = new JFrame(GUIConstants.gameName + " " + GUIConstants.gameVersion);
        frame.setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Optional maximized window
        
        mainPanel = new JPanel();
        layout = new CardLayout();
        mainPanel.setLayout(layout);

        // Instantiate all major panels here
        TitleScreenPanel titlePanel = new TitleScreenPanel();
        GameSelectionPanel selectionPanel = new GameSelectionPanel();
        connectionsPanel = new ConnectionsPanel();
        InstructionsPanel instructionsPanel = new InstructionsPanel();
        leaderboardPanel = new LeaderboardPanel();

        // Add panels to the main panel with a unique string key for navigation
        mainPanel.add(titlePanel, "Title Screen Panel");
        mainPanel.add(selectionPanel, "Connections Start Panel");
        mainPanel.add(connectionsPanel, "Connections Panel");
        mainPanel.add(leaderboardPanel, "Leaderboard Panel");
        mainPanel.add(instructionsPanel, "Instructions Panel");

        currentPanel = "Title Screen Panel";
        layout.show(mainPanel, currentPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Returns the main application JFrame.
     *
     * @return the JFrame instance representing the main window
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Returns the main JPanel container holding all other panels.
     *
     * @return the main JPanel with CardLayout
     */
    public JPanel getPanel() {
        return mainPanel;
    }

    /**
     * Navigates back to the previous panel, if available in the history stack.
     * <p>
     * This method pops the last panel name from the stack and switches the display
     * to that panel. If no history exists, no action is taken.
     * </p>
     */
    public void goBack() {
        if (!panelHistory.isEmpty()) {
            String previousPanel = panelHistory.pop();
            System.out.println("Current Panel: " + currentPanel + " Previous Panel: " + previousPanel);
            layout.show(mainPanel, previousPanel);
            currentPanel = previousPanel;
        }
    }

    /**
     * Switches the display to the panel identified by the specified name.
     * <p>
     * Before switching, the current panel's name is pushed onto the history stack
     * to allow returning back via {@link #goBack()}.
     * </p>
     *
     * @param panelName the unique string identifier of the panel to show
     */
    public void jump(String panelName) {
        if (!panelName.equals(currentPanel)) {
            panelHistory.push(currentPanel); // Save current panel before switching
            layout.show(mainPanel, panelName);
            System.out.println("Jumping from " + currentPanel + " to " + panelName);
            currentPanel = panelName;
        }
    }

    /**
     * Revalidates and repaints the main frame.
     * <p>
     * This forces the GUI to refresh its layout and appearance, useful after
     * adding or removing components dynamically.
     * </p>
     */
    public void reloadFrame() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Revalidates and repaints the leaderboard panel.
     * <p>
     * Useful to refresh leaderboard contents after updates.
     * </p>
     */
    public void reloadLeaderboardFrame() {
        leaderboardPanel.revalidate();
        leaderboardPanel.repaint();
    }

    /**
     * Adds a new panel to the main container and immediately jumps to display it.
     * <p>
     * The new panel is registered under the given name for future navigation.
     * </p>
     *
     * @param newPanel the JPanel instance to add
     * @param name     the unique name identifier for this panel
     */
    public void addPanel(JPanel newPanel, String name) {
        mainPanel.add(newPanel, name);
        reloadFrame();
        jump(name);
    }

    /**
     * Returns the singleton instance of the ConnectionsPanel.
     *
     * @return the ConnectionsPanel instance
     */
    public ConnectionsPanel getConnectionsPanel() {
        return connectionsPanel;
    }
}
