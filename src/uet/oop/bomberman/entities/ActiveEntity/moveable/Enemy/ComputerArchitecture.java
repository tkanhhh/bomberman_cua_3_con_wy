package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.graphics.Sprite;

/**
 * ComputerArchitecture is as hard as DSA
 */
public class ComputerArchitecture extends Enemy {
    private int randomTimeInterval = 60;     // thời gian giữa mỗi lần random hướng

    public ComputerArchitecture(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1; // fixed speed is 1
        this.direction = 3;
        this.scoreValue = 15; // kill 1 CA gains 15 points

        //CREATE CA ANIMATION
        createMoveUpAnimation(Sprite.ca_left1, Sprite.ca_left2, Sprite.ca_left3);
        createMoveDownAnimation(Sprite.ca_right1, Sprite.ca_right2, Sprite.ca_right3);
        createMoveLeftAnimation(Sprite.ca_left1,Sprite.ca_left2, Sprite.ca_left3);
        createMoveRightAnimation(Sprite.ca_right1, Sprite.ca_right2, Sprite.ca_right3);
    }

    public void moveUP() {
        this.setY(this.getY() - 2);
        this.setImg(Sprite.movingSprite(moveUpAnimation.get(0), moveUpAnimation.get(1), moveUpAnimation.get(2), this.getY(), animationBetWeen).getFxImage());
    }

    public void moveDOWN() {
        this.setY(this.getY() + 2);
        this.setImg(Sprite.movingSprite(moveDownAnimation.get(0), moveDownAnimation.get(1), moveDownAnimation.get(2), this.getY(), animationBetWeen).getFxImage());
    }

    public void moveLEFT() {
        this.setX(this.getX() - speed);
        this.setImg(Sprite.movingSprite(moveLeftAnimation.get(0), moveLeftAnimation.get(1), moveLeftAnimation.get(2), this.getX(), animationBetWeen).getFxImage());
    }

    public void moveRIGHT() {
        this.setX(this.getX() + speed);
        this.setImg(Sprite.movingSprite(moveRightAnimation.get(0), moveRightAnimation.get(1), moveRightAnimation.get(2), this.getX(), animationBetWeen).getFxImage());
    }

    @Override
    public void update() {

        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Xoá
            }
            // Animation when CA dies
            if (animationTime > 60) {
                setImg(Sprite.ca_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animationTime,20).getFxImage());
            }
        } else {

            if (this.getY() % Sprite.SCALED_SIZE == 0 && this.getX() % Sprite.SCALED_SIZE == 0) {
                direction = EasyMode.calculateDirection(this.getEXSmallY(), this.getSmallX(), BombermanGame.map);
            }

            if (direction == 0) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' ' ) {
                    moveUP();
                }
            }

            if (direction == 1) {
                if (BombermanGame.map[getEXSmallY()+1][getEXSmallX()] == ' '&& BombermanGame.bombmap[getEXSmallY()+1][getEXSmallX()] == ' ' ){
                    moveDOWN();
                }
            }

            if (direction == 2) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' ' ){
                    moveLEFT();
                }
            }

            if (direction == 3) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()+1] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()+1] == ' ' ){
                    moveRIGHT();
                }
            }

        }
    }
}

