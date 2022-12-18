package com.github.mlk.aoc2022;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17 {

    static boolean[][] line = new boolean[][]{{true, true, true, true}};
    static boolean[][] plus = new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, true, false}
        };
    static boolean[][] mirrorL = new boolean[][]{
            {true, true, true},
            {false, false, true},
            {false, false, true},
    };
    static boolean[][] lineDown = new boolean[][] {{true},{true},{true},{true}};
    static boolean[][] square = new boolean[][]{
            { true, true},
            { true, true},
    };

    static List<boolean[][]> shapes = List.of(line, plus, mirrorL, lineDown, square);

    static class WorldMap {
        private final List<boolean[]> data = new ArrayList<>();
        private long offSet = 0;

        long size() {
            return offSet + data.size();
        }

        void addShape(Piece shape) {
            addShape(shape.shape, shape.x, shape.y);
        }

        void removeShape(Piece shape) {
            removeShape(shape.shape, shape.x, shape.y);
        }

        public void addShape(boolean[][] shape, int x, int y) {
            for(int cy = 0; cy< shape.length; cy++) {
                boolean[] line = getLineAndAdd((y + cy));
                for(int cx = 0; cx< shape[0].length; cx++) {
                    if(shape[cy][cx]) {
                         line[x + cx] = true;
                    }
                }
            }
        }

        public void removeShape(boolean[][] shape, int x, int y) {
            for(int cy = 0; cy< shape.length; cy++) {
                boolean[] line = getLineAndAdd(y + cy);
                for(int cx = 0; cx< shape[0].length; cx++) {
                    if(shape[cy][cx]) {
                        line[x + cx] = false;
                    }
                }
            }
        }

        public void printLines(int lines) {
            for(int y = lines; y>=-1; y--) {
                boolean[] line = getLine(y);

                for(int x = 0; x<7; x++) {
                    if(line[x]) {
                        System.out.print("#");
                    } else {
                        System.out.print(" ");
                    }

                }
                System.out.println();
            }
        }

        public boolean[] getLineAndAdd(int y) {
            long offsetY = y - offSet;

            for(int i = data.size(); i<=offsetY; i++) {
                data.add(new boolean[7]);
            }

            while(offsetY > data.size()) {
                data.add(new boolean[7]);
            }

            return data.get((int)offsetY);
        }

        public boolean[] getLine(int y) {
            long offsetY = y - offSet;

            if(y < offSet) {
                boolean[] floor = new boolean[7];
                Arrays.fill(floor, true);
                return floor;
            }

            if(data.size() <= offsetY) {
                return new boolean[7];
            }

            return data.get((int)offsetY);

        }

        boolean collides(boolean[][] shape, int x, int y, int height, int width) {

            for (int cy = 0; cy < height; cy++) {
                boolean[] line = getLine(((cy + y)));
                for (int cx = 0; cx < width; cx++) {
                    if (line[x + cx] && shape[cy][cx]) {
                        return true;
                    }
                }

            }
            return false;
        }

        void offset() {
            if(data.size() > 2000) {
                //System.out.println("ofset " + data.size());
                offSet += 1000;
                for (int i = 0; i < 1000; i++) {
                    data.remove(0);
                }
                System.gc();
                System.gc();
            }
        }
    }

    static class Piece {
        boolean[][] shape;
        int x;
        int y;
        int width;

        Piece(boolean[][] shape,
        int x,
        int y) {
            this.shape = shape;
            this.x = x;
            this.y = y;
            width = shape[0].length;
        }

        void left(WorldMap map) {
            if(x > 0) {
                int newX = x - 1;
                if(map.collides(shape, newX, y, shape.length, width)) {
                    return;
                }
                x = newX;
            }

        }
        void right(WorldMap map) {
            //System.out.println("x" + x + " width" + width + " " + (x+width) );
            if((x + width) < 7) {
                int newX = x + 1;
                //System.out.println(x + " - " + newX + " " + width);

                if(map.collides(shape, newX, y, shape.length, width)) {
                    return;
                }
                x = newX;
            }
        }

        boolean drop(WorldMap worldMap) {
            if(worldMap.collides(shape, x, y-1, shape.length, width)){
                return true;
            }
            y--;
            return false;
        }
    }

    public static void main(String... arg) {

        WorldMap map = new WorldMap();

        Piece piece = new Piece(line, 2, 3);
        char[] input = Day17Data.data.toCharArray();

        System.out.println(input.length + " / " + shapes.size());
        System.out.println(1000000000000L / 200000 );

        int actionIndex = 0;
        int shapeIndex = 1;
        long actions = 0;

        long start = System.currentTimeMillis();

        while(true) {
            if(input[actionIndex] == '>') piece.right(map);
            if(input[actionIndex] == '<') piece.left(map);
            if(piece.drop(map)) {
                map.addShape(piece);

                int topY = -1;
                for(int y = piece.y; y< map.size() + 2; y++) {
                    //System.out.print(y + " ");
                    boolean[] dd = map.getLine(y);
                    boolean empty = true;
                    for(boolean n : dd) {
                        if(n) empty = false;
                    }
                    //System.out.println(empty);
                    if(empty) {
                        topY = y;
                        break;
                    }
                }
                //System.out.println(topY + " " + piece.y);
                piece = new Piece(shapes.get(shapeIndex), 2, topY + 3);
                shapeIndex++;
                actions++;
                if(shapeIndex >= shapes.size()) shapeIndex=0;
                if(map.data.size() > 2000) {
                    map.offset();
                }
                if(shapeIndex == 0 && actionIndex == 0) {
                    System.out.println(actions);
                }

                if(actions % 5000000 == 0) {
                    long time = System.currentTimeMillis() - start;
                    System.out.println(actions + " " + map.size() + " " + map.data.size() + " " + ((time/1000)/60));

                }
                if(actions > 1000000000000L)
                 break;
            }
            actionIndex++;
            if(actionIndex>=input.length) actionIndex = 0;
        }


//        map.printLines(20);

        System.out.println(piece.y + piece.shape.length);
        System.out.println(map.data.size() + map.offSet);
        System.out.println(map.size());

        getTop(piece, map);


    /*
        System.out.println(test.drop(map));
        System.out.println(test.drop(map));
        System.out.println(test.drop(map));

     */
    }

    static int getTop(Piece piece, WorldMap map) {
        System.out.println("getTop " + piece.x + "x" +  piece.y + "   " + map.data.size() + 2);
        int topY = -1;
        for(int y = piece.y-3; y>= map.size() + 2; y++) {
            System.out.print(" >> " + y);
            boolean[] dd = map.getLine(y);
            boolean empty = true;
            for(boolean n : dd) {
                if(n) empty = false;
            }
            System.out.println(empty);
            if(empty) {
                topY = y;
                break;
            }
        }
        System.out.println(topY + " " + piece.y);

        return topY;
    }
}

