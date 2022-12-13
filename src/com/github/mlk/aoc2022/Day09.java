package com.github.mlk.aoc2022;

import java.util.HashSet;
import java.util.Set;

public class Day09 {
    static String input = """
            <retracted>""";
    static String data = """
            <retracted>""";

    public static void main(String... argv) {
        Point head = new Point(0, 0);
        Point[] tail = new Point[9];
        for(int i = 0; i<9;i++) {
            tail[i] = new Point(0, 0);
        }

        //System.out.println(head.follow(tail));
        //if(true)
         //   return;

        Set<Point> previousTailLocations = new HashSet<>();

        for(String line : data.split("\n")) {
            for(int i = 0; i < Integer.parseInt(line.substring(2)); i++) {
                switch (line.substring(0, 1)) {
                    case "R":
                        head = head.moveRight();
                        break;
                    case "L":
                        head = head.moveLeft();
                        break;
                    case "U":
                        head = head.moveUp();
                        break;
                    case "D":
                        head = head.moveDown();
                        break;
                }
                tail[0] = head.follow(tail[0]);
                for(int ti = 1; ti<9;ti++) {
                    tail[ti] = tail[ti-1].follow(tail[ti]);
                }

                previousTailLocations.add(tail[8]);
            }
        }
        int width = previousTailLocations.stream().map(Point::x).sorted((x, y) -> y-x).findFirst().get();
        int height = previousTailLocations.stream().map(Point::y).sorted((x, y) -> y-x).findFirst().get();
        char[][] data = new char[height + 1][width + 1];
        /*for(int i = 0; i<data.length; i++) {
            Arrays.fill(data[i], '.');
        }
        previousTailLocations.forEach(p -> data[p.y][p.x] = '#');

        for(int i = data.length-1; i>=0; i--) {
            System.out.println(new String(data[i]));
        }*/

        System.out.println(previousTailLocations.size());
    }


    record Point(int x, int y) {
        Point moveUp() {
            return new Point(x, y+1);
        }
        Point moveDown() {
            return new Point(x, y-1);
        }
        Point moveLeft() {
            return new Point(x-1, y);
        }
        Point moveRight() {
            return new Point(x+1, y);
        }

        Point follow(Point previousLocation) {
            int newX = previousLocation.x;
            int newY = previousLocation.y;

            if(x >= previousLocation.x - 1 && x <= previousLocation.x + 1 && y >= previousLocation.y - 1 && y <= previousLocation.y + 1) {
                return previousLocation;
            }

            if(x == previousLocation.x && y!= previousLocation.y) {
                if(y > previousLocation.y()) {
                    newY++;
                } else {
                    newY--;
                }
            } else if(y == previousLocation.y && x!= previousLocation.x) {
                if(x > previousLocation.x()) {
                    newX++;
                } else {
                    newX--;
                }
            } else {
                if(y > previousLocation.y()) {
                    newY++;
                } else {
                    newY--;
                }
                if(x > previousLocation.x()) {
                    newX++;
                } else {
                    newX--;
                }
            }

            return new Point(newX, newY);
        }
    }
}
