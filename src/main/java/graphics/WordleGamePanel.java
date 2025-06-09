package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.text.Font;
import kalisz.KaliszTimes;

public class WordleGamePanel extends JPanel implements PanelAttributes {
	BufferedImage background, backButtonImage, playButtonImage, keyboardButtonImage;
	ArrayList<KeyboardButton> keyboardButtons = new ArrayList<KeyboardButton>();
	
	public WordleGamePanel() {
		this.setPreferredSize(new Dimension(GraphicsHandler.WINDOW_WIDTH, GraphicsHandler.WINDOW_HEIGHT));
		 loadImages();
		 setLayout(null);
		 
		 
		 Button backButton = new Button(backButtonImage);
		 backButton.setLocation(41, 35);
		 backButton.setSize(74, 59);
		


		 
		 
		 
		 backButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Back button pressed");
				KaliszTimes.getGraphicsHandler().previousPanel();
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {	}
			 
		 });
		 
		 
		 add(backButton);
		 
		 //Loads keyboard
		 loadKeyboard();
		
		 
		repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(background, 0, 0, GraphicsHandler.WINDOW_WIDTH, GraphicsHandler.WINDOW_HEIGHT - SHIFT_UP, this);
	
    
       
	}
	@Override
	public void loadImages() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("resources/WordleGameBackground.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			keyboardButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/keyboard_letter.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	private void loadKeyboard() {
		//Create top keyboard buttons
		for(int topRow = 0; topRow < 10; topRow++) {
			KeyboardButton button = new KeyboardButton(keyboardButtonImage, 'A', 464 + (topRow * 99), 652);
			keyboardButtons.add(button);
		}

		//Add all keyboard buttons to panel.
		for(KeyboardButton button : keyboardButtons) {
			add(button);
		}


	}
}
