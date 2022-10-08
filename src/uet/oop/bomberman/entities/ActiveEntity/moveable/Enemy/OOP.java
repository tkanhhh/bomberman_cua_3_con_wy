package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.graphics.Sprite;

/**
 *
 */
public class OOP extends Enemy {
    private int randomTimeInterval = 60; // time between finding a path randomly
    public OOP(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 1; // fixed-speed is 1
        this.direction = 3;
        this.scoreValue = 15; // kill 1 OOP gains 15 points

        //CREATE OOP ANIMATION
        createMoveUpAnimation(Sprite.oop_left1, Sprite.oop_left2, Sprite.oop_left3);
        createMoveDownAnimation(Sprite.oop_right1, Sprite.oop_right2, Sprite.oop_right3);
        createMoveLeftAnimation(Sprite.oop_left1, Sprite.oop_left2, Sprite.oop_left3);
        createMoveRightAnimation(Sprite.oop_right1, Sprite.oop_right2, Sprite.oop_right3);
    }

    @Override
    public void update() {

        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Delete
            }
            // Animation when oop dies
            if(animationTime > 60) {
                setImg(Sprite.oop_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animationTime,20).getFxImage());
            }
        } else {

            if(this.getY() % Sprite.SCALED_SIZE == 0 && this.getX() % Sprite.SCALED_SIZE == 0) {
                direction = EasyMode.calculateDirection(this.getEXSmallY(), this.getSmallX(), BombermanGame.map);
            }



            if (direction == 0) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' ') {
                    moveUp();
                }
            }

            if (direction == 1) {
                if (BombermanGame.map[getEXSmallY() + 1][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY() + 1][getEXSmallX()] == ' ') {
                    moveDown();
                }
            }

            if (direction == 2) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' ') {
                    moveLeft();
                }
            }

            if (direction == 3) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX() + 1] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX() + 1] == ' ') {
                    moveRight();
                }
            }

        }
    }
}

