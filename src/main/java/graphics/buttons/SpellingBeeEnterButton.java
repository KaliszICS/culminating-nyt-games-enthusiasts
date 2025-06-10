package graphics.buttons;


import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.SpellingBeeGamePanel;
import kalisz.KaliszTimes;

public class SpellingBeeEnterButton extends Button {

    public SpellingBeeEnterButton(BufferedImage image) {
        super(image);
    }
    


	@Override
	public void mousePressed(MouseEvent e) {
		//Generate a new spelling bee instance
		SpellingBeeGamePanel newSpellingBeeGamePanel = new SpellingBeeGamePanel();
        System.out.println(newSpellingBeeGamePanel);
        KaliszTimes.getGraphicsHandler().addToPanel(newSpellingBeeGamePanel);
        KaliszTimes.getGraphicsHandler().reloadFrame();
        KaliszTimes.getGraphicsHandler().jumpToRecentlyAdded();
	}

}
