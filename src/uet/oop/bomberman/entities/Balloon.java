package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Balloon extends Entity {
    private Balloon balloon;

    public Balloon(int x, int y, Image img) {
        super( x, y, img);
    }


    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}
