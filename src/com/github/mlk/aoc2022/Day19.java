package com.github.mlk.aoc2022;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day19 {

    record Blueprint(int id, int oreRobotCost, int clayRobotCost,int obsidianOreRobotCost, int obsidianClayRobotCost, int geodeOreRobotCost, int geodeObsidianRobotCost,
                     int maxOreCost, int maxClayClost, int maxObsidianCost) {

        public Blueprint(int id, int oreRobotCost, int clayRobotCost,int obsidianOreRobotCost, int obsidianClayRobotCost, int geodeOreRobotCost, int geodeObsidianRobotCost) {
            this(id, oreRobotCost, clayRobotCost, obsidianOreRobotCost, obsidianClayRobotCost, geodeOreRobotCost, geodeObsidianRobotCost,
                    Stream.of(oreRobotCost, clayRobotCost, obsidianOreRobotCost, geodeOreRobotCost).mapToInt(x -> x).max().getAsInt(),
                    obsidianClayRobotCost,
                    geodeObsidianRobotCost);
        }

        static String regex = "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.";
        static Pattern pattern = Pattern.compile(regex);
        static Blueprint parse(String value) {
            Matcher m = pattern.matcher(value);
            m.find();
            return new Blueprint(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6)), Integer.parseInt(m.group(7)));
        }
    }

    record Minute(int minute, int ore, int clay, int obsidian, int geode, int oreBots, int clayBots, int obsidianBots, int geodeBots) {

        public List<Minute> options(Blueprint blueprint) {
            List<Minute> options = new ArrayList<>();
            if(ore < blueprint.maxOreCost || clay < blueprint.maxClayClost || obsidian < blueprint.maxObsidianCost()) {
                // Do nothing
                options.add(new Minute(minute + 1, ore + oreBots, clay + clayBots, obsidian + obsidianBots, geode + geodeBots, oreBots, clayBots, obsidianBots, geodeBots));
            }

            if(ore >= blueprint.geodeOreRobotCost && obsidian >= blueprint.geodeObsidianRobotCost) {
                options.clear();
                options.add(new Minute(minute + 1, (ore - blueprint.geodeOreRobotCost) + oreBots, clay + clayBots, (obsidian - blueprint.geodeObsidianRobotCost) + obsidianBots, geode + geodeBots, oreBots, clayBots, obsidianBots, geodeBots + 1));
                return options;
            }

            if(ore >= blueprint.obsidianOreRobotCost && clay >= blueprint.obsidianClayRobotCost) {
                options.add(new Minute(minute + 1, (ore - blueprint.obsidianOreRobotCost) + oreBots, (clay - blueprint.obsidianClayRobotCost) + clayBots, obsidian + obsidianBots, geode + geodeBots, oreBots, clayBots, obsidianBots + 1, geodeBots));
            }

            if(ore >= blueprint.oreRobotCost && oreBots <= blueprint.maxOreCost) {
                options.add(new Minute(minute + 1, (ore - blueprint.oreRobotCost) + oreBots, clay + clayBots, obsidian + obsidianBots, geode + geodeBots, oreBots + 1, clayBots, obsidianBots, geodeBots));
            }
            if(ore >= blueprint.clayRobotCost && clayBots <= blueprint.maxClayClost) {
                options.add(new Minute(minute + 1, (ore - blueprint.clayRobotCost) + oreBots, clay + clayBots, obsidian + obsidianBots, geode + geodeBots, oreBots, clayBots + 1, obsidianBots, geodeBots));
            }


            return options;
        }
    }

    public static void main(String... arv) throws InterruptedException {
        List<Blueprint> blueprints = new ArrayList<>();
        for(String v : Day1Data.data.split("\n")) {
            blueprints.add(Blueprint.parse(v));
        }

        //System.out.println(max(new Minute(1, 1, 0, 0, 0, 1, 0, 0, 0), blueprints.get(1), 24));

        List<Integer> scores = new ArrayList<>();
        ExecutorService s = Executors.newFixedThreadPool(3);
        for(Blueprint blueprint : blueprints) {
            s.execute(() -> {
                        ;
                        System.out.println(blueprint.id + "/" + blueprints.size() + " - " + new Date());
                        long hey = System.currentTimeMillis();
                        int i = max(new Minute(1, 1, 0, 0, 0, 1, 0, 0, 0), blueprint, 32);

                        scores.add(i);
                        System.out.println(" > " + blueprint.id + " " + i + " " + (((System.currentTimeMillis() - hey) / 1000)));
                    });

            /*
            List<Minute> currentMinute = List.of(new Minute(1, 1, 0, 0, 0, 1, 0, 0, 0));

            System.gc();
            System.gc();

            for (int i = 0; i < 31; i++) {
                int maxBots = currentMinute.stream().mapToInt(x -> x.geodeBots).max().getAsInt();
                if (maxBots > 1) {
                    currentMinute.removeIf(x -> x.geodeBots < (maxBots-4));
                }

                System.out.println((i + 1) + "  " + currentMinute.size());

                List<Minute> nextMinute = new ArrayList<>();
                for (Minute now : currentMinute) {
                    nextMinute.addAll(now.options(blueprint));
                }
                currentMinute = nextMinute;
                System.gc();
                System.gc();
            }



            System.out.println(" >> " + currentMinute.stream().mapToInt(x -> x.geode).max().getAsInt());


            scores.add(currentMinute.stream().mapToInt(x -> x.geode).max().getAsInt() * blueprint.id);
             */

        }
        s.awaitTermination(60, TimeUnit.MINUTES);
        System.out.println(scores.stream().mapToInt(x -> x).reduce((a, m) -> a*m));
    }

    static int max(Minute minute, Blueprint bp, int targetMinute) {
        if(minute.minute == (targetMinute )) {
            return minute.geode;
            //return minute.options(bp).stream().max((x, y) -> x.geode - y.geode).get().geode;
        }
        int max = 0;
        if(minute.minute > 23 && minute.geodeBots < 1) {
            return -1;
        }
        for(Minute next : minute.options(bp)) {

            int c = max(next, bp, targetMinute);
            if(max <= c) max = c;
        }
        return max;
    }

}
