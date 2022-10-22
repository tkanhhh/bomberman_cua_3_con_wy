package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Bomber;
import uet.oop.bomberman.entities.ActiveEntity.moveable.Enemy.BOSS_UET;

public class HardMode {

    /**
     * Dijkstra algorithm to calculate path for enemy
     * Enemy's coordinate is x and y
     * Calculate based on path on map
     *
     * @return 0 ,1 ,2, 3: moveUp , moveDown , moveLeft, moveRight
     */

    public static Position dijkstra(BOSS_UET boss, int distance, char[][] map, char[][] bombmap){
        int[][] dists = new int[13][31];
        boolean[][] visiteds = new boolean[13][31];
        Position[][] parents = new Position[13][31];


        for(int i = 0; i < 13; i++){
            for(int j = 0; j < 31; j++){
                parents[i][j] = new Position();
            }
        }

        // INITIALIZATION
        parents[boss.getSmallY()][boss.getSmallX()].x = -1;
        parents[boss.getSmallY()][boss.getSmallX()].y = -1;
        for(int i = 0; i < 13; i++){
            for(int j = 0; j < 31; j++){
                dists[i][j] = 99;
                visiteds[i][j] = false;
            }
        }
        dists[boss.getSmallY()][boss.getSmallX()] = 0;

        //PROSES SEARCH SHORTEST PATH
        for(int count = 1; count < (31*13); count++){
            //Travel all vertices except target
            int min = 99;
            Position chosenV = new Position();
            for(int i = 0; i < 13; i++){
                for(int j = 0; j < 31; j++){
                    if (!visiteds[i][j] && (dists[i][j] < min)){
                        chosenV.x = j;
                        chosenV.y = i;
                        min = dists[i][j];
                    }
                }
            }

            for (int i = 0; i < BombermanGame.activeEntities.size(); i++) {
                if (BombermanGame.activeEntities.get(i) instanceof Bomber) {
                    if (min != 99) {
                        visiteds[chosenV.y][chosenV.x] = true;  //vertex selected
                        if ((chosenV.y == BombermanGame.activeEntities.get(i).getSmallY()) && (chosenV.x == BombermanGame.activeEntities.get(i).getSmallX()))  //check if the target is a destination
                        {
                            int m = chosenV.y;
                            int n = chosenV.x;
                            Position path = new Position();

                            while ((n != boss.getSmallX()) || (m != boss.getSmallY())) {  // Path Backtracking Process
                                path.x = n;
                                path.y = m;
                                m = parents[path.y][path.x].y;
                                n = parents[path.y][path.x].x;
                            }
                            distance = dists[chosenV.y][chosenV.x];
                            return path;
                        }

                        //update the value of the selected vertex's neighbor distance
                        int left = chosenV.x - 1;
                        int right = chosenV.x + 1;
                        int up = chosenV.y - 1;
                        int down = chosenV.y + 1;
                        if ((left >= 0) && (map[chosenV.y][left] != '#') && (map[chosenV.y][left] != '%') && (map[chosenV.y][left] != '*') && (bombmap[chosenV.y][left] != '@') && (!visiteds[chosenV.y][left])) {
                            //if not out of bounds and not wall and not visited
                            if (dists[chosenV.y][left] > (dists[chosenV.y][chosenV.x] + 1)) {
                                dists[chosenV.y][left] = dists[chosenV.y][chosenV.x] + 1;
                                parents[chosenV.y][left].x = chosenV.x;
                                parents[chosenV.y][left].y = chosenV.y;
                            }

                        }
                        if ((right < 31) && (map[chosenV.y][right] != '#') && (map[chosenV.y][right] != '%') && (map[chosenV.y][right] != '*') && (bombmap[chosenV.y][right] != '@') && (!visiteds[chosenV.y][right])) {
                            //if not out of bounds and not wall and not visited
                            if (dists[chosenV.y][right] > (dists[chosenV.y][chosenV.x] + 1)) {
                                dists[chosenV.y][right] = dists[chosenV.y][chosenV.x] + 1;
                                parents[chosenV.y][right].x = chosenV.x;
                                parents[chosenV.y][right].y = chosenV.y;
                            }

                        }
                        if ((up >= 0) && (map[up][chosenV.x] != '#') && (map[up][chosenV.x] != '%') && (map[up][chosenV.x] != '*') && (bombmap[up][chosenV.x] != '@') && (!visiteds[up][chosenV.x])) {
                            //if not out of bounds and not wall and not visited
                            if (dists[up][chosenV.x] > (dists[chosenV.y][chosenV.x] + 1)) {
                                dists[up][chosenV.x] = dists[chosenV.y][chosenV.x] + 1;
                                parents[up][chosenV.x].x = chosenV.x;
                                parents[up][chosenV.x].y = chosenV.y;
                            }
                        }
                        if ((down < 13) && (map[down][chosenV.x] != '#') && (map[down][chosenV.x] != '%') && (map[down][chosenV.x] != '*') && (bombmap[down][chosenV.x] != '@') && (!visiteds[down][chosenV.x])) {
                            //if not out of bounds and not wall and not visited
                            if (dists[down][chosenV.x] > (dists[chosenV.y][chosenV.x] + 1)) {
                                dists[down][chosenV.x] = dists[chosenV.y][chosenV.x] + 1;
                                parents[down][chosenV.x].x = chosenV.x;
                                parents[down][chosenV.x].y = chosenV.y;
                            }
                        }
                    } else { // no candidate anymore
                        break;
                    }
                }
            }
        }

        //path not found
        Position path = new Position();
        distance = 99;
        path.x = 99;
        path.y = 99;
        return path;
    }

    public static Position findClosest(BOSS_UET boss) {

        int TempDistance = 0;
        int MinDistance = 99;
        Position tempPath;
        Position Path = new Position();
        Path.x = 99;
        Path.y = 99; //default value


        tempPath = dijkstra(boss, TempDistance, BombermanGame.map, BombermanGame.bombmap);
        if (TempDistance < MinDistance) {
            MinDistance = TempDistance;
            Path = tempPath;
        }

        return Path;
    }

    public static int Moving(BOSS_UET boss, Position path, char[][] map, char[][] bombmap){
        if ((boss.getSmallX() - path.x) > 0) {
            char c = map[boss.getSmallY()][boss.getSmallX() - 1];
            char b = bombmap[boss.getSmallY()][boss.getSmallX() - 1];
            if (c != '%' && c != '#' && c != '*' && b != '@') {
                return 2;
            }
        }
        else if ((boss.getSmallX() - path.x) < 0){
            char c = map[boss.getSmallY()][boss.getSmallX() + 1];
            char b = bombmap[boss.getSmallY()][boss.getSmallX() + 1];
            if (c != '%' && c != '#' && c != '*' && b != '@') {
                return 3;
            }
        }
        else if ((boss.getSmallY() - path.y) > 0){
            char c = map[boss.getSmallY() - 1][boss.getSmallX()];
            char b = bombmap[boss.getSmallY() - 1][boss.getSmallX()];
            if (c != '%' && c != '#' && c != '*' && b != '@') {
                return 0;
            }
        }
        else if ((boss.getSmallY() - path.y) < 0){
            char c = map[boss.getSmallY() + 1][boss.getSmallX()];
            char b = bombmap[boss.getSmallY() + 1][boss.getSmallX()];
            if (c != '%' && c != '#' && c != '*' && b != '@') {
                return 1;
            }
        }
        else {
            return EasyMode.calculateDirection(boss.getSmallY(), boss.getSmallX(), map);
        }
        return EasyMode.calculateDirection(boss.getSmallY(), boss.getSmallX(), map);
    }
}