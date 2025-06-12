package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
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
import logic.LeaderboardHandler;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class ConnectionsPanel extends TemplatePanel {
	ArrayList<WordleButton> wordleButtons = new ArrayList<WordleButton>();
	private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();

	ArrayList<SpellingBeeEnterButton> spellingBeeButtons = new ArrayList<SpellingBeeEnterButton>();

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
						
						String code = connectionsGame.submitGuess();

						if(code.equals("not enough words")) {
							KaliszTimes.popup("You have not selected enough words to make a connection!");
						}else if(code.equals("wrong")){
							KaliszTimes.popup("That is the incorrect connection! -1 guess!");
						}else if(code.equals("game over")) {
							KaliszTimes.popup("You lost :(");
						}else{
							//Win the category
							//set each word in current guess to revealed, to mark as green (handled in gui)

							KaliszTimes.popup("You successfully guessed the category: " + connectionsGame.convertCategoryColorToName(code));

							//Update all buttons to reflect changes
							for(WordleButton wordleButton : wordleButtons)
								wordleButton.repaint();
							for(SpellingBeeEnterButton spellingBeeEnterButton : spellingBeeButtons) {
								spellingBeeEnterButton.repaint();
							}

							if(connectionsGame.getWin()) {
								
								KaliszTimes.popup("You have successfully completed today's The Kalisz Times Game!\nCongratulations!\nFeel free to see your stats in the leaderboard.");
								connectionsGame.winEvent();
							}
						}
						connectionsGame.deselectAll();
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

		  //Username Label
		


		 //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		add(new StatsButton(GUIConstants.viewStatsButtonImage));

		 //Add Shuffle Button
		 int refShuffleX = 720;
		 int refShuffleY = 925;
		 Button shuffleButton = new Button(GUIConstants.shuffleButtonImage, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY));
		 shuffleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KaliszTimes.popup("Shuffled connections board!");
				shuffleBoardUI();
			}
		 });
		 add(shuffleButton);
		 
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

	  List<String> fullBoard = connectionsGame.getBoard();
    
    // Separate into Wordle (5-letter) and Spelling Bee (7-letter) words
    // while tracking their original indices
    List<Integer> wordleIndices = new ArrayList<>();
    List<Integer> spellingBeeIndices = new ArrayList<>();

    for (int i = 0; i < fullBoard.size(); i++) {
        String word = fullBoard.get(i);
        if (word.length() == 5) {
            wordleIndices.add(i);
        } else if (word.length() == 7) {
            spellingBeeIndices.add(i);
        }
    }

    // Create Wordle buttons (5-letter words)
    for (int i = 0; i < wordleIndices.size(); i++) {
        int boardIndex = wordleIndices.get(i);
        String word = fullBoard.get(boardIndex);

        WordleButton wordleButton = new WordleButton(
            GUIConstants.wordleButtonImage, 
            boardIndex, // CRUCIAL: Store the original board index
            word.toUpperCase()
        );

        // Position calculation (2 rows of 4)
        int refX = 695 + ((i % 4) * 153);
        int refY = 250 + ((i / 4) * 153);
        
        wordleButton.setBounds(
            GUIConstants.scaleX(refX), 
            GUIConstants.scaleY(refY), 
            GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH), 
            GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT)
        );

        

        wordleButtons.add(wordleButton);
        add(wordleButton);
    }

    // Create Spelling Bee buttons (7-letter words)
    for (int i = 0; i < spellingBeeIndices.size(); i++) {
        int boardIndex = spellingBeeIndices.get(i);
        String word = fullBoard.get(boardIndex);

        SpellingBeeEnterButton spellingBeeButton = new SpellingBeeEnterButton(
            GUIConstants.spellingBeeButtonImage, 
            boardIndex, // CRUCIAL: Store the original board index
            word.toUpperCase()
        );

        // Position calculation (2 rows of 4)
        int refX = 695 + ((i % 4) * 153);
        int refY = 550 + ((i / 4) * 153);
        
        spellingBeeButton.setBounds(
            GUIConstants.scaleX(refX), 
            GUIConstants.scaleY(refY), 
            GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH), 
            GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT)
        );

        // Add click handler that uses the correct board index
        

        spellingBeeButtons.add(spellingBeeButton);
        add(spellingBeeButton);
    }



		











		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(GUIConstants.connections_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		

		//Label text
		String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
		int refLabelX = 250;
		int refLabelY = 75;

		graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
		graphics.setColor(Color.black); // or whatever color you want
		graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));

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
private void shuffleBoardUI() {

    // Step 1: Combine all buttons into one list
    List<Button> allButtons = new ArrayList<>();
    allButtons.addAll(wordleButtons);
    allButtons.addAll(spellingBeeButtons);

    // Step 2: Shuffle the combined list
    Collections.shuffle(allButtons);

    // Step 3: Reposition in a 4x4 grid layout (4 columns per row)
    for (int i = 0; i < allButtons.size(); i++) {
        Button button = allButtons.get(i);

        int refX = 695 + ((i % 4) * 153); // column offset
        int refY = 250 + ((i / 4) * 153); // row offset (spans both wordle + bee)

        button.setBounds(
            GUIConstants.scaleX(refX),
            GUIConstants.scaleY(refY),
            GUIConstants.scaleX(GUIConstants.DEFAULT_BUTTON_WIDTH),
            GUIConstants.scaleY(GUIConstants.DEFAULT_BUTTON_HEIGHT)
        );
    }

    revalidate();
    repaint();


}
class WordWithIndex {
		// Helper class to track words with their original board indices
    String word;
    int index;
    
    public WordWithIndex(String word, int index) {
        this.word = word;
        this.index = index;
    }
}
	}
	

 

