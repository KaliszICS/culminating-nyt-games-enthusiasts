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

import graphics.buttons.BackButton;
import graphics.buttons.KeyboardButton;
import graphics.utils.GUIConstants;
import graphics.utils.PanelAttributes;
//import javafx.scene.text.Font;
import kalisz.KaliszTimes;
import logic.DictionaryChecker;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class WordleGamePanel extends JPanel implements PanelAttributes, KeyListener {
	BufferedImage background, backButtonImage, playButtonImage, keyboardButtonImage, keyboard_backspace_button_image, keyboard_enter_button_image;
	ArrayList<KeyboardButton> keyboardButtons = new ArrayList<KeyboardButton>();
	private static final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();

	private String[][] grid = new String[GUIConstants.WORDLE_NUM_OF_ROWS][GUIConstants.WORDLE_NUM_OF_COLUMNS];

	private ArrayList<Character> lettersGuessed = new ArrayList<Character>();
	private ArrayList<Character> lettersInQueue = new ArrayList<Character>();
	private int rowNumber = 0;
	
	public WordleGamePanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 loadImages();
		 
		 setLayout(null);
		
		 
		 
		
		 add(new BackButton(backButtonImage));


		
		 //Loads keyboard
		 loadKeyboard();
		

		 
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
	
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		
		graphics.setFont(new Font("Arial", Font.BOLD, 40));

		//Draw preset strings in set positions that can be modified of the wordle grid
		for(int row = 0; row < GUIConstants.WORDLE_NUM_OF_ROWS; row++) {
			for(int column = 0; column < GUIConstants.WORDLE_NUM_OF_COLUMNS; column++) {
				if(grid[row][column] != null)
					graphics.drawString(grid[row][column], GUIConstants.WINDOW_WIDTH / 2 - 180 + (column * 82), GUIConstants.WINDOW_HEIGHT / 5 - 10 + (row * 82)); 
			}
		}
       
	}
	@Override
	public void loadImages() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("resources/WordleGameBackground.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			keyboardButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/keyboard_letter.png"));
			keyboard_backspace_button_image = ImageIO.read(getClass().getResourceAsStream("resources/backspace_button.png"));
			keyboard_enter_button_image = ImageIO.read(getClass().getResourceAsStream("resources/enter_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Magic number galore!!!
    
	private void loadKeyboard() {
		//Create top keyboard buttons
		char[] topRow= new char[] {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'};
		for(int i = 0; i < 10; i++) {
			KeyboardButton button = new KeyboardButton(keyboardButtonImage, topRow[i], 464 + (i * 99), 652, KeyboardClickEvent.NORMAL_KEY);
			keyboardButtons.add(button);
		}
		
		char[] middleRow = new char[] {'A', 'S', 'D', 'F', 'G', 'H', 'I', 'K', 'L'};
		for(int i = 0; i < 9; i++) {// i * 99
			KeyboardButton button = new KeyboardButton(keyboardButtonImage, middleRow[i], 510 + (i * 100), 785, KeyboardClickEvent.NORMAL_KEY);
			keyboardButtons.add(button);
		}

		char[] bottomRow = new char[] {'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
		for(int i = 0; i < 7; i++) {// i * 99
			KeyboardButton button = new KeyboardButton(keyboardButtonImage, bottomRow[i], 608 + (i * 100), 913, KeyboardClickEvent.NORMAL_KEY); 
			keyboardButtons.add(button);
		}
		//Enter and backwards buttons
		KeyboardButton enterButton = new KeyboardButton(keyboard_enter_button_image, 467, 913, KeyboardClickEvent.ENTER); 
			keyboardButtons.add(enterButton);

		KeyboardButton backspaceButton = new KeyboardButton(keyboard_backspace_button_image, 1318, 913, KeyboardClickEvent.BACKSPACE); 
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
			EventHandler.fireWordleClickEvent(this, e.getKeyChar(), KeyboardClickEvent.NORMAL_KEY);
		}else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.ENTER);
		}else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			EventHandler.fireWordleClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
