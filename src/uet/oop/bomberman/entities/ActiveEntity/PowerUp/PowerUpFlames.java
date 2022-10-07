package uet.oop.bomberman.entities.ActiveEntity.PowerUp;

import javafx.scene.image.Image;

public class PowerUpFlames extends PowerUp {

    public int value = 1; // cộng 1 power cho bomber

    public PowerUpFlames(int x, int y, Image img) {
        super(x, y, img);
    }

}