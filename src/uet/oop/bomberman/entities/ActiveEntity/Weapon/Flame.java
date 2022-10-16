package uet.oop.bomberman.entities.ActiveEntity.Weapon;

import javafx.scene.PointLight;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.Portal;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

//Create flame when bomb explode
public class Flame extends ActiveEntity {

    private final int bomberID; // distinguish bomb from different players
    public int direction;       // 0-up , 1-down , 2-left , 3-right  direction of flame
    protected int timeToExplode = 0; // countdown time before show up flame
    public int _timeAfter = 40;        // countdown time flame showing up
    public boolean last;               // check last flame length

    /**
     * @param x
     * @param y
     * @param direction hướng ,0-up , 1-down , 2-left , 3-right
     * @param last last flame length
     * @param img
     */
    public Flame(int x, int y, int direction, boolean last, Image img, int bomberID) {
        super(x, y, img);
        this.direction = direction;
        this.last = last;
        this.show = false;
        this.delete = false;
        this.bomberID = bomberID;
    }

    @Override
    public void update() {

        if (timeToExplode > 0) { // not explode
            timeToExplode--;    // countdown to explode
        } else { // exploded
            show = true;    // show
            _timeAfter--;   // countdown

            if (_timeAfter < 0) {
                delete = true;
            }

            if (direction == 0 || direction == 1 && !last) { // vertical - not last
                setImg(Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _timeAfter, animationBetWeen).getFxImage());
            }
            if (direction == 0 && last) { // vertical - up - last
                setImg(Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _timeAfter, animationBetWeen).getFxImage());
            }
            if (direction == 1 && last) { // vertical - down - last
                setImg(Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, _timeAfter, animationBetWeen).getFxImage());
            }

            if (direction == 2 || direction == 3 && !last) { // horizontal - not last
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _timeAfter, animationBetWeen).getFxImage());
            }
            if (direction == 2 && last) { // horizontal - left - last
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, _timeAfter, animationBetWeen).getFxImage());
            }
            if (direction == 3 && last) { // horizontal - right - last
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _timeAfter, animationBetWeen).getFxImage());
            }
        }
    }

    /**
     * Check collision between flame and other entities
     * Any active entity collide with flame must dead, except for power up
     * @param entity
     */
    @Override
    public void collide(ActiveEntity entity) {
        if(!show) { //unnecessary to check
            return;
        }
        if(entity.isDead) {
            return;
        }
        if(entity instanceof Flame) { // unnecessary to check
            return;
        }
        if (entity instanceof Bomb && this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
            ((Bomb) entity).timeToExplode = 0;
            return;
        }
        if (!entity.isDead && this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
            entity.isDead = true;
            if(entity instanceof Enemy) {
                BombermanGame.countenemy--;
            }
            if(entity instanceof Bomber) {
                BombermanGame.countBomber--;
            }
            BombermanGame.score.put(this.bomberID,BombermanGame.score.get(bomberID)+entity.scoreValue);
        }
    }
}