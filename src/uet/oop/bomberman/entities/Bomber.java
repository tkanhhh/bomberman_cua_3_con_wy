package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.event.KeyAdapter;

public class Bomber extends Entity {

    KeyAdapter inputAction;


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    public Bomber(int x, int y, Image img, KeyAdapter inputAction) {
        super(x,y,img);
        this.inputAction = inputAction;
    }

    

    @Override
    public void update() {

    }
}
