package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import graphics.buttons.BackButton;
import graphics.buttons.Button;

import kalisz.KaliszTimes;

public class InstructionsPanel extends TemplatePanel {
	
		
		
		public InstructionsPanel() {
			this.setPreferredSize(new Dimension(GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT));
			
			 setLayout(null);
			
			 add(new BackButton(GUIConstants.backButtonImage));
			 
			 
			 
			 int refX = 1920 / 4 + 100;
			 int refY = 200;
			 int refWidth = 800;
			 int refHeight = 600;
			JEditorPane instructionsPane = new JEditorPane();
			instructionsPane.setContentType("text/html");
			instructionsPane.setText(getInstructionsHTML());
			instructionsPane.setEditable(false);
			instructionsPane.setOpaque(true);
			instructionsPane.setBackground(Color.WHITE); // or your game’s panel background
			instructionsPane.setCaretPosition(0); // Start at the top

			// Put it in a scroll pane
			JScrollPane scrollPane = new JScrollPane(instructionsPane);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			scrollPane.setBounds(
				GUIConstants.scaleX(refX),
				GUIConstants.scaleY(refY),
				GUIConstants.scaleX(refWidth),
				GUIConstants.scaleY(refHeight)
			);

			add(scrollPane);

		Button playButton = new Button(GUIConstants.playButtonImage);
		 refX = 842;
		refY = 835;
		 refWidth =286;
		 refHeight = 93;
		 playButton.setBounds(GUIConstants.scaleX(refX), GUIConstants.scaleY(refY), GUIConstants.scaleX(refWidth), GUIConstants.scaleY(refHeight));
		
		 playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Play button pressed");
				KaliszTimes.getGraphicsHandler().jump("Connections Panel");
			}
			 
		 });
		 
		 
		 add(playButton);
			 
			 
		repaint();
		}

private String getInstructionsHTML() {
    return "<html>" +
        "<body style='font-family:sans-serif; font-size:12px; padding:10px;'>" +

        "<h1 style='color:#2C3E50;'>💻 The Kalisz Times Games: <i>Intertwined Edition</i></h1>" +

        "<p>This game reimagines the popular <b>New York Times Games</b> app as a streamlined yet deeply interconnected experience. " +
        "Instead of playing games independently, you'll embark on a <b>progressive puzzle journey</b> through <span style='color:#DAA520'><b>Connections</b></span>, the central hub and ultimate objective.</p>" +

        "<h2 style='color:#34495E;'>🎯 Core Concept</h2>" +
        "<ul>" +
        "<li><b>Connections</b> is the centerpiece of the game.</li>" +
        "<li>To solve it, players must first complete hidden <b>Wordle</b> and <b>Spelling Bee</b> puzzles.</li>" +
        "<li>This “game-within-a-game” format emphasizes mastery and strategy.</li>" +
        "</ul>" +

        "<h2 style='color:#34495E;'>🕹️ Gameplay & Rules</h2>" +

        "<h3>Main Page & Start</h3>" +
        "<ul>" +
        "<li>Launch the app to see the main menu.</li>" +
        "<li>Click the <b>App Logo</b> to enter the <b>Connections</b> board.</li>" +
        "</ul>" +

        "<h3>Connections: The Hub of Challenges</h3>" +
        "<ul>" +
        "<li>See a grid of <b>16 tiles</b>—your main puzzle board.</li>" +
        "<li><b>Twist:</b> Tiles are locked until mini-games are solved.</li>" +
        "<li>8 tiles hide <b>Wordle</b> games; 8 tiles hide <b>Spelling Bee</b> games.</li>" +
        "</ul>" +

        "<h3>Solving Mini-Games</h3>" +

        "<b>Wordle Tiles:</b>" +
        "<ul>" +
        "<li>Click a Wordle tile to enter the classic 5-letter guessing game.</li>" +
        "<li>Guess the word within limited attempts to unlock the tile.</li>" +
        "</ul>" +

        "<b>Spelling Bee Tiles:</b>" +
        "<ul>" +
        "<li>Click to see 7 letters with one central letter.</li>" +
        "<li>Reach the target score or find the pangram to solve.</li>" +
        "</ul>" +

        "<b>Progression:</b>" +
        "<ul>" +
        "<li><b>All 16 mini-games</b> must be completed to unlock the full Connections game.</li>" +
        "</ul>" +

        "<h3>Unlocking Connections</h3>" +
        "<ul>" +
        "<li>Once all tiles are solved, the words are revealed.</li>" +
        "<li>Group the words into <b>4 categories</b> of 4 words each based on themes.</li>" +
        "</ul>" +

        "<h3>🏁 Game Completion & Scoring</h3>" +
        "<ul>" +
        "<li>Complete the Connections board to finish the game.</li>" +
        "<li>Performance is tracked via a leaderboard based on:" +
        "<ul>" +
        "<li><b>⏱️ Total Time</b> to complete all mini-games + Connections</li>" +
        "<li><b>🔤 Wordle Attempts</b></li>" +
        "<li><b>🐝 Spelling Bee Score</b></li>" +
        "<li><b>❌ Mistakes</b> in Connections</li>" +
        "</ul>" +
        "</li>" +
        "</ul>" +

        "<p style='text-align:center; margin-top:20px;'>" +
        "<b><i>Are you ready to connect the words, unlock the puzzle, and rise to the top?</i></b><br>" +
        "🌟 <span style='color:#27AE60; font-weight:bold;'>Good luck!</span> 🌟" +
        "</p>" +

        "</body>" +
        "</html>";
}
		public void paintComponent(Graphics g) {
			Graphics2D graphics = (Graphics2D) g;
			super.paintComponent(g);
			
			graphics.drawImage(GUIConstants.stats_background_image, 0, 0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT, this);
			
			//Label text
			String labelText = "Signed in as: " + KaliszTimes.player.getUsername();
			int refLabelX = 250;
			int refLabelY = 75;

			graphics.setFont(new Font("SansSerif", Font.PLAIN, GUIConstants.scaleFont(20)));
			graphics.setColor(Color.black); // or whatever color you want
			graphics.drawString(labelText, GUIConstants.scaleX(refLabelX), GUIConstants.scaleY(refLabelY));
		}
	
		@Override
	public void focus() {
		setFocusable(true);
    	SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}
}
