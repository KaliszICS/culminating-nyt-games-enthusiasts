package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import kalisz.KaliszTimes;

public class BackButton extends Button  {

    public BackButton(BufferedImage image) {
        super(image);
        setLocation(41, 35);
		 setSize(74, 59);
		addMouseListener(this);


    }
    	 
		
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Back button pressed");
			KaliszTimes.getGraphicsHandler().previousPanel();
	}
			 
		
}
