package com.github.mlk.aoc2022;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 {

    static int careAbout = 4000000;

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

            Point leftDownPoint = new Point(location().x() - (size+1), location.y);
            Point rightDownPoint = new Point(location().x() + (size+1), location.y);

            Point leftUpPoint = new Point(location().x() - (size+1), location.y);
            Point rightUpPoint = new Point(location().x() + (size+1), location.y);

            Point downRight = new Point(1, 1);
            Point downLeft = new Point(-1, 1);
            Point upRight = new Point(1, -1);
            Point upLeft = new Point(-1, -1);
            while(leftDownPoint.x != location.x) {
                add(points, leftDownPoint);
                add(points, rightDownPoint);
                leftDownPoint = leftDownPoint.add(downRight);
                rightDownPoint = rightDownPoint.add(downLeft);

                add(points, leftUpPoint);
                add(points, rightUpPoint);
                leftUpPoint = leftUpPoint.add(upRight);
                rightUpPoint = rightUpPoint.add(upLeft);
            }
            add(points, leftDownPoint);
            add(points, rightDownPoint);
            add(points, leftUpPoint);
            add(points, rightUpPoint);
            return points;
        }

        static void add(Set<Point> points, Point x) {
            if(x.x() > 0 && x.x < careAbout && x.y > 0 && x.y < careAbout) {
               points.add(x);
            }
        }
    }

    public static void main(String... arg) {
        String regex = "Sensor at (.*): closest beacon is at (.*)";
        Pattern pattern = Pattern.compile(regex);

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

}

