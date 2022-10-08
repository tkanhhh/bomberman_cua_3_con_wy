package uet.oop.bomberman.entities.ActiveEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class ActiveEntity extends Entity {

    public boolean isDead = false; // not dead at first
    public boolean delete = false; // if true then delete in entity list
    public int animationBetWeen = 20;
    public Integer scoreValue = 0;

    public ActiveEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    public abstract void collide(ActiveEntity entity);
}
