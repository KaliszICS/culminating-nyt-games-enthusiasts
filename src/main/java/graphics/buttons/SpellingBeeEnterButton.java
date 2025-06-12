package graphics.buttons;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import graphics.ConnectionsPanel;
import graphics.GUIConstants;
import graphics.GraphicsHandler;
import graphics.SpellingBeeGamePanel;
import graphics.SpellingBeeGamePanel;

import kalisz.KaliszTimes;

/**
 * Represents a button that launches or resumes a {@link SpellingBeeGamePanel} when clicked.
 * Initially, this button starts a new game instance or resumes an existing one tied to the button.
 * Once marked as "finished", clicking it will instead pass the result (spelling bee answer) to the
 * {@link ConnectionsPanel} for selection.
 *
 * @see Button
 * @see SpellingBeeGamePanel
 * @see GraphicsHandler
 * @see ConnectionsPanel
 */

public class SpellingBeeEnterButton extends Button {
    private SpellingBeeGamePanel spellingBeeInstance;
	private boolean finished = false;
    int SPELLING_BEE_BOARD_OFFSET = 8;
	private String spellingBeeAnswer;
	private int uniqueID;

    /**
     * Constructs a new Spelling Bee Enter button.
     *
     * @param image             the image used as the background of the button
     * @param uniqueID          the ID of this button in the Connections game
     * @param spellingBeeAnswer the answer word revealed upon completion
     */
    public SpellingBeeEnterButton(BufferedImage image, int uniqueID, String spellingBeeAnswer) {
        super(image);
		this.uniqueID = uniqueID + SPELLING_BEE_BOARD_OFFSET;
        this.spellingBeeAnswer = spellingBeeAnswer;
		
    }
    
    /**
     * Handles mouse press events. If the spelling bee instance hasn't started yet, a new one is created
     * and tracked in the {@link GraphicsHandler}. If already present, it resumes the game panel.
     *
     * @param e the mouse event triggering the press
     */
	@Override
	public void mousePressed(MouseEvent e) {
		//Generate a new SpellingBee instance
		if(!GraphicsHandler.activeSpellingBeeInstances.containsKey(this)) {
			//Track SpellingBee instance
			spellingBeeInstance = new SpellingBeeGamePanel(this);
			GraphicsHandler.activeSpellingBeeInstances.put(this, spellingBeeInstance);
			KaliszTimes.getGraphicsHandler().addPanel(spellingBeeInstance, "Spelling Bee Game Panel " + uniqueID);
		}else{
			spellingBeeInstance = GraphicsHandler.activeSpellingBeeInstances.get(this);
			KaliszTimes.getGraphicsHandler().jump("Spelling Bee Game Panel " + uniqueID);
			
		}
		spellingBeeInstance.focus();
     
	}
    /**
     * Updates the cursor to a hand when the mouse hovers over the button.
     *
     * @param e the mouse event triggering the hover
     */
	@Override
	public void mouseEntered(MouseEvent e) { //overrides, so it can disable? might change this
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
	/**
     * Custom paint method that handles drawing the background, status messages, and final answer.
     *
     * @param g the graphics context used for painting
     */
	 @Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
        if(ConnectionsPanel.connectionsGame.currentGuess.contains(uniqueID)) {
            graphics.setColor(Color.gray);
            graphics.fillRect(0, 0, 100, 100);
        }

        if (!finished) {// paints the image from the Button class
			 super.paintComponent(g);
			if(spellingBeeInstance != null && spellingBeeInstance.spellingBeeGame != null && !spellingBeeInstance.spellingBeeGame.getWin()) {
                
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

		
			int refFontSize = 20;
			graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleFont(refFontSize)));
			graphics.setColor(Color.black);

			// Draw character centered
			int textWidth = graphics.getFontMetrics().stringWidth(spellingBeeAnswer);
			int textHeight = graphics.getFontMetrics().getAscent();
			int textX = (getWidth() - textWidth) / 2;
			int textY = (getHeight() + textHeight) / 2 - 4;

			graphics.drawString(spellingBeeAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
		}
    }
	/**
     * Returns the instance of the spelling bee game panel associated with this button.
     *
     * @return the {@link SpellingBeeGamePanel} instance
     */
	public SpellingBeeGamePanel getSpellingBeeInstance() {
		return this.spellingBeeInstance;
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
	    /**
     * Gets the keyword (correct spelling bee answer) associated with this button.
     *
     * @return the solution word
     */
    public String getKeyWord() {
        return this.spellingBeeAnswer;
    }
   
}
