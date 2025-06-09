package kalisz;

import graphics.GraphicsHandler;
import logic.AIHandler;

public class KaliszTimes {
	
	private static GraphicsHandler handler = null;
	
	public static void main(String args[]) {
		handler = new GraphicsHandler();
		handler.initiate();
		
		//System.out.println(AIHandler.chatGPT("Generate me a list of words for wordle, without any other text. Just the raw words with a space in between").split(" "));
	}

	public static GraphicsHandler getHandler() {
		return handler;
	}
	
}
