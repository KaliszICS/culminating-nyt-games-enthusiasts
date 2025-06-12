package graphics.utils;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Utility class to initialize JavaFX toolkit in a separate thread.
 * This is useful when JavaFX needs to be started before any UI is shown,
 * or when running JavaFX in non-JavaFX thread contexts.
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class JFXInitializer extends Application {

    /**
     * Entry point for JavaFX application. No UI is shown here.
     * 
     * @param primaryStage The primary stage provided by JavaFX runtime.
     */
    @Override
    public void start(Stage primaryStage) {
        // No UI needed, just initialize JavaFX runtime
    }

    /**
     * Initializes JavaFX toolkit by launching the Application
     * on a new thread.
     */
    public static void initJavaFX() {
        Thread javafxInitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(JFXInitializer.class);
            }
        });

        javafxInitThread.setDaemon(true);
        javafxInitThread.start();
    }
}
