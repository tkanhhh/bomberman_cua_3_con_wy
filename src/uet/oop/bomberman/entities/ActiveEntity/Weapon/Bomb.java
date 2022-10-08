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

// Quang - Class định nghĩa lớp bomb
public class Bomb extends ActiveEntity {

    public boolean exploded;            // đã nổ chưa
    protected int timeToExplode = 120;  // thời gian chờ trước khi nổ, 2s
    protected int _timeAfter = 40;      // thời gian sau khi nổ
    public int bomberID;
    public int radius;
    public boolean added = false;
    private boolean played = false;
    private boolean hasAnyOne = true;

    public List<Flame> flameList = new ArrayList<>();


    /**
     * Constructor để tạo 1 quả bom.
     * @param x toạ độ đơn vị
     * @param y toạ độ đơn vị
     * @param img hình ảnh
     */
    public Bomb(int x, int y, Image img, int bomberID, int radius) {
        super(x, y, img);
        this.exploded = false;                  // ban đầu bom chưa nổ
        this.delete = false;                    // ban đầu bom chưa bị xoá
        this.bomberID = bomberID;               // của bomber đặt quả bom này
        this.radius = radius;                   // độ dài flame

        BombermanGame.bombmap[this.getSmallY()][getSmallX()] = '@';
        BombermanGame.countbomb.put(bomberID, BombermanGame.countbomb.get(bomberID) + 1);

        // for 4 hướng để tạo flamelist chứa các flame của vụ nổ
        // flame chỉ dừng tạo tiếp khi gặp tường hoặc brick, flame có thể xuyên qua portal
        for (int i = 1; i <= radius; i++) {
            char whichType = BombermanGame.map[this.getSmallY()][this.getSmallX()+i];
            if(whichType != '#' && whichType != '%') {
                if ( i == radius || whichType == '*') { // nếu là cái cuối hoặc gặp tường
                    this.flameList.add(new Flame(this.getSmallX()+i,this.getSmallY(),3,true,Sprite.explosion_vertical.getFxImage(),bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX()+i,this.getSmallY(),3,false,Sprite.explosion_vertical.getFxImage(),bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= radius; i++) {
            char whichType = BombermanGame.map[this.getSmallY()+i][this.getSmallX()];
            if(whichType != '#' && whichType != '%') {
                if ( i == radius || whichType == '*') {
                    this.flameList.add(new Flame(this.getSmallX(),this.getSmallY()+i,1,true,Sprite.explosion_vertical.getFxImage(),bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX(),this.getSmallY()+i,1,false,Sprite.explosion_vertical.getFxImage(),bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= radius; i++) {
            char whichType = BombermanGame.map[this.getSmallY()][this.getSmallX()-i];
            if(whichType != '#' && whichType != '%') {
                if ( i == radius || whichType == '*') {
                    this.flameList.add(new Flame(this.getSmallX()-i,this.getSmallY(),2,true,Sprite.explosion_vertical.getFxImage(),bomberID));
                    break;
                } else
                {
                    this.flameList.add(new Flame(this.getSmallX()-i,this.getSmallY(),2,false,Sprite.explosion_vertical.getFxImage(),bomberID));
                }
            } else
            {
                break;
            }
        }

        for (int i = 1; i <= radius; i++) {
            char whichType = BombermanGame.map[this.getSmallY()-i][this.getSmallX()];
            if(whichType != '#' && whichType != '%') {
                if ( i == radius || whichType == '*') {
                    flameList.add(new Flame(this.getSmallX(),this.getSmallY()-i,0,true,Sprite.explosion_vertical.getFxImage(),bomberID));
                    break;
                } else
                {
                    flameList.add(new Flame(this.getSmallX(),this.getSmallY()-i,0,false,Sprite.explosion_vertical.getFxImage(),bomberID));
                }
            } else
            {
                break;
            }
        }

    }

    @Override
    public void update() {
        if (timeToExplode > 0) { // Chưa nổ
            timeToExplode--;    // Đếm ngược thời gian bom nổ
            // Animation bom khi chưa nổ
            setImg(Sprite.movingSprite(Sprite.bomb,Sprite.bomb_1,Sprite.bomb_2,timeToExplode, animationBetWeen).getFxImage());
        } else { // Nổ rồi

            if(!played) {
                new Sound("sound/boom.wav","default") ;
                played = true;
            }

            if(!added) {
                BombermanGame.activeEntities.addAll(this.flameList);
                added = true;
            }
            exploded = true;
            _timeAfter--; // Đếm ngược thời gian bom sau khi nổ
            if (_timeAfter < 0) { // Nếu đã hết thời gian sau khi nổ
                delete = true; // Xoá
                BombermanGame.bombmap[getSmallY()][getSmallX()] = ' ';
                BombermanGame.countbomb.put(bomberID,BombermanGame.countbomb.get(bomberID)-1);
                //System.out.println(BombermanGame.countbomb.get(bomberID));
            }
            // Animation bom nổ
            setImg(Sprite.movingSprite(Sprite.bomb_exploded,Sprite.bomb_exploded1,Sprite.bomb_exploded2,_timeAfter, animationBetWeen).getFxImage());
        }
    }

    @Override
    public void collide(ActiveEntity entity) {
        if(!exploded) { // bomb chưa nổ thì ko check va chạm
            return;
        }
        if(entity.isDead) { // entity đó chết rồi thì thôi
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
