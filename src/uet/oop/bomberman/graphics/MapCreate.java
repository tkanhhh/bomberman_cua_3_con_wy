package uet.oop.bomberman.graphics;


import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MapCreate {
    private int width = 0;
    private int height = 0;
    private int[][] toaDo;
    public static final List<Entity> block = new ArrayList<>();
    public ArrayList<Integer> map = new ArrayList<Integer>();
    public void MapCreate() {
        try {
            String inputFile = "res/levels/Level1.txt";
            FileReader file = new FileReader(inputFile);
            Scanner input = new Scanner(file);

            //lấy token nha
            StringTokenizer elementsInFile = new StringTokenizer(input.nextLine());

            height = Integer.parseInt(elementsInFile.nextToken());
            width = Integer.parseInt(elementsInFile.nextToken());

            while (input.hasNextLine()) {
                toaDo = new int[width][height];
                for (int i = 0; i < height; i++) {
                    StringTokenizer elementTile = new StringTokenizer(input.nextLine());
                    for (int j = 0; j < width; j++) {
                        int element = Integer.parseInt(elementTile.nextToken());
                        Entity entity;

                        switch (element) {
                            case 2:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }

                        toaDo[j][i] = element;
                        block.add(entity);
                    }
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }






    }
}
