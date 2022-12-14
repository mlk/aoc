package com.github.mlk.aoc2022;

import java.util.HashSet;
import java.util.Set;

public class Day06 {
    static String input = "<retracted>";

    public static void main(String... argb) {
        for(int i = 0; i< input.length() - 14; i++) {
            String sub = input.substring(i, i + 14);

            Set<Character> data = new HashSet<>();
            for(char c : sub.toCharArray()) {
                data.add(c);
            }
            if(data.size() == 14) {
                System.out.println(sub);
                System.out.println(i + 14);
                return;
            }
        }
    }
}
