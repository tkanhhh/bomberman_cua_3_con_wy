package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.EasyMode;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Kondoria ăn được vật phẩm- nah, bỏ qua
 */
public class Kondoria extends Enemy {
    private int randomTimeInterval = 60;     // thời gian giữa mỗi lần random hướng

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1; // tốc độ cố định là 1
        this.direction = 3;
        this.scoreValue = 15;

        //CREATE BALLOOM ANIMATION
        createMoveUpAnimation(Sprite.kondoria_left1,Sprite.kondoria_left2,Sprite.kondoria_left3);
        createMoveDownAnimation(Sprite.kondoria_right1,Sprite.kondoria_right2,Sprite.kondoria_right3);
        createMoveLeftAnimation(Sprite.kondoria_left1,Sprite.kondoria_left2,Sprite.kondoria_left3);
        createMoveRightAnimation(Sprite.kondoria_right1,Sprite.kondoria_right2,Sprite.kondoria_right3);
    }

    public void moveUP() {
        this.setY(this.getY() - speed);
        this.setImg(Sprite.movingSprite(moveUpAnimation.get(0),moveUpAnimation.get(1),moveUpAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    public void moveDOWN() {
        this.setY(this.getY() + speed);
        this.setImg(Sprite.movingSprite(moveDownAnimation.get(0),moveDownAnimation.get(1),moveDownAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    public void moveLEFT() {
        this.setX(this.getX() - 2);
        this.setImg(Sprite.movingSprite(moveLeftAnimation.get(0),moveLeftAnimation.get(1),moveLeftAnimation.get(2),this.getX(), animationBetWeen).getFxImage());
    }

    public void moveRIGHT() {
        this.setX(this.getX() + 2);
        this.setImg(Sprite.movingSprite(moveRightAnimation.get(0),moveRightAnimation.get(1),moveRightAnimation.get(2),this.getX(), animationBetWeen).getFxImage());
    }

    @Override
    public void update() {

        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Xoá
            }
            // Animation ballom chết
            if(animationTime>60) {
                setImg(Sprite.kondoria_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animationTime,20).getFxImage());
            }
        } else {

            if(this.getY()%Sprite.SCALED_SIZE == 0 && this.getX()%Sprite.SCALED_SIZE == 0) {
                direction = EasyMode.calculateDirection(this.getEXSmallY(), this.getSmallX(), BombermanGame.map);
            }



            if (direction == 0) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' '){
                    moveUP();
                }
            }

            if (direction == 1) {
                if (BombermanGame.map[getEXSmallY()+1][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()+1][getEXSmallX()] == ' '){
                    moveDOWN();
                }
            }

            if (direction == 2) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' '){
                    moveLEFT();
                }
            }

            if (direction == 3) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()+1] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()+1] == ' '){
                    moveRIGHT();
                }
            }

        }
    }
}

