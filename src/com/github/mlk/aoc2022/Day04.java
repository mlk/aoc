package com.github.mlk.aoc2022;

import java.util.HashSet;
import java.util.Set;

public class Day04 {
    static final String demo = """
            <retracted>""";

    static String data = """
            <retracted>""";

    public static void main(String... arg) {
        int count = 0;
        for(String line : data.split("\n")) {
            String s1 = line.split(",")[0];
            String s2 = line.split(",")[1];
            Range r1 = Range.parse(s1);
            Range r2 = Range.parse(s2);
            if(r1.overlap(r2)) {
                System.out.println(line);
                count++;
            } else if(r2.overlap(r1)) {
                System.out.println(line);
                count++;
            }
        }
        System.out.println(count);
    }

    record Range (int start, int end){
        static Range parse(String l) {
            String s1 = l.split("-")[0];
            String s2 = l.split("-")[1];

            return new Range(Integer.parseInt(s1), Integer.parseInt(s2));
        }

        boolean contains(Range r1) {
            return start >= r1.start && end <= r1.end;
        }
        boolean overlap(Range r1) {
            return create().stream().anyMatch(x -> r1.create().contains(x));
        }
        Set<Integer> create() {
            Set<Integer> range = new HashSet<>();
            for(int i = start; i<= end; i++) {
                range.add(i);
            }
            return range;
        }
    }
}
