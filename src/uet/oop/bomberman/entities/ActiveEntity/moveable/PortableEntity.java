package uet.oop.bomberman.entities.ActiveEntity.moveable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public abstract class PortableEntity extends ActiveEntity {

    public int speed; //speed of entities

    //create animation of entities going up, down, left, right
    protected List<Sprite> moveUpAnimation = new ArrayList<>();

    protected List<Sprite> moveDownAnimation = new ArrayList<>();

    protected List<Sprite> moveLeftAnimation = new ArrayList<>();

    protected List<Sprite> moveRightAnimation = new ArrayList<>();

    public PortableEntity(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 4;
    }

    public void createMoveUpAnimation(Sprite sprt1, Sprite sprt2, Sprite sprt3) {
        moveUpAnimation.add(sprt1);
        moveUpAnimation.add(sprt2);
        moveUpAnimation.add(sprt3);
    }

    public void createMoveDownAnimation(Sprite sprt1, Sprite sprt2, Sprite sprt3) {
        moveDownAnimation.add(sprt1);
        moveDownAnimation.add(sprt2);
        moveDownAnimation.add(sprt3);
    }

    public void createMoveLeftAnimation(Sprite sprt1, Sprite sprt2, Sprite sprt3) {
        moveLeftAnimation.add(sprt1);
        moveLeftAnimation.add(sprt2);
        moveLeftAnimation.add(sprt3);
    }

    public void createMoveRightAnimation(Sprite sprt1, Sprite sprt2, Sprite sprt3) {
        moveRightAnimation.add(sprt1);
        moveRightAnimation.add(sprt2);
        moveRightAnimation.add(sprt3);
    }

    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();

    public static boolean canMove(int x,int y, char[][] map) {
        return map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] != '#'
                && map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] != '*'
                && map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] != '%';
    }



    @Override
    public void update() {

    }
}
