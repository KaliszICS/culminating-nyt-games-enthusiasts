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
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;

import kalisz.KaliszTimes;

public class SpellingBeeWinPanel extends TemplatePanel {
	BufferedImage background, backButtonImage, playButtonImage;
	
	public SpellingBeeWinPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
		
		 setLayout(null);
		 
		 
		add(new BackButton(GUIConstants.backButtonImage));
		

		 
		 
		 
		 addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//KaliszTimes.getGraphicsHandler().nextPanel();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			 
		 });
		 
		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		
		
		int SHIFT_UP = 25;
		graphics.drawImage(background, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT - SHIFT_UP, this);
		

	}
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
    
	
}
