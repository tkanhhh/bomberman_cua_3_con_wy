package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {

    public boolean show = true; // if true then render

    //X coordinate start with top-left corner on Canvas
    protected Sprite sprite;

    protected int x;

    //Y coordinate start with top-left corner on Canvas
    protected int y;

    protected Image img;

    protected int speed = 4;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    //x coordinate on canvas -> x unit on map
    public int getSmallX() {
        return (x + (Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
    }

    //y coordinate on canvas -> y unit on map
    public int getSmallY(){
        return (y + (Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
    }

    public int getEXSmallX() {
        return (x)/Sprite.SCALED_SIZE;
    }

    public int getEXSmallY(){
        return (y)/Sprite.SCALED_SIZE;
    }

    //Contructor, convert from x to x unit, y to y unit
    public Entity( int x, int y, Image img) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
