package graphics;

import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class TemplatePanel extends JPanel {
    public void paintComponent(Graphics g){}
	public abstract void focus();

}