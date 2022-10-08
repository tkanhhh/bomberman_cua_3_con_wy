package uet.oop.bomberman.entities.ActiveEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends ActiveEntity {

    private int animationBetween = 40;
    private int animationTime = 40;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        this.scoreValue = 5;
    }

    @Override
    public void collide(ActiveEntity entity) {

    }

    @Override
    public void update() {
        if (isDead) {
            animationTime--; // countdown after bomb explode
            if (animationTime < 0) {
                BombermanGame.map[getSmallY()][getSmallX()] = ' ';
                delete = true; // Delete
            }
            // Animation brick explode
            setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animationTime, animationBetween).getFxImage());
        }
    }
}

