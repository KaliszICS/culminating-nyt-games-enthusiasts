package graphics.buttons;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.ConnectionsPanel;
import graphics.GUIConstants;
import graphics.GraphicsHandler;
import graphics.WordleGamePanel;

import kalisz.KaliszTimes;

public class WordleButton extends Button {
	private WordleGamePanel wordleInstance;
	private boolean finished = false;
	private String wordleAnswer;
	private int uniqueID;

    public WordleButton(BufferedImage image, int uniqueID, String wordleAnswer) {
        super(image);
		this.uniqueID = uniqueID;
		this.wordleAnswer = wordleAnswer;
		
    }
    

	@Override
	public void mousePressed(MouseEvent e) {
		//Generate a new wordle instance
		if(!GraphicsHandler.activeWordleInstances.containsKey(this)) {
			//Track wordle instance
			wordleInstance = new WordleGamePanel(this);
			GraphicsHandler.activeWordleInstances.put(this, wordleInstance);
			KaliszTimes.getGraphicsHandler().addPanel(wordleInstance, "Wordle Game Panel " + uniqueID);
		}else{
			wordleInstance = GraphicsHandler.activeWordleInstances.get(this);
			KaliszTimes.getGraphicsHandler().jump("Wordle Game Panel " + uniqueID);
			
		}
		wordleInstance.focus();


        
        

     
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { //overrides, so it can disable? might change this
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
	 @Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;

		if(ConnectionsPanel.connectionsGame.currentGuess.contains(uniqueID)) {
            graphics.setColor(Color.gray);
            graphics.fillRect(0, 0, 100, 100);
        }
        if (!finished) {// paints the image from the Button class
			 super.paintComponent(g);
			if(wordleInstance != null && wordleInstance.wordleGame != null && !wordleInstance.wordleGame.getWin()) {
				graphics.setFont(new Font("Arial", Font.BOLD, 15));
				int textWidth = graphics.getFontMetrics().stringWidth("In Progress");
				int textHeight = graphics.getFontMetrics().getAscent();
				int textX = (getWidth() - textWidth) / 2;
				int textY = (getHeight() + textHeight) / 4;
				graphics.setColor(Color.red);
				graphics.drawString("In Progress", GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
			}

			
        // Draw the image only if not finished
    	} else {

		
			
			graphics.setFont(new Font("Arial", Font.BOLD, 25));
			graphics.setColor(Color.black);

			// Draw character centered
			int textWidth = graphics.getFontMetrics().stringWidth(wordleAnswer);
			int textHeight = graphics.getFontMetrics().getAscent();
			int textX = (getWidth() - textWidth) / 2;
			int textY = (getHeight() + textHeight) / 2 - 4;

			graphics.drawString(wordleAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
		}
    }

	public WordleGamePanel getWordleInstance() {
		return this.wordleInstance;
	}
	public void setFinished() {
		finished = true;
		removeMouseListener(this);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ConnectionsPanel.connectionsGame.selectWord(uniqueID);
                repaint();
			}
		});
	}
	public String getWordleAnswer() {
		return wordleAnswer;
	}
}
