package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day05 {

    static String input= """
            <retracted>
            """;

    static String instructions = """
            <retracted>""";

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
