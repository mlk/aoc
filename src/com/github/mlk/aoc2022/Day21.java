package com.github.mlk.aoc2022;

import java.util.*;
import java.util.function.Supplier;

public class Day21 {

    record Monkey(String name, String value) {
        long computeValue() {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return urkMath(value);
            }
        }

        Optional<Monkey> lhMonkey() {
            try {
                Long.parseLong(value);
                return Optional.empty();
            } catch (NumberFormatException e) {
                String[] parsed = value.split(" ");
                Monkey lhMonkey = monkeys.get(parsed[0]);
                return Optional.of(lhMonkey);
            }
        }

        Optional<Monkey> rhMonkey() {
            try {
                Long.parseLong(value);
                return Optional.empty();
            } catch (NumberFormatException e) {
                String[] parsed = value.split(" ");
                Monkey rhMonkey = monkeys.get(parsed[2]);
                return Optional.of(rhMonkey);
            }
        }

        boolean isLeaf() {
            return !value.contains(" ");
        }

        void display(int tabs) {
            char[] t = new char[tabs];
            Arrays.fill(t, ' ');
            String s = new String(t);

            if(isMe()) {
                System.out.println(s + "<<UNKNOWN>>");
            } else if(isLeaf()) {
                System.out.println(s + value);
            } else {
                System.out.println(s + "(");
                String[] parsed = value.split(" ");
                lhMonkey().get().display(tabs + 1);
                System.out.println(s + parsed[1]);
                rhMonkey().get().display(tabs + 1);
                System.out.println(s + ")");
            }
        }


        boolean isMe() {
            return name.equals("humn");
        }

        boolean containsMe() {
            if(isMe()) {
                return true;
            }
            Optional<Monkey> lhMonkey = lhMonkey();
            if(lhMonkey.isEmpty()) {
                return false;
            }
            if(lhMonkey.get().containsMe()) {
                return true;
            } else {
                return rhMonkey().get().containsMe();
            }
        }
    }

    static Map<String, Monkey> monkeys = new HashMap<>();

    static Long urkMath(String math) {
        String[] parsed = math.split(" ");
        Monkey lhMonkey = monkeys.get(parsed[0]);
        Monkey rhMonkey = monkeys.get(parsed[2]);

        if (parsed[1].equals("+")) {
            return lhMonkey.computeValue() + rhMonkey.computeValue();
        } else if (parsed[1].equals("-")) {
            return lhMonkey.computeValue() - rhMonkey.computeValue();
        } else if (parsed[1].equals("*")) {
            return lhMonkey.computeValue() * rhMonkey.computeValue();
        } else if (parsed[1].equals("/")) {
            return lhMonkey.computeValue() / rhMonkey.computeValue();
        }
        throw new RuntimeException();
    }

    static Long target(String math) {
        String[] parsed = math.split(" ");
        Monkey lhMonkey = monkeys.get(parsed[0]);
        Monkey rhMonkey = monkeys.get(parsed[2]);

        if (parsed[1].equals("+")) {
            return lhMonkey.computeValue() - rhMonkey.computeValue();
        } else if (parsed[1].equals("-")) {
            return lhMonkey.computeValue() + rhMonkey.computeValue();
        } else if (parsed[1].equals("*")) {
            return lhMonkey.computeValue() / rhMonkey.computeValue();
        } else if (parsed[1].equals("/")) {
            return lhMonkey.computeValue() * rhMonkey.computeValue();
        }
        throw new RuntimeException();
    }


    public static void main(String[] arg) {

        for (String line : Day21Data.data.split("\n")) {
            String[] parts = line.split(": ");

            monkeys.put(parts[0], new Monkey(parts[0], parts[1]));
        }

        System.out.println("Part 1: " + monkeys.get("root").computeValue());

        Monkey root = monkeys.get("root");
        Monkey withMe;
        Monkey target;
        if(root.lhMonkey().get().containsMe()) {
            withMe = root.lhMonkey().get();
            target = root.rhMonkey().get();
        } else {
            withMe = root.rhMonkey().get();
            target = root.lhMonkey().get();
        }
        long targetActual = target.computeValue();

        cache(withMe);

        long start = 0;
        long end = Long.MAX_VALUE;

        while(true) {

            long chunkSize = (end - start) / 1000L;
            if(chunkSize < 100) {
                chunkSize = 1;
            }
            long value = tryBetween(start, end, chunkSize, targetActual, withMe);
            if(chunkSize == 1) {
                System.out.println("Part 2: " + value);
                return;
            }
            start = value - chunkSize;
            end = value + 1;
        }

    }

    static long tryBetween(long start, long end, long chunkSize, long targetActual, Monkey withMe) {
        System.out.println("  >>  " + start + " to " + end + " chunk: " + chunkSize);

        for(long i = start; i <= end; i+=chunkSize) {
            monkeys.put("humn", new Monkey("humn", "" + i));
            if(chunkSize == 1) {
                if (targetActual == withMe.computeValue()) {
                    return i;
                }
            } else {
                if (targetActual >= withMe.computeValue()) {
                    return i;
                }
            }
        }
        throw new RuntimeException();
    }

    static void cache(Monkey withMe) {
        if(withMe.rhMonkey().isPresent()) {
            doCache(withMe.rhMonkey().get());
            doCache(withMe.lhMonkey().get());

            cache(withMe.rhMonkey().get());
            cache(withMe.lhMonkey().get());
        }
    }

    static void doCache(Monkey monkey) {
        if(!monkey.containsMe() && monkey.rhMonkey().isPresent()) {
            //System.out.println("Caching " + monkey );
            monkeys.put(monkey.name, new Monkey(monkey.name(), "" + monkey.computeValue()));
        }
    }
}
