package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GUIConstants {
    private static final int BASE_WINDOW_WIDTH = 1920;
    private static final int BASE_WINDOW_HEIGHT = 1080;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    private static double scaleX, scaleY;

    
    public static final String gameName = "The Kalisz Times Games";
	public static final String gameVersion = "1.0";

    public static final int DEFAULT_BUTTON_HEIGHT = 100;
    public static final int DEFAULT_BUTTON_WIDTH = 100;

    public static final int WORDLE_NUM_OF_ROWS = 6;
    public static final int WORDLE_NUM_OF_COLUMNS = 5;

    public static BufferedImage connections_game_panel_background, spellingbee_game_panel_background, backButtonImage, playButtonImage, wordleButtonImage, 
    spellingBeeButtonImage, kaliszGamesLogoImage, viewStatsButtonImage, shuffleButtonImage, deselectAllButtonImage, submitButtonImage
    , keyboardButtonImage, keyboard_backspace_button_image, keyboard_enter_button_image, wordle_game_panel_background,
    normal_letter_image, golden_letter_image,
    spelling_bee_delete_button_image,
    spelling_bee_enter_button_image,
    spelling_bee_shuffle_button_image,
    connections_start_background_image,
    wordle_start_background_image,
    title_screen_background_image,
    spelling_bee_start_background_image,
    wordle_win_background_image,
    stats_background_image,
    stats_next_button_image
    ,stats_prev_button_image,
    question_mark_image;


    public GUIConstants() {

        loadImages();
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     // Dimension screenSize = new Dimension(1020, 800);
        WINDOW_WIDTH = (int) screenSize.getWidth();
        WINDOW_HEIGHT = (int) screenSize.getHeight();
        scaleX = screenSize.getWidth() / BASE_WINDOW_WIDTH;
        scaleY = screenSize.getHeight() / BASE_WINDOW_HEIGHT;

       
    }

    public static int scaleX(int refX) {
        return (int) (refX * scaleX);
    }
     public static int scaleY(int refY) {
        return (int) (refY * scaleY);
    }
    public static int scaleFont(int fontSize) { //Vertical scaling preferred.
       double scale = Math.min(scaleX, scaleY); 
        return (int) (fontSize * scale);

    }

    public void loadImages() {
        try {
			connections_game_panel_background = ImageIO.read(getClass().getResourceAsStream("resources/ConnectionsBackground.png"));
            wordle_game_panel_background = ImageIO.read(getClass().getResourceAsStream("resources/WordleGameBackground.png"));
			backButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Back Button.jpg"));
			playButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/Play Button.png"));
			wordleButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/wordle_button.png"));
			spellingBeeButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_button.png"));
			kaliszGamesLogoImage = ImageIO.read(getClass().getResourceAsStream("resources/kalisz_games_logo.png"));
            viewStatsButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/view_stats_button.png"));
            shuffleButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/shuffle_button.png"));
            deselectAllButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/deselect_all_button.png"));
            submitButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/submit_button.png"));

            keyboardButtonImage = ImageIO.read(getClass().getResourceAsStream("resources/keyboard_letter.png"));
			keyboard_backspace_button_image = ImageIO.read(getClass().getResourceAsStream("resources/backspace_button.png"));
			keyboard_enter_button_image = ImageIO.read(getClass().getResourceAsStream("resources/enter_button.png"));
            normal_letter_image = ImageIO.read(getClass().getResourceAsStream("resources/normal_letter.png"));
            golden_letter_image = ImageIO.read(getClass().getResourceAsStream("resources/golden_letter.png"));
            spellingbee_game_panel_background= ImageIO.read(getClass().getResourceAsStream("resources/SpellingBeeGameScreen.png"));

            spelling_bee_delete_button_image= ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_delete_button.png"));
            spelling_bee_enter_button_image= ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_enter_button.png"));
            spelling_bee_shuffle_button_image= ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_shuffle_button.png"));
            connections_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/connections_start_background.png"));
            wordle_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/wordle_start_background.png"));
            title_screen_background_image = ImageIO.read(getClass().getResourceAsStream("resources/title_screen_background.png"));
            spelling_bee_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_start_background.png"));
             wordle_win_background_image = ImageIO.read(getClass().getResourceAsStream("resources/wordle_win_background.png"));
           stats_background_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_background.png"));
             stats_next_button_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_next.png"));
             stats_prev_button_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_prev.png"));
              question_mark_image= ImageIO.read(getClass().getResourceAsStream("resources/question_mark.png"));


		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
