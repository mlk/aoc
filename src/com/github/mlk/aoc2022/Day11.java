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
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
                        
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
                        
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
                        
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
            """;

    static String data = """
            Monkey 0:
              Starting items: 83, 97, 95, 67
              Operation: new = old * 19
              Test: divisible by 17
                If true: throw to monkey 2
                If false: throw to monkey 7
                        
            Monkey 1:
              Starting items: 71, 70, 79, 88, 56, 70
              Operation: new = old + 2
              Test: divisible by 19
                If true: throw to monkey 7
                If false: throw to monkey 0
                        
            Monkey 2:
              Starting items: 98, 51, 51, 63, 80, 85, 84, 95
              Operation: new = old + 7
              Test: divisible by 7
                If true: throw to monkey 4
                If false: throw to monkey 3
                        
            Monkey 3:
              Starting items: 77, 90, 82, 80, 79
              Operation: new = old + 1
              Test: divisible by 11
                If true: throw to monkey 6
                If false: throw to monkey 4
                        
            Monkey 4:
              Starting items: 68
              Operation: new = old * 5
              Test: divisible by 13
                If true: throw to monkey 6
                If false: throw to monkey 5
                        
            Monkey 5:
              Starting items: 60, 94
              Operation: new = old + 5
              Test: divisible by 3
                If true: throw to monkey 1
                If false: throw to monkey 0
                        
            Monkey 6:
              Starting items: 81, 51, 85
              Operation: new = old * old
              Test: divisible by 5
                If true: throw to monkey 5
                If false: throw to monkey 1
                        
            Monkey 7:
              Starting items: 98, 81, 63, 65, 84, 71, 84
              Operation: new = old + 3
              Test: divisible by 2
                If true: throw to monkey 2
                If false: throw to monkey 3""";

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
