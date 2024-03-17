package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.github.mlk.aoc2022.Day1Data.input;

public class Day14 {

    public static void main(String... arg) {
        WallSet walls = WallSet.parse(input);
        Map map = new Map(walls.maxX(), walls.maxY(), true, new HashMap<>());
        walls.apply(map);

        map.output(494, 0, 503, 11);

        int index = 0;
        Point startLocation = new Point(500, 0);
        try {
            do {
                index++;
            } while(!simulate(new Sand(startLocation), map).point().equals(startLocation));
        } catch(OutOfBoundsException e) {
            index--;
        }

        System.out.println(index);

        map.output(480, 0, 520, 12);
    }

    record Map(int maxX, int maxY, boolean boundsChecking, HashMap<Point, MapPart> map) {
        void set(Point p, MapPart part) {
            map.put(p, part);
        }

        MapPart get(Point p) {
            if(!inBound(p)) {
                throw new OutOfBoundsException();
            }

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

        void output(int startX, int startY, int endX, int endY) {
            for(int y = startY; y<=endY; y++) {
                for(int x = startX; x<=endX; x++) {
                    System.out.print(get(new Point(x, y)).display);
                }
                System.out.println();
            }
        }
    }

    private static Sand simulate(Sand sand, Map map) {
        Point fallenTo = null;
        do {
            fallenTo = sand.fallsTo(map);

            if (fallenTo != null) {
                sand = new Sand(fallenTo);
            }
        } while (fallenTo != null);

        map.set(sand.point, MapPart.SAND);
        return sand;
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

        Point fallsTo(Map map) {
            for(Point newLocation : Arrays.stream(dirs)
                    .map(x -> x.add(point))
                    .toList()) {

                if(!map.get(newLocation).blocker) {
                    return newLocation;
                }
            }

            return null;
        }
    }

    static class OutOfBoundsException extends RuntimeException {}

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

        void apply(Map map) {
            for(Wall wall : walls()) {
                wall.apply(map);
            }
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

        public void apply(Map map) {
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
