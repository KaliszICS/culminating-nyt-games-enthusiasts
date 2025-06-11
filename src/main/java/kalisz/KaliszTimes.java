package kalisz;

import graphics.GUIConstants;
import graphics.GraphicsHandler;
import logic.AIHandler;
import logic.AudioHandler;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import java.awt.Dimension;

public class KaliszTimes {
	
	private static GraphicsHandler handler = null;
	private static AudioHandler audioHandler = null;

	public static void main(String args[]) {
		audioHandler = new AudioHandler();

		//Initialize constants
		GUIConstants constants = new GUIConstants();
		System.out.println(GUIConstants.WINDOW_WIDTH + " " + GUIConstants.WINDOW_HEIGHT);

		//KaliszTimes.getAudioHandler().playSound("/button_sound.wav"); //work on this
		handler = new GraphicsHandler();
		handler.initiate();

		
		
		//System.out.println(AIHandler.chatGPT("Generate me a list of words for wordle, without any other text. Just the raw words with a space in between").split(" "));
	}

	public static GraphicsHandler getGraphicsHandler() {
		return handler;
	}
	public static AudioHandler getAudioHandler() {
		return audioHandler;
	}
	public static void popup(String message) {
		JOptionPane.showMessageDialog(null, message, "Information!", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
