package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.KeyHandle;


import uet.oop.bomberman.KeyHandle;
import uet.oop.bomberman.graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Bomber extends Entity {

//    KeyHandle inputAction;

    //KeyEvent input;
    int movementBuffer = 0;

    String direction = "idle";
//    protected KeyHandle input;

    Text input;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomber(int x, int y, Image img, Text input) {
        super(x, y, img);
        this.input = input;
    }

    @Override
    public void update() {
        switch (input.getText()) {
            case "W":
                if (movementBuffer == 0) {
                    movementBuffer += 16;
                    direction = "up";
                }
            case "A":
                if (movementBuffer == 0) {
                    movementBuffer += 16;
                    direction = "left";
                }
            case "S":
                if (movementBuffer == 0) {
                    movementBuffer += 16;
                    direction = "down";
                }
            case "D":
                if (movementBuffer == 0) {
                    movementBuffer += 16;
                    direction = "right";
                }
            default:
                break;
        }
        if (movementBuffer > 0) {
            switch (direction) {
                case "up":
                    y -= Math.min(speed, movementBuffer);
                    break;
                case "down":
                    y += Math.min(speed, movementBuffer);
                    break;
                case "left":
                    x -= Math.min(speed, movementBuffer);
                    break;
                case "right":
                    x += Math.min(speed, movementBuffer);
                    break;
            }
        }
        if (movementBuffer == 0) {
            direction = "idle";
        }
    }

//    @Override
//    public void render(GraphicsContext gc) {
//        Image frame = null;
//        switch (direction) {
//            case "up":
//                frame = Sprite.player_up.getFxImage();
//                break;
//            case "down":
//                frame = Sprite.player_down.getFxImage();
//                break;
//            case "left":
//                frame = Sprite.player_left.getFxImage();
//                break;
//            case "right":
//                frame = Sprite.player_right.getFxImage();
//                break;
//        }
//        gc.drawImage(frame, x, y);
//    }



}