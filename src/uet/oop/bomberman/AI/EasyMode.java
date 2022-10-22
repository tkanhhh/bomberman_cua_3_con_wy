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
        int tmp0 = 0, tmp1 = 0, tmp2 = 0, tmp3 = 0; //check if can move up, down, left, right
        while (!find) {
            key = random.nextInt(4);
            if (key == 0) { // moveUp
                if (map[x - 1][y] == ' ' && BombermanGame.bombmap[x-1][y] == ' ') {
                    find = true;
                    tmp0 = 0;
                    break;
                } else {
                    tmp0++;
                }
            }
            if (key == 1) { // moveDown
                if (map[x + 1][y] == ' '  && BombermanGame.bombmap[x+1][y] == ' ') {
                    find = true;
                    tmp1 = 0;
                    break;
                } else {
                    tmp1++;
                }
            }
            if (key == 3) { // moveRight
                if (map[x][y + 1] == ' '  && BombermanGame.bombmap[x][y+1] == ' ') {
                    find = true;
                    tmp3 = 0;
                    break;
                } else {
                    tmp3++;
                }
            }
            if (key == 2) { // moveLeft
                if (map[x][y - 1] == ' '  && BombermanGame.bombmap[x][y-1] == ' ') {
                    find = true;
                    tmp2 = 0;
                    break;
                } else {
                    tmp2++;
                }
            }
            if (tmp1 > 0 && tmp2 > 0 && tmp3 > 0 && tmp0 > 0) { // if cannot move -> break
                key = 4;
                break;
            }
        }
        return key;
    }
}
