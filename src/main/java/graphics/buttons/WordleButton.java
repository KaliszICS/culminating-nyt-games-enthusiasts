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

/**
 * A button that launches or resumes a Wordle game instance.
 * When clicked, this button either creates a new Wordle game panel or
 * brings an existing one to focus. Once the Wordle game linked to this button
 * is finished, clicking the button instead sends the answer word to the
 * Connections game for selection.
 * 
 * @see Button
 * @see WordleGamePanel
 * @see ConnectionsPanel
 */
public class WordleButton extends Button {
	private WordleGamePanel wordleInstance;
	private boolean finished = false;
	private String wordleAnswer;
	private int uniqueID;

    /**
     * Constructs a WordleButton with a given image, unique ID, and answer word.
     * 
     * @param image       The background image of the button.
     * @param uniqueID    Unique identifier to correlate with Connections game.
     * @param wordleAnswer The answer word revealed after the game is finished.
     */
    public WordleButton(BufferedImage image, int uniqueID, String wordleAnswer) {
        super(image);
		this.uniqueID = uniqueID;
		this.wordleAnswer = wordleAnswer;
		
    }
    
    /**
     * Handles mouse press events.
     * If no Wordle instance is active for this button, it creates a new one,
     * tracks it, and adds its panel to the graphics handler. Otherwise, it
     * focuses on the existing game panel.
     * 
     * @param e Mouse event that triggered the press.
     */
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
    /**
     * Changes cursor to hand cursor on mouse hover.
     * 
     * @param e Mouse event for entering the button area.
     */
	
	 
    /**
     * Paints the button.
     * <p>
     * When the game is in progress, shows "In Progress" text.
     * When finished, displays the answer word centered.
     * Also greys out the button if currently selected in the Connections game.
     * </p>
     * 
     * @param g The Graphics context to draw on.
     */
	 @Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		if(ConnectionsPanel.connectionsGame.isWordRevealed(this.wordleAnswer)){
            switch(ConnectionsPanel.connectionsGame.getWordColor(this.wordleAnswer)) {
                case "blue":
                    graphics.setColor(Color.blue);
                    break;
                case "green":
                    graphics.setColor(Color.green);
                    break;
                case "yellow":
                    graphics.setColor(Color.yellow);
                    break;
                case "purple":
                    graphics.setColor(Color.magenta);
                    break;
            }
            graphics.fillRect(0, 0, 100, 100);
        
        }

		if(ConnectionsPanel.connectionsGame.currentGuess.contains(uniqueID)) {
            graphics.setColor(Color.gray);
            graphics.fillRect(0, 0, 100, 100);
        }
        if (!finished) {// paints the image from the Button class
			 super.paintComponent(g);
			if(wordleInstance != null && wordleInstance.wordleGame != null && !wordleInstance.wordleGame.getWin()) {

				int refFontSize = 15;
				graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));
				int textWidth = graphics.getFontMetrics().stringWidth("In Progress");
				int textHeight = graphics.getFontMetrics().getAscent();
				int textX = (getWidth() - textWidth) / 2;
				int textY = (getHeight() + textHeight) / 4;
				graphics.setColor(Color.red);
				graphics.drawString("In Progress", GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
			}

			
        // Draw the image only if not finished
    	} else {

		
			int refFontSize = 25;
			graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));
			graphics.setColor(Color.black);

			// Draw character centered
			int textWidth = graphics.getFontMetrics().stringWidth(wordleAnswer);
			int textHeight = graphics.getFontMetrics().getAscent();
			int textX = (getWidth() - textWidth) / 2;
			int textY = (getHeight() + textHeight) / 2 - 4;

			graphics.drawString(wordleAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
		}
    }
    /**
     * Returns the Wordle game panel instance associated with this button.
     * 
     * @return The {@link WordleGamePanel} instance.
     */
	public WordleGamePanel getWordleInstance() {
		return this.wordleInstance;
	}
	/**
     * Marks this button as finished, removes the existing mouse listener,
     * and adds a new one that will select this word in the Connections game.
     */
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
    /**
     * Gets the answer word for this Wordle button.
     * 
     * @return The answer word as a String.
     */
	public String getWordleAnswer() {
		return wordleAnswer;
	}
}
