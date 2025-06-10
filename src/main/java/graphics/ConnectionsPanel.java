package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.WordleButton;
import graphics.utils.PanelAttributes;
import kalisz.KaliszTimes;

public class ConnectionsPanel extends JPanel implements PanelAttributes {
	ArrayList<Button> wordleButtons = new ArrayList<Button>();
	ArrayList<Button> spellingBeeButtons = new ArrayList<Button>();
	
	public ConnectionsPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 loadImages();
		 setLayout(null);
		 
		 
		 //Add back button
		 add(new BackButton(GUIConstants.backButtonImage));

		 //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		 int refStatsX = 1600;
		 int refStatsY = 20;
		 add(new Button(GUIConstants.viewStatsButtonImage, GUIConstants.scaleX(refStatsX), GUIConstants.scaleY(refStatsY)));

		 //Add Shuffle Button
		 int refShuffleX = 720;
		 int refShuffleY = 925;
		 add(new Button(GUIConstants.shuffleButtonImage, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY)));
		 
		 //Add Deselect All Button
		int refDeselectX = 878;
		int refDeselectY = 925;
		add(new Button(GUIConstants.deselectAllButtonImage, GUIConstants.scaleX(refDeselectX), GUIConstants.scaleY(refDeselectY)));

		//Add Submit Button
		int refSubmitX = 1088;
		int refSubmitY = 925;
		add(new Button(GUIConstants.submitButtonImage, GUIConstants.scaleX(refSubmitX), GUIConstants.scaleY(refSubmitY)));

		 //Add wordle icons and make them all clickable. Store all of these new buttons in a list.
		 //8 tiles

		 for(int i = 0; i < 8; i++) {
			Button wordleButton = new WordleButton(GUIConstants.wordleButtonImage);

			int refX = 695 + ( (i / 2) < 2 ? i * 153 : (i - 4) * 153);
			int refY = 250 + ( (i / 2) >= 2 ? 153 : 0);

			int refWidth = GUIConstants.DEFAULT_BUTTON_WIDTH;
			int refHeight = GUIConstants.DEFAULT_BUTTON_HEIGHT; 

			wordleButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));
			

			wordleButtons.add(wordleButton);
		 }
		 
		
		 
		 //Add the wordle buttons using the stored buttons in the arraylist
		 for(Button b : wordleButtons) {
			add(b);
		 }

		 //Add Spelling Bee Buttons
		  for(int i = 0; i < 8; i++) {
			Button spellingBeeButton = new SpellingBeeEnterButton(GUIConstants.spellingBeeButtonImage);
			int refX = 695 + ( (i / 2) < 2 ? i * 153 : (i - 4) * 153);
			int refY = 550 + ( (i / 2) >= 2 ? 153 : 0);
			int refWidth = GUIConstants.DEFAULT_BUTTON_WIDTH;
			int refHeight = GUIConstants.DEFAULT_BUTTON_HEIGHT;


			spellingBeeButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));

			spellingBeeButtons.add(spellingBeeButton);
		 }

		 for(Button b : spellingBeeButtons) {
			add(b);
		 }


		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(GUIConstants.connections_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		

	}
	@Override
	public void loadImages() {
		
	}
    
	
}
