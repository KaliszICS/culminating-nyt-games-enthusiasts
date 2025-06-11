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


    }
    	 
		
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Back button pressed");
			if(!KaliszTimes.inAd)
			KaliszTimes.getGraphicsHandler().goBack();
				else
			KaliszTimes.popup("You may not leave the game until you've finished watching the ad!\n(Reminder: Kalisz Times Games is a freemium model. By watching an ad, you directly support the developers of this game. We thank you for your support and contributions)");
	}
			 
		
}
