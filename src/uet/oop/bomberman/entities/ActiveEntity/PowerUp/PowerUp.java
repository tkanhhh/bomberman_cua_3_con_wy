package uet.oop.bomberman.entities.ActiveEntity.PowerUp;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.utils.Sound;

/**
 * Powerup - class chung cho các item
 * thuộc tính :
 * boolean active - check xem item đó đã được active chưa
 */
public abstract class PowerUp extends ActiveEntity {

    protected boolean active;
    protected int timeAfter = 20;
    private boolean playedS = false;

    public PowerUp(int x, int y, Image img) {
        super(x, y, img);
        active = false; // mặc định là chưa được active
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
            timeAfter--; // Đếm ngược thời gian bom sau active Powerup
            if (timeAfter < 0) { // Nếu đã hết thời gian sau khi active
                delete = true; // Xoá
            }
        }
    }
}

