package graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class KeyboardButton extends Button {
    BufferedImage image;
    char character;
    int x, y;
    
    public KeyboardButton(BufferedImage image, char character, int x, int y) {
        super(image);
        this.setSize(100, 118);
        this.x = x;
        this.y = y;

        this.setLocation(x, y);
        this.character = character;

        

    }
    @Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		graphics.setFont(new Font("Arial", Font.BOLD, 20));
		graphics.drawString("" + character, 464, 652);
    
       
	}
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
