package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.PortableEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends PortableEntity {

        protected int animationTime = 90;
        int animation = 0;
        protected int direction = 3;

        public Enemy(int x, int y, Image img) {
                super(x, y, img);
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
                this.setY(this.getY() - speed);
                this.setImg(Sprite.movingSprite(moveUpAnimation.get(0), moveUpAnimation.get(1), moveUpAnimation.get(2), animation, animationBetWeen).getFxImage());
        }

        @Override
        public void moveDown() {
                this.setY(this.getY() + speed);
                this.setImg(Sprite.movingSprite(moveDownAnimation.get(0), moveDownAnimation.get(1), moveDownAnimation.get(2), animation, animationBetWeen).getFxImage());
        }

        @Override
        public void moveLeft() {
                this.setX(this.getX() - speed);
                this.setImg(Sprite.movingSprite(moveLeftAnimation.get(0), moveLeftAnimation.get(1), moveLeftAnimation.get(2), animation, animationBetWeen).getFxImage());
        }

        public void moveRight() {
                this.setX(this.getX() + speed);
                this.setImg(Sprite.movingSprite(moveRightAnimation.get(0), moveRightAnimation.get(1), moveRightAnimation.get(2), animation, animationBetWeen).getFxImage());
        }
}
