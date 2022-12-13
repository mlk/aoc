package com.github.mlk.aoc2022;

import java.util.Arrays;

public class Day10 {
    static String input = """
            <retracted>
            """;

    static String data = """
            <retracted>""";


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
