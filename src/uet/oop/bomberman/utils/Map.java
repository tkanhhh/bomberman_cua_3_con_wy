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
    public static int Dsa_num = 0;
    public static int Oop_num = 0;
    public static int Ca_num = 0;
    public static int s_item_num = 0;
    public static int b_item_num = 0;
    public static int f_item_num = 0;

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

            //Random portal
            while (true) {
                int tmp1 = (int)Math.floor(Math.random() * (height));
                int tmp2 = (int)Math.floor(Math.random() * (width));

                if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 2 && tmp2 <= 2) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                    map[tmp1][tmp2] = 'x';
                    break;
                }
            }

            // Number of items each level
            if (BombermanGame.level == 1) {
                s_item_num = 1;
                b_item_num = 1;
                f_item_num = 1;
            } else if (BombermanGame.level == 2) {
                s_item_num = 1;
                b_item_num = 2;
                f_item_num = 1;
            } else {
                s_item_num = 1;
                b_item_num = 3;
                f_item_num = 1;
            }

            //Random speed item
            for (int i = 0; i < s_item_num; i++) {
                while (true) {
                    int tmp1 = (int) Math.floor(Math.random() * (height));
                    int tmp2 = (int) Math.floor(Math.random() * (width));

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 2 && tmp2 <= 2) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        map[tmp1][tmp2] = 's';
                        break;
                    }
                }
            }

            //Random flame item
            for (int i = 0; i < f_item_num; i++ ) {
                while (true) {
                    int tmp1 = (int) Math.floor(Math.random() * (height));
                    int tmp2 = (int) Math.floor(Math.random() * (width));

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 2 && tmp2 <= 2) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        map[tmp1][tmp2] = 'f';
                        break;
                    }
                }
            }

            //Random bomb item
            for (int i = 0; i < b_item_num; i++) {
                while (true) {
                    int tmp1 = (int) Math.floor(Math.random() * (height));
                    int tmp2 = (int) Math.floor(Math.random() * (width));

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 2 && tmp2 <= 2) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        map[tmp1][tmp2] = 'b';
                        break;
                    }
                }
            }

            //Random brick
            for (int i = 0; i < 50; i++) {
                while (true) {
                    int tmp1 = (int)Math.floor(Math.random() * (height - 1 - 1 + 1) + 1);
                    int tmp2 = (int)Math.floor(Math.random() * (width - 1 - 1 + 1) + 1);

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 2 && tmp2 <= 2) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        map[tmp1][tmp2] = '*';
                        break;
                    }
                }
            }

            // Number of enemies each level
            if (BombermanGame.level == 1) {
                Dsa_num = 1;
                Oop_num = 0;
                Ca_num = 0;
            } else if (BombermanGame.level == 2) {
                Dsa_num = 1;
                Oop_num = 1;
                Ca_num = 0;
            } else {
                Dsa_num = 1;
                Oop_num = 1;
                Ca_num = 1;
            }

            //Random dsa
            for (int i = 0; i < Dsa_num; i++) {
                while (true) {
                    int tmp1 = (int)Math.floor(Math.random() * (height - 1 - 1 + 1) + 1);
                    int tmp2 = (int)Math.floor(Math.random() * (width - 1 - 1 + 1) + 1);

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 4 && tmp2 <= 4) && !(tmp1 >= height - 4 && tmp2 >= width - 4)) {
                        if (!((map[tmp1 - 1][tmp2] != ' ') && (map[tmp1][tmp2 - 1] != ' ') && (map[tmp1 + 1][tmp2] != ' ') && (map[tmp1][tmp2 + 1] != ' '))) {
                            map[tmp1][tmp2] = '1';
                            break;
                        }
                    }
                }
            }

            //Random oop
            for (int i = 0; i < Oop_num; i++) {
                while (true) {
                    int tmp1 = (int)Math.floor(Math.random() * (height - 1 - 1 + 1) + 1);
                    int tmp2 = (int)Math.floor(Math.random() * (width - 1 - 1 + 1) + 1);

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 4 && tmp2 <= 4) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        if (!((map[tmp1 - 1][tmp2] != ' ') && (map[tmp1][tmp2 - 1] != ' ') && (map[tmp1 + 1][tmp2] != ' ') && (map[tmp1][tmp2 + 1] != ' '))) {
                            map[tmp1][tmp2] = '2';
                            break;
                        }
                    }
                }
            }

            //Random CA
            for (int i = 0; i < Ca_num; i++) {
                while (true) {
                    int tmp1 = (int)Math.floor(Math.random() * (height - 1 - 1 + 1) + 1);
                    int tmp2 = (int)Math.floor(Math.random() * (width - 1 - 1 + 1) + 1);

                    if (map[tmp1][tmp2] == ' ' && tmp1 != 7 && tmp2 != 15 && !(tmp1 <= 4 && tmp2 <= 4) && !(tmp1 >= height - 2 && tmp2 >= width - 2)) {
                        if (!((map[tmp1 - 1][tmp2] != ' ') && (map[tmp1][tmp2 - 1] != ' ') && (map[tmp1 + 1][tmp2] != ' ') && (map[tmp1][tmp2 + 1] != ' '))) {
                            map[tmp1][tmp2] = '3';
                            break;
                        }
                    }
                }
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
