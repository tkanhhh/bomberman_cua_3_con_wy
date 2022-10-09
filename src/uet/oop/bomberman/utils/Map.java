package uet.oop.bomberman.utils;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.ActiveEntity;
import uet.oop.bomberman.entities.ActiveEntity.Brick;
import uet.oop.bomberman.entities.ActiveEntity.Portal;
import uet.oop.bomberman.entities.ActiveEntity.PowerUp.PowerUpFlames;
import uet.oop.bomberman.entities.ActiveEntity.PowerUp.PowerUpMoreBombs;
import uet.oop.bomberman.entities.ActiveEntity.PowerUp.PowerUpSpeed;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.*;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.FixedEntity.Grass;
import uet.oop.bomberman.entities.FixedEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.util.Scanner;

public class Map {
    // Đọc map
    public static char[][] ReadMap(String map_file) {
        char[][] map = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        int level = 1;
        int height = 0;
        int width = 0;
        try {
            FileReader file = new FileReader(map_file);
            Scanner sc = new Scanner(file);
            level = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            map = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];
            sc.nextLine();

            for (int i = 0; i < height; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < width; j++)
                    map[i][j] = line.charAt(j);
            }
            sc.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static void LoadMap() {
        BombermanGame.bombmap = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];
        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                char a = BombermanGame.map[i][j];
                BombermanGame.bombmap[i][j] = ' ';
                Entity object = null;
                switch (a) {
                    case '#': // WALL - FIXED OBJECT
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        BombermanGame.stillObjects.add(object);
                        break;

                    case '%': // WALL - FIXED OBJECT
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        BombermanGame.stillObjects.add(object);
                        break;

                    case '*': // BRICK - ACTIVE OBJECT
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        BombermanGame.activeEntities.add((ActiveEntity) object);
                        break;

                    case 'x': // PORTAL - ACTIVE OBJECT
                        // layer 0 - grass
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }

                        // layer 1 - Portal
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        BombermanGame.activeEntities.add((ActiveEntity) object);

                        // layer 2 - brick
                        BombermanGame.activeEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.map[i][j] = '*';
                        break;

                    case '1': // DSA - PORTABLE ENTITY
                        BombermanGame.activeEntities.add(new Dsa(j, i, Sprite.dsa_left1.getFxImage()));
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        BombermanGame.map[i][j] = ' ';
                        BombermanGame.countenemy++;
                        break;

                    case '2': // OOP - PORTABLE ENTITY
                        BombermanGame.activeEntities.add(new Oop(j, i, Sprite.oop_right1.getFxImage()));
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        BombermanGame.map[i][j] = ' ';
                        BombermanGame.countenemy++;
                        break;

                    case '3': // CA - PORTABLE ENTITY
                        BombermanGame.activeEntities.add(new ComputerArchitecture(j, i, Sprite.ca_right1.getFxImage()));
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        BombermanGame.map[i][j] = ' ';
                        BombermanGame.countenemy++;
                        break;

                    case '4': // BOSS_UET - PORTABLE ENTITY
                        BombermanGame.activeEntities.add(new BOSS_UET(j, i, Sprite.oneal_right1.getFxImage()));
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        BombermanGame.map[i][j] = ' ';
                        BombermanGame.countenemy++;
                        break;

                    case 'b': // MORE BOMBS
                        // layer 0 - grass
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }

                        // layer 1 - PowerUp
                        object = new PowerUpMoreBombs(j, i, Sprite.powerup_bombs.getFxImage());
                        BombermanGame.activeEntities.add((ActiveEntity) object);

                        // layer 2 - Brick
                        BombermanGame.activeEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.map[i][j] = '*';
                        break;

                    case 'f': // MORE FLAMES
                        // layer 0 - grass
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }

                        // layer 1 - PowerUp
                        object = new PowerUpFlames(j, i, Sprite.powerup_flames.getFxImage());
                        BombermanGame.activeEntities.add((ActiveEntity) object);

                        // layer 2 - Brick
                        BombermanGame.activeEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.map[i][j] = '*';
                        break;

                    case 's': // SPEED
                        // layer 0 - grass
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }

                        // layer 1 - PowerUp
                        object = new PowerUpSpeed(j, i, Sprite.powerup_speed.getFxImage());
                        BombermanGame.activeEntities.add((ActiveEntity) object);

                        // layer 2 - Brick
                        BombermanGame.activeEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        BombermanGame.map[i][j] = '*';
                        break;


                    default: // GRASS
                        if (BombermanGame.level == 1) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        } else if (BombermanGame.level == 2) {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass2.getFxImage()));
                        } else {
                            BombermanGame.stillObjects.add(new Grass(j, i, Sprite.grass3.getFxImage()));
                        }
                        BombermanGame.map[i][j] = ' ';
                        break;
                }

            }
        }
    }
}
