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

import kalisz.KaliszTimes;

public class TitleScreenPanel extends JPanel implements PanelAttributes, MouseListener {
	BufferedImage background;
	
	public TitleScreenPanel() {
		this.setPreferredSize(new Dimension(GraphicsHandler.WINDOW_WIDTH, GraphicsHandler.WINDOW_HEIGHT));
		 loadImages();
		 
		 
		 this.addMouseListener(this);
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
			background = ImageIO.read(getClass().getResourceAsStream("TitleScreenBackground.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Next panel");
		KaliszTimes.getHandler().nextPanel();
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
}
