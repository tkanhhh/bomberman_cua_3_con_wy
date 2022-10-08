package uet.oop.bomberman.entities.FixedEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

// Các vật thể nằm yên không có gì đặc biệt
public class FixedEntity extends Entity {
    public FixedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
