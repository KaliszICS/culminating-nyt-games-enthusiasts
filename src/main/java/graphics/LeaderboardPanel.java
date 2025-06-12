package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;

public class LeaderboardPanel extends TemplatePanel  {

    public String currentPanel = "";
    public LeaderboardPanel() { 
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		
		 setLayout(null);
		 
		 
		 //Add back button
		 add(new BackButton(GUIConstants.backButtonImage));

		 //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));


		//Add next and previous buttons
        int refX = 1920 / 2 - 500;
        int refY = 200;
        Button prevStats = new Button(GUIConstants.stats_prev_button_image, GUIConstants.scaleX(refX), GUIConstants.scaleY(refY)); 
        prevStats.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            
            }
        });
        
		add(prevStats);

        refX = 1920 / 2 + 500;
        Button nextStats = new Button(GUIConstants.stats_next_button_image, GUIConstants.scaleX(refX), GUIConstants.scaleY(refY));        
		add(nextStats);
		 

        
		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(GUIConstants.stats_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		

        
	
    }
    @Override
    public void focus() {
      setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
    
}
