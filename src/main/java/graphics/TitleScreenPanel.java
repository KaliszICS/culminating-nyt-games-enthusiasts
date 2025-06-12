package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import kalisz.KaliszTimes;

/**
 * TitleScreenPanel represents the initial screen of the application.
 * Displays the title background and listens for mouse clicks to
 * navigate to the instructions panel.
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * 
 */
public class TitleScreenPanel extends TemplatePanel {
    
    /**
     * Constructs the TitleScreenPanel, sets preferred size,
     * and adds a mouse listener to detect clicks to proceed.
     */
    public TitleScreenPanel() {
        this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));

        // Add mouse listener to handle panel transition on mouse press
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Next panel");
                KaliszTimes.getGraphicsHandler().jump("Instructions Panel");
            }       
        });

        repaint();
    }

    /**
     * Paints the background image of the title screen.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(g);
        
        graphics.drawImage(GUIConstants.title_screen_background_image,
                           0, 0, 
                           GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT, 
                           this);
    }

    /**
     * Requests focus for this panel to enable key events.
     */
    @Override
    public void focus() {
        setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }
}
