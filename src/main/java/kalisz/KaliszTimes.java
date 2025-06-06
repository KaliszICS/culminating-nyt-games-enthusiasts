package kalisz;

import graphics.GraphicsHandler;

public class KaliszTimes {
	
	private static GraphicsHandler handler = null;
	
	public static void main(String args[]) {
		handler = new GraphicsHandler();
		handler.initiate();
	}

	public static GraphicsHandler getHandler() {
		return handler;
	}
	
}
