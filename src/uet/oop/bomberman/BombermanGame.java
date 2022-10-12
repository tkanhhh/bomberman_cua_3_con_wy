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
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.BOSS_UET;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.FixedEntity.Grass;
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
    public static int countenemy = 0;
    public static int countBomber = 0;

    public static int bossLife = 2;

    public static boolean checkBoss = false; //check if boss has been rendered more than 1 times or not

    public static int gameoverTIMEDELAY = 120;

    /**
     * char[][] contains index of brick, wall and bomber
     */
    public static char[][] map;
    public static char[][] bombmap = new char[HEIGHT][WIDTH];

    /**
     * HashMap countbomb count the number of bombs were put on map
     */
    public static HashMap<Integer, Integer> countbomb = new HashMap<>();

    /**
     * HashMap Score save score of players
     */
    public static HashMap<Integer,Integer> score = new HashMap<>();

    /**
     * ArrayList contains activeEntities in game
     */
    public static List<ActiveEntity> activeEntities = new ArrayList<>();

    /**
     * ArrayList contain stillObject, ex: wall, grass
     */
    public static List<Entity> stillObjects = new ArrayList<>();

    int bomberMode = 0;
    int gameoverTimer = 300;
    public static boolean portalCheck = false;
    ImageView buttonPlayer1;
    ImageView buttonPlayer2;
    ImageView buttonExit;
    ImageView startscreenView;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        //=======================START - LEVELUP - GAME OVER SCREEN====================
        Image startscreen = new Image("images/author.png");
        Image levelUpScreen = new Image("images/levelup.png");
        Image gameover = new Image("images/gameover.png");
        Image congrat = new Image("images/congrat.png");

        startscreenView = new ImageView(startscreen);
        ImageView levelUpView = new ImageView(levelUpScreen);
        ImageView gameoverView = new ImageView(gameover);
        ImageView congratView = new ImageView(congrat);

        startscreenView.setX(0);
        startscreenView.setY(0);
        startscreenView.setScaleX(1);
        startscreenView.setScaleY(1);

        levelUpView.setX(0);
        levelUpView.setY(0);
        levelUpView.setScaleX(1);
        levelUpView.setScaleY(1);
        levelUpView.setVisible(false);

        gameoverView.setX(0);
        gameoverView.setY(0);
        gameoverView.setScaleX(1);
        gameoverView.setScaleY(1);
        gameoverView.setVisible(false);

        congratView.setX(0);
        congratView.setY(0);
        congratView.setScaleX(1);
        congratView.setScaleY(1);
        congratView.setVisible(false);

        Image player1 = new Image("images/button1.png");
        Image player2 = new Image("images/button2.png");
        Image exit = new Image("images/exit.png");

        buttonPlayer1 = new ImageView(player1);
        buttonPlayer1.setLayoutX(380);
        buttonPlayer1.setLayoutY(330);
        buttonPlayer1.setFitHeight(64);
        buttonPlayer1.setFitWidth(290);


        buttonPlayer2 = new ImageView(player2);
        buttonPlayer2.setLayoutX(380);
        buttonPlayer2.setLayoutY(430);
        buttonPlayer2.setFitHeight(64);
        buttonPlayer2.setFitWidth(290);

        buttonExit = new ImageView(exit);
        buttonExit.setLayoutX(380);
        buttonExit.setLayoutY(530);
        buttonExit.setFitHeight(64);
        buttonExit.setFitWidth(290);


        //=================================================


        // Create root container and add canvas to root
        Group root = new Group();
        Menu.createMenu(root);
        root.getChildren().add(canvas);


        //==================add START SCREEN=======
        root.getChildren().add(startscreenView);
        root.getChildren().add(levelUpView);
        root.getChildren().add(gameoverView);
        root.getChildren().add(congratView);
        root.getChildren().add(buttonPlayer1);
        root.getChildren().add(buttonPlayer2);
        root.getChildren().add(buttonExit);
        //==========================================

        canvas.setTranslateY(48);


        // Create scene and add root to scene
        scene = new Scene(root);

        // Add scene to stage
        stage.setScene(scene);
        stage.show();
        stage.setTitle("BOMBERMAN CỦA 3 CON WỶ :>");
        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);

        // Play BGM
        new Sound("sound/start.wav", "title");

        // Listen to input from scene
        KeyHandle.keyHandlers(scene);

        gameState = "startmenu";
        // 60 times per 1 second
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

                if (gameState.equals("gameover")) {
                    if (gameoverTimer > 0) { // count delay time after game over, 5 s
                        gameoverTimer--;
                        gameoverView.setVisible(true);
                    } else { // reset game
                        level = 1;
                        resetGame();
                        map = Map.ReadMap("res/levels/Level" + level + ".txt");
                        Map.LoadMap();
                        gameoverView.setVisible(false);
                        gameoverTimer = 300;    // set time delay if game over
                        gameState = "startmenu";
                        return;
                    }
                }

                if (gameState.equals("levelup")) {
                    if (level == maxLevel) {
                        levelUpView.setVisible(false);
                        gameState = "congrat";
                        gameoverTimer = 300;
                        return;
                    }
                    if (gameoverTimer > 0) { // count delay time after game over, 5 s
                        gameoverTimer--;
                        levelUpView.setVisible(true);
                    } else { // reset game
                        level++;
                        resetGame();
                        map = Map.ReadMap("res/levels/Level" + level + ".txt");
                        Map.LoadMap();
                        if (bomberMode == 1) {
                            addOnePlayer();
                        }
                        if (bomberMode == 2) {
                            addTwoPlayer();
                        }
                        levelUpView.setVisible(false);
                        gameState = "running";
                        gameoverTimer = 300;    // set time delay if game over
                        return;
                    }
                }

                if (gameState.equals("congrat")) {
                    if (gameoverTimer > 0) { // count delay time after game over, 5 s
                        gameoverTimer--;
                        congratView.setVisible(true);
                    } else { // reset game
                        level = 1;
                        resetGame();
                        map = Map.ReadMap("res/levels/Level" + level + ".txt");
                        Map.LoadMap();
                        congratView.setVisible(false);
                        gameoverTimer = 300;    // set time delay if game over
                        gameState = "startmenu";
                        return;
                    }
                }
            }
        };
        timer.start();

        // Start 1 player
        buttonPlayer1.setOnMouseClicked(event -> {
            Menu.Player2.setVisible(false);
            Menu.boss.setVisible(false);
            resetGame();
            map = Map.ReadMap("res/levels/Level" + level + ".txt");
            Map.LoadMap();
            addOnePlayer();
            hideStart();
        });

        //1 player button will be smaller if mouse point at
        buttonPlayer1.setOnMouseEntered(mouseEvent -> {
            buttonPlayer1.setLayoutX(380);
            buttonPlayer1.setLayoutY(330);
            buttonPlayer1.setFitHeight(50);
            buttonPlayer1.setFitWidth(276);
        });

        // 1 player button back to normal
        buttonPlayer1.setOnMouseExited(mouseEvent -> {
            buttonPlayer1.setLayoutX(380);
            buttonPlayer1.setLayoutY(330);
            buttonPlayer1.setFitHeight(64);
            buttonPlayer1.setFitWidth(290);
        });

        // Start 2 players
        buttonPlayer2.setOnMouseClicked(event -> {
            Menu.boss.setVisible(false);
            resetGame();
            map = Map.ReadMap("res/levels/Level" + level + ".txt");
            Map.LoadMap();
            addTwoPlayer();
            hideStart();
        });

        // 2 player button will be smaller if mouse point at
        buttonPlayer2.setOnMouseEntered(mouseEvent -> {
            buttonPlayer2.setLayoutX(380);
            buttonPlayer2.setLayoutY(430);
            buttonPlayer2.setFitHeight(50);
            buttonPlayer2.setFitWidth(276);
        });

        // 2 player button back to normal
        buttonPlayer2.setOnMouseExited(mouseEvent -> {
            buttonPlayer2.setLayoutX(380);
            buttonPlayer2.setLayoutY(430);
            buttonPlayer2.setFitHeight(64);
            buttonPlayer2.setFitWidth(290);
        });

        buttonExit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });

        buttonExit.setOnMouseEntered(mouseEvent -> {
            buttonExit.setLayoutX(380);
            buttonExit.setLayoutY(530);
            buttonExit.setFitHeight(50);
            buttonExit.setFitWidth(276);
        });

        buttonExit.setOnMouseExited(mouseEvent -> {
            buttonExit.setLayoutX(380);
            buttonExit.setLayoutY(530);
            buttonExit.setFitHeight(64);
            buttonExit.setFitWidth(290);
        });
    }

    private void hideStart() {
        startscreenView.setVisible(false);
        buttonPlayer1.setDisable(true);
        buttonPlayer1.setVisible(false);
        buttonPlayer2.setVisible(false);
        buttonPlayer2.setDisable(true);
        buttonExit.setVisible(false);
        buttonExit.setDisable(true);
    }

    private void showStart() {
        startscreenView.setVisible(true);
        buttonPlayer1.setVisible(true);
        buttonPlayer2.setVisible(true);
        buttonExit.setVisible(true);
        buttonPlayer1.setDisable(false);
        buttonPlayer2.setDisable(false);
        buttonExit.setDisable(false);
    }

    private void addOnePlayer() {
        bomberMode = 1;
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), 0, "UP","DOWN","LEFT","RIGHT","ENTER");
        activeEntities.add(bomberman);
        countBomber = 1;
        gameState = "running";
    }

    private void addTwoPlayer() {
        bomberMode = 2;
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), 0, "UP","DOWN","LEFT","RIGHT","ENTER");
        Bomber bomberman2 = new Bomber(1, 11, Sprite.player_right.getFxImage(), 1, "W","S","A","D","SPACE");
        activeEntities.add(bomberman);
        activeEntities.add(bomberman2);
        countBomber = 2;
        gameState = "running";
    }

    private void resetGame() { // reset game
        activeEntities.clear();
        stillObjects.clear();
        score.clear();
        countbomb.clear();
        countenemy = 0;
        map = new char[1][1]; //will be re assign later
        bombmap = new char[1][1]; //will be reassign later
        gameTime = 48800;
    }

    public static int timeAfterExplode = 120;
    BOSS_UET boss = new BOSS_UET(15, 5, Sprite.oneal_right1.getFxImage());
    /**
     * update menu
     */
    public void update() {
        Menu.updateMenu();
        if (countBomber <= 0 ) {
            gameoverTIMEDELAY--;
            if(gameoverTIMEDELAY < 0) {
                gameState = "gameover";
                gameoverTIMEDELAY = 120;
                return;
            }
        }

        if (gameTime < 0) {
            gameState = "gameover";
            return;
        }
        if (countenemy <= 0 && bossLife > 0) {
            if (timeAfterExplode == 0) {
                    Menu.boss.setVisible(true);
                    bossLife--;
                    checkBoss = true;
                    boss = new BOSS_UET(boss.getX_dead(), boss.getY_dead(), Sprite.oneal_right1.getFxImage());
                    BombermanGame.bombmap[boss.getY_dead()][boss.getX_dead()] = ' ';
                    BombermanGame.activeEntities.add(boss);
                    BombermanGame.countenemy++;
                    timeAfterExplode = 100;
            }
            timeAfterExplode--;
        }

        if (countenemy <= 0 && portalCheck && bossLife == 0) {
            gameState = "levelup";
            Menu.boss.setVisible(false);
            bossLife = 2;
            boss = new BOSS_UET(15, 5, Sprite.oneal_right1.getFxImage());
            return;
        }

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
     * Render
     */
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        for (int i = 0; i < activeEntities.size(); i++) {
            if (activeEntities.get(i).show){
                activeEntities.get(i).render(gc);
            }
        }
    }
}
