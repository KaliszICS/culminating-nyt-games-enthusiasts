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

public class SpellingBeeEnterButton extends Button {
    private SpellingBeeGamePanel spellingBeeInstance;
	private boolean finished = false;
    int SPELLING_BEE_BOARD_OFFSET = 7;
	private String spellingBeeAnswer;
	private int uniqueID;

    public SpellingBeeEnterButton(BufferedImage image, int uniqueID, String spellingBeeAnswer) {
        super(image);
		this.uniqueID = uniqueID + SPELLING_BEE_BOARD_OFFSET;
        this.spellingBeeAnswer = spellingBeeAnswer;
		
    }
    

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
			if(spellingBeeInstance != null && spellingBeeInstance.spellingBeeGame != null && !spellingBeeInstance.spellingBeeGame.getWin()) {
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

		
			
			graphics.setFont(new Font("Arial", Font.BOLD, 20));
			graphics.setColor(Color.black);

			// Draw character centered
			int textWidth = graphics.getFontMetrics().stringWidth(spellingBeeAnswer);
			int textHeight = graphics.getFontMetrics().getAscent();
			int textX = (getWidth() - textWidth) / 2;
			int textY = (getHeight() + textHeight) / 2 - 4;

			graphics.drawString(spellingBeeAnswer, GUIConstants.scaleX(textX), GUIConstants.scaleY(textY));
		}
    }

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
    public String getKeyWord() {
        return this.spellingBeeAnswer;
    }
   
}
