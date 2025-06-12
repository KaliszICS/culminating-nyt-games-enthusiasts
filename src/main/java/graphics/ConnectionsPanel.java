package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.StatsButton;
import graphics.buttons.WordleButton;
import graphics.utils.GameDataHandler;

import kalisz.KaliszTimes;
import logic.Connections;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class ConnectionsPanel extends TemplatePanel {
	ArrayList<WordleButton> wordleButtons = new ArrayList<WordleButton>();
	private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();

	ArrayList<Button> spellingBeeButtons = new ArrayList<Button>();

	static GameDataHandler data;
	public static Connections connectionsGame;
	
	public ConnectionsPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 
		 setLayout(null);
		 
		
		 //RUN CONNECTIONS
		 connectionsGame = new Connections(GameDataHandler.yellowWords, GameDataHandler.yellowCategory,
		 GameDataHandler.greenWords, GameDataHandler.greenCategory, GameDataHandler.blueWords, GameDataHandler.blueCategory, GameDataHandler.purpleWords, GameDataHandler.purpleCategory);

		//ADD EVENT HANDLER
		 addKeyboardListener(new KeyboardClickEventListener() {

			@Override
			public void handleClick(KeyboardClickEvent e) {
				int clickType = e.getClickType();
				switch(clickType) {
					case KeyboardClickEvent.ENTER:
						for(int i : connectionsGame.currentGuess)
						System.out.println(i);
						String code = connectionsGame.submitGuess();

						if(code.equals("not enough words")) {
							KaliszTimes.popup("You have not selected enough words to make a connection!");
						}else if(code.equals("wrong")){
							KaliszTimes.popup("That is the incorrect connection! -1 guess!");
						}else if(code.equals("game over")) {
							KaliszTimes.popup("You lost :(");
						}else{
							KaliszTimes.popup("You successfully guessed the category: " + code);
						}
						repaint();
						break;
					case KeyboardClickEvent.DESELECT_ALL:
						connectionsGame.deselectAll();
						repaint();
						break;
					}
				}

				
					
			});

		
		
		 
		 //Add back button
		 add(new BackButton(GUIConstants.backButtonImage));

		 //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		add(new StatsButton(GUIConstants.viewStatsButtonImage));

		 //Add Shuffle Button
		 int refShuffleX = 720;
		 int refShuffleY = 925;
		 add(new Button(GUIConstants.shuffleButtonImage, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY)));
		 
		 //Add Deselect All Button
		int refDeselectX = 878;
		int refDeselectY = 925;
		Button deselectAllButton = new Button(GUIConstants.deselectAllButtonImage, GUIConstants.scaleX(refDeselectX), GUIConstants.scaleY(refDeselectY));
		deselectAllButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				EventHandler.fireConnectionsClickEvent(getPanel(), ' ', KeyboardClickEvent.DESELECT_ALL);
			}
		});
		
		add(deselectAllButton);

		//Add Submit Button
		int refSubmitX = 1088;
		int refSubmitY = 925;
		Button submitButton = new Button(GUIConstants.submitButtonImage, GUIConstants.scaleX(refSubmitX), GUIConstants.scaleY(refSubmitY));
		submitButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				EventHandler.fireConnectionsClickEvent(getPanel(), ' ', KeyboardClickEvent.ENTER);
			}
		});
		add(submitButton);

		 //Add wordle icons and make them all clickable. Store all of these new buttons in a list.

		 //Step 1: Make an array of wordle answers
		ArrayList<String> wordleAnswers = new ArrayList<>();
		//Step 2: Add the first two elements of each colour of array into the list (these are the wordle answers)
		Collections.addAll(wordleAnswers,
    		connectionsGame.getYellowWords()[0], connectionsGame.getYellowWords()[1],
    		connectionsGame.getGreenWords()[0], connectionsGame.getGreenWords()[1],
    			connectionsGame.getBlueWords()[0], connectionsGame.getBlueWords()[1],
    		connectionsGame.getPurpleWords()[0], connectionsGame.getPurpleWords()[1]
		);

		// Step 3: Shuffle to randomize
		Collections.shuffle(wordleAnswers);



		 //8 tiles

		 for(int i = 0; i < 8; i++) {
			WordleButton wordleButton = new WordleButton(GUIConstants.wordleButtonImage, i, wordleAnswers.get(i).toUpperCase());

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


		  //Step 1: Make an array of spelling bee answers
		ArrayList<String> spellingBeeAnswers = new ArrayList<>();
		//Step 2: Add the last two elements of each colour of array into the list (these are the spelling bee answers)
		Collections.addAll(spellingBeeAnswers,
    		connectionsGame.getYellowWords()[2], connectionsGame.getYellowWords()[3],
    		connectionsGame.getGreenWords()[2], connectionsGame.getGreenWords()[3],
    			connectionsGame.getBlueWords()[2], connectionsGame.getBlueWords()[3],
    		connectionsGame.getPurpleWords()[2], connectionsGame.getPurpleWords()[3]
		);

		// Step 3: Shuffle to randomize
		Collections.shuffle(spellingBeeAnswers);



		 //Add Spelling Bee Buttons
		  for(int i = 0; i < 8; i++) {
			Button spellingBeeButton = new SpellingBeeEnterButton(GUIConstants.spellingBeeButtonImage, i, spellingBeeAnswers.get(i).toUpperCase());
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
	public ArrayList<WordleButton> getWordleButtons() {
		return this.wordleButtons;
	}
    
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
	 public void addKeyboardListener(KeyboardClickEventListener listener) {
        listeners.add(listener);
    }

    public void removeKeyboardListener(KeyboardClickEventListener listener) {
        listeners.remove(listener);
    }
	public ArrayList<KeyboardClickEventListener> getListeners() {
		return listeners;
	}
	public ConnectionsPanel getPanel() {
		return this;
	}
}
