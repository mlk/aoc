package com.github.mlk.aoc2022;

import java.util.HashSet;
import java.util.Set;

public class Day03 {

    static final String data = """
            <retracted>""";

    static String testData = """
            <retracted>""";

    public static void main(String[] args) {
        int total = 0;
        String[] datar = data.split("\n");
        for(int i =0; i<datar.length/3; i++) {
            int first = i * 3;
            int second = first + 1;
            int third = second + 1;

            Set<Character> l1 = toSet(datar[first]);
            l1.retainAll( toSet(datar[second]));
            l1.retainAll( toSet(datar[third]));

            total+= (toNumber(l1.stream().findAny().get()));
        }
        System.out.println(total);
    }

    static Set<Character> toSet(String v) {
        HashSet<Character> d = new HashSet<>();
        for(char c : v.toCharArray()) {
            d.add(c);
        }
        return d;
    }

    static int toNumber(char c) {
        if(c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        }
        return c - 'A' + 27;
    }
}
