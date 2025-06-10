package graphics;
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

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.buttons.BackButton;
import graphics.buttons.Button;
import graphics.buttons.Image;
import graphics.buttons.SpellingBeeButton;
import graphics.utils.PanelAttributes;
import kalisz.KaliszTimes;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class SpellingBeeGamePanel extends JPanel {
	private static final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();
	private ArrayList<Button> letterButtons = new ArrayList<Button>();
	private String currentTextString = "";
	
	public SpellingBeeGamePanel() {
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

		 //Add Delete Button
		int refDeleteX = 337;
		int refDeleteY = 753;
		Button deleteButton = new Button(GUIConstants.spelling_bee_delete_button_image, GUIConstants.scaleX(refDeleteX), GUIConstants.scaleY(refDeleteY));
		deleteButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				EventHandler.fireSpellingBeeClickEvent(this, ' ', KeyboardClickEvent.BACKSPACE);
			}
		});
		add(deleteButton);

		 //Add Shuffle Button
		int refShuffleX = 485;
		int refShuffleY = 753;
		add(new Button(GUIConstants.spelling_bee_shuffle_button_image, GUIConstants.scaleX(refShuffleX), GUIConstants.scaleY(refShuffleY)));

		 //Add Enter Button
		int refEnterX = 586;
		int refEnterY = 753;
		add(new Button(GUIConstants.spelling_bee_enter_button_image, GUIConstants.scaleX(refEnterX), GUIConstants.scaleY(refEnterY)));
		 
		 //Golden letter
		 int refX1 = 455;
		 int refY1 = 465;
		 Button goldenLetter = new SpellingBeeButton(GUIConstants.golden_letter_image, 'A', GUIConstants.scaleX(refX1), GUIConstants.scaleY(refY1));
		
		 letterButtons.add(goldenLetter);
		 add(goldenLetter);


		 int refX2 = 340;
		 int refY2 = 400;
		 Button leftTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'B', GUIConstants.scaleX(refX2), GUIConstants.scaleY(refY2));
		letterButtons.add(leftTopLetter);

		 
		 int refX3 = 575;
		 int refY3 = 400;
		 Button rightTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'C', GUIConstants.scaleX(refX3), GUIConstants.scaleY(refY3));
		
		letterButtons.add(rightTopLetter);
		
		int refX4 = 340;
		int refY4 = 532;
		Button leftBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'D', GUIConstants.scaleX(refX4), GUIConstants.scaleY(refY4));
		letterButtons.add(leftBottomLetter);
		
		int refX5 = 575;
		int refY5 = 532;
		Button rightBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'E', GUIConstants.scaleX(refX5), GUIConstants.scaleY(refY5));
		letterButtons.add(rightBottomLetter);
		

		int refX6 = 455;
		int refY6 = 330;
		Button middleTopLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'F', GUIConstants.scaleX(refX6), GUIConstants.scaleY(refY6));
		letterButtons.add(middleTopLetter);
		 
		int refX7 = 455;
		int refY7 = 598;
		Button middleBottomLetter = new SpellingBeeButton(GUIConstants.normal_letter_image, 'G', GUIConstants.scaleX(refX7), GUIConstants.scaleY(refY7));
		letterButtons.add(middleBottomLetter);
		 

		  addKeyboardListener(new KeyboardClickEventListener() {

			@Override
			public void handleClick(KeyboardClickEvent e) {
				System.out.println("Clicked");
				if(e.getClickType() == KeyboardClickEvent.NORMAL_KEY) {
				currentTextString+=e.getKeyClicked();
				repaint();
				}
				if(e.getClickType() == KeyboardClickEvent.BACKSPACE && currentTextString.length() > 0) {
					currentTextString = currentTextString.substring(0, currentTextString.length() - 1);
					repaint();
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

		graphics.drawString(currentTextString, GUIConstants.scaleX(refX), GUIConstants.scaleY(refY));
		

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
}
