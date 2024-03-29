package uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.AI.HardMode;
import uet.oop.bomberman.AI.Position;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class BOSS_UET extends Enemy {

    private int randomTimeInterval = 60;     // time between 2 random
    ArrayList<Integer> arrayList = new ArrayList<>();
    int animation = 0;
    int max = 3;
    int min = 1;

    private int x_dead = 15;
    private int y_dead = 7;
    Position path;

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
        createMoveUpAnimation(Sprite.boss_left1,Sprite.boss_left2,Sprite.boss_left3);
        createMoveDownAnimation(Sprite.boss_right1,Sprite.boss_right2,Sprite.boss_right3);
        createMoveLeftAnimation(Sprite.boss_left1,Sprite.boss_left2,Sprite.boss_left3);
        createMoveRightAnimation(Sprite.boss_right1,Sprite.boss_right2,Sprite.boss_right3);
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

    public int getX_dead() {
        if (isDead) {
            x_dead = (this.getX() + (Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
        }
        return x_dead;
    }

    public int getY_dead() {
        if (isDead) {
            y_dead = (this.getY() + (Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
        }
        return y_dead;
    }

    @Override
    public void update() {
        animation++;
        if (animation > 100) {
            animation=0;
        }

        if (isDead) {
            animationTime--;
            if (animationTime < 0) {
                delete = true; // Xoá
            }
            // Animation when boss dies
            if (animationTime > 60) {
                setImg(Sprite.boss_dead.getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,animationTime,20).getFxImage());
            }
        } else {

            if(this.getY()%Sprite.SCALED_SIZE == 0 && this.getX()%Sprite.SCALED_SIZE == 0 && randomTimeInterval< 0) {
                path = HardMode.findClosest(this);
                direction = HardMode.Moving(this, path, BombermanGame.map, BombermanGame.bombmap);
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
