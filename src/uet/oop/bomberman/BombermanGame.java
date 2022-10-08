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
    public static int countenemy = 0;
    public static int countBomber = 0;
    public static int gameoverTIMEEDELAY = 120;
    /**
     * Mảng 2 chiều char[][] chứa vị trí của brick, wall và bomber
     * phục vụ cho việc dò đường của enemy
     */
    public static char[][] map;
    public static char[][] bombmap = new char[HEIGHT][WIDTH];

    /**
     * HashMap countbomb đếm số lượng bom đã đặt trên map của các bomber
     */
    public static HashMap<Integer, Integer> countbomb = new HashMap<>();

    /**
     * HashMap Score lưu điểm số của các BomberMan
     */
    public static HashMap<Integer,Integer> score = new HashMap<>();

    /**
     * ArrayList chứa các activeEntities trong game
     */
    public static List<ActiveEntity> activeEntities = new ArrayList<>();

    /**
     * ArrayList chứa các stillObject, ví dụ : wall, grass
     */
    public static List<Entity> stillObjects = new ArrayList<>();

    int bomberMode = 0;
    int gameoverTimer = 300;
    public static boolean portalCheck = false;
    ImageView buttonPlayer1;
    ImageView buttonPlayer2;
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
        Image startscreen = new Image("images/author1.png");
        Image levelUpScreen = new Image("images/levelup.png");
        Image gameover = new Image("images/gameover.png");

        startscreenView = new ImageView(startscreen);
        ImageView levelUpView = new ImageView(levelUpScreen);
        ImageView gameoverView = new ImageView(gameover);

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


        // Tao root container và add canvas vào root
        Group root = new Group();
        Menu.createMenu(root);
        root.getChildren().add(canvas);


        //==================add START SCREEN=======
        root.getChildren().add(startscreenView);
        root.getChildren().add(levelUpView);
        root.getChildren().add(gameoverView);
        root.getChildren().add(buttonPlayer1);
        root.getChildren().add(buttonPlayer2);
        //==========================================

        canvas.setTranslateY(48);


        // Tao scene và add root vào scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        stage.setTitle("BOMBERMAN CỦA 3 CON WỶ :>");
        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);

        // Bật nhạc nền
        new Sound("sound/start.wav", "title");

        // Listen to input từ scene
        KeyHandle.keyHandlers(scene);

        gameState = "startmenu";
        // 60 lần 1 giây
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
                    if (gameoverTimer > 0) { // đếm thời gian delay sau khi game over, 5 giây
                        gameoverTimer--;
                        gameoverView.setVisible(true);
                    } else { // reset game
                        level = 1;
                        resetGame();
                        map = Map.ReadMap("res/levels/Level" + level + ".txt");
                        Map.LoadMap();
                        gameoverView.setVisible(false);
                        gameoverTimer = 300;    // set lại thời gian delay nếu game over
                        gameState = "startmenu";
                        return;
                    }
                }

                if (gameState.equals("levelup")) {
                    if (level == maxLevel) {
                        levelUpView.setVisible(false);
                        gameState = "gameover";
                        gameoverTimer = 300;
                        return;
                    }
                    if (gameoverTimer > 0) { // đếm thời gian delay sau khi game over, 5 giây
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
                        gameoverTimer = 300;    // set lại thời gian delay nếu game over
                        return;
                    }
                }


            }
        };
        timer.start();

        // Bắt đầu chơi chế độ 1 người
        buttonPlayer1.setOnMouseClicked(event -> {
            Menu.Player2.setVisible(false);
            resetGame();
            map = Map.ReadMap("res/levels/Level" + level + ".txt");
            Map.LoadMap();
            addOnePlayer();
            hideStart();
        });

        //Bắt đầu chơi chế độ 2 người
        buttonPlayer2.setOnMouseClicked(event -> {

            resetGame();
            map = Map.ReadMap("res/levels/Level" + level + ".txt");
            Map.LoadMap();
            addTwoPlayer();
            hideStart();
        });
    }

    private void hideStart() {
        startscreenView.setVisible(false);
        buttonPlayer1.setDisable(true);
        buttonPlayer1.setVisible(false);
        buttonPlayer2.setVisible(false);
        buttonPlayer2.setDisable(true);
    }

    private void showStart() {
        startscreenView.setVisible(true);
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

    private void resetGame() { // reset các thuộc tính của Game , lên level hoặc game over
        activeEntities.clear();
        stillObjects.clear();
        score.clear();
        countbomb.clear();
        countenemy=0;
        map = new char[1][1]; //will be re assign later
        bombmap = new char[1][1]; //will be re assign later
        gameTime = 48800;
    }


    /**
     * Được gọi 60 lần mỗi giây
     * update menu bao gồm thời gian, điểm số,....
     */
    public void update() {
        Menu.updateMenu();
        if(countBomber <=0 ) { // nếu bomber chết hết
            gameoverTIMEEDELAY--; // delay 2 giây
            if(gameoverTIMEEDELAY<0){
                gameState = "gameover";
                gameoverTIMEEDELAY = 120;
                return;
            }
        }

        if(gameTime < 0) {
            gameState = "gameover";
            return;
        }
        if(countenemy<=0 && portalCheck) {
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
     * Render các thứ trên canvas
     * Render 60 lần 1 giây
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
