package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.awt.*;

public class Creature extends Entity {

    protected int move;
    protected int changeImage;

    public Creature(int x, int y, Image img) { super(x, y, img); }

    public Creature(int x, int y, Image img, int move, int changeImage) {
        super(x, y, img);
        this.move = move;
        this. changeImage = changeImage;
    }

    public void setChangeImage(int changeImage) {
        this.changeImage = changeImage;
    }

    public int getChangeImage() {
        return changeImage;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public void update() {

    }
}
