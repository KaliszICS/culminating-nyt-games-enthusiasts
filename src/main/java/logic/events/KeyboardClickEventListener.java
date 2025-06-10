package logic.events;

import java.util.EventListener;

public interface KeyboardClickEventListener extends EventListener {
    
    void handleClick(KeyboardClickEvent e);

}
