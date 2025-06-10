package graphics;
import java.awt.CardLayout;

import javax.swing.*;

public class GraphicsHandler {
	
	
	private static JFrame frame = null;
	private static CardLayout layout = null;
	private static JPanel mainPanel = null;

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
        
        ConnectionsPanel connectionsPanel = new ConnectionsPanel();
        
      //  WordleStartPanel wordleStartPanel = new WordleStartPanel();
        
      //  WordleGamePanel wordleGamePanel = new WordleGamePanel();
        
      //  WordleWinPanel wordleWinPanel = new WordleWinPanel();
        
       // SpellingBeeStartPanel sbStartPanel = new SpellingBeeStartPanel();
        
        SpellingBeeGamePanel sbGamePanel = new  SpellingBeeGamePanel();
        
       // SpellingBeeWinPanel sbWinPanel = new  SpellingBeeWinPanel();
        
       //temp mainPanel.add(titlePanel);
       //temp mainPanel.add(selectionPanel);
       // mainPanel.add(connectionsPanel);
      //  mainPanel.add(wordleStartPanel);
      //  mainPanel.add(wordleGamePanel);
      //  mainPanel.add(wordleWinPanel);
      //  mainPanel.add(sbStartPanel);
        mainPanel.add(sbGamePanel);
      //  mainPanel.add(sbWinPanel);
        
        
        
        
        frame.add(mainPanel);
        
        
        
        frame.setVisible(true);
    }
    
    public JFrame getFrame() {
    	return frame;
    }
    
    public JPanel getPanel() {
    	return mainPanel;
    }
    public void nextPanel() {
    	layout.next(getPanel());
    }
    public void previousPanel() {
    	layout.previous(getPanel());
    }
    public void jumpToRecentlyAdded() {
        layout.last(getPanel());
    }
    public void reloadFrame() {
        frame.add(getPanel());
        frame.revalidate(); //reload frame??
    }
    public void addToPanel(JComponent newItem) {
        getPanel().add(newItem);
    }
}