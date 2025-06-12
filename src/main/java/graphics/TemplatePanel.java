package graphics;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Abstract base panel class that extends JPanel.
 * Provides a custom paintComponent method to be overridden,
 * and enforces subclasses to implement a focus method.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 * 
 */
public abstract class TemplatePanel extends JPanel {

    /**
     * Paints this component.
     * Subclasses should override this method to perform custom painting.
     * 
     * @param g the Graphics context to use for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        // Default empty implementation; override in subclasses
    }

    /**
     * Requests focus for this panel.
     * Subclasses must implement this method to define
     * how the panel gains keyboard focus.
     */
    public abstract void focus();
}
