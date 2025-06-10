package graphics.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import graphics.GUIConstants;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;
import logic.events.KeyboardClickEventListener;

public class KeyboardButton extends Button {
    BufferedImage image;
    char character;
    int x, y;
    int clickType;
    
    public KeyboardButton(BufferedImage image, char character, int x, int y, int clickType) {
        super(image);
       
        this.x = x;
        this.y = y;
        this.clickType = clickType;

        int HEIGHT_OFFSET = 25;
     

        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y), GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));
        this.character = character;

        

    }
    //Enter / backspace buttons
     public KeyboardButton(BufferedImage image, int x, int y, int clickType) {
        super(image);
        this.x = x;
        this.y = y;
        this.character = ' ';
        this.clickType = clickType;

         int HEIGHT_OFFSET = 25;
         

    
        this.setBounds(GUIConstants.scaleX(x), GUIConstants.scaleY(y), GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight() - HEIGHT_OFFSET));

        

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
    //Sends character as an event

    @Override
	public void mousePressed(MouseEvent e) {
		 EventHandler.fireWordleClickEvent(this, this.character, this.clickType);
	}

    
}
