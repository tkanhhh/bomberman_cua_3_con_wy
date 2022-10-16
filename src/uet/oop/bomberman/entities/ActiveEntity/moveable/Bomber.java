package uet.oop.bomberman.entities.ActiveEntity.moveable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.Weapon.Bomb;
import uet.oop.bomberman.entities.ActiveEntity.PowerUp.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.KeyHandle;
import uet.oop.bomberman.utils.Sound;

/**
 * Player
 */
public class Bomber extends PortableEntity {

    public int bomberID = 0;
    public int maxBomb; // số lượng bom tối đa mà bomber đặt được trên map
    public int timeDelayEachBom = 30;
    private int powerFlames;
    private int animationTime = 60;
    private boolean playedS = false;

    //KEY BINDING
    private final String keyUP;
    private final String keyDOWN;
    private final String keyLEFT;
    private final String keyRIGHT;
    private final String putBomb;

    /**
     * Create a bomberman with attributes and button to move
     * @param x
     * @param y
     * @param img
     * @param keyUP String - button move up
     * @param keyDOWN String - button move down
     * @param keyLEFT String - button move left
     * @param keyRIGHT String - button move right
     * @param putBomb String - bomb
     */
    public Bomber(int x, int y, Image img, int bomberid, String keyUP, String keyDOWN, String keyLEFT, String keyRIGHT, String putBomb) {
        super(x, y, img);        // create bomberman with x/y-coordinate
        this.isDead = false;     // not dead at first
        this.maxBomb = 1;        // max bomb can put in map is 1
        this.speed = 3;          // at first speed is 3
        this.powerFlames = 1;    // at first length of flame is 1
        this.bomberID = bomberid;
        this.scoreValue = 50;

        BombermanGame.countbomb.put(bomberid,0);
        BombermanGame.score.put(bomberid,0);

        if (bomberid == 0) {
            //Create bomber animation
            createMoveUpAnimation(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2);
            createMoveDownAnimation(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2);
            createMoveLeftAnimation(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2);
            createMoveRightAnimation(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2);
        } else {
            createMoveUpAnimation(Sprite.player2_up, Sprite.player2_up_1, Sprite.player2_up_2);
            createMoveDownAnimation(Sprite.player2_down, Sprite.player2_down_1, Sprite.player2_down_2);
            createMoveLeftAnimation(Sprite.player2_left, Sprite.player2_left_1, Sprite.player2_left_2);
            createMoveRightAnimation(Sprite.player2_right, Sprite.player2_right_1, Sprite.player2_right_2);
        }

        //BIND KEY
        this.keyUP = keyUP;
        this.keyDOWN = keyDOWN;
        this.keyLEFT = keyLEFT;
        this.keyRIGHT = keyRIGHT;
        this.putBomb = putBomb;
    }

    @Override
    public void moveUp() {
        if (canMove(this.getX()+7,this.getY()-speed, BombermanGame.map) && canMove(this.getX()+ Sprite.SCALED_SIZE-9,this.getY()-speed,BombermanGame.map)) {
            if (BombermanGame.map[(this.getY()-speed)/ Sprite.SCALED_SIZE][(this.getX()+7)/Sprite.SCALED_SIZE] != '%') {
                this.setY(this.getY() - speed);
            }

        }
        this.setImg(Sprite.movingSprite(moveUpAnimation.get(0),moveUpAnimation.get(1),moveUpAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveDown() {
        if (canMove(this.getX()+7,this.getY()+speed+Sprite.SCALED_SIZE,BombermanGame.map) && canMove(this.getX()+Sprite.SCALED_SIZE-9,this.getY()+speed+Sprite.SCALED_SIZE-7,BombermanGame.map)) {
            if (BombermanGame.map[(this.getY()+speed+Sprite.SCALED_SIZE)/ Sprite.SCALED_SIZE][(this.getX()+7)/Sprite.SCALED_SIZE] != '%') {
                this.setY(this.getY() + speed);
            }
        }
        this.setImg(Sprite.movingSprite(moveDownAnimation.get(0),moveDownAnimation.get(1),moveDownAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveLeft() {
        if (canMove(this.getX()-speed,this.getY()+7,BombermanGame.map) && canMove(this.getX()-speed,this.getY()+Sprite.SCALED_SIZE-7,BombermanGame.map)) {
            if (BombermanGame.map[(this.getY()+7)/ Sprite.SCALED_SIZE][(this.getX()-speed)/Sprite.SCALED_SIZE] != '%') {
                this.setX(this.getX() - speed);
            }

        }
        this.setImg (Sprite.movingSprite(moveLeftAnimation.get(0),moveLeftAnimation.get(1),moveLeftAnimation.get(2),this.getX(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveRight() {
        if (canMove(this.getX()+Sprite.SCALED_SIZE-7+speed,this.getY()+7,BombermanGame.map) && canMove(this.getX()+speed+Sprite.SCALED_SIZE-7,this.getY()+Sprite.SCALED_SIZE-9,BombermanGame.map)) {
            if (BombermanGame.map[(this.getY()+7)/ Sprite.SCALED_SIZE][(this.getX()+Sprite.SCALED_SIZE-9+speed)/Sprite.SCALED_SIZE] != '%') {
                this.setX(this.getX() + speed);
            }
        }
        this.setImg(Sprite.movingSprite(moveRightAnimation.get(0),moveRightAnimation.get(1),moveRightAnimation.get(2),this.getX(), animationBetWeen).getFxImage());
    }

    public void upgrade(PowerUp powerUp) {
        if (powerUp instanceof PowerUpFlames) {
            this.powerFlames += ((PowerUpFlames) powerUp).value;
        }
        if (powerUp instanceof PowerUpSpeed) {
            this.speed += ((PowerUpSpeed) powerUp).value;
        }
        if (powerUp instanceof PowerUpMoreBombs) {
            this.maxBomb += ((PowerUpMoreBombs) powerUp).value;
        }
    }

    @Override
    public void update() {
        timeDelayEachBom--;
        if (isDead) {
            animationTime--;
            if (!playedS) {
                new Sound("sound/just_died.wav", "just_died");
                playedS = true;
            }
            if (animationTime < 0) {
                delete = true;
            }
            // Animation when bomber dies
            if (bomberID == 0) {
                setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animationTime, 30).getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.player2_dead1, Sprite.player2_dead2, Sprite.player2_dead3, animationTime, 30).getFxImage());
            }
        } else {
            if (KeyHandle.getKeys().contains(keyUP)) {
                moveUp();
            }
            if (KeyHandle.getKeys().contains(keyDOWN)) {
                moveDown();
            }
            if (KeyHandle.getKeys().contains(keyLEFT)) {
                moveLeft();
            }
            if (KeyHandle.getKeys().contains(keyRIGHT)) {
                moveRight();
            }
            if (KeyHandle.getKeys().contains(putBomb) && BombermanGame.map[this.getSmallY()][getSmallX()] != '@') {
                if (BombermanGame.countbomb.get(bomberID) < maxBomb && timeDelayEachBom <= 0) {
                    new Sound("sound/putboom.wav", "default");
                    timeDelayEachBom = 50;
                    BombermanGame.activeEntities.add(new Bomb(this.getSmallX(), this.getSmallY(), Sprite.bomb.getFxImage(), bomberID,powerFlames));
                    System.gc();
                }
            }
        }
    }

    @Override
    public void collide(ActiveEntity entity) {
        //check if Bomber collide with power up item
        if (entity instanceof PowerUp && !((PowerUp) entity).isActive()) {
            if (this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
                this.upgrade((PowerUp) entity);
                ((PowerUp) entity).setActive(true);
            }
        }
    }
}
