package uet.oop.bomberman.entities.ActiveEntity.PowerUp;

import javafx.scene.image.Image;

public class PowerUpSpeed extends PowerUp {

    public int value = 1; // sau khi cho bomber speed thì sẽ xóa

    public PowerUpSpeed(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}