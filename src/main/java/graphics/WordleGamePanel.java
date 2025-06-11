package graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.KeyboardButton;
import graphics.buttons.WordleButton;
import graphics.utils.PanelAttributes;
//import javafx.application.Platform;
//import javafx.scene.text.Font;
import kalisz.KaliszTimes;
import logic.DictionaryChecker;
import logic.Wordle;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class WordleGamePanel extends JPanel implements KeyListener, PanelAttributes {
	
	WordleButton origin;
	ArrayList<KeyboardButton> keyboardButtons = new ArrayList<KeyboardButton>();
	private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();

	private String[][] grid = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];
	private String[][] gridColours = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];


	private ArrayList<Character> lettersInQueue = new ArrayList<Character>();
	private int rowNumber = 0; //Will change every time user clicks enter.
	

	public Wordle wordleGame;
	
	public WordleGamePanel(WordleButton origin) {
		this.origin = origin;
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 
		 setLayout(null);
		 
		 
		 add(new BackButton(GUIConstants.backButtonImage));

		  //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		 int refStatsX = 1600;
		 int refStatsY = 20;
		 add(new Button(GUIConstants.viewStatsButtonImage, GUIConstants.scaleX(refStatsX), GUIConstants.scaleY(refStatsY)));

		
		 //Loads keyboard
		 loadKeyboard();
		
		 //Adds physical keyboard compatibility 
		addKeyListener(this);
		

		//Implement Franklin's Wordle Game 
		this.wordleGame = new Wordle(origin.getWordleAnswer());


		 //Adds digital keyboard listener (ours) that will listen for special KeyboardClickEvent
		 addKeyboardListener(new KeyboardClickEventListener() {

			
			@Override
			public void handleClick(KeyboardClickEvent e) {
				System.out.println(wordleGame.getWord());
				if(rowNumber <= 5) { //If max rows is not exceeded
					char letter = e.getKeyClicked();

					System.out.println(lettersInQueue.size());

					//If letters in queue is not full, and user presses a "normal" key, add that key to the current row.
					if(lettersInQueue.size() < 5 && e.getClickType() == KeyboardClickEvent.NORMAL_KEY) { //ADD LETTER TO GRID
						grid[rowNumber][lettersInQueue.size()] = "" + letter; //Set letter in appropriate grid position to keyboard click.
						repaint();

						lettersInQueue.add(letter);
						wordleGame.inputLetter(letter);
					}
					//If letters in queue is not empty, give user the ability to backspace.
					if(lettersInQueue.size() > 0 && e.getClickType() == KeyboardClickEvent.BACKSPACE) {
						grid[rowNumber][lettersInQueue.size() - 1] = null;
						lettersInQueue.remove(lettersInQueue.size() - 1);
						repaint();

						wordleGame.deleteLetter();
					}
					//If queue is full, give user the ability to submit their word to verify each individual letter.
					if(e.getClickType() == KeyboardClickEvent.ENTER) {
						if(rowNumber == 5 && !wordleGame.getCurrentGuess().equals(wordleGame.getWord())) {
							System.out.println("Lost");
							KaliszTimes.adPopup("Uh oh! You exceeded the max number of guesses without guessing the right word!\nWatch an ad to continue!\n(Reminder: Kalisz Times Games is a freemium model. By watching an ad, you directly support the developers of this game. We thank you for your support and contributions)");
							/* */
							/*Platform.runLater(() -> {
								KaliszTimes.showVideoPopup("Ad.mp4", () -> {
									KaliszTimes.popup("Your ad is over! You may now resume to playing the game.");



									//WIN EVENT
									origin.setFinished();
									origin.repaint();

										// Properly remove panel from parent
									getPanel().setFocusable(false);
									getPanel().setVisible(false);
									

										Container parent = getPanel().getParent();
										if (parent != null) {
											parent.remove(getPanel());
											parent.revalidate();
											parent.repaint();
										}

										GraphicsHandler.activeWordleInstances.remove(origin); // cleanup
										KaliszTimes.getGraphicsHandler().goBack(); // navigate
							


								}
							);
							});

							*/
						}

						if(lettersInQueue.size() == 5) {
							int result = wordleGame.submitGuess();
							System.out.println("Guess submitted");
							for(char c : lettersInQueue) {
								System.out.println(c);
							}
							if (result == 1) { // guess submitted successfully
								String[] guessResult = wordleGame.getGuessData(); // e.g., ["green", "grey", "yellow", ...]

								for (int i = 0; i < 5; i++) {
									String color = guessResult[i];
									// Apply coloring to your GUI (this depends on how you want to show feedback: background color, etc.)
									// You could store this color in a parallel 2D array like String[][] gridColors;
									// and draw it in your paintComponent() with colored rectangles.
									gridColours[rowNumber][i] = color;
									

									System.out.println(color);
									repaint();
				}
							//Clear queue, go to next row number.
							lettersInQueue.clear();
							//wordleGame.clearGuess();
							rowNumber++;


						if(wordleGame.getWin()) {
							KaliszTimes.popup("Congratulations! " + wordleGame.getWord() + " was the correct word!");
							origin.setFinished();
							origin.repaint();

								// Properly remove panel from parent
							getPanel().setFocusable(false);
							getPanel().setVisible(false);
							

								Container parent = getPanel().getParent();
								if (parent != null) {
									parent.remove(getPanel());
									parent.revalidate();
									parent.repaint();
								}

								GraphicsHandler.activeWordleInstances.remove(origin); // cleanup
								KaliszTimes.getGraphicsHandler().goBack(); // navigate
							


						}
					}

			}else {
					KaliszTimes.popup("Your guess is invalid!");
				}

						
					}

				}
			}
			 
		 });
		 
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(GUIConstants.wordle_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		
		//Set colours in wordle grid
		
		for(int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
			for(int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
				if(gridColours[row][column] != null) {
					String colour = gridColours[row][column];
					
					if(colour.equals("green")) {
						graphics.setColor(Color.green);

					}else if(colour.equals("yellow")) {
						graphics.setColor(new Color(176, 170, 4)); //RGB for gold
					}else if(colour.equals("grey")) {
						graphics.setColor(Color.gray);
					}
					int HORIZONTAL_OFFSET = GUIConstants.scaleX(-200 + (column * 82));
				
					int VERTICAL_OFFSET = GUIConstants.scaleY(-63 + row * 82);

					int refX = GUIConstants.WINDOW_WIDTH / 2 + HORIZONTAL_OFFSET;
					int refY = GUIConstants.WINDOW_HEIGHT / 5 + VERTICAL_OFFSET;

					int squareWidth = 68;
					
					
					graphics.fillRect(refX, refY, GUIConstants.scaleX(squareWidth), GUIConstants.scaleY(squareWidth));

					//Draw border
					graphics.setColor(Color.black);
					graphics.drawRect(refX, refY, GUIConstants.scaleX(squareWidth), GUIConstants.scaleY(squareWidth));
				}
			}
		}


		graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleY(40)));
		//Draw preset strings in set positions that can be modified of the wordle grid
		for(int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
			for(int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
				if(grid[row][column] != null) {
					int HORIZONTAL_OFFSET = GUIConstants.scaleX(-180 + (column * 82));
				
					int VERTICAL_OFFSET = GUIConstants.scaleY(-10 + row * 82);

					int refX = GUIConstants.WINDOW_WIDTH / 2 + HORIZONTAL_OFFSET;
					int refY = GUIConstants.WINDOW_HEIGHT / 5 + VERTICAL_OFFSET;

					if(gridColours[row][column] != null) //Set text to white if there is a colour in that space
						graphics.setColor(Color.white);
					else
						graphics.setColor(Color.black); //otherwise set to black

					graphics.drawString(grid[row][column], refX, refY); 

				}
			}
		}
       
	}
	

    
	private void loadKeyboard() {
		//Create top keyboard buttons
		

		char[] topRow= new char[] {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'};
		for(int i = 0; i < 10; i++) {

			int refX =  466 + (i * 99);
			int refY = 639;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, topRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY, this);
			keyboardButtons.add(button);
		}
		
		char[] middleRow = new char[] {'A', 'S', 'D', 'F', 'G', 'H', 'I', 'K', 'L'};
		for(int i = 0; i < 9; i++) {
			
			int refX = 512 + (i * 100);
			int refY = 772;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, middleRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY, this);
			keyboardButtons.add(button);
		}

		char[] bottomRow = new char[] {'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
		for(int i = 0; i < 7; i++) {
			int refX = 610 + (i * 100);
			int refY = 900;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, bottomRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY, this); 
			keyboardButtons.add(button);
		}
		//Enter and backwards buttons
		int refX = 467;
		int refY = 900;
		KeyboardButton enterButton = new KeyboardButton(GUIConstants.keyboard_enter_button_image, refX, refY, KeyboardClickEvent.ENTER, this); 
			keyboardButtons.add(enterButton);

		int refX2 = 1318;
		int refY2 = 900;
		KeyboardButton backspaceButton = new KeyboardButton(GUIConstants.keyboard_backspace_button_image, refX2, refY2, KeyboardClickEvent.BACKSPACE, this); 
			keyboardButtons.add(backspaceButton);

		//Add all keyboard buttons to panel.
		for(KeyboardButton button : keyboardButtons) {
			add(button);
		}


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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		System.out.println("Hi");
		//This will simulate pressing the buttons, sending events for the panel to read.
		if(Character.isLetter(e.getKeyChar())) {
			EventHandler.fireWordleClickEvent(this, Character.toUpperCase(e.getKeyChar()), KeyboardClickEvent.NORMAL_KEY);
		}else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.ENTER);
		}else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	//Use selection sort to sort the keyboard
	private void sortKeyboard() {
		    // Only sort normal letter buttons (exclude ENTER and BACKSPACE)
    int n = keyboardButtons.size();

    // First, find the portion of the list that contains normal keys
    int normalKeyCount = 0;
    for (KeyboardButton button : keyboardButtons) {
        if (button.getCharacter() != ' ') {
            normalKeyCount++;
        } else {
            break; // Assume special keys are at the end, last two indexes
        }
    }

    // Apply selection sort to the first normalKeyCount elements
    for (int i = 0; i < normalKeyCount - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < normalKeyCount; j++) {
            if (keyboardButtons.get(j).getCharacter() < keyboardButtons.get(minIndex).getCharacter()) {
                minIndex = j;
            }
        }

        // Swap buttons at i and minIndex
        KeyboardButton temp = keyboardButtons.get(i);
        keyboardButtons.set(i, keyboardButtons.get(minIndex));
        keyboardButtons.set(minIndex, temp);
    }
		
	}


	private void modifyKeyboard(Graphics2D graphics) {
		for(int i = 0; i < wordleGame.getOverallGuessData().length; i++) {

			int refX = 0;
			int refY = 0;
			//graphics.fillRect(, i, i, i);
		}
	}
	public WordleGamePanel getPanel() {
		return this;
	}
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
}