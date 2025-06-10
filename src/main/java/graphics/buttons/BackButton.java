package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import kalisz.KaliszTimes;

public class BackButton extends Button  {

    public BackButton(BufferedImage image) {
        super(image);

		int refX = 41;
		int refY = 35;
		int refWidth = 74;
		int refHeight = 59;

		setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));

		addMouseListener(this);


    }
    	 
		
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Back button pressed");
			KaliszTimes.getGraphicsHandler().previousPanel();
	}
			 
		
}
