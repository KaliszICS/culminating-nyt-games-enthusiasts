package kalisz;

import graphics.GUIConstants;
import graphics.GraphicsHandler;
import graphics.player.PlayerLoginGUI;
import graphics.utils.GameDataHandler;
import graphics.utils.JFXInitializer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Player;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Kalisz Times game application.
 * <p>
 * This class is responsible for initializing core game data, graphics constants,
 * and UI components. It manages the application's lifecycle including player login,
 * game start, and handling both informational popups and video advertisements.
 * <p>
 * It integrates Swing for basic dialogs and JavaFX for video playback, ensuring
 * smooth interaction between the two UI frameworks.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * @author aksayan-nirmalan-ics4u1-2-2025
 */
public class KaliszTimes {
    
    /**
     * The central graphics handler managing all graphical components and rendering.
     */
    private static GraphicsHandler handler = null;
    
    /**
     * Flag indicating whether an advertisement video is currently playing.
     */
    public static boolean inAd = false;
    
    /**
     * Enables or disables debug mode for additional logging and diagnostics.
     */
    public static boolean debugMode = false;
    
    /**
     * When true, disables all advertisements (for example, premium or debug mode).
     */
    public static boolean removeAds = false;
    
    /**
     * The currently logged-in player.
     */
    public static Player player;

    /**
     * Application entry point. Initializes JavaFX, game data, constants, 
     * and launches the player login UI on the Swing event dispatch thread.
     *
     * @param args Command line arguments (ignored)
     */
    public static void main(String args[]) {
        // Initialize JavaFX toolkit for video playback support
        JFXInitializer.initJavaFX();
        
        // Load all game data resources
        new GameDataHandler();
        
        // Initialize graphical constants (window size, colors, fonts, etc.)
        new GUIConstants();
        System.out.println(GUIConstants.WINDOW_WIDTH + " " + GUIConstants.WINDOW_HEIGHT);
        
        // Initialize the main graphics handler
        handler = new GraphicsHandler();
        
        // Launch player login GUI safely on Swing EDT
        SwingUtilities.invokeLater(() -> new PlayerLoginGUI());
    }

    /**
     * Starts the main game logic for the specified player.
     * 
     * @param player the Player object representing the logged-in user
     */
    public static void initiateGame(Player player) {
        KaliszTimes.player = player;
        handler.initiate();
    }
    
    /**
     * Provides access to the global graphics handler instance.
     *
     * @return the GraphicsHandler instance responsible for rendering
     */
    public static GraphicsHandler getGraphicsHandler() {
        return handler;
    }
    
    /**
     * Shows a simple informational popup dialog with a given message.
     * Uses Swing's JOptionPane to display a modal message box.
     * 
     * @param message the text message to display to the user
     */
    public static void popup(String message) {
        JOptionPane.showMessageDialog(
            getGraphicsHandler().getFrame(),
            message,
            "Information!",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Shows a popup dialog prompting the user to watch an advertisement in order to revive.
     * Uses Swing's JOptionPane to display a modal message box.
     *
     * @param message the text message explaining the ad requirement
     */
    public static void adPopup(String message) {
        JOptionPane.showMessageDialog(
            getGraphicsHandler().getFrame(),
            message,
            "Watch an ad to revive!",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Displays a modal popup window that plays a video advertisement.
     * The popup blocks interaction with the main window until the video ends.
     * The user is prevented from closing the popup manually.
     * 
     * If ads are disabled via {@code removeAds}, the video will be skipped and the
     * specified {@code onVideoEnd} callback will be invoked immediately.
     * 
     * @param videoFilePath the local filesystem path to the video file to play
     * @param onVideoEnd    a callback Runnable executed when the video finishes playing;
     *                      may be {@code null} if no action is required
     */
    public static void showVideoPopup(String videoFilePath, Runnable onVideoEnd) {
        if (!removeAds) {
            // Create a new JavaFX stage (window) for the ad video
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // block other windows
            popupStage.setTitle("30 Second Ad");
            
            // Prevent the user from closing the window manually
            popupStage.setOnCloseRequest(event -> event.consume());
            
            // Load the video file and prepare media playback
            File file = new File(videoFilePath);
            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            
            // Layout container for the media view
            BorderPane root = new BorderPane(mediaView);
            
            // Configure the media view size and aspect ratio
            mediaView.setFitWidth(640);
            mediaView.setFitHeight(480);
            mediaView.setPreserveRatio(true);
            
            // Set scene and show popup stage
            Scene scene = new Scene(root, 640, 480);
            popupStage.setScene(scene);
            popupStage.show();
            
            // Set behavior when video ends
            mediaPlayer.setOnEndOfMedia(() -> {
                inAd = false;
                popupStage.close();
                mediaPlayer.dispose();
                
                // Show main game window again
                KaliszTimes.getGraphicsHandler().getFrame().setVisible(true);
                
                // Run any additional code passed by the caller
                if (onVideoEnd != null) {
                    onVideoEnd.run();
                }
            });
            
            // Start playing video
            mediaPlayer.play();
            inAd = true;
            
            // Hide main window while ad is playing
            KaliszTimes.getGraphicsHandler().getFrame().setVisible(false);
        } else {
            // If ads are removed, immediately run the callback if provided
            if (onVideoEnd != null) {
                onVideoEnd.run();
            }
        }
    }
}
