package graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

public class GraphicsHandler {
    

    public void initiate() throws Exception {
        JFrame frame = new JFrame("NYT Games");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        //Initiate title screen panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        System.out.println("Hi");
        ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("title.png")));

        
        JLabel start = new JLabel(image);
        
        start.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Hello");
                    JOptionPane.showMessageDialog(frame, "Image Clicked!");
                }       
         });
         panel.add(start);
         frame.add(panel);
         frame.pack();
         frame.setVisible(true);
    }
        
}