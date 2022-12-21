package com.github.mlk.aoc2022;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Day21 {

    static Map<String, Supplier<Long>> monkeys = new HashMap<>();

    static Long urkMath(String math) {
        String[] parsed = math.split(" ");
        Supplier<Long> lhMonkey = monkeys.get(parsed[0]);
        Supplier<Long> rhMonkey = monkeys.get(parsed[2]);

        if (parsed[1].equals("+")) {
            return lhMonkey.get() + rhMonkey.get();
        } else if (parsed[1].equals("-")) {
            return lhMonkey.get() - rhMonkey.get();
        } else if (parsed[1].equals("*")) {
            return lhMonkey.get() * rhMonkey.get();
        } else if (parsed[1].equals("/")) {
            return lhMonkey.get() / rhMonkey.get();
        }
        throw new RuntimeException();
    }

    public static void main(String[] arg) {

        for (String line : Day21Data.data.split("\n")) {
            String[] parts = line.split(": ");
            Supplier<Long> value;
            try {
                long i = Long.parseLong(parts[1]);
                value = () -> i;
            } catch (NumberFormatException e) {
                value = () -> urkMath(parts[1]);
            }
            monkeys.put(parts[0], value);
        }

        System.out.println(monkeys.get("root").get());

    }

}
