package uet.oop.bomberman.utils;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Menu {
    private static ImageView statusGame;
    public static Text level, time, boss;
    public static Text Player1, Player2;


    public static void createMenu(Group root) {
        level = new Text("Level: 1");
        level.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        level.setFill(Color.WHITE);
        level.setX(100);
        level.setY(32);

        time = new Text("Times: 120");
        time.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        time.setFill(Color.WHITE);
        time.setX(400);
        time.setY(32);

        Player1 = new Text("Player 1:");
        Player1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Player1.setFill(Color.WHITE);
        Player1.setX(700);
        Player1.setY(32);

        Player2 = new Text("Player 2:");
        Player2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Player2.setFill(Color.WHITE);
        Player2.setX(1000);
        Player2.setY(32);

        boss = new Text("Boss life:");
        boss.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        boss.setFill(Color.WHITE);
        boss.setX(1300);
        boss.setY(32);


        Image newGame = new Image("images/back.png");
        statusGame = new ImageView(newGame);
        statusGame.setX(32);
        statusGame.setY(0);
        statusGame.setFitHeight(48);
        statusGame.setFitWidth(48);

        Pane pane = new Pane();
        pane.getChildren().addAll(level, time, boss, Player1, Player2, statusGame);
        pane.setMinSize(BombermanGame.WIDTH * Sprite.SCALED_SIZE, 64);
        pane.setMaxSize(2000, 2000);
        pane.setStyle("-fx-background-color: #2d65a2");


        root.getChildren().add(pane);

        statusGame.setOnMouseClicked(event -> {
            System.out.println("CLICKED");
            BombermanGame.tutView.setVisible(false);
            if (BombermanGame.gameState.equals("running") || BombermanGame.gameState.equals("tutorial")) {
                BombermanGame.gameState = "startmenu";
                BombermanGame.level = 1;
                statusGame.setImage(newGame);
            }
        });

    }

    public static void updateMenu() {
        level.setText("Level: " + BombermanGame.level);
        time.setText("Time: " + BombermanGame.gameTime-- / 60);
        Player1.setText("Player 1: " + BombermanGame.score.get(0));
        Player2.setText("Player 2: " + BombermanGame.score.get(1));
        boss.setText("Boss resurrection: " + BombermanGame.bossLife);
    }
}
