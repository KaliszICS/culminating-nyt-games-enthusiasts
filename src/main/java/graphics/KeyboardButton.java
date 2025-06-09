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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the image from the Button class
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(new Font("Arial", Font.BOLD, 25));

        // Draw character centered
        int textWidth = graphics.getFontMetrics().stringWidth("" + character);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + character, textX, textY);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
