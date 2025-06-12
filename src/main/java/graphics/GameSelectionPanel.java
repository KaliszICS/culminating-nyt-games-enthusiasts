package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.StatsButton;
import kalisz.KaliszTimes;

public class GameSelectionPanel extends TemplatePanel {

	
	public GameSelectionPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		
		 setLayout(null);
		 
		 
		
		 add(new BackButton(GUIConstants.backButtonImage));
		 
		//Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		add(new StatsButton(GUIConstants.viewStatsButtonImage));


		 //Play button
		 Button playButton = new Button(GUIConstants.playButtonImage);
		 int refX = 842;
		 int refY = 775;
		 int refWidth =286;
		 int refHeight = 93;
		 playButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));
		
		 playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Play button pressed");
				KaliszTimes.getGraphicsHandler().jump("Connections Panel");
			}
			 
		 });
		 
		 
		 add(playButton);
		 
		 
		 repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		graphics.drawImage(GUIConstants.connections_start_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT, this);
		

	}
	
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
	
}
