package logic.events;

import graphics.ConnectionsPanel;
import graphics.SpellingBeeGamePanel;
import graphics.WordleGamePanel;

/**
 * Utility class for firing keyboard click events in different game panels.
 * <p>
 * This class centralizes the event firing process for Wordle, Spelling Bee,
 * and Connections game panels. It creates keyboard click events and dispatches
 * them to all registered listeners on the source panel.
 * </p>
 * 
 * <p><b>Note:</b> The {@code clickType} parameter should correspond to
 * predefined constants representing the type of click (e.g., single press,
 * long press, etc.) to avoid magic numbers.</p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 * 
 */
public class EventHandler {

    /**
     * Fires a keyboard click event from a WordleGamePanel.
     * <p>
     * Creates a {@link KeyboardClickEvent} representing a key press on the Wordle keyboard,
     * then notifies all registered listeners for that panel.
     * </p>
     *
     * @param source    The WordleGamePanel where the click originated.
     * @param keyClicked The character key that was clicked.
     * @param clickType  The type of click event (e.g., press, release). Should match defined constants.
     */
    public static void fireWordleClickEvent(WordleGamePanel source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : source.getListeners()) {
            listener.handleClick(event);
        }
    }

    /**
     * Fires a keyboard click event from a SpellingBeeGamePanel.
     * <p>
     * Creates a {@link KeyboardClickEvent} representing a key press on the Spelling Bee keyboard,
     * then notifies all registered listeners for that panel.
     * </p>
     *
     * @param source    The SpellingBeeGamePanel where the click originated.
     * @param keyClicked The character key that was clicked.
     * @param clickType  The type of click event (e.g., press, release). Should match defined constants.
     */
    public static void fireSpellingBeeClickEvent(SpellingBeeGamePanel source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : source.getListeners()) {
            listener.handleClick(event);
        }
    }

    /**
     * Fires a keyboard click event from a ConnectionsPanel. This is the way of tracking submit/deselect/etc events from the buttons.
     * <p>
     * Creates a {@link KeyboardClickEvent} representing a key press on the Connections panel,
     * then notifies all registered listeners for that panel.
     * </p>
     *
     * @param source    The ConnectionsPanel where the click originated.
     * @param keyClicked The character key that was clicked.
     * @param clickType  The type of click event (e.g., press, release). Should match defined constants.
     */
    public static void fireConnectionsClickEvent(ConnectionsPanel source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : source.getListeners()) {
            listener.handleClick(event);
        }
    }
}
