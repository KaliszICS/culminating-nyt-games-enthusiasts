package kalisz;


import graphics.GUIConstants;
import graphics.GraphicsHandler;
import graphics.utils.GameDataHandler;
//import graphics.utils.JFXInitializer;
/*
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.AIHandler;
*/

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JOptionPane;

import java.awt.Dimension;

/**
 * Entry point for the Kalisz Times game.
 * Initializes game data, constants, and graphics. Handles ad video popups and general popups.
 * 
 * This class integrates both JavaFX and Swing components to manage UI alerts and media playback.
 */

public class KaliszTimes {
	
	private static GraphicsHandler handler = null;
	
	public static boolean inAd = false;

	public static void main(String args[]) {
		new GameDataHandler(); //Initialize game data.


		System.out.println(GameDataHandler.yellowCategory + " YELLOW");
		for(String yellow : GameDataHandler.yellowWords) {
			System.out.println(yellow);
		}
		
		
		//JFXInitializer.initJavaFX();

        // Initialize JavaFX to support ad player
       

		//Initialize constants
		new GUIConstants();
		System.out.println(GUIConstants.WINDOW_WIDTH + " " + GUIConstants.WINDOW_HEIGHT);

		//KaliszTimes.getAudioHandler().playSound("/button_sound.wav"); //work on this
		handler = new GraphicsHandler();
		handler.initiate();

		
		
		//System.out.println(AIHandler.chatGPT("Generate me a list of words for wordle, without any other text. Just the raw words with a space in between").split(" "));
	}
    /**
     * Gets the global graphics handler for the application.
     *
     * @return the GraphicsHandler instance
     */
	public static GraphicsHandler getGraphicsHandler() {
		return handler;
	}
	/**
     * Displays a simple informational popup message using Swing.
     *
     * @param message the message to display in the dialog
     */
	public static void popup(String message) {
		JOptionPane.showMessageDialog(null, message, "Information!", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
     * Displays an ad revival popup message using Swing.
     *
     * @param message the message prompting the user to watch an ad
     */
	public static void adPopup(String message) {
		JOptionPane.showMessageDialog(null, message, "Watch an ad to revive!", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
     * Displays a modal video popup that plays a 30-second ad.
     * Blocks the user from interacting with the main application until the video finishes.
     * 
     * @param videoFilePath the path to the video file to be played
     * @param onVideoEnd a Runnable that is executed when the video finishes
     */
	public static void showVideoPopup(String videoFilePath, Runnable onVideoEnd) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // blocks other windows until closed
        popupStage.setTitle("30 Second Ad");

		popupStage.setOnCloseRequest(event -> event.consume()); //Blocks user from closing the ad

        File file = new File(videoFilePath);
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        BorderPane root = new BorderPane(mediaView);

		
       
		mediaView.setFitWidth(640);
		mediaView.setFitHeight(480);
		mediaView.setPreserveRatio(true);
        Scene scene = new Scene(root, 640, 480);
        popupStage.setScene(scene);
        popupStage.show();


        mediaPlayer.setOnEndOfMedia(() -> {
			inAd = false;
			popupStage.close();
			mediaPlayer.dispose(); 
			if (onVideoEnd != null) {
            onVideoEnd.run();  // Ppost-video code ran here
        	}
			
    	});

   		 mediaPlayer.play();
		 inAd = true;
    }
	*/
}
