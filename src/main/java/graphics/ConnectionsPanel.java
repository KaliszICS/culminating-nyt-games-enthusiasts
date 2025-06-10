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
import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.WordleButton;
import graphics.utils.GUIConstants;
import graphics.utils.PanelAttributes;
import kalisz.KaliszTimes;

public class ConnectionsPanel extends JPanel implements PanelAttributes {
	BufferedImage background, backButtonImage, playButtonImage, wordleButtonImage, spellingBeeButtonImage;
	ArrayList<Button> wordleButtons = new ArrayList<Button>();
	ArrayList<Button> spellingBeeButtons = new ArrayList<Button>();
	
	public ConnectionsPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 loadImages();
		 setLayout(null);
		 
		 
		 
		 add(new BackButton(backButtonImage));
		 
		 
		 //Add wordle icons and make them all clickable. Store all of these new buttons in a list.
		 //8 tiles

		 for(int i = 0; i < 8; i++) {
			Button wordleButton = new WordleButton(wordleButtonImage);
			wordleButton.setSize(GUIConstants.DEFAULT_BUTTON_WIDTH, GUIConstants.DEFAULT_BUTTON_HEIGHT);


			wordleButton.setLocation(695 + ( (i / 2) < 2 ? i * 153 : (i - 4) * 153), 250 + ( (i / 2) >= 2 ? 153 : 0)); //center of box

			

			wordleButtons.add(wordleButton);
		 }
		 
		
		 
		 //Add the wordle buttons using the stored buttons in the arraylist
		 for(Button b : wordleButtons) {
			add(b);
		 }

		 //Add Spelling Bee Buttons
		  for(int i = 0; i < 8; i++) {
			Button spellingBeeButton = new SpellingBeeEnterButton(spellingBeeButtonImage);
			spellingBeeButton.setSize(GUIConstants.DEFAULT_BUTTON_WIDTH, GUIConstants.DEFAULT_BUTTON_HEIGHT);


			spellingBeeButton.setLocation(695 + ( (i / 2) < 2 ? i * 153 : (i - 4) * 153), 550 + ( (i / 2) >= 2 ? 153 : 0)); //center of box

			

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
		graphics.drawImage(background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		

	}
	@Override
	public void loadImages() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("resources/ConnectionsBackground.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			wordleButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/wordle_button.png"));
			spellingBeeButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	
}
