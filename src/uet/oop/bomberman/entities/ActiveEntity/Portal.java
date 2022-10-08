package uet.oop.bomberman.entities.ActiveEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.Entity;

public class Portal extends ActiveEntity {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void collide(ActiveEntity entity) {
        if (entity instanceof Bomber) {
            if (entity.isDead) {
                return;
            }
            if (this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
                BombermanGame.portalCheck = true;
            }
            if(this.getSmallX() != entity.getSmallX() || this.getSmallY() != entity.getSmallY()){
                BombermanGame.portalCheck = false;
            }
        }
    }

    @Override
    public void update() {

    }
}
