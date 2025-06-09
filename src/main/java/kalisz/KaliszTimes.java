package kalisz;

import graphics.GraphicsHandler;
import logic.AIHandler;
import logic.AudioHandler;

public class KaliszTimes {
	
	private static GraphicsHandler handler = null;
	private static AudioHandler audioHandler = null;

	public static void main(String args[]) {
		audioHandler = new AudioHandler();
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
	
}
