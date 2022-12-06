package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day5 {

    static String input= """
                [G] [R]                 [P]   \s
                [H] [W]     [T] [P]     [H]   \s
                [F] [T] [P] [B] [D]     [N]   \s
            [L] [T] [M] [Q] [L] [C]     [Z]   \s
            [C] [C] [N] [V] [S] [H]     [V] [G]
            [G] [L] [F] [D] [M] [V] [T] [J] [H]
            [M] [D] [J] [F] [F] [N] [C] [S] [F]
            [Q] [R] [V] [J] [N] [R] [H] [G] [Z]
             1   2   3   4   5   6   7   8   9\s
            """;

    static String instructions = """
move 5 from 8 to 2
move 2 from 4 to 5
move 3 from 3 to 9
move 4 from 1 to 8
move 5 from 9 to 1
move 3 from 3 to 8
move 2 from 4 to 7
move 6 from 6 to 5
move 5 from 2 to 4
move 2 from 9 to 1
move 1 from 7 to 1
move 4 from 7 to 3
move 5 from 1 to 5
move 3 from 1 to 4
move 8 from 5 to 3
move 7 from 3 to 2
move 10 from 4 to 7
move 1 from 7 to 3
move 1 from 6 to 2
move 3 from 8 to 4
move 4 from 3 to 2
move 1 from 1 to 2
move 4 from 3 to 1
move 2 from 1 to 7
move 3 from 5 to 1
move 7 from 8 to 4
move 9 from 5 to 1
move 9 from 2 to 7
move 6 from 4 to 9
move 14 from 7 to 5
move 2 from 1 to 4
move 6 from 7 to 1
move 4 from 4 to 9
move 6 from 2 to 8
move 2 from 4 to 9
move 2 from 9 to 3
move 3 from 8 to 3
move 5 from 9 to 4
move 1 from 2 to 9
move 5 from 5 to 3
move 3 from 2 to 7
move 1 from 1 to 4
move 3 from 7 to 5
move 4 from 9 to 6
move 2 from 9 to 3
move 5 from 1 to 6
move 7 from 6 to 5
move 1 from 2 to 3
move 10 from 1 to 5
move 1 from 8 to 3
move 14 from 3 to 7
move 1 from 8 to 4
move 2 from 6 to 1
move 28 from 5 to 9
move 1 from 2 to 1
move 5 from 4 to 6
move 2 from 4 to 3
move 13 from 7 to 8
move 1 from 3 to 5
move 1 from 5 to 2
move 1 from 3 to 6
move 1 from 5 to 6
move 22 from 9 to 1
move 1 from 2 to 7
move 3 from 9 to 5
move 2 from 7 to 5
move 18 from 1 to 4
move 7 from 8 to 3
move 4 from 6 to 8
move 2 from 5 to 8
move 5 from 3 to 9
move 2 from 5 to 1
move 3 from 6 to 8
move 1 from 5 to 9
move 2 from 3 to 6
move 10 from 1 to 5
move 15 from 8 to 6
move 10 from 6 to 8
move 1 from 9 to 4
move 1 from 1 to 3
move 4 from 4 to 3
move 5 from 3 to 5
move 9 from 5 to 6
move 13 from 6 to 5
move 8 from 5 to 7
move 8 from 9 to 6
move 2 from 6 to 4
move 2 from 6 to 2
move 3 from 7 to 4
move 2 from 2 to 8
move 1 from 5 to 4
move 3 from 7 to 9
move 1 from 5 to 9
move 5 from 6 to 9
move 10 from 8 to 3
move 3 from 8 to 1
move 5 from 9 to 2
move 1 from 6 to 4
move 4 from 5 to 6
move 7 from 3 to 7
move 5 from 6 to 5
move 19 from 4 to 8
move 15 from 8 to 3
move 2 from 1 to 5
move 7 from 5 to 9
move 2 from 7 to 2
move 3 from 3 to 8
move 5 from 5 to 8
move 10 from 9 to 3
move 1 from 4 to 2
move 10 from 8 to 3
move 29 from 3 to 2
move 2 from 3 to 4
move 1 from 1 to 5
move 2 from 8 to 4
move 1 from 9 to 1
move 1 from 3 to 9
move 1 from 1 to 9
move 2 from 3 to 4
move 33 from 2 to 1
move 2 from 2 to 4
move 1 from 3 to 1
move 22 from 1 to 2
move 6 from 4 to 9
move 4 from 7 to 1
move 16 from 1 to 4
move 3 from 7 to 6
move 2 from 9 to 4
move 1 from 5 to 2
move 9 from 4 to 2
move 1 from 6 to 5
move 7 from 4 to 2
move 6 from 9 to 8
move 4 from 4 to 9
move 4 from 8 to 3
move 2 from 4 to 3
move 2 from 2 to 5
move 2 from 5 to 2
move 1 from 5 to 6
move 3 from 9 to 5
move 1 from 6 to 8
move 2 from 6 to 5
move 1 from 3 to 2
move 1 from 8 to 4
move 2 from 8 to 2
move 5 from 5 to 6
move 44 from 2 to 8
move 1 from 4 to 8
move 3 from 6 to 8
move 2 from 6 to 2
move 37 from 8 to 3
move 1 from 9 to 4
move 1 from 2 to 5
move 5 from 8 to 6
move 1 from 4 to 6
move 1 from 2 to 4
move 16 from 3 to 2
move 1 from 4 to 5
move 1 from 8 to 3
move 4 from 8 to 2
move 1 from 8 to 7
move 2 from 5 to 8
move 15 from 2 to 4
move 5 from 6 to 3
move 1 from 7 to 4
move 1 from 8 to 9
move 1 from 6 to 7
move 1 from 8 to 3
move 2 from 2 to 8
move 1 from 9 to 3
move 2 from 8 to 4
move 1 from 4 to 6
move 33 from 3 to 7
move 1 from 6 to 3
move 1 from 4 to 8
move 1 from 8 to 9
move 4 from 4 to 3
move 9 from 4 to 7
move 3 from 4 to 8
move 11 from 7 to 2
move 14 from 7 to 6
move 1 from 8 to 3
move 1 from 9 to 5
move 1 from 5 to 1
move 8 from 2 to 9
move 1 from 8 to 7
move 6 from 3 to 6
move 18 from 6 to 4
move 1 from 2 to 7
move 1 from 3 to 6
move 14 from 4 to 2
move 4 from 4 to 3
move 3 from 6 to 3
move 19 from 2 to 6
move 16 from 6 to 8
move 1 from 1 to 8
move 16 from 8 to 7
move 3 from 9 to 4
move 3 from 6 to 2
move 3 from 4 to 7
move 4 from 3 to 2
move 2 from 2 to 4
move 4 from 9 to 8
move 5 from 2 to 8
move 29 from 7 to 5
move 6 from 8 to 2
move 2 from 3 to 4
move 2 from 2 to 6
move 1 from 3 to 5
move 4 from 2 to 6
move 8 from 7 to 5
move 1 from 7 to 5
move 2 from 8 to 6
move 1 from 8 to 7
move 6 from 6 to 1
move 2 from 7 to 6
move 1 from 9 to 7
move 3 from 1 to 7
move 3 from 6 to 1
move 1 from 7 to 6
move 3 from 1 to 6
move 1 from 1 to 5
move 4 from 6 to 3
move 2 from 4 to 2
move 38 from 5 to 6
move 3 from 3 to 8
move 4 from 8 to 6
move 22 from 6 to 8
move 1 from 7 to 8
move 2 from 6 to 2
move 2 from 5 to 2
move 2 from 2 to 1
move 2 from 4 to 6
move 2 from 2 to 1
move 1 from 1 to 9
move 2 from 8 to 5
move 2 from 2 to 8
move 2 from 5 to 2
move 2 from 7 to 2
move 1 from 3 to 1
move 4 from 1 to 8
move 1 from 9 to 5
move 1 from 1 to 7
move 1 from 2 to 8
move 29 from 8 to 3
move 15 from 3 to 2
move 12 from 2 to 5
move 1 from 1 to 6
move 3 from 2 to 1
move 6 from 3 to 8
move 2 from 3 to 9
move 1 from 6 to 7
move 12 from 5 to 8
move 2 from 7 to 1
move 2 from 1 to 4
move 2 from 4 to 2
move 1 from 5 to 8
move 1 from 3 to 6
move 2 from 3 to 4
move 3 from 1 to 4
move 5 from 8 to 9
move 4 from 4 to 2
move 5 from 9 to 6
move 26 from 6 to 8
move 7 from 2 to 8
move 3 from 3 to 1
move 1 from 6 to 4
move 14 from 8 to 6
move 2 from 1 to 2
move 1 from 1 to 3
move 18 from 8 to 5
move 15 from 8 to 2
move 5 from 6 to 8
move 4 from 5 to 8
move 7 from 2 to 5
move 2 from 9 to 6
move 1 from 2 to 1
move 7 from 2 to 3
move 7 from 8 to 1
move 2 from 6 to 3
move 1 from 4 to 6
move 2 from 8 to 6
move 10 from 3 to 9
move 18 from 5 to 8
move 1 from 4 to 6
move 2 from 1 to 9
move 12 from 6 to 9
move 1 from 6 to 9
move 9 from 8 to 4
move 6 from 1 to 2
move 3 from 8 to 9
move 14 from 9 to 8
move 5 from 4 to 9
move 2 from 4 to 5
move 16 from 8 to 5
move 12 from 5 to 4
move 7 from 5 to 1
move 1 from 1 to 8
move 1 from 5 to 8
move 1 from 4 to 9
move 8 from 2 to 7
move 12 from 4 to 3
move 2 from 2 to 5
move 1 from 3 to 2
move 3 from 5 to 4
move 1 from 4 to 8
move 3 from 4 to 9
move 18 from 9 to 8
move 8 from 3 to 1
move 5 from 8 to 1
move 1 from 2 to 5
move 3 from 7 to 1
move 3 from 7 to 5
move 1 from 8 to 9
move 5 from 9 to 7
move 2 from 3 to 6
move 16 from 1 to 4
move 14 from 8 to 6
move 2 from 5 to 6
move 4 from 1 to 6
move 3 from 4 to 9
move 15 from 6 to 1
move 5 from 4 to 3
move 2 from 8 to 2
move 6 from 4 to 3
move 15 from 1 to 5
move 14 from 5 to 3
move 5 from 6 to 2
move 2 from 4 to 7
move 1 from 1 to 6
move 2 from 3 to 4
move 3 from 8 to 1
move 1 from 5 to 1
move 5 from 7 to 1
move 7 from 1 to 3
move 3 from 6 to 2
move 4 from 9 to 5
move 2 from 4 to 3
move 4 from 7 to 9
move 8 from 2 to 9
move 1 from 9 to 1
move 2 from 2 to 8
move 11 from 9 to 1
move 6 from 5 to 1
move 21 from 3 to 2
move 1 from 8 to 5
move 5 from 1 to 7
move 12 from 1 to 8
move 1 from 5 to 2
move 5 from 3 to 2
move 4 from 7 to 2
move 1 from 7 to 8
move 13 from 2 to 5
move 13 from 2 to 5
move 2 from 2 to 1
move 1 from 1 to 9
move 26 from 5 to 4
move 3 from 2 to 7
move 2 from 3 to 9
move 1 from 1 to 6
move 5 from 3 to 2
move 2 from 9 to 6
move 1 from 1 to 8
move 3 from 1 to 6
move 24 from 4 to 9
move 13 from 9 to 1
move 2 from 6 to 2
move 3 from 7 to 5
move 2 from 9 to 7
move 8 from 8 to 3
move 4 from 8 to 5
move 2 from 7 to 2
move 8 from 9 to 4
move 10 from 1 to 2
move 1 from 9 to 1
move 1 from 9 to 2
move 4 from 3 to 2
move 4 from 1 to 8
move 3 from 4 to 8
move 12 from 2 to 3
move 3 from 4 to 6
move 5 from 3 to 2
move 9 from 3 to 9
move 4 from 2 to 9
move 1 from 3 to 7
move 6 from 8 to 2
move 4 from 6 to 8
move 1 from 3 to 8
move 6 from 9 to 1
move 2 from 1 to 8
move 5 from 5 to 8
move 3 from 6 to 8
move 1 from 5 to 1
move 7 from 8 to 2
move 1 from 1 to 4
move 1 from 4 to 6
move 1 from 9 to 4
move 1 from 5 to 9
move 1 from 4 to 7
move 12 from 8 to 2
move 4 from 4 to 3
move 2 from 3 to 1
move 1 from 7 to 2
move 1 from 6 to 8
move 1 from 8 to 6
move 4 from 9 to 3
move 1 from 9 to 3
move 13 from 2 to 3
move 3 from 1 to 7
move 2 from 9 to 4
move 2 from 1 to 9
move 2 from 7 to 2
move 1 from 4 to 1
move 2 from 7 to 5
move 14 from 3 to 8
move 1 from 8 to 5
move 2 from 1 to 4
move 2 from 3 to 4
move 2 from 3 to 4
move 10 from 8 to 3
move 2 from 4 to 8
move 1 from 9 to 3
move 3 from 2 to 3
move 16 from 2 to 4
move 1 from 8 to 5
move 11 from 3 to 4
move 2 from 3 to 7
move 3 from 5 to 1
move 1 from 1 to 2
move 3 from 2 to 5
move 1 from 1 to 9
move 2 from 7 to 4
move 8 from 4 to 3
move 1 from 6 to 7
move 1 from 8 to 6
move 1 from 5 to 1
move 6 from 3 to 5
move 2 from 1 to 3
move 5 from 5 to 7
move 2 from 7 to 2
move 2 from 3 to 4
move 4 from 7 to 1
move 1 from 6 to 8
move 1 from 2 to 1
move 3 from 1 to 6
move 2 from 9 to 6
move 8 from 2 to 1
move 2 from 6 to 2
move 2 from 6 to 3
move 6 from 3 to 5
move 2 from 4 to 6
move 2 from 2 to 9
move 1 from 8 to 6
move 2 from 6 to 5
move 1 from 9 to 1
move 11 from 5 to 8
move 7 from 8 to 6
move 23 from 4 to 1
move 1 from 5 to 9
move 1 from 4 to 6
move 2 from 4 to 8
move 1 from 3 to 1
move 6 from 8 to 3
move 2 from 9 to 6
move 3 from 6 to 1
move 3 from 8 to 7
move 1 from 3 to 6
move 18 from 1 to 2
move 5 from 3 to 8
move 13 from 2 to 9
move 5 from 9 to 7
move 1 from 8 to 6
move 5 from 2 to 6
move 2 from 1 to 7
move 9 from 7 to 8
move 11 from 8 to 6
move 2 from 9 to 4
move 16 from 6 to 1
move 2 from 4 to 6
move 1 from 8 to 9
move 1 from 7 to 6
move 8 from 1 to 5
move 3 from 6 to 5
move 8 from 6 to 4
move 7 from 9 to 5
move 1 from 8 to 1
move 6 from 5 to 1
move 9 from 5 to 7
move 4 from 7 to 9
move 1 from 4 to 8
move 1 from 8 to 3
move 1 from 1 to 8
move 1 from 8 to 7
move 22 from 1 to 3
move 1 from 6 to 7
move 2 from 9 to 4
move 1 from 9 to 6
move 1 from 9 to 4
move 10 from 4 to 3
move 1 from 1 to 2
move 2 from 5 to 4
move 27 from 3 to 8
move 5 from 3 to 9""";

    public static void main(String[] args) {
	    String[] starting = input.split("\n");
        System.out.println(starting[starting.length - 1]);
        String[] buckets = starting[starting.length - 1].split(" +");
        int numberOfBuckets = Integer.parseInt(buckets[buckets.length - 1]);
        Stack<String>[] actualBuckets = new Stack[numberOfBuckets];
        for(int i = 0; i<numberOfBuckets; i++) {
            actualBuckets[i] = new Stack<>();
        }
        for(int i = starting.length -2; i>=0; i--) {
            for(int b = 0; b< numberOfBuckets; b++) {
                String value = starting[i].substring(b * 4, (b * 4) + 3);
                System.out.println(value);
                if(!value.isBlank()) {
                    actualBuckets[b].push(value);
                }
            }
        }
        for(int b = 0; b< numberOfBuckets; b++) {
            System.out.println(actualBuckets[b]);
        }


        for(String line : instructions.split("\n")) {
            String[] items = line.split(" ");
            int blocks = Integer.parseInt(items[1]);
            int from = Integer.parseInt(items[3]) - 1;
            int to = Integer.parseInt(items[5]) - 1;
            List<String> data = new ArrayList<>();

            System.out.println(line);
            for(int i = 0; i<blocks; i++) {
                data.add(actualBuckets[from].pop());
            }
            for(int i = blocks - 1; i>=0; i--) {
                actualBuckets[to].push(data.get(i));
            }
        }
        for(int b = 0; b< numberOfBuckets; b++) {
            System.out.println(actualBuckets[b]);
        }
    }
}
