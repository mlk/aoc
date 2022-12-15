package com.github.mlk.aoc2022;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {

    static int careY = 4000000;

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

        public long freq() {
            return ((long)x) * 4000000 + y;
        }
    }


    record Sensor(Point location, int size) {
        Set<Point> getPerimeter() {

            Set<Point> points = new HashSet<>();

            Point leftStart = new Point(location().x() - (size+1), location.y);
            Point rightStart = new Point(location().x() + (size+1), location.y);

            Point leftUpStart = new Point(location().x() - (size+1), location.y);
            Point rightUpStart = new Point(location().x() + (size+1), location.y);


            Point dowRight = new Point(1, 1);
            Point dowLeft = new Point(-1, 1);
            Point upRight = new Point(1, -1);
            Point upLeft = new Point(-1, -1);
            while(leftStart.x != location.x) {
                add(points, leftStart);
                add(points, rightStart);
                leftStart = leftStart.add(dowRight);
                rightStart = rightStart.add(dowLeft);

                add(points, leftUpStart);
                add(points, rightUpStart);
                leftUpStart = leftUpStart.add(upRight);
                rightUpStart = rightUpStart.add(upLeft);
            }
            add(points, leftStart);
            add(points, rightStart);
            add(points, leftUpStart);
            add(points, rightUpStart);
            return points;
        }

        static void add(Set<Point> points, Point x) {
            if(x.x() > 0 && x.x < careY && x.y > 0 && x.y < careY) {
               points.add(x);
            }
        }
    }

    public static void main(String... arg) {

        String regex = "Sensor at (.*): closest beacon is at (.*)";
        Pattern pattern = Pattern.compile(regex);



        System.out.println("Building");
        List<Sensor> sensors = new ArrayList<>();
        List<Point> beacons = new ArrayList<>();

        for(String line : Day15Data.data.split("\n")) {

            Matcher m = pattern.matcher(line);
            m.find();
            Point sensor = Point.parse(m.group(1));
            Point beacon = Point.parse(m.group(2));
            int size = sensor.manhattanDistance(beacon);
            sensors.add(new Sensor(sensor, size));
            beacons.add(beacon);

        }



        sensors.stream().flatMap(x -> x.getPerimeter().stream())
                .collect(Collectors.toSet())
                .forEach(p -> {
            if (sensors.stream().noneMatch(s -> s.location().manhattanDistance(p) <= s.size())) {
                System.out.println(p + " " + p.freq());
            }
        });
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

        List<Point> getGetUnknownForLine(int y) {
            int minX = 0; //minX();
            int maxX = careY; //maxX();
            List<Point> unknowns = new ArrayList<>();
            for(int x =minX; x<= maxX; x++) {
                Point p = new Point(x, y);
                if(!map.containsKey(p)) {
                    unknowns.add(p);
                }
            }
            return unknowns;
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

