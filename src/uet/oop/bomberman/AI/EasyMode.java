package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;

import java.util.Random;

/**
 * AI easy mode: enemy moves randomly, find the movable path
 */
public class EasyMode {
    public static int calculateDirection(int x, int y, char[][] map) {
        Random random = new Random();
        int key = random.nextInt(4);
        boolean find = false;
        while (!find) {
            key = random.nextInt(4);
            if (key == 0) { // moveUp
                if (map[x - 1][y] == ' ' && BombermanGame.bombmap[x-1][y] == ' ') {
                    find = true;
                    break;
                }
            }
            if (key == 1) { // moveDown
                if (map[x + 1][y] == ' '  && BombermanGame.bombmap[x+1][y] == ' ') {
                    find = true;
                    break;
                }
            }
            if (key == 3) { // moveRight
                if (map[x][y + 1] == ' '  && BombermanGame.bombmap[x][y+1] == ' ') {
                    find = true;
                    break;
                }
            }
            if (key == 2) { // moveLeft
                if (map[x][y - 1] == ' '  && BombermanGame.bombmap[x][y-1] == ' ') {
                    find = true;
                    break;
                }
            }
        }
        return key;
    }
}
