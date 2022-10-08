package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.PortableEntity;

public class Enemy extends PortableEntity {

        protected int animationTime = 90;
        protected int direction;

        public Enemy(int xUnit, int yUnit, Image img) {
                super(xUnit, yUnit, img);
        }

        @Override
        public void collide(ActiveEntity entity) {
                if (this.isDead) {
                        return;
                }
                if (entity instanceof Bomber && !((Bomber) entity).isDead) {
                        if (this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
                                entity.isDead = true;
                                BombermanGame.countBomber--;
                        }
                }
        }

        @Override
        public void moveUp() {

        }

        @Override
        public void moveDown() {

        }

        @Override
        public void moveLeft() {

        }

        @Override
        public void moveRight() {

        }
}
