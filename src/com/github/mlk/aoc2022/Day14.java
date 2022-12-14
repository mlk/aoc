package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {
    static String testInput = """
            <retracted>""";

    static String input = """
            <retracted>""";

    public static void main(String... arg) {
        WallSet walls = WallSet.parse(input);

        MapPart[][] map = new MapPart[walls.maxY() + 1][walls.maxX() + 1];

        for(int idx = 0; idx < map.length; idx++) {
            Arrays.fill(map[idx], MapPart.AIR);
        }

        for(Wall wall : walls.walls()) {
            wall.drawWalls(map);
        }

        output(map, 494, 0, 503, 9);


        int index = 0;
        while(simulate(new Sand(new Point(500, 0)), map)) {
            index++;
        }

        System.out.println(index);

        output(map, 494, 0, 503, 9);
    }

    private static boolean simulate(Sand sand, MapPart[][] map) {
        try {
            Point fallenTo = null;
            do {
                fallenTo = sand.fall(map);

                if (fallenTo != null) {
                    sand = new Sand(fallenTo);
                }
            } while (fallenTo != null);

            map[sand.point.y][sand.point.x] = MapPart.SAND;

            return true;
        } catch(SandOutOfBoundsException e) {
            return false;
        }
    }

    record Point(int x, int y) {
        Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }

        public boolean inBounds(MapPart[][] map) {

            return x >= 0 && x < map[0].length && y >= 0 && y<map.length;
        }
    }


    record Sand(Point point) {
        static Point[]  dirs = {new Point(0, 1), new Point(-1, 1), new Point(1, 1)};

        Point fall(MapPart[][] map) {
            for(Point newLocation : Arrays.stream(dirs)
                    .map(x -> x.add(point))
                    .toList()) {
                if(!newLocation.inBounds(map)) {
                    throw new SandOutOfBoundsException();
                }
                if(!map[newLocation.y()][newLocation.x()].blocker) {
                    return newLocation;
                }
            }

            return null;
        }
    }

    static class SandOutOfBoundsException extends RuntimeException {}

    static void output(MapPart[][] map, int startX, int startY, int endX, int endY) {
        for(int y = startY; y<=endY; y++) {
            for(int x = startX; x<=endX; x++) {
                System.out.print(map[y][x].display);
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

    record Wall(List<WallPoint> points) {
        static Wall parse(String line) {
            String[] points = line.split(" -> ");
            List<WallPoint> wallPoints = new ArrayList<>();
            for(String point : points) {
                wallPoints.add(WallPoint.parse(point));
            }

            return new Wall(wallPoints);
        }

        int maxX() {
            return points.stream().mapToInt(WallPoint::x).max().getAsInt();
        }

        int maxY() {
            return points.stream().mapToInt(v -> v.y).max().getAsInt();
        }

        public void drawWalls(MapPart[][] map) {
            WallPoint from = points().get(0);
            for(int idx = 1; idx < points().size(); idx++) {
                WallPoint to = points().get(idx);
                int minX = Math.min(from.x(), to.x());
                int maxX = Math.max(from.x(), to.x());
                int minY = Math.min(from.y(), to.y());
                int maxY = Math.max(from.y(), to.y());
                for(int x = minX; x<=maxX; x++) {
                    for (int y = minY; y <=maxY; y++) {
                        map[y][x] = MapPart.WALL;
                    }
                }
                from = to;
            }
        }
    }

    record WallPoint(int x, int y) {
        static WallPoint parse(String point) {
            String[] xy = point.split(",");
            return new WallPoint(Integer.parseInt(xy[0].trim()), Integer.parseInt(xy[1].trim()));
        }
    }
}
