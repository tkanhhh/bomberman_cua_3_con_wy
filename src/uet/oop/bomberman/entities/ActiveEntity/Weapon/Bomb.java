package uet.oop.bomberman.entities.ActiveEntity.Weapon;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.Sound;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends ActiveEntity {

    public boolean exploded;            // check if bomb exploded or not
    protected int timeToExplode = 120;  // time before explode - 2s
    protected int timeAfter = 40;      // time after explode
    public int bomberID;
    public int length;
    public boolean added = false;
    private boolean played = false;

    public List<Flame> flameList = new ArrayList<>();


    /**
     * Constructor to create a bomb
     * @param x
     * @param y
     * @param img
     */
    public Bomb(int x, int y, Image img, int bomberID, int length) {
        super(x, y, img);
        this.exploded = false;                  // at first not explode
        this.delete = false;                    // at first not delete
        this.bomberID = bomberID;               // distinguish bomb from different players
        this.length = length;                   // flame length

        BombermanGame.bombmap[this.getSmallY()][getSmallX()] = '@';
        BombermanGame.countbomb.put(bomberID, BombermanGame.countbomb.get(bomberID) + 1);

        // 4 directions of flame list contains flames
        // flame stop if reach wall
        for (int i = 1; i <= length; i++) {
            char whichType = BombermanGame.map[this.getSmallY()][this.getSmallX() + i];
            if(whichType != '#' && whichType != '%') {
                if (i == length || whichType == '*') { // if reach end or wall
                    this.flameList.add(new Flame(this.getSmallX() + i, this.getSmallY(),3,true, Sprite.explosion_vertical.getFxImage(), bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX() + i, this.getSmallY(),3,false, Sprite.explosion_vertical.getFxImage(), bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= length; i++) {
            char whichType = BombermanGame.map[this.getSmallY() + i][this.getSmallX()];
            if(whichType != '#' && whichType != '%') {
                if (i == length || whichType == '*') {
                    this.flameList.add(new Flame(this.getSmallX(),this.getSmallY() + i,1,true,Sprite.explosion_vertical.getFxImage(), bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX(),this.getSmallY() + i,1,false,Sprite.explosion_vertical.getFxImage(), bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= length; i++) {
            char whichType = BombermanGame.map[this.getSmallY()][this.getSmallX() - i];
            if(whichType != '#' && whichType != '%') {
                if (i == length || whichType == '*') {
                    this.flameList.add(new Flame(this.getSmallX() - i,this.getSmallY(),2,true, Sprite.explosion_vertical.getFxImage(), bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX() - i, this.getSmallY(),2,false, Sprite.explosion_vertical.getFxImage(), bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= length; i++) {
            char whichType = BombermanGame.map[this.getSmallY() - i][this.getSmallX()];
            if(whichType != '#' && whichType != '%') {
                if ( i == length || whichType == '*') {
                    flameList.add(new Flame(this.getSmallX(),this.getSmallY() - i,0,true, Sprite.explosion_vertical.getFxImage(), bomberID));
                    break;
                } else
                {
                    flameList.add(new Flame(this.getSmallX(),this.getSmallY() - i,0,false, Sprite.explosion_vertical.getFxImage(), bomberID));
                }
            } else
            {
                break;
            }
        }

    }

    @Override
    public void update() {
        if (timeToExplode > 0) { // not explode yet
            timeToExplode--;    // countdown
            // Animation when not explode yet
            setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeToExplode, animationBetWeen).getFxImage());
        } else { // exploded
            if(!played) {
                new Sound("sound/boom.wav","default") ;
                played = true;
            }

            if(!added) {
                BombermanGame.activeEntities.addAll(this.flameList);
                added = true;
            }
            exploded = true;
            timeAfter--; // countdown
            if (timeAfter < 0) {
                delete = true; // Delete
                BombermanGame.bombmap[getSmallY()][getSmallX()] = ' ';
                BombermanGame.countbomb.put(bomberID, BombermanGame.countbomb.get(bomberID) - 1);
            }
            // Animation when bomb explode
            setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeAfter, animationBetWeen).getFxImage());
        }
    }

    @Override
    public void collide(ActiveEntity entity) {
        if(!exploded) { // unnecessary to check collision if bomb hasn't exploded yet
            return;
        }
        if(entity.isDead) { // unnecessary to check collision if entity is dead
            return;
        }
        if (!entity.isDead && this.getSmallX() == entity.getSmallX() && this.getSmallY() == entity.getSmallY()) {
            entity.isDead = true;
            if (entity instanceof Enemy) {
                BombermanGame.countenemy--;
            }
            if (entity instanceof Bomber) {
                BombermanGame.countBomber--;
            }
            BombermanGame.score.put(this.bomberID, BombermanGame.score.get(bomberID) + entity.scoreValue);
        }
    }
}
