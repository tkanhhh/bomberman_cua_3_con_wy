package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;

public class HardMode {

    /**
     * BFS algorithm to calculate path for enemy
     * Enemy's coordinate is x and y
     * Tính toán đường đi dựa trên map truyền vào
     *
     * @param x: the x-coordinate
     * @param y: the y-coordinate
     * @return 0 ,1 ,2, 3: moveUp , moveDown , moveLeft, moveRight
     */
    public static int BFS(int x, int y, char[][] map) {
        for (int i = 0; i < BombermanGame.activeEntities.size(); i++) {
            if (BombermanGame.activeEntities.get(i) instanceof Bomber) {
                if (x - 2 == BombermanGame.activeEntities.get(i).getSmallY() && y == BombermanGame.activeEntities.get(i).getSmallX()) {
                    return 0;
                } else if (x + 2 == BombermanGame.activeEntities.get(i).getSmallY() && y == BombermanGame.activeEntities.get(i).getSmallX()) {
                    return 1;
                } else if (x == BombermanGame.activeEntities.get(i).getSmallY() && y - 2 == BombermanGame.activeEntities.get(i).getSmallX()) {
                    return 2;
                } else if (x == BombermanGame.activeEntities.get(i).getSmallY() &&  y + 2 == BombermanGame.activeEntities.get(i).getSmallX() ) {
                    return 3;
                } else {
                    return EasyMode.calculateDirection(x, y, map);
                }
            }

        }
        return EasyMode.calculateDirection(x, y, map);
    }
}


