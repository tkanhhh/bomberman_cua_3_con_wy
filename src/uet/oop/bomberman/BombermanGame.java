package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BombermanGame extends Application {
    
//    public static final int WIDTH = 20;
    public static int WIDTH = 25;
    public static int HEIGHT = 15;

    private int[][] toaDo;

    //public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        new MapCreate();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() {
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                }
//                stillObjects.add(object);
//            }
//        }
        try {
            String inputFile = "res/levels/Level1.txt";
            FileReader file = new FileReader(inputFile);
            Scanner input = new Scanner(file);

            //lấy token nha
            StringTokenizer elementsInFile = new StringTokenizer(input.nextLine());

            HEIGHT = Integer.parseInt(elementsInFile.nextToken());
            WIDTH = Integer.parseInt(elementsInFile.nextToken());

            while (input.hasNextLine()) {
                toaDo = new int[WIDTH][HEIGHT];
                for (int i = 0; i < HEIGHT; i++) {
                    StringTokenizer elementTile = new StringTokenizer(input.nextLine());
                    for (int j = 0; j < WIDTH; j++) {
                        int element = Integer.parseInt(elementTile.nextToken());
                        Entity entity;

                        switch (element) {
                            case 2:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }

                        toaDo[j][i] = element;
                        stillObjects.add(entity);
                    }
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
