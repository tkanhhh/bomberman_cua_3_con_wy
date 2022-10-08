package uet.oop.bomberman.utils;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;

public class KeyHandle {
    static HashSet<String> keysList = new HashSet<String>();
    public boolean up = false, down = false, left = false, right = false;

    public static void keyHandlers(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keysList.add(event.getCode().toString());
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keysList.remove(event.getCode().toString());
            }
        });
    }

    public static HashSet<String> getKeys() {
        return keysList;
    }
}
