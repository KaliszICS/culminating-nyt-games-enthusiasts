package graphics.buttons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import graphics.LeaderboardPanel;
import kalisz.KaliszTimes;

public class StatsButton extends Button {
   
    public StatsButton(BufferedImage image) {
        super(image);

		int refX = 1600;
		int refY = 20;
		int refWidth = getWidth();
		int refHeight = getHeight();

		setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));

    }
    	@Override
	public void mousePressed(MouseEvent e) {
	
		//KaliszTimes.getGraphicsHandler().addPanel(new LeaderboardPanel(), "Leaderboard Panel");
        KaliszTimes.getGraphicsHandler().jump("Leaderboard Panel");
	}
}
