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

import kalisz.KaliszTimes;

public class ConnectionsPanel extends JPanel implements PanelAttributes {
	BufferedImage background, backButtonImage, playButtonImage, wordleButtonImage;
	ArrayList<Button> wordleButtons = new ArrayList<Button>();
	
	public ConnectionsPanel() {
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
		 
		 
		 //Add wordle icons and make them all clickable. Store all of these new buttons in a list.
		 //8 tiles

		 for(int i = 0; i < 8; i++) {
			Button wordleButton = new WordleButton(wordleButtonImage);
			wordleButton.setSize(100, 100);


			wordleButton.setLocation(695 + ( (i / 2) < 2 ? i * 153 : (i - 4) * 153), 250 + ( (i / 2) >= 2 ? 153 : 0)); //center of box

			

			wordleButtons.add(wordleButton);
		 }
		 
		
		 
		 //Add the wordle buttons using the stored buttons in the arraylist
		 for(Button b : wordleButtons) {
			add(b);
		 }

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
			background = ImageIO.read(getClass().getResourceAsStream("resources/ConnectionsBackground.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			wordleButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/wordle_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	
}
