package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.KeyHandle;
import uet.oop.bomberman.utils.Map;
import uet.oop.bomberman.utils.Menu;
import uet.oop.bomberman.utils.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    Scene scene;
    private GraphicsContext gc;
    private Canvas canvas;
    public static String gameState = "";
    public static long gameTime = 14400;
    public static int level = 1;
    public static int maxLevel = 3;
    public static int countEnemy = 0;
    public static int countBomber = 0;
    public static int gameOverTIMEDELAY = 120;
    /**
     * Mảng 2 chiều char[][] chứa vị trí của brick, wall và bomber
     * phục vụ cho việc dò đường của enemy
     */
    public static char[][] map;
    public static char[][] bombMap = new char[HEIGHT][WIDTH];

    /**
     * HashMap countBomb count bomb already used by player
     */
    public static HashMap<Integer, Integer> countBomb = new HashMap<>();

    /**
     * HashMap Score contains score of players
     */
    public static HashMap<Integer,Integer> score = new HashMap<>();

    /**
     * ArrayList contains activeEntities
     */
    public static List<ActiveEntity> activeEntities = new ArrayList<>();

    /**
     * ArrayList contains stillObject, eg : wall, grass
     */
    public static List<Entity> stillObjects = new ArrayList<>();

    int bomberMode = 0;
    int gameOverTimer = 300;
    public static boolean portalCheck = false;
    ImageView buttonPlayer1;
    ImageView buttonPlayer2;
    ImageView startScreenView;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Create Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        //=======================START - LEVELUP - GAME OVER SCREEN====================
        Image startScreen = new Image("images/author1.png");
        Image levelUpScreen = new Image("images/levelup.png");
        Image gameOver = new Image("images/gameover.png");

        startScreenView = new ImageView(startScreen);
        ImageView levelUpView = new ImageView(levelUpScreen);
        ImageView gameOverView = new ImageView(gameOver);

        startScreenView.setX(0);
        startScreenView.setY(0);
        startScreenView.setScaleX(1);
        startScreenView.setScaleY(1);

        levelUpView.setX(0);
        levelUpView.setY(0);
        levelUpView.setScaleX(1);
        levelUpView.setScaleY(1);
        levelUpView.setVisible(false);

        gameOverView.setX(0);
        gameOverView.setY(0);
        gameOverView.setScaleX(1);
        gameOverView.setScaleY(1);
        gameOverView.setVisible(false);

        Image player1 = new Image("images/button1.png");
        Image player2 = new Image("images/button2.png");

        buttonPlayer1 = new ImageView(player1);
        buttonPlayer1.setLayoutX(560);
        buttonPlayer1.setLayoutY(330);
        buttonPlayer1.setFitHeight(64);
        buttonPlayer1.setFitWidth(384);


        buttonPlayer2 = new ImageView(player2);
        buttonPlayer2.setLayoutX(556);
        buttonPlayer2.setLayoutY(430);
        buttonPlayer2.setFitHeight(60);
        buttonPlayer2.setFitWidth(380);


        //=================================================


        // Create root container and add canvas to root
        Group root = new Group();
        Menu.createMenu(root);
        root.getChildren().add(canvas);


        //==================add START SCREEN=======
        root.getChildren().add(startScreenView);
        root.getChildren().add(levelUpView);
        root.getChildren().add(gameOverView);
        root.getChildren().add(buttonPlayer1);
        root.getChildren().add(buttonPlayer2);
        //==========================================

        canvas.setTranslateY(48);


        // Create scene and add root to scene
        scene = new Scene(root);

        // Add scene in stage
        stage.setScene(scene);
        stage.show();
        stage.setTitle("BOMBERMAN CỦA 3 CON WỶ :>");
        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);

        // play bg music
        new Sound("sound/start.wav", "title");

        // Listen to input from scene
        KeyHandle.keyHandlers(scene);

        gameState = "startmenu";
        // 60 per 1 s
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameState.equals("startmenu")) {
                    showStart();
                }

                if (gameState.equals("running")) {
                    render();
                    update();
                }

                if (gameState.equals("pause")) {
                    render();
                }

                if (gameState.equals("gameover")) {
                    if (gameOverTimer > 0) { // countdown after game over
                        gameOverTimer--;
                        gameOverView.setVisible(true);
                    } else { // reset game
                        level = 1;
                        resetGame();
                        map = Map.MapReader("res/levels/Level" + level + ".txt");
                        Map.MapLoader();
                        gameOverView.setVisible(false);
                        gameOverTimer = 300;    // reset time delay if game over
                        gameState = "startmenu";
                        return;
                    }
                }

                if (gameState.equals("levelup")) {
                    if (level == maxLevel) {
                        levelUpView.setVisible(false);
                        gameState = "gameover";
                        gameOverTimer = 300;
                        return;
                    }
                    if (gameOverTimer > 0) { // countdown game over time delay
                        gameOverTimer--;
                        levelUpView.setVisible(true);
                    } else { // reset game
                        level++;
                        resetGame();
                        map = Map.MapReader("res/levels/Level" + level + ".txt");
                        Map.MapLoader();
                        if (bomberMode == 1) {
                            addOnePlayer();
                        }
                        if (bomberMode == 2) {
                            addTwoPlayer();
                        }
                        levelUpView.setVisible(false);
                        gameState = "running";
                        gameOverTimer = 300;    // reset delay time if game over
                        return;
                    }
                }


            }
        };
        timer.start();

        // Start 1 player mode
        buttonPlayer1.setOnMouseClicked(event -> {
            Menu.Player2.setVisible(false);
            resetGame();
            map = Map.MapReader("res/levels/Level" + level + ".txt");
            Map.MapLoader();
            addOnePlayer();
            hideStart();
        });

        // Start 2 players mode
        buttonPlayer2.setOnMouseClicked(event -> {

            resetGame();
            map = Map.MapReader("res/levels/Level" + level + ".txt");
            Map.MapLoader();
            addTwoPlayer();
            hideStart();
        });
    }

    private void hideStart() {
        startScreenView.setVisible(false);
        buttonPlayer1.setDisable(true);
        buttonPlayer1.setVisible(false);
        buttonPlayer2.setVisible(false);
        buttonPlayer2.setDisable(true);
    }

    private void showStart() {
        startScreenView.setVisible(true);
        buttonPlayer1.setVisible(true);
        buttonPlayer2.setVisible(true);
        buttonPlayer1.setDisable(false);
        buttonPlayer2.setDisable(false);
    }

    private void addOnePlayer() {
        bomberMode = 1;
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), 0, "UP","DOWN","LEFT","RIGHT","ENTER");
        activeEntities.add(bomberman);
        countBomber=1;
        gameState = "running";
    }

    private void addTwoPlayer() {
        bomberMode = 2;
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), 0, "UP","DOWN","LEFT","RIGHT","ENTER");
        Bomber bomberman2 = new Bomber(1, 11, Sprite.player_right.getFxImage(), 1, "W","S","A","D","TAB");
        activeEntities.add(bomberman);
        activeEntities.add(bomberman2);
        countBomber=2;
        gameState = "running";
    }

    private void resetGame() { // reset Game
        activeEntities.clear();
        stillObjects.clear();
        score.clear();
        countBomb.clear();
        countEnemy=0;
        map = new char[1][1]; //will be re-assign later
        bombMap = new char[1][1]; //will be re-assign later
        gameTime = 48800;
    }


    /**
     * update menu
     */
    public void update() {
        Menu.updateMenu();
        if(countBomber <=0 ) { // nếu bomber chết hết
            gameOverTIMEDELAY--; // delay 2 giây
            if(gameOverTIMEDELAY<0){
                gameState = "gameover";
                gameOverTIMEDELAY = 120;
                return;
            }
        }

        if(gameTime < 0) {
            gameState = "gameover";
            return;
        }
        if(countEnemy<=0 && portalCheck) {
            gameState = "levelup";
            return;
        }
        System.out.println(KeyHandle.getKeys());

        for (int i = 0; i < activeEntities.size(); i++) {
            activeEntities.get(i).update();
            for (int j = 0; j < activeEntities.size(); j++) {
                if (i != j)
                    activeEntities.get(i).collide(activeEntities.get(j));
            }
        }

        for (int i = 0; i < activeEntities.size(); i++) {
            if (activeEntities.get(i).delete) {
                activeEntities.remove(activeEntities.get(i));
            }
        }
    }




    /**
     * Render on canvas
     * Render 60 times in 1s
     */
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        for (int i = 0; i < activeEntities.size(); i++) {
            if(activeEntities.get(i).show){
                activeEntities.get(i).render(gc);
            }
        }
    }
}
