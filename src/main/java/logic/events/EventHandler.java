package logic.events;

import graphics.SpellingBeeGamePanel;
import graphics.WordleGamePanel;

public class EventHandler {

public static void fireWordleClickEvent(Object source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : WordleGamePanel.getListeners()) {
            listener.handleClick(event);
        }
    }
 public static void fireSpellingBeeClickEvent(Object source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : SpellingBeeGamePanel.getListeners()) {
            listener.handleClick(event);
        }
    }
}