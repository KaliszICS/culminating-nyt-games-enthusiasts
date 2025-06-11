package logic.events;

import graphics.SpellingBeeGamePanel;
import graphics.WordleGamePanel;

public class EventHandler {

public static void fireWordleClickEvent(WordleGamePanel source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : source.getListeners()) {
            listener.handleClick(event);
        }
    }
 public static void fireSpellingBeeClickEvent(SpellingBeeGamePanel source, char keyClicked, int clickType) {
        KeyboardClickEvent event = new KeyboardClickEvent(source, keyClicked, clickType);
        for (KeyboardClickEventListener listener : source.getListeners()) {
            listener.handleClick(event);
        }
    }
}