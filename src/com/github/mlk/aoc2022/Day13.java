package com.github.mlk.aoc2022;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;

public class Day13 {
    static String input = """
            <retracted>
            [[2]]
            [[6]]""";

    static String testInput = """
            <retracted>
            [[2]]
            [[6]]""";

    static ScriptEngineManager factory = new ScriptEngineManager();
    static ScriptEngine engine = factory.getEngineByName("JavaScript");

    static Object eval(String data) {
        try {
            return engine.eval(data);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... arg) throws ScriptException {

        List<Object> ordered = Arrays.stream(input.split("\n")).filter(x -> !x.isBlank())
                .map(Day13::eval)
                .sorted((x, y) -> inOrder(y, x).ordering)
                .toList();

        int index = 1;
        int total = 1;
        for (Object i : ordered) {
            ScriptObjectMirror item = (ScriptObjectMirror) i;
            if(item.size() == 1 && item.getSlot(0) instanceof ScriptObjectMirror first && first.size() == 1 &&
                    (first.getSlot(0).equals(2) || first.getSlot(0).equals(6))) {
                total *= index;
            }
            index++;
        }
        System.out.println(total);
    }

    static Order inOrder(Object left, Object right) {
        try {
            if (left instanceof Integer && right instanceof Integer) {
                if ((Integer) left < (Integer) right) {
                    return Order.IN;
                } else if ((Integer) left > (Integer) right) {
                    return Order.OUT;
                } else {
                    return Order.CONTINUE;
                }
            }
            if (left instanceof ScriptObjectMirror leftM && right instanceof ScriptObjectMirror rightM) {
                int minLength = Math.min(leftM.size(), rightM.size());
                for (int i = 0; i < minLength; i++) {
                    Order currentValue = inOrder(leftM.getSlot(i), rightM.getSlot(i));
                    if (currentValue != Order.CONTINUE) {
                        return currentValue;
                    }
                }
                if (leftM.size() < rightM.size()) {
                    return Order.IN;
                } else if (leftM.size() > rightM.size()) {
                    return Order.OUT;
                }
                return Order.CONTINUE;
            } else if (left instanceof Integer && right instanceof ScriptObjectMirror) {
                return inOrder(engine.eval("[" + left + "]"), right);
            } else {
                return inOrder(left, engine.eval("[" + right + "]"));
            }
        } catch(ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    enum Order { IN(1), OUT(-1), CONTINUE(0);
        final int ordering;

        Order(int ordering) {
            this.ordering = ordering;
        }
    }

}
