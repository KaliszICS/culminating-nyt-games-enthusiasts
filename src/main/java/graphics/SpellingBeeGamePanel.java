package graphics;
import java.awt.Dimension;
import java.awt.Font;
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
import graphics.buttons.SpellingBeeButton;
import graphics.utils.GUIConstants;
import graphics.utils.PanelAttributes;
import kalisz.KaliszTimes;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class SpellingBeeGamePanel extends JPanel implements PanelAttributes {
	BufferedImage background, backButtonImage, playButtonImage, normalLetterImage, goldenLetterImage;
	private static final ArrayList<KeyboardClickEventListener> listeners = new ArrayList<KeyboardClickEventListener>();
	private ArrayList<Button> letterButtons = new ArrayList<Button>();
	private String currentTextString = "";
	
	public SpellingBeeGamePanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		 loadImages();
		 setLayout(null);
		 
		 
		 
		 add(new BackButton(backButtonImage));
		 
		 
		 
		 //Golden letter
		 Button goldenLetter = new SpellingBeeButton(goldenLetterImage, 'A');
		 goldenLetter.setLocation(455, 465);
		 letterButtons.add(goldenLetter);
		 add(goldenLetter);


		 Button leftTopLetter = new SpellingBeeButton(normalLetterImage, 'B');
		 leftTopLetter.setLocation(340, 400);
		  letterButtons.add(leftTopLetter);

		 
		Button rightTopLetter = new SpellingBeeButton(normalLetterImage, 'C');
		 rightTopLetter.setLocation(575, 400);
		  letterButtons.add(rightTopLetter);
		

		  Button leftBottomLetter = new SpellingBeeButton(normalLetterImage, 'D');
		 leftBottomLetter.setLocation(340, 532);
		  letterButtons.add(leftBottomLetter);
		
		 
		Button rightBottomLetter = new SpellingBeeButton(normalLetterImage, 'E');
		 rightBottomLetter.setLocation(575, 532);
		  letterButtons.add(rightBottomLetter);
		

		Button middleTopLetter = new SpellingBeeButton(normalLetterImage, 'F');
		 middleTopLetter.setLocation(455, 330);
		 letterButtons.add(middleTopLetter);
		 
		Button middleBottomLetter = new SpellingBeeButton(normalLetterImage, 'G');
		 middleBottomLetter.setLocation(455, 598);
		letterButtons.add(middleBottomLetter);
		 

		  addKeyboardListener(new KeyboardClickEventListener() {

			@Override
			public void handleClick(KeyboardClickEvent e) {
				System.out.println("Clicked");
				currentTextString+=e.getKeyClicked();
				repaint();
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
		graphics.drawImage(background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		graphics.setFont(new Font("Arial", Font.BOLD, 30));
		graphics.drawString(currentTextString, 455, 200);
		

	}
	@Override
	public void loadImages() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("resources/SpellingBeeGameScreen.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			normalLetterImage = ImageIO.read(getClass().getResourceAsStream("resources/normal_letter.png"));
			goldenLetterImage = ImageIO.read(getClass().getResourceAsStream("resources/golden_letter.png"));
		} catch (IOException e) {
			e.printStackTrace();
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
}
