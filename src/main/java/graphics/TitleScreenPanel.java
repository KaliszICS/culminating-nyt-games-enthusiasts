package graphics;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import graphics.buttons.Button;

import java.awt.event.MouseAdapter;
import kalisz.KaliszTimes;

public class TitleScreenPanel extends TemplatePanel {
	
	public TitleScreenPanel() {
		this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));

		int refX = 150;
		 int refY = 100;
		 Button instructionsButton = new Button(GUIConstants.question_mark_image, GUIConstants.scaleX(refX), GUIConstants.scaleY(refY));
		 instructionsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KaliszTimes.getGraphicsHandler().jump("Instructions Panel");
			}
		 });
		 instructionsButton.setSize(200, 200);
		 add(instructionsButton);

		 this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("Next panel");
					KaliszTimes.getGraphicsHandler().jump("Instructions Panel");
					//KaliszTimes.getGraphicsHandler().jump("Connections Start Panel");
	}		
		 });
		 
		 repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		
		graphics.drawImage(GUIConstants.title_screen_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT, this);
		

	}
	@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
}
