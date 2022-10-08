package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;

import java.util.Random;

/**
 * Ai easy mode: enemy moves randomly, find the movable path
 */
public class EasyMode {
    public static int calculateDirection(int x, int y, char[][] map) {
        Random random = new Random(); // random 1 in 4 paths
        int key = random.nextInt(4); // a path to move
        boolean found = false; // if found a path
        while (!found) {
            key = random.nextInt(4);
            if (key == 0) { // move up
                if (map[x - 1][y] == ' ' && BombermanGame.bombmap[x-1][y] == ' ') {
                    found = true;
                    break;
                }
            }
            if (key == 1) { // move down
                if (map[x + 1][y] == ' '  && BombermanGame.bombmap[x+1][y] == ' ') {
                    found = true;
                    break;
                }
            }
            if (key == 2) { // move left
                if (map[x][y - 1] == ' '  && BombermanGame.bombmap[x][y-1] == ' ') {
                    found = true;
                    break;
                }
            }
            if (key == 3) { // move right
                if (map[x][y + 1] == ' '  && BombermanGame.bombmap[x][y+1] == ' ') {
                    found = true;
                    break;
                }
            }
        }
        return key;
    }
}
