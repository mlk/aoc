package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    static int careY = 2000000;

    record Point(int x, int y) {
        static Point parse(String value) {
            String[] xy = value.split(", ");
            return new Point(Integer.parseInt(xy[0].substring(2)), Integer.parseInt(xy[1].substring(2)));
        }

        Point add(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        int manhattanDistance(Point other) {
            return Math.abs(x - other.x()) + Math.abs(y - other.y());
        }

        List<Point> circle(int size) {
            List<Point> points = new ArrayList<>();

            for(int cx = x - size; cx < x + size; cx++) {
                //for(int cy = y - size; cy < y + size; cy++) {
                int cy = careY;
                    Point p = new Point(cx, cy);

                    if(manhattanDistance(p) <= size) {
                        points.add(p);
                    }
                //}
            }
            return points;
        }
    }

    public static void main(String... arg) {
        String regex = "Sensor at (.*): closest beacon is at (.*)";
        Pattern pattern = Pattern.compile(regex);

        Map map = new Map(new HashMap<>());

        for(String line : Day15Data.data.split("\n")) {
            Matcher m = pattern.matcher(line);
            m.find();
            Point sensor = Point.parse(m.group(1));
            Point beacon = Point.parse(m.group(2));
            int size = sensor.manhattanDistance(beacon);
            for (Point empty : sensor.circle(size)) {
                map.empty(empty);
            }
            map.map.put(sensor, Thingie.SENSOR);
            map.map.put(beacon, Thingie.BEACON);
        }

        List<Thingie> line = map.getLine(careY);
        System.out.println(line.size());
        System.out.println(line.stream().filter(x -> !x.equals(Thingie.UNKNOWN) && !x.equals(Thingie.BEACON) ).count());

    }

    enum Thingie {
        UNKNOWN('.'), SENSOR('S'), BEACON('B'), EMPTY('#');
        final char display;

        Thingie(char display) {
            this.display = display;
        }
    }

    record Map(HashMap<Point, Thingie> map) {
        void empty(Point point) {
            map.putIfAbsent(point, Thingie.EMPTY);
        }

        List<Thingie> getLine(int y) {
            int minX = minX();
            int maxX = maxX();

            List<Thingie> items = new ArrayList<>();
            for(int x =minX; x<= maxX; x++) {
                items.add(get(new Point(x, y)));
            }
            return items;
        }

        Thingie get(Point xy) {
            return map.getOrDefault(xy, Thingie.UNKNOWN);
        }

        int minX() {
            return map.keySet().stream().mapToInt(x -> x.x).min().getAsInt();
        }

        int maxX() {
            return map.keySet().stream().mapToInt(x -> x.x).max().getAsInt();
        }
    }
}

