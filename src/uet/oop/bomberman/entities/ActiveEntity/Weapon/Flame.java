package uet.oop.bomberman.entities.ActiveEntity.Weapon;

import javafx.scene.PointLight;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.Portal;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

//Quang - Class Flame để tạo ra đối tượng Flame khi bom nổ
public class Flame extends ActiveEntity {

    private final int bomberID; // bomber voi id nay dat qua bom nay
    public int direction;       // 0-up , 1-down , 2-left , 3-right  Hướng của flame, truyền vào lúc tạo đối tượng
    protected int timeToExplode = 0; // thời gian đếm ngược trước khi xuất hiện các flame, 2s
    public int _timeAfter = 40;        // thời gian xuất hiện các flame - đếm ngược
    public boolean last;               // nếu là Flame cuối cùng ( ở đuôi ) thì true

    /**
     * Tạo 1 đối tượng flame với toạ độ đơn vị x,y, hướng của flame, và có là flame cuối cùng hay không
     * @param x toạ độ đơn vị x - trục ngang
     * @param y toạ độ đơn vị y - trục dọc
     * @param direction hướng ,0-up , 1-down , 2-left , 3-right
     * @param last nếu là flame cuối cùng thì true
     * @param img hình ảnh- không quan trọng, hàm update sẽ tự chọn dựa theo direction và last
     */
    public Flame(int x, int y, int direction, boolean last, Image img, int bomberID) {
        super(x, y, img);
        this.direction = direction;
        this.last = last;
        this.show = false;  // ban đầu chưa hiển thị, sau khi đếm ngược xong mới hiển thị
        this.delete = false;   // chưa xoá, hiển thị xong xuôi mới xoá
        this.bomberID = bomberID;
    }

    @Override
    public void update() {

        if (timeToExplode > 0) { // Chưa nổ
            timeToExplode--;    // Đếm ngược tới lúc nổ
        } else { // Nổ rồi
            show = true;    // hiển thị
            _timeAfter--;   // đếm ngược thời gian hiển thị

            if (_timeAfter < 0) {   // nếu hết thời gian hiển thị thì xoá
                delete = true;     // xoá
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
     * Check Va Chạm giữa Flame và các Entity khác
     * Flame chạm Active Entity nào cũng kill nó luôn, trừ powerup(không xử lí thuộc tính is dead)
     * @param entity đối tượng cần check va chạm với
     */
    @Override
    public void collide(ActiveEntity entity) {
        if(!show) { // flame chưa hiện thì chưa check va chạm
            return;
        }
        if(entity.isDead) {
            return;
        }
        if(entity instanceof Flame) { // không check va chạm với flame khác
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