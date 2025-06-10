package graphics.buttons;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GUIConstants;
import logic.events.EventHandler;
import logic.events.KeyboardClickEvent;



public class SpellingBeeButton extends Button {
    char letter;
    public SpellingBeeButton(BufferedImage image, char letter, int x, int y) {
        super(image);
        this.letter = letter;
        this.setBounds(x, y, GUIConstants.scaleX(image.getWidth()), GUIConstants.scaleY(image.getHeight()));
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse clicked");
		EventHandler.fireSpellingBeeClickEvent(this, letter, KeyboardClickEvent.NORMAL_KEY);
		
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the image from the Button class
        Graphics2D graphics = (Graphics2D) g;
        graphics.setFont(new Font("Arial", Font.BOLD, 25));

        // Draw character centered
        int textWidth = graphics.getFontMetrics().stringWidth("" + letter);
        int textHeight = graphics.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        graphics.drawString("" + letter, textX, textY);
    }

    public char getLetter() {
        return this.letter;
    }
}
