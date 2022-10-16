package uet.oop.bomberman.entities.ActiveEntity.PowerUp;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.utils.Sound;

/**
 * Powerup - class of items
 * Attribute: boolean active - check if item has already been active
 */
public abstract class PowerUp extends ActiveEntity {

    protected boolean active;
    protected int timeAfter = 10;
    private boolean playedS = false; // play sound

    public PowerUp(int x, int y, Image img) {
        super(x, y, img);
        active = false; // not active at first
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void collide(ActiveEntity entity) {
        //do nothing
    }

    @Override
    public void update() {
        if (active) {
            if (!playedS) {
                new Sound("sound/powerup.wav", "default");
                playedS = true;
            }
            timeAfter--; // countdown after active power up
            if (timeAfter < 0) {
                delete = true; // Delete
            }
        }
    }
}

