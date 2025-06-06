package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    BufferedImage background;
    
    
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("logo.jpg");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
    public void loadImages() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("resources/title.png"));
			//title = ImageIO.read(getClass().getResourceAsStream("Title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
