package uet.oop.bomberman.entities.ActiveEntity.PowerUp;

import javafx.scene.image.Image;

public class PowerUpFlames extends PowerUp {

    public int value = 1; // add 1 length of flame

    public PowerUpFlames(int x, int y, Image img) {
        super(x, y, img);
    }

}