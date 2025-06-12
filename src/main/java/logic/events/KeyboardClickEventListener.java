package logic.events;

import java.util.EventListener;

/**
 * Listener interface for receiving keyboard click events.
 * <p>
 * Classes interested in processing {@link KeyboardClickEvent} should implement this interface.
 * The {@code handleClick} method will be called when a keyboard click event occurs.
 * </p>
 * 
 * @author Elliot Chan
 */
public interface KeyboardClickEventListener extends EventListener {
    
    /**
     * Invoked when a keyboard click event is fired.
     *
     * @param e the {@link KeyboardClickEvent} containing details about the key pressed and event type
     */
    void handleClick(KeyboardClickEvent e);

}
