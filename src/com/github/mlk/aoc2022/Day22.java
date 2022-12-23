package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day22 {
    enum Space {
        AIR('.'), WALL('#'), WARP(' ');

        Space(char display) {
            this.display = display;
        }

        final char display;

    }

    record Point(int x, int y) {
        Point add(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        Point reset(Point other) {
            return new Point(other.x >= 0 ? other.x : x, other.y >= 0 ? other.y : y);
        }
    }

    static class Direction {
        Direction(Point offset, char draw, int pass) {
            this.offset = offset;
            this.draw = draw;
        }

        int pass;
        char draw;
        Point offset;
        Point reset;
        Direction turnLeft;
        Direction turnRight;

        @Override
        public String toString() {
            return "Direction[offset=" + offset.toString() + "]";
        }
    }

    record Movement(Point point, Direction d) {}

    static Direction left = new Direction(new Point(-1, 0), '<', 2);
    static Direction right = new Direction(new Point(1, 0), '>', 0);
    static Direction up  = new Direction(new Point(0, -1), '^', 3);
    static Direction down  = new Direction(new Point(0, 1), 'v', 1);

    static {
        left.turnLeft = down;
        left.turnRight = up;

        right.turnLeft = up;
        right.turnRight = down;
        right.reset = new Point(0, -1);

        up.turnLeft = left;
        up.turnRight = right;

        down.turnLeft = right;
        down.turnRight = left;
        down.reset = new Point(-1, 0);
    }

    public static void main(String[] arg) {


        String[] input = Day22Data.exampleMap.split("\n");
        String[] path = Day22Data.exampleMovement.replace("R", "\nR\n").replace("L", "\nL\n").split("\n");

        Space[][] map = new Space[input.length + 2][Arrays.stream(input).mapToInt(String::length).max().getAsInt() + 2];
        Arrays.fill(map[0], Space.WARP);
        Arrays.fill(map[map.length - 1], Space.WARP);

        left.reset = new Point(map[0].length -1, -1);
        up.reset = new Point(-1, map.length-1 );

        int y = 1;
        for(String line : input) {
            Arrays.fill(map[y], Space.WARP);

            int x = 1;
            for(char p : line.toCharArray()) {
                if(p == '#') {
                    map[y][x] = Space.WALL;
                } else if(p == '.') {
                    map[y][x] = Space.AIR;
                }
                x++;
            }

            y++;
        }

        //displayMap(map, List.of());

        Point me = new Point(1, 1);
        while(map[me.y][me.x] != Space.AIR) {
            me = me.add(right.offset);
        }
        me.add(left.offset);
        System.out.println(me);
        Direction meD = right;

        List<Movement> movementList = new ArrayList<>();
        movementList.add(new Movement(me, meD));

        for(String currentMovement : path) {
            System.out.println(currentMovement);

            if(currentMovement.isBlank()) {
                System.out.println("Blank");
            } else if(currentMovement.equals("L")) {
                meD = meD.turnLeft;
            } else if(currentMovement.equals("R")) {
                meD = meD.turnRight;
            } else {

                int moves = Integer.parseInt(currentMovement);
                for(int i = 0; i<moves; i++) {
                    Point next = me.add(meD.offset);
                    if(map[next.y][next.x] == Space.AIR) {
                        me = next;
                        movementList.add(new Movement(me, meD));
                    } if(map[next.y][next.x] == Space.WARP) {
                        next = next.reset(meD.reset);
                        while(map[next.y][next.x] == Space.WARP) {
                            next = next.add(meD.offset);
                        }
                        if(map[next.y][next.x] == Space.AIR) {
                            me = next;
                            movementList.add(new Movement(me, meD));
                        }
                    }
                }
            }
        }

        displayMap(map, movementList);

        System.out.println(me);

        System.out.println(1000 * me.y + (4 * me.x) + meD.pass);
    }

    static void displayMap(Space[][] spaces, List<Movement> dirs) {
        for(int y = 0; y<spaces.length; y++) {
            for(int x = 0; x<spaces[0].length; x++) {
                boolean found = false;
                for(Movement m : dirs) {
                    if(m.point.x == x && m.point.y == y) {
                        System.out.print(m.d.draw);
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    System.out.print(spaces[y][x].display);
                }
            }
            System.out.println();
        }
    }
}
