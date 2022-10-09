package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.AI.HardMode;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Random;

public class BOSS_UET extends Enemy {

    private int randomTimeInterval = 60;     // thời gian giữa mỗi lần random hướng
    Random random = new Random();
    ArrayList<Integer> arrayList = new ArrayList<>();
    int animation = 0;
    int max = 3;
    int min = 1;

    public BOSS_UET(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 1; // fixed-speed is 1
        this.direction = 3;
        this.scoreValue = 20;
        arrayList.add(0);
        arrayList.add(2);
        arrayList.add(0);
        arrayList.add(0);

        //CREATE BOSS ANIMATION
        createMoveUpAnimation(Sprite.oneal_left1,Sprite.oneal_left2,Sprite.oneal_left3);
        createMoveDownAnimation(Sprite.oneal_right1,Sprite.oneal_right2,Sprite.oneal_right3);
        createMoveLeftAnimation(Sprite.oneal_left1,Sprite.oneal_left2,Sprite.oneal_left3);
        createMoveRightAnimation(Sprite.oneal_right1,Sprite.oneal_right2,Sprite.oneal_right3);
    }

    public void moveUp() {
        this.setY(this.getY() - speed);
        this.setImg(Sprite.movingSprite(moveUpAnimation.get(0),moveUpAnimation.get(1),moveUpAnimation.get(2),animation, animationBetWeen).getFxImage());
    }

    public void moveDown() {
        this.setY(this.getY() + speed);
        this.setImg(Sprite.movingSprite(moveDownAnimation.get(0),moveDownAnimation.get(1),moveDownAnimation.get(2),animation, animationBetWeen).getFxImage());
    }

    public void moveLeft() {
        this.setX(this.getX() - speed);
        this.setImg(Sprite.movingSprite(moveLeftAnimation.get(0),moveLeftAnimation.get(1),moveLeftAnimation.get(2),animation, animationBetWeen).getFxImage());
    }

    public void moveRight() {
        this.setX(this.getX() + speed);
        this.setImg(Sprite.movingSprite(moveRightAnimation.get(0),moveRightAnimation.get(1),moveRightAnimation.get(2),animation, animationBetWeen).getFxImage());
    }

    @Override
    public void update() {
        animation++;
        if(animation > 100) {
            animation=0;
        }
        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Xoá
            }
            // Animation when boss dies
            if(animationTime>60) {
                setImg(Sprite.oneal_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animationTime,20).getFxImage());
            }
        } else {

            if(this.getY()%Sprite.SCALED_SIZE == 0 && this.getX()%Sprite.SCALED_SIZE == 0 && randomTimeInterval< 0) {
                direction = HardMode.BFS(this.getEXSmallY(),this.getSmallX(), BombermanGame.map);
                speed = (int)Math.floor(Math.random()*(max-min+1)+min);
            } else {
                randomTimeInterval--;
            }



            if (direction == 0) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' '){
                    moveUp();
                }
            }

            if (direction == 1) {
                if (BombermanGame.map[getEXSmallY()+1][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()+1][getEXSmallX()] == ' '){
                    moveDown();
                }
            }

            if (direction == 2) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()] == ' '){
                    moveLeft();
                }
            }

            if (direction == 3) {
                if (BombermanGame.map[getEXSmallY()][getEXSmallX()+1] == ' ' && BombermanGame.bombmap[getEXSmallY()][getEXSmallX()+1] == ' '){
                    moveRight();
                }
            }

        }
    }
}
