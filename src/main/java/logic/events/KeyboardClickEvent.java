package logic.events;
import java.util.EventObject;

public class KeyboardClickEvent extends EventObject {
    char keyClicked;
    public static final int NORMAL_KEY = 0;
    public static final int ENTER = 1;
    public static final int BACKSPACE = 2;
    public static final int DESELECT_ALL = 3;

    int clickType;


    public KeyboardClickEvent(Object source, char keyClicked, int clickType) {
        super(source);
        this.keyClicked = keyClicked;
        this.clickType = clickType;
    }

    public char getKeyClicked() {
        return this.keyClicked;
    }
    public int getClickType() {
        return this.clickType;
    }
}