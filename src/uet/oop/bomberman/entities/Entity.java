package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {

    public boolean show = true; // if true then render

    //Tọa độ X tính từ góc trái trên trong Canvas
    protected Sprite sprite;

    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
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

    //Từ toạ độ x trên canvas -> toạ độ đơn vị trên MAP , có vài việc cần làm
    public int getSmallX() {
        return (x+(Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
    }

    //Từ toạ độ y canvas -> toạ độ đơn vị trên MAP , có vài việc cần làm
    public int getSmallY(){
        return (y+(Sprite.SCALED_SIZE/2))/Sprite.SCALED_SIZE;
    }

    public int getEXSmallX() {
        return (x)/Sprite.SCALED_SIZE;
    }

    //Từ toạ độ y canvas -> toạ độ đơn vị trên MAP , có vài việc cần làm
    public int getEXSmallY(){
        return (y)/Sprite.SCALED_SIZE;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
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
