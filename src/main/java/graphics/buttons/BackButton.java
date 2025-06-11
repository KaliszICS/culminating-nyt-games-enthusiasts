package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import kalisz.KaliszTimes;


/**
 * A custom button that handles the "Back" functionality in the Kalisz Times game.
 * 
 * When clicked, this button navigates to the previous screen via {@code GraphicsHandler.goBack()},
 * unless an advertisement is currently playing. In that case, it displays a popup message
 * informing the user that they cannot exit the game until the ad finishes.
 * 
 * This class extends the {@link Button} class and is initialized with a {@link BufferedImage}
 * used for rendering the button.
 */

public class BackButton extends Button  {


	/**
     * Constructs a BackButton with the specified image.
     * Sets the scaled position and size of the button using GUI constants.
     *
     * @param image the image to display as the button
     */
    public BackButton(BufferedImage image) {
        super(image);

		int refX = 41;
		int refY = 35;
		int refWidth = 74;
		int refHeight = 59;

		setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));


    }
    	 
	/**
     * Called when the mouse is pressed on the button.
     * If an ad is not playing, it navigates back to the previous screen.
     * If an ad is playing, it prevents navigation and shows a popup message.
     *
     * @param e the MouseEvent triggered by user interaction
     */
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Back button pressed");
			if(!KaliszTimes.inAd)
			KaliszTimes.getGraphicsHandler().goBack();
				else
			KaliszTimes.popup("You may not leave the game until you've finished watching the ad!\n(Reminder: Kalisz Times Games is a freemium model. By watching an ad, you directly support the developers of this game. We thank you for your support and contributions)");
	}
			 
		
}
