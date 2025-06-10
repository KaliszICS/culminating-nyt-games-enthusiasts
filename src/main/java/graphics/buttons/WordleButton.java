package graphics.buttons;


import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.WordleGamePanel;
//import javafx.scene.Cursor;
import kalisz.KaliszTimes;

public class WordleButton extends Button {

    public WordleButton(BufferedImage image) {
        super(image);
    }
    
    @Override
	public void mouseClicked(MouseEvent e) {
		
      
        
	}


	@Override
	public void mousePressed(MouseEvent e) {
		//Generate a new wordle instance
		  WordleGamePanel newWordlePanel = new WordleGamePanel();
        System.out.println(newWordlePanel);
        KaliszTimes.getGraphicsHandler().addToPanel(newWordlePanel);
        KaliszTimes.getGraphicsHandler().reloadFrame();
        KaliszTimes.getGraphicsHandler().jumpToRecentlyAdded();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	// @Override
	// public void mouseEntered(MouseEvent e) {
	// 	//this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	// }


	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
