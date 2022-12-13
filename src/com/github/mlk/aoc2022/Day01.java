package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.List;

public class Day01 {
    static String data = """
    <retracted>""";

    public static void main(String[] args) {
	    int current = 0;
        List<Integer> snacks = new ArrayList<>();
        for(String row : data.split("\n")) {
            if(row.isBlank()) {
                snacks.add(current);
                current = 0;
            } else {
                current += Integer.parseInt(row);
            }
        }
        System.out.println(snacks.stream().sorted((x, y) -> y - x).limit(3).reduce((x, y) -> x+y));
    }
}
