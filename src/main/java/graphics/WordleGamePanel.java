package graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.KeyboardButton;
import graphics.utils.PanelAttributes;
//import javafx.scene.text.Font;
import kalisz.KaliszTimes;
import logic.DictionaryChecker;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class WordleGamePanel extends JPanel implements KeyListener {
	
	ArrayList<KeyboardButton> keyboardButtons = new ArrayList<KeyboardButton>();
	private static final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();

	private String[][] grid = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];

	private ArrayList<Character> lettersGuessed = new ArrayList<Character>();
	private ArrayList<Character> lettersInQueue = new ArrayList<Character>();
	private int rowNumber = 0;
	
	public WordleGamePanel() {
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
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());

		 
		 //Adds digital keyboard listener (ours) that will listen for special KeyboardClickEvent
		 addKeyboardListener(new KeyboardClickEventListener() {

			
			@Override
			public void handleClick(KeyboardClickEvent e) {
				if(rowNumber <= 5) {

					System.out.println(lettersInQueue.size());
					if(lettersInQueue.size() < 5 && e.getClickType() == KeyboardClickEvent.NORMAL_KEY) { //ADD LETTER TO GRID
						grid[rowNumber][lettersInQueue.size()] = "" + e.getKeyClicked(); //Set letter in appropriate grid position to keyboard click.
						repaint();
						lettersInQueue.add(e.getKeyClicked());
					}
					if(lettersInQueue.size() > 0 && e.getClickType() == KeyboardClickEvent.BACKSPACE) {
						grid[rowNumber][lettersInQueue.size() - 1] = null;
						lettersInQueue.remove(lettersInQueue.size() - 1);
						repaint();
					}

					if(lettersInQueue.size() == 5 && e.getClickType() == KeyboardClickEvent.ENTER) {
						String submittedString = "";
						for(char c : lettersInQueue)
							submittedString+=c;
						

						System.out.println(submittedString);
						lettersInQueue.clear();
						rowNumber++;

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
		
		graphics.setFont(new Font("Arial", Font.BOLD, GUIConstants.scaleY(40)));

		//Draw preset strings in set positions that can be modified of the wordle grid
		for(int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
			for(int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
				if(grid[row][column] != null) {
					int HORIZONTAL_OFFSET = GUIConstants.scaleX(-190 + (column * 82));
				
					int VERTICAL_OFFSET = GUIConstants.scaleY(-10 + row * 82);

					int refX = GUIConstants.WINDOW_WIDTH / 2 + HORIZONTAL_OFFSET;
					int refY = GUIConstants.WINDOW_HEIGHT / 5 + VERTICAL_OFFSET;

					
					graphics.drawString(grid[row][column], refX, refY); 

				}
			}
		}
       
	}
	

	//Magic number galore!!!
    
	private void loadKeyboard() {
		//Create top keyboard buttons
		

		char[] topRow= new char[] {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'};
		for(int i = 0; i < 10; i++) {

			int refX =  466 + (i * 99);
			int refY = 639;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, topRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY);
			keyboardButtons.add(button);
		}
		
		char[] middleRow = new char[] {'A', 'S', 'D', 'F', 'G', 'H', 'I', 'K', 'L'};
		for(int i = 0; i < 9; i++) {// i * 99
			
			int refX = 512 + (i * 100);
			int refY = 772;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, middleRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY);
			keyboardButtons.add(button);
		}

		char[] bottomRow = new char[] {'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
		for(int i = 0; i < 7; i++) {// i * 99
			int refX = 610 + (i * 100);
			int refY = 900;
			KeyboardButton button = new KeyboardButton(GUIConstants.keyboardButtonImage, bottomRow[i], refX, refY, KeyboardClickEvent.NORMAL_KEY); 
			keyboardButtons.add(button);
		}
		//Enter and backwards buttons
		int refX = 467;
		int refY = 900;
		KeyboardButton enterButton = new KeyboardButton(GUIConstants.keyboard_enter_button_image, refX, refY, KeyboardClickEvent.ENTER); 
			keyboardButtons.add(enterButton);

		int refX2 = 1318;
		int refY2 = 900;
		KeyboardButton backspaceButton = new KeyboardButton(GUIConstants.keyboard_backspace_button_image, refX2, refY2, KeyboardClickEvent.BACKSPACE); 
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
	public static ArrayList<KeyboardClickEventListener> getListeners() {
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
}
