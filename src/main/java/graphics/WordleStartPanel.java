package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import kalisz.KaliszTimes;

public class WordleStartPanel extends JPanel implements PanelAttributes {
	
		BufferedImage background, backButtonImage, playButtonImage;
		
		public WordleStartPanel() {
			this.setPreferredSize(new Dimension(GraphicsHandler.WINDOW_WIDTH, GraphicsHandler.WINDOW_HEIGHT));
			 loadImages();
			 setLayout(null);
			 
			 
			 Button backButton = new Button(backButtonImage);
			 backButton.setLocation(41, 35);
			 backButton.setSize(74, 59);
			 System.out.println(backButtonImage.getHeight() + backButtonImage.getWidth());
			 backButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Back button pressed");
					KaliszTimes.getHandler().previousPanel();
				}

				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				 
			 });
			 
			 
			 add(backButton);
			 
			 
			 //Play button
			 Button playButton = new Button(playButtonImage);
			 playButton.setLocation(822, 813);
			 playButton.setSize(286, 93);
			 playButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Play button pressed");
					KaliszTimes.getHandler().nextPanel();
				}

				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				 
			 });
			 
			 
			 add(playButton);
			 
			 
			 
			 
			 
			 repaint();
		}
		public void paintComponent(Graphics g) {
			Graphics2D graphics = (Graphics2D) g;
			super.paintComponent(g);
			
			graphics.drawImage(background, 0, 0, GraphicsHandler.WINDOW_WIDTH, GraphicsHandler.WINDOW_HEIGHT, this);
			

		}
		@Override
		public void loadImages() {
			try {
				background = ImageIO.read(getClass().getResourceAsStream("resources/WordleStartScreen.png"));
				backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
				playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
		
}
