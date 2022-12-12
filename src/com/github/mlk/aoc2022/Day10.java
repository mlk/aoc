package com.github.mlk.aoc2022;

import java.util.Arrays;

public class Day10 {
    static String input = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx -35
            addx 1
            addx 24
            addx -19
            addx 1
            addx 16
            addx -11
            noop
            noop
            addx 21
            addx -15
            noop
            noop
            addx -3
            addx 9
            addx 1
            addx -3
            addx 8
            addx 1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx -36
            noop
            addx 1
            addx 7
            noop
            noop
            noop
            addx 2
            addx 6
            noop
            noop
            noop
            noop
            noop
            addx 1
            noop
            noop
            addx 7
            addx 1
            noop
            addx -13
            addx 13
            addx 7
            noop
            addx 1
            addx -33
            noop
            noop
            noop
            addx 2
            noop
            noop
            noop
            addx 8
            noop
            addx -1
            addx 2
            addx 1
            noop
            addx 17
            addx -9
            addx 1
            addx 1
            addx -3
            addx 11
            noop
            noop
            addx 1
            noop
            addx 1
            noop
            noop
            addx -13
            addx -19
            addx 1
            addx 3
            addx 26
            addx -30
            addx 12
            addx -1
            addx 3
            addx 1
            noop
            noop
            noop
            addx -9
            addx 18
            addx 1
            addx 2
            noop
            noop
            addx 9
            noop
            noop
            noop
            addx -1
            addx 2
            addx -37
            addx 1
            addx 3
            noop
            addx 15
            addx -21
            addx 22
            addx -6
            addx 1
            noop
            addx 2
            addx 1
            noop
            addx -10
            noop
            noop
            addx 20
            addx 1
            addx 2
            addx 2
            addx -6
            addx -11
            noop
            noop
            noop
            """;

    static String data = """
            addx 2
            addx 15
            addx -11
            addx 6
            noop
            noop
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx 7
            addx -1
            addx 3
            addx 1
            addx 5
            addx 1
            noop
            addx -38
            noop
            addx 1
            addx 6
            addx 3
            noop
            addx -8
            noop
            addx 13
            addx 2
            addx 3
            addx -2
            addx 2
            noop
            addx 3
            addx 9
            addx -2
            addx 2
            addx -10
            addx 11
            addx 2
            addx -14
            addx -21
            addx 2
            noop
            addx 5
            addx 29
            addx -2
            noop
            addx -19
            noop
            addx 2
            addx 11
            addx -10
            addx 2
            addx 5
            addx -9
            noop
            addx 14
            addx 2
            addx 3
            addx -2
            addx 3
            addx 1
            noop
            addx -37
            noop
            addx 13
            addx -8
            noop
            noop
            noop
            noop
            addx 13
            addx -5
            addx 3
            addx 3
            addx 3
            noop
            noop
            noop
            noop
            noop
            noop
            noop
            addx 6
            addx 3
            addx 1
            addx 5
            addx -15
            addx 5
            addx -27
            addx 30
            addx -23
            addx 33
            addx -32
            addx 2
            addx 5
            addx 2
            addx -16
            addx 17
            addx 2
            addx -10
            addx 17
            addx 10
            addx -9
            addx 2
            addx 2
            addx 5
            addx -29
            addx -8
            noop
            noop
            noop
            addx 19
            addx -11
            addx -1
            addx 6
            noop
            noop
            addx -1
            addx 3
            noop
            addx 3
            addx 2
            addx -3
            addx 11
            addx -1
            addx 5
            addx -2
            addx 5
            addx 2
            noop
            noop
            addx 1
            noop
            noop""";


    static char[] currentCrtLine = new char[40];

    public static void main(String... arg) {
        int cycle = 1;
        int x = 1;

        Arrays.fill(currentCrtLine, '-');

        for(String line : data.split("\n")) {
            if (line.startsWith("noop")) {
                cycle = incCycle(cycle, x);
            } else if (line.startsWith("addx")) {
                cycle = incCycle(cycle, x);
                cycle = incCycle(cycle, x);
                x += Integer.parseInt(line.substring(5));
            }
        }

    }

    static int incCycle(int cycle, int x) {
        int pixel = ((cycle -1) % 40);
        if(pixel == (x -1) || pixel == x || pixel == x + 1) {
            currentCrtLine[pixel] = '#';
        } else {
            currentCrtLine[pixel] = ' ';
        }

        if(cycle % 40 == 0) {
            System.out.println(new String(currentCrtLine));
            currentCrtLine = new char[40];
            Arrays.fill(currentCrtLine, '-');
        }
        return ++cycle;
    }
}
