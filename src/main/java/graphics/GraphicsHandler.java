package graphics;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.*;

import graphics.buttons.SpellingBeeEnterButton;
import graphics.buttons.WordleButton;

public class GraphicsHandler {
	
	
	private static JFrame frame = null;
	private static CardLayout layout = null;
	private static JPanel mainPanel = null;
    public static HashMap<WordleButton, WordleGamePanel> activeWordleInstances = new HashMap<>();
    public static HashMap<SpellingBeeEnterButton, SpellingBeeGamePanel> activeSpellingBeeInstances = new HashMap<>();


    private static ConnectionsPanel connectionsPanel;
    public static String currentPanel;
    private static Stack<String> panelHistory = new Stack<>();
   
    public void initiate() {
        frame = new JFrame(GUIConstants.gameName + " " + GUIConstants.gameVersion);
        frame.setSize(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //   frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        mainPanel = new JPanel();
        layout = new CardLayout();
        mainPanel.setLayout(layout);
        
        TitleScreenPanel titlePanel = new TitleScreenPanel();
        
       GameSelectionPanel selectionPanel = new GameSelectionPanel();
        
        connectionsPanel = new ConnectionsPanel();
        
      //  WordleStartPanel wordleStartPanel = new WordleStartPanel();
        
      //  WordleGamePanel wordleGamePanel = new WordleGamePanel();
        
      //  WordleWinPanel wordleWinPanel = new WordleWinPanel();
        
       // SpellingBeeStartPanel sbStartPanel = new SpellingBeeStartPanel();
        
        //SpellingBeeGamePanel sbGamePanel = new  SpellingBeeGamePanel();
        
       // SpellingBeeWinPanel sbWinPanel = new  SpellingBeeWinPanel();
        
        mainPanel.add(titlePanel, "Title Screen Panel");
        
        mainPanel.add(selectionPanel, "Connections Start Panel");
       
        mainPanel.add(connectionsPanel, "Connections Panel");
    

       
      //  mainPanel.add(wordleStartPanel);
      //  mainPanel.add(wordleGamePanel);
      //  mainPanel.add(wordleWinPanel);
      //  mainPanel.add(sbStartPanel);
       // mainPanel.add(sbGamePanel);
      //  mainPanel.add(sbWinPanel);
        
        
        
        
        currentPanel = "Title Screen Panel";
        layout.show(mainPanel, currentPanel);

         frame.add(mainPanel);
         frame.setVisible(true);
    }
    
    public JFrame getFrame() {
    	return frame;
    }
    
    public JPanel getPanel() {
    	return mainPanel;
    }
    public void goBack() {
        if (!panelHistory.isEmpty()) {
        String previousPanel = panelHistory.pop();
         System.out.println("Current Panel: " + currentPanel + " Previous Panel: " + previousPanel);
        layout.show(mainPanel, previousPanel);
        currentPanel = previousPanel;
       
        }
    }

    public void jump(String panelName) {
        if (!panelName.equals(currentPanel)) {
            panelHistory.push(currentPanel); // Save current panel before switching
            layout.show(mainPanel, panelName);
              System.out.println("Jumping from " + currentPanel + " to " + panelName);
            currentPanel = panelName;
           
     }
    }
    public void reloadFrame() {
          frame.revalidate();
          frame.repaint();
    }
    public void addPanel(JPanel newPanel, String name) {
        mainPanel.add(newPanel, name);
        reloadFrame();
        jump(name);
    }
  
    public ConnectionsPanel getConnectionsPanel() {
        return connectionsPanel;
    }
}