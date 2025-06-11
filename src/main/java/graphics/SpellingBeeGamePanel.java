package graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeButton;
import graphics.buttons.SpellingBeeEnterButton;
import graphics.utils.PanelAttributes;

import kalisz.KaliszTimes;
import logic.SpellingBee;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class SpellingBeeGamePanel extends JPanel implements KeyListener, PanelAttributes {
	private final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();
	private ArrayList<Button> letterButtons = new ArrayList<Button>();
	
	public SpellingBee spellingBeeGame;
	SpellingBeeEnterButton origin;
	
	public SpellingBeeGamePanel(SpellingBeeEnterButton origin) {
		this.origin = origin;
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		setLayout(null);
		 System.out.println(origin.getKeyWord());
		spellingBeeGame = new SpellingBee(origin.getKeyWord());
		
 		//Adds physical keyboard compatibility 
		addKeyListener(this);
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
		 
		 add(new BackButton(GUIConstants.backButtonImage));
		 
		  //Add KaliszGames Logo
		 int refKaliszX = 1920 / 2 - 250;
		 int refKaliszY = 10;
		 add(new Image(GUIConstants.kaliszGamesLogoImage, GUIConstants.scaleX(refKaliszX), GUIConstants.scaleY(refKaliszY)));

		 //Add View Stats Button
		 int refStatsX = 1600;
		 int refStatsY = 20;
		 add(new Button(GUIConstants.viewStatsButtonImage, GUIConstants.scaleX(refStatsX), GUIConstants.scaleY(refStatsY)));

		 //Add Delete Button
		int refDeleteX = 337;
		int refDeleteY = 753;
		Button deleteButton = new Button(GUIConstants.spelling_bee_delete_button_image, GUIConstants.scaleX(refDeleteX), GUIConstants.scaleY(refDeleteY));

		deleteButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				EventHandler.fireSpellingBeeClickEvent(getPanel(), ' ', KeyboardClickEvent.BACKSPACE);
			}
		});
		add(deleteButton);

		 //Add Shuffle Button
		int refShuffleX = 485;
		int refShuffleY = 753;

		Button shuffleButton = new Button(GUIConstants.spelling_bee_shuffle_button_image, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY));
		shuffleButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				spellingBeeGame.shuffle();
				for (int i = 0; i < 6; i++) {
					SpellingBeeButton b = (SpellingBeeButton) letterButtons.get(i + 1); // skip golden letter at index 0
					b.setLetter(spellingBeeGame.getLetters().get(i));
			}


				revalidate();
				repaint();

				KaliszTimes.getGraphicsHandler().getFrame().revalidate();
				KaliszTimes.getGraphicsHandler().getFrame().repaint();
				
				KaliszTimes.popup("Successfully shuffled letters!");
			}
		});
		add(shuffleButton);

		 //Add Enter Button
		int refEnterX = 586;
		int refEnterY = 753;
		Button submitButton = new Button(GUIConstants.spelling_bee_enter_button_image, GUIConstants.scaleX(refEnterX), GUIConstants.scaleY(refEnterY));
		
		submitButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				EventHandler.fireSpellingBeeClickEvent(getPanel(), ' ', KeyboardClickEvent.ENTER);
			}
		});
		add(submitButton);
		 System.out.println("SIZE " + spellingBeeGame.getLetters().size() + " " + spellingBeeGame.getKeyword());
		 //Golden letter
		 int refX1 = 455;
		 int refY1 = 465;
		 Button goldenLetter = new SpellingBeeButton(GUIConstants.golden_letter_image, spellingBeeGame.getGoldenLetter(), GUIConstants.scaleX(refX1), GUIConstants.scaleY(refY1), getPanel());
		
		 letterButtons.add(goldenLetter);
		 //add(goldenLetter);


		 int refX2 = 340;
		 int refY2 = 400;
		 Button leftTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(1), GUIConstants.scaleX(refX2), GUIConstants.scaleY(refY2), getPanel());
		letterButtons.add(leftTopLetter);

		 
		 int refX3 = 575;
		 int refY3 = 400;
		 Button rightTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(2), GUIConstants.scaleX(refX3), GUIConstants.scaleY(refY3), getPanel());
		
		letterButtons.add(rightTopLetter);
		
		int refX4 = 340;
		int refY4 = 532;
		Button leftBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(3), GUIConstants.scaleX(refX4), GUIConstants.scaleY(refY4), getPanel());
		letterButtons.add(leftBottomLetter);
		
		int refX5 = 575;
		int refY5 = 532;
		Button rightBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(4), GUIConstants.scaleX(refX5), GUIConstants.scaleY(refY5), getPanel());
		letterButtons.add(rightBottomLetter);
		

		int refX6 = 455;
		int refY6 = 330;
		Button middleTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(5), GUIConstants.scaleX(refX6), GUIConstants.scaleY(refY6), getPanel());
		letterButtons.add(middleTopLetter);
		 
		int refX7 = 455;
		int refY7 = 598;
		Button middleBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, spellingBeeGame.getLetters().get(6), GUIConstants.scaleX(refX7), GUIConstants.scaleY(refY7), getPanel());
		letterButtons.add(middleBottomLetter);
		 

		  addKeyboardListener(new KeyboardClickEventListener() {

			@Override
			public void handleClick(KeyboardClickEvent e) {
				
				if(e.getClickType() == KeyboardClickEvent.NORMAL_KEY) {
					spellingBeeGame.inputLetter(e.getKeyClicked());
					System.out.println("Clicked");

				
				repaint();
				}
				if(e.getClickType() == KeyboardClickEvent.BACKSPACE) {
					spellingBeeGame.deleteLetter();

					repaint();
				}
				if(e.getClickType() == KeyboardClickEvent.ENTER) {
					int code = spellingBeeGame.submitWord();
					repaint();
					switch(code) {
						case -3:
							KaliszTimes.popup("Too short: below 4 characters!");
							
							break;
						case -2:
							KaliszTimes.popup("Doesn't contain the golden letter!");
							break;

						case -1:
							KaliszTimes.popup("Invalid word!");
							break;

						case 0:
							KaliszTimes.popup("Already guessed this word!");
							break;
					}
					if(spellingBeeGame.getWin()) {
							KaliszTimes.popup("Congratulations! " + spellingBeeGame.getKeyword() + " was the correct word!");
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

								GraphicsHandler.activeSpellingBeeInstances.remove(origin); // cleanup
								KaliszTimes.getGraphicsHandler().goBack(); // navigate
					}
				

				}

			}
		  });


		  //Initiate buttons

		  for(Button b : letterButtons) {
			add(b);
		  }


		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(GUIConstants.spellingbee_game_panel_background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		graphics.setFont(new Font("Arial", Font.BOLD, 30));

		int refX = 1920 / 4 - 150;
		int refY = 200;

		//Draw string character by character in order to support custom colours.
		int offsetX = 0;

		char[] lettersOfWord = spellingBeeGame.getCurrentWord().toCharArray();
		FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());

		for (int i = 0; i < lettersOfWord.length; i++) {
			char currentChar = lettersOfWord[i];
			int charWidth = metrics.charWidth(currentChar); // precise width for this char

			// Set color
			if (currentChar == spellingBeeGame.getGoldenLetter())
				graphics.setColor(new Color(176, 170, 4)); // gold
			else
				graphics.setColor(Color.BLACK);

			// Draw the character
			graphics.drawString(String.valueOf(currentChar),
				GUIConstants.scaleX(refX + offsetX),
				GUIConstants.scaleY(refY));

			// Increment offset by width of current char
			offsetX += charWidth;
		}


		//Draws word list
		graphics.setColor(Color.black);
		for(int i = 0; i < spellingBeeGame.getWordsFound().size(); i++) {
			int VERTICAL_OFFSET = 100 * i;
			int refWordListX = 1000;
			int refWordListY = 250;
			if(spellingBeeGame.getWordsFound().get(i).equals(spellingBeeGame.getKeyword()))
				graphics.setColor(new Color(176, 170, 4)); //gold
			else
				graphics.setColor(Color.BLACK);

			graphics.drawString(spellingBeeGame.getWordsFound().get(i), GUIConstants.scaleX(refWordListX), GUIConstants.scaleY(refWordListY + VERTICAL_OFFSET));
		}
		//Draws number of words found
		graphics.setFont(new Font("Arial", Font.BOLD, 20));
		int refNumOfWordsX = 1150;
		int refNumOfWordsY = 210;
		graphics.drawString("" + spellingBeeGame.getWordsFound().size(), GUIConstants.scaleX(refNumOfWordsX), GUIConstants.scaleY(refNumOfWordsY));
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
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		//This will simulate pressing the buttons, sending events for the panel to read.
		
		if(Character.isLetter(e.getKeyChar())) {
			EventHandler.fireSpellingBeeClickEvent(this, Character.toUpperCase(e.getKeyChar()), KeyboardClickEvent.NORMAL_KEY);
		}else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			EventHandler.fireSpellingBeeClickEvent(this, ' ', KeyboardClickEvent.ENTER);
		}else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			EventHandler.fireSpellingBeeClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}

	public SpellingBeeGamePanel getPanel() {
		return this;
	}
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
}
