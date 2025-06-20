package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * {@code GUIConstants} provides configuration constants, scaling utilities,
 * and shared resources (such as images) used throughout the GUI application.
 * <p>
 * It dynamically calculates scaling factors based on the user's current screen
 * resolution relative to a base resolution (1920x1080) to enable resolution-independent
 * sizing for UI components.
 * </p>
 * <p>
 * This class also loads and stores all key image assets as {@link BufferedImage}s
 * for reuse in UI elements.
 * </p>
 * 
 * @author @FranklinZhu1
 * @author @elliot-chan-ics4u1-2-2025
 * @author @julie-lin-ics4u1-2-2025
 * 
 */
public class GUIConstants {
    
    /** The base window width for which UI elements are designed */
    private static final int BASE_WINDOW_WIDTH = 1920;
    
    /** The base window height for which UI elements are designed */
    private static final int BASE_WINDOW_HEIGHT = 1080;

    /** Actual window width detected from the user's screen */
    public static int WINDOW_WIDTH;

    /** Actual window height detected from the user's screen */
    public static int WINDOW_HEIGHT;

    /** Horizontal scaling factor relative to base width */
    private static double scaleX;

    /** Vertical scaling factor relative to base height */
    private static double scaleY;

    /** Name of the game shown in window titles */
    public static final String gameName = "The Kalisz Times Games";

    /** Version string of the game */
    public static final String gameVersion = "1.0";

    /** Default button height in pixels before scaling */
    public static final int DEFAULT_BUTTON_HEIGHT = 100;

    /** Default button width in pixels before scaling */
    public static final int DEFAULT_BUTTON_WIDTH = 100;

    /** Number of rows in the Wordle game grid */
    public static final int WORDLE_NUM_OF_ROWS = 6;

    /** Number of columns in the Wordle game grid */
    public static final int WORDLE_NUM_OF_COLUMNS = 5;

    // Image resources loaded once and shared globally

    public static BufferedImage connections_game_panel_background,
            spellingbee_game_panel_background,
            backButtonImage,
            playButtonImage,
            wordleButtonImage,
            spellingBeeButtonImage,
            kaliszGamesLogoImage,
            viewStatsButtonImage,
            shuffleButtonImage,
            deselectAllButtonImage,
            submitButtonImage,
            keyboardButtonImage,
            keyboard_backspace_button_image,
            keyboard_enter_button_image,
            wordle_game_panel_background,
            normal_letter_image,
            golden_letter_image,
            spelling_bee_delete_button_image,
            spelling_bee_enter_button_image,
            spelling_bee_shuffle_button_image,
            connections_start_background_image,
            wordle_start_background_image,
            title_screen_background_image,
            spelling_bee_start_background_image,
            wordle_win_background_image,
            stats_background_image,
            stats_next_button_image,
            stats_prev_button_image,
            question_mark_image;

    /**
     * Constructor initializes scaling factors based on the user's current screen resolution
     * and loads all necessary image resources.
     * <p>
     * Call this once early in program execution to prepare the UI constants and assets.
     * </p>
     */
    public GUIConstants() {
        loadImages();

       // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // For testing with a fixed size, uncomment below:
         Dimension screenSize = new Dimension(1020, 800);

        WINDOW_WIDTH = (int) screenSize.getWidth();
        WINDOW_HEIGHT = (int) screenSize.getHeight();

        scaleX = screenSize.getWidth() / (double) BASE_WINDOW_WIDTH;
        scaleY = screenSize.getHeight() / (double) BASE_WINDOW_HEIGHT;
    }

    /**
     * Scales a horizontal (X-axis) dimension from the base resolution to the current screen size.
     *
     * @param refX the reference dimension designed for the base width (1920px)
     * @return the scaled horizontal dimension suitable for the current screen
     */
    public static int scaleX(int refX) {
        return (int) (refX * scaleX);
    }

    /**
     * Scales a vertical (Y-axis) dimension from the base resolution to the current screen size.
     *
     * @param refY the reference dimension designed for the base height (1080px)
     * @return the scaled vertical dimension suitable for the current screen
     */
    public static int scaleY(int refY) {
        return (int) (refY * scaleY);
    }

    /**
     * Scales a font size based on the smaller of the horizontal and vertical scale factors.
     * <p>
     * Vertical scaling is preferred to maintain readability and aspect ratio.
     * </p>
     *
     * @param fontSize the font size designed for the base resolution
     * @return the scaled font size for the current screen resolution
     */
    public static int scaleFont(int fontSize) {
        double scale = Math.min(scaleX, scaleY);
        return (int) (fontSize * scale);
    }

    /**
     * Loads all image resources from the "resources" folder relative to this class.
     * <p>
     * Images are stored as static BufferedImage variables for global reuse.
     * </p>
     * <p>
     * Prints stack trace if any image fails to load.
     * </p>
     */
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
            spellingbee_game_panel_background = ImageIO.read(getClass().getResourceAsStream("resources/SpellingBeeGameScreen.png"));
            spelling_bee_delete_button_image = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_delete_button.png"));
            spelling_bee_enter_button_image = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_enter_button.png"));
            spelling_bee_shuffle_button_image = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_shuffle_button.png"));
            connections_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/connections_start_background.png"));
            wordle_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/wordle_start_background.png"));
            title_screen_background_image = ImageIO.read(getClass().getResourceAsStream("resources/title_screen_background.png"));
            spelling_bee_start_background_image = ImageIO.read(getClass().getResourceAsStream("resources/spelling_bee_start_background.png"));
            wordle_win_background_image = ImageIO.read(getClass().getResourceAsStream("resources/wordle_win_background.png"));
            stats_background_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_background.png"));
            stats_next_button_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_next.png"));
            stats_prev_button_image = ImageIO.read(getClass().getResourceAsStream("resources/stats_prev.png"));
            question_mark_image = ImageIO.read(getClass().getResourceAsStream("resources/question_mark.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
