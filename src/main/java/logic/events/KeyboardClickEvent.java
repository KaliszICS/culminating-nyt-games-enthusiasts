package logic.events;

import java.util.EventObject;

/**
 * Represents a keyboard click event fired from various game panels.
 * <p>
 * This event encapsulates information about a key press, including
 * which key was clicked and the type of the click.
 * </p>
 * 
 * <p>Extends {@link java.util.EventObject} to be used in event listener frameworks.</p>
 * 
 * @author Elliot Chan
 */
public class KeyboardClickEvent extends EventObject {

    /**
     * The character key that was clicked.
     */
    private final char keyClicked;

    /**
     * Constant representing a normal key press event.
     */
    public static final int NORMAL_KEY = 0;

    /**
     * Constant representing the Enter key press event.
     */
    public static final int ENTER = 1;

    /**
     * Constant representing the Backspace key press event.
     */
    public static final int BACKSPACE = 2;

    /**
     * Constant representing a "deselect all" action triggered by the keyboard.
     */
    public static final int DESELECT_ALL = 3;

    /**
     * The type of click event.
     * Should be one of the predefined constants: NORMAL_KEY, ENTER, BACKSPACE, DESELECT_ALL.
     */
    private final int clickType;

    /**
     * Constructs a new KeyboardClickEvent.
     *
     * @param source     The object where the event originated (typically a game panel).
     * @param keyClicked The character key that was clicked.
     * @param clickType  The type of the click event. Should match one of the defined constants.
     */
    public KeyboardClickEvent(Object source, char keyClicked, int clickType) {
        super(source);
        this.keyClicked = keyClicked;
        this.clickType = clickType;
    }

    /**
     * Returns the character key that was clicked.
     *
     * @return The key character.
     */
    public char getKeyClicked() {
        return keyClicked;
    }

    /**
     * Returns the type of the click event.
     *
     * @return The click type, one of {@link #NORMAL_KEY}, {@link #ENTER}, {@link #BACKSPACE}, or {@link #DESELECT_ALL}.
     */
    public int getClickType() {
        return clickType;
    }
}
