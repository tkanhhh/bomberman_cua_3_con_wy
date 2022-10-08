package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

/**
 * DSA extends class PortableEntity, add an attributes - randomTimeInterval
 */
public class DSA extends Enemy {

    private int randomTimeInterval = 30; // time between finding a path randomly
    private int animationTime = 90;
    int animation = 0;
    Random random = new Random();

    public DSA(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 1; // fixed speed is 1
        this.direction = 3;
        this.scoreValue = 10; // kill 1 DSA gain 10 points

        //CREATE DSA ANIMATION
        createMoveUpAnimation(Sprite.dsa_left1, Sprite.dsa_left2, Sprite.dsa_left3);
        createMoveDownAnimation(Sprite.dsa_right1, Sprite.dsa_right2, Sprite.dsa_right3);
        createMoveLeftAnimation(Sprite.dsa_left1, Sprite.dsa_left2, Sprite.dsa_left3);
        createMoveRightAnimation(Sprite.dsa_right1, Sprite.dsa_right2, Sprite.dsa_right3);
    }

    @Override
    public void update() {
        animation++;
        if (animation > 100) {
            animation=0;
        }
        speed = random.nextInt(2);
        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Delete
            }
            // Animation when DSA dies
            if(animationTime > 60) {
                setImg(Sprite.dsa_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animationTime,20).getFxImage());
            }

        } else {

            if
            (this.getY() % Sprite.SCALED_SIZE == 0 && this.getX() % Sprite.SCALED_SIZE == 0 && randomTimeInterval < 0) {
                direction = EasyMode.calculateDirection(this.getEXSmallY(), this.getSmallX(), BombermanGame.map);
                randomTimeInterval = 30;
            } else {
                randomTimeInterval--;
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