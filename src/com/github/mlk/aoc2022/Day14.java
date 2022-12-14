package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.github.mlk.aoc2022.Day14Data.input;
import static com.github.mlk.aoc2022.Day14Data.testInput;

public class Day14 {

    public static void main(String... arg) {
        WallSet walls = WallSet.parse(testInput);

        Map map = new Map(walls.maxX(), walls.maxY(), false, new HashMap<>());

        for(Wall wall : walls.walls()) {
            wall.drawWalls(map);
        }

        output(map, 494, 0, 503, 11);


        int index = 1;
        while(simulate(new Sand(new Point(500, 0)), map)) {
            index++;
        }

        System.out.println(index);

        output(map, 480, 0, 520, 12);
    }

    record Map(int maxX, int maxY, boolean boundsChecking, HashMap<Point, MapPart> map) {
        void set(Point p, MapPart part) {
            map.put(p, part);
        }

        MapPart get(Point p) {
            if(p.y() >= maxY + 2) {
                return MapPart.WALL;
            }

            return map.getOrDefault(p, MapPart.AIR);
        }

        public boolean inBound(Point p) {
            if(boundsChecking) {
                return p.x >= 0 && p.x <= maxX && p.y >= 0 && p.y<=maxY;
            } else {
                return true;
            }
        }
    }

    private static boolean simulate(Sand sand, Map map) {
        try {
            Point fallenTo = null;
            do {
                fallenTo = sand.fall(map);

                if (fallenTo != null) {
                    sand = new Sand(fallenTo);
                }
            } while (fallenTo != null);


            map.set(sand.point, MapPart.SAND);

            return !sand.point.equals(new Point(500, 0));

        } catch(SandOutOfBoundsException e) {
            return false;
        }
    }

    record Point(int x, int y) {
        Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }

        static Point parse(String point) {
            String[] xy = point.split(",");
            return new Point(Integer.parseInt(xy[0].trim()), Integer.parseInt(xy[1].trim()));
        }
    }


    record Sand(Point point) {
        static Point[]  dirs = {new Point(0, 1), new Point(-1, 1), new Point(1, 1)};

        Point fall(Map map) {
            for(Point newLocation : Arrays.stream(dirs)
                    .map(x -> x.add(point))
                    .toList()) {
                if(!map.inBound(newLocation)) {
                    throw new SandOutOfBoundsException();
                }
                if(!map.get(newLocation).blocker) {
                    return newLocation;
                }
            }

            return null;
        }
    }

    static class SandOutOfBoundsException extends RuntimeException {}

    static void output(Map map, int startX, int startY, int endX, int endY) {
        for(int y = startY; y<=endY; y++) {
            for(int x = startX; x<=endX; x++) {
                System.out.print(map.get(new Point(x, y)).display);
            }
            System.out.println();
        }
    }

    enum MapPart {
        SAND(true, 'o'), WALL(true, '#'), AIR(false, '.');

        final boolean blocker;
        final char display;

        MapPart(boolean blocker, char display) {
            this.blocker = blocker;
            this.display = display;
        }
    }

    record WallSet(List<Wall> walls) {
        static WallSet parse(String data) {
            List<Wall> walls = new ArrayList<>();
            for(String line : data.split("\n")) {
                walls.add(Wall.parse(line));
            }
            return new WallSet(walls);
        }

        int maxX() {
            return walls.stream().mapToInt(Wall::maxX).max().getAsInt();
        }

        int maxY() {
            return walls.stream().mapToInt(Wall::maxY).max().getAsInt();
        }
    }

    record Wall(List<Point> points) {
        static Wall parse(String line) {
            String[] points = line.split(" -> ");
            List<Point> wallPoints = new ArrayList<>();
            for(String point : points) {
                wallPoints.add(Point.parse(point));
            }

            return new Wall(wallPoints);
        }

        int maxX() {
            return points.stream().mapToInt(Point::x).max().getAsInt();
        }

        int maxY() {
            return points.stream().mapToInt(Point::y).max().getAsInt();
        }

        public void drawWalls(Map map) {
            Point from = points().get(0);
            for(int idx = 1; idx < points().size(); idx++) {
                Point to = points().get(idx);
                int minX = Math.min(from.x(), to.x());
                int maxX = Math.max(from.x(), to.x());
                int minY = Math.min(from.y(), to.y());
                int maxY = Math.max(from.y(), to.y());
                for(int x = minX; x<=maxX; x++) {
                    for (int y = minY; y <=maxY; y++) {
                        map.set(new Point(x, y), MapPart.WALL);
                    }
                }
                from = to;
            }
        }
    }
}
