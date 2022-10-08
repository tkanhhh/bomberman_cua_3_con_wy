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
 * Nhân vật chính bomberman - thế thừa từ tớp MovableEntity
 */
public class Bomber extends PortableEntity {

    public int bomberID =0;
    public int maxBomb; // số lượng bom tối đa mà bomber đặt được trên map
    public int timeDelayEachBom = 30;
    private int powerFlames;
    private int animationTime = 60;
    private boolean wallpass; // đi xuyên tường
    private boolean playedS = false;

    //KEY BINDING
    private final String keyUP;
    private final String keyDOWN;
    private final String keyLEFT;
    private final String keyRIGHT;
    private final String putBomb;

    /**
     * Tạo 1 con bomberman với các thuộc tính và nút muốn gán để di chuyển con bomberman đó
     * @param x x đơn vị
     * @param y y đơn vị
     * @param img hình ảnh
     * @param keyUP String - nút đi lên
     * @param keyDOWN String - nút đi xuống
     * @param keyLEFT String - nút đi trái
     * @param keyRIGHT String - nút đi phải
     * @param putBomb String - nút đặt bom
     */
    public Bomber(int x, int y, Image img, int bomberid, String keyUP, String keyDOWN, String keyLEFT, String keyRIGHT, String putBomb) {
        super(x, y, img);        // tạo đối tượng bomber với toạ độ x,y là toạ độ đơn vị
        this.isDead = false;     // ban đầu đối tượng chưa chết
        this.maxBomb = 1;        // số bom tối đa mang theo được là 1
        this.speed = 2;          // tốc độ di chuyển ban đầu bằng 3
        this.powerFlames = 1;    // mặc định ban đầu bom có độ dài 1
        this.bomberID = bomberid;
        this.wallpass = false;
        this.scoreValue = 50;

        BombermanGame.countbomb.put(bomberid,0);
        BombermanGame.score.put(bomberid,0);

        //Create bomber animation
        createMoveUpAnimation(Sprite.player_up,Sprite.player_up_1,Sprite.player_up_2);
        createMoveDownAnimation(Sprite.player_down,Sprite.player_down_1,Sprite.player_down_2);
        createMoveLeftAnimation(Sprite.player_left,Sprite.player_left_1,Sprite.player_left_2);
        createMoveRightAnimation(Sprite.player_right,Sprite.player_right_1,Sprite.player_right_2);

        //BIND KEY
        this.keyUP = keyUP;
        this.keyDOWN = keyDOWN;
        this.keyLEFT = keyLEFT;
        this.keyRIGHT = keyRIGHT;
        this.putBomb = putBomb;
    }

    @Override
    public void moveUp() {
        if (canMove(this.getX()+3,this.getY()-speed, BombermanGame.map) && canMove(this.getX()+ Sprite.SCALED_SIZE-5,this.getY()-speed,BombermanGame.map) || wallpass) {
            if(BombermanGame.map[(this.getY()-speed)/ Sprite.SCALED_SIZE][(this.getX()+3)/Sprite.SCALED_SIZE] != '%') {
                this.setY(this.getY() - speed);
            }

        }
        this.setImg(Sprite.movingSprite(moveUpAnimation.get(0),moveUpAnimation.get(1),moveUpAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveDown() {
        if (canMove(this.getX()+3,this.getY()+speed+Sprite.SCALED_SIZE,BombermanGame.map) && canMove(this.getX()+Sprite.SCALED_SIZE-5,this.getY()+speed+Sprite.SCALED_SIZE-3,BombermanGame.map) || wallpass ) {
            if(BombermanGame.map[(this.getY()+speed+Sprite.SCALED_SIZE)/ Sprite.SCALED_SIZE][(this.getX()+3)/Sprite.SCALED_SIZE] != '%') {
                this.setY(this.getY() + speed);
            }
        }
        this.setImg(Sprite.movingSprite(moveDownAnimation.get(0),moveDownAnimation.get(1),moveDownAnimation.get(2),this.getY(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveLeft() {
        if (canMove(this.getX()-speed,this.getY()+3,BombermanGame.map) && canMove(this.getX()-speed,this.getY()+Sprite.SCALED_SIZE-3,BombermanGame.map) || wallpass) {
            if(BombermanGame.map[(this.getY()+3)/ Sprite.SCALED_SIZE][(this.getX()-speed)/Sprite.SCALED_SIZE] != '%') {
                this.setX(this.getX() - speed);
            }

        }
        this.setImg(Sprite.movingSprite(moveLeftAnimation.get(0),moveLeftAnimation.get(1),moveLeftAnimation.get(2),this.getX(), animationBetWeen).getFxImage());
    }

    @Override
    public void moveRight() {
        if (canMove(this.getX()+Sprite.SCALED_SIZE-5+speed,this.getY()+3,BombermanGame.map) && canMove(this.getX()+speed+Sprite.SCALED_SIZE-3,this.getY()+Sprite.SCALED_SIZE-5,BombermanGame.map) || wallpass) {
            if(BombermanGame.map[(this.getY()+3)/ Sprite.SCALED_SIZE][(this.getX()+Sprite.SCALED_SIZE-5+speed)/Sprite.SCALED_SIZE] != '%') {
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
                delete = true; // Xoá
            }
            // Animation Bomber chết
            setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animationTime, 30).getFxImage());
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
        //check bomber va chạm với power up
        if (entity instanceof PowerUp && !((PowerUp) entity).isActive()) {
            if (this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
                this.upgrade((PowerUp) entity);
                ((PowerUp) entity).setActive(true);
            }
        }
    }
}
