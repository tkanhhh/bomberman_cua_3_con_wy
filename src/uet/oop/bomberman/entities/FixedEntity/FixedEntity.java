package uet.oop.bomberman.entities.FixedEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

// Entity can't move
public class FixedEntity extends Entity {
    public FixedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
