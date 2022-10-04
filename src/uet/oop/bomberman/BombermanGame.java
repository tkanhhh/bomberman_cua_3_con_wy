package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.MapCreate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.KeyHandle;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class BombermanGame extends Application {
    
//    public static final int WIDTH = 20;
    public static int WIDTH = 25;
    public static int HEIGHT = 15;

    private int[][] coordinate;

    //public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    Text text = new Text();
    private KeyHandle keyInput;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        createMap();
        keyInput = new KeyHandle();

        addKeyListener(keyInput);
        new MapCreate();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode().toString()) {
                    case "W":
                        keyInput.up = true;
                        break;
                    case "A":
                        keyInput.left = true;
                        break;
                    case "S":
                        keyInput.down = true;
                        break;
                    case "D":
                        keyInput.right = true;
                        break;
                }

                text.setText(event.getCode().toString());
                System.out.println(keyInput.down + " " + keyInput.left + " " + keyInput.right);
            }
        });

        scene.setOnKeyReleased(event -> text.setText(""));

//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                switch(event.getCode().toString()) {
//                    case "W":
//                        keyInput.up = false;
//                        break;
//                    case "A":
//                        keyInput.left = false;
//                        break;
//                    case "S":
//                        keyInput.down = false;
//                        break;
//                    case "D":
//                        keyInput.right = false;
//                        break;
//                }
////                System.out.println(keyInput.down + " " + keyInput.left + " " + keyInput.right);
//            }
//        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), text);
        entities.add(bomberman);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
    }

    public void createMap() {
        try {
            String inputFile = "res/levels/Level1.txt";
            FileReader file = new FileReader(inputFile);
            Scanner input = new Scanner(file);

            //lấy token nha
            StringTokenizer elementsInFile = new StringTokenizer(input.nextLine());

            HEIGHT = Integer.parseInt(elementsInFile.nextToken());
            WIDTH = Integer.parseInt(elementsInFile.nextToken());

            while (input.hasNextLine()) {
                coordinate = new int[WIDTH][HEIGHT];
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

                        coordinate[j][i] = element;
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


//    public void handle(KeyEvent event) {
//        System.out.println(event.getCode().toString());
//    }
}
