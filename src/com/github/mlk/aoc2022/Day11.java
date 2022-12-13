package com.github.mlk.aoc2022;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day11 {
    static String testData = """
            <retracted>
            """;

    static String data = """
            <retracted>""";

    static ScriptEngineManager factory = new ScriptEngineManager();
    static ScriptEngine engine = factory.getEngineByName("JavaScript");
    static int totalMonkeys = 8;
    record Monkey(int number, List<int[]> items, Function<Integer, Integer> operation, int divisibleTest, int trueMonkey, int falseMonkey) {
        static Monkey parse(String monkey) throws ScriptException {
            String[] lines = monkey.split("\n");
            int number = Integer.parseInt(lines[0].substring(7, lines[0].length() - 1).trim());
            int divisibleTest =  Integer.parseInt(lines[3].substring(21).trim());
            int trueMonkey = Integer.parseInt(lines[4].substring(29).trim());
            int falseMonkey = Integer.parseInt(lines[5].substring(29).trim());
            String calc = lines[2].substring(18);

            List<int[]> items = Arrays.stream(lines[1].substring(18).split(",")).map(String::trim).map(Integer::parseInt).map((x) -> {
                int[] values = new int[totalMonkeys];
                Arrays.fill(values, x);
                return values;
            }).toList();

            Function<Integer, Integer> operation = (oldValue) -> {

                engine.put("old", oldValue);

                try {
                    return ((Number) engine.eval("Math.floor( " + calc + ") ")).intValue();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            return new Monkey(number, new ArrayList<>(items), operation, divisibleTest, trueMonkey, falseMonkey);
        }

        public static void main(String... arg) throws ScriptException {
            List<Monkey> monkeys = new ArrayList<>();
            for(String monkey : data.split("\n\n")) {
                Monkey parsedMonkey = Monkey.parse(monkey);
                monkeys.add(parsedMonkey.number(), parsedMonkey);
            }

            int[] monkeyInspections = new int[monkeys.size()];
            for(int i = 0; i< 10000; i++) {
                for(Monkey monkey : monkeys) {

                    for(int[] item : monkey.items()) {
                        monkeyInspections[monkey.number]++;

                        for(int itemMonkey = 0; itemMonkey<monkeys.size(); itemMonkey++) {
                            item[itemMonkey] = monkey.operation().apply(item[itemMonkey]) % monkeys.get(itemMonkey).divisibleTest();
                        }

                        if(item[monkey.number] == 0) {
                            monkeys.get(monkey.trueMonkey()).items().add(item);
                        } else {
                            monkeys.get(monkey.falseMonkey()).items().add(item);
                        }
                    }
                    monkey.items().clear();
                }
            }
            for(int i = 0; i<monkeyInspections.length; i++) {
                System.out.println(i + " " + monkeyInspections[i]);
            }
            int[] sorted = Arrays.stream(monkeyInspections).sorted().toArray();
            System.out.println(((long)sorted[sorted.length-1]) * sorted[sorted.length-2]);
        }

    }

}
