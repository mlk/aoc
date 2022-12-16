package com.github.mlk.aoc2022;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.reverseOrder;

public class Day16 {


    record Room (String name, int flow, List<String> leadsTo) {

    }


    static Map<String, Room> rooms = new HashMap<>();

    record Route(Room s, Room e) {

        static Route create(Room s, Room e) {
            if(s.name.compareTo(e.name) > 0) {
                Room os = s;
                s = e;
                e = os;
            }

            return new Route(s, e);
        }
    }

    static Map<Route, Integer> distances = new HashMap<>();

    static void findRoute(Route r, Map<Route, Integer> baseDistances) {
        if(r.s == r.e) {
            return;
        }

        if(!distances.containsKey(r)) {

            for (Room m : rooms.values()) {
                if (m != r.s && m != r.e) {
                    Route midL = Route.create(r.s, m);
                    Route midR = Route.create(r.e, m);

                    if(baseDistances.containsKey(midL) && baseDistances.containsKey(midR)) {
                        int distance = baseDistances.get(midL) + baseDistances.get(midR);
                        if(distances.containsKey(r)) {
                            if(distances.get(r) > distance) {
                                distances.put(r, distance);
                            }
                        } else {
                            distances.put(r, distance);
                        }
                    }
                }
            }
        }
    }

    public static void main(String... ar) {
        String regex = "Valve (..) has flow rate=(\\d+); tunnels? leads? to valves? (.*)";
        Pattern pattern = Pattern.compile(regex);

        for(String line : Day16Data.data.split("\n")) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String name = matcher.group(1);
            int flow = Integer.parseInt(matcher.group(2));
            String[] leadsTo = matcher.group(3).split(", ");
            rooms.put(name, new Room(name, flow, List.of(leadsTo)));
        }

        for(Room r : rooms.values()) {
            for(String kid : r.leadsTo) {
                distances.put(Route.create(r, rooms.get(kid)), 1);
            }
        }

        for(int i = 0; i<5; i++) {


            for (Room s : rooms.values()) {
                for (Room e : rooms.values()) {
                    if (s != e) {
                        Route route = Route.create(s, e);
                        findRoute(route, Map.copyOf(distances));
                    }
                }
            }

        }

        List<Room> roomsToVisit = rooms.values().stream().filter(x -> x.flow > 0).sorted(reverseOrder(Comparator.comparingInt(x -> x.flow))).toList();

        Room startingRoom = rooms.get("AA");
        Leaf path = Leaf.create2(null, startingRoom, 30, roomsToVisit, Action.VALVE, 0);

        System.out.println(path.max());
    }

    enum Action {WALK, VALVE}

    record Leaf(Leaf parent, Room room, int timeRemaining, int leafFlow, List<Leaf> children, Action action, int stepsRemaining) {

        static Leaf create2(Leaf parent, Room room, int timeRemaining, List<Room> toVisit, Action action, int stepsRemaining) {


            int runningTotal = parent == null ? 0 : parent.leafFlow();

            //System.out.println(room.name + " " + action + stepsRemaining + " " + runningTotal + " " + timeRemaining);

            Leaf current;
            if(action == Action.VALVE) {
                List<Leaf> children = new ArrayList<>();
                int flow = room.flow * (timeRemaining - 1);
                if(room.flow > 0) timeRemaining--;

                current = new Leaf(parent, room, timeRemaining, runningTotal + flow, children, action, stepsRemaining);

                for (Room childRoom : toVisit) {

                    int distance = distances.get(Route.create(room, childRoom));
                    if (distance <= (timeRemaining - 1)) {
                        List<Room> leftToVist = new ArrayList<>(toVisit);
                        leftToVist.remove(childRoom);

                        children.add(create2(current, childRoom, timeRemaining, leftToVist, Action.WALK, distance));
                    }
                }
            } else {
                List<Leaf> children = new ArrayList<>();
                current = new Leaf(parent, room, timeRemaining - 1, runningTotal, children, action, stepsRemaining - 1);
                if(timeRemaining > 0) {
                    if (stepsRemaining - 1 == 0) {
                        children.add(create2(current, room, timeRemaining - 1, toVisit, Action.VALVE, 0));
                    } else {
                        children.add(create2(current, room, timeRemaining - 1, toVisit, Action.WALK, stepsRemaining - 1));
                    }
                }

            }
            return current;
        }

        int max() {
            /*char[] spaces = new char[tab()];
            Arrays.fill(spaces, ' ');
            System.out.println(new String(spaces) + room.name + " - " + action + " " + leafFlow);*/
            if(children.isEmpty()) {
                return leafFlow;
            }
            return children.stream().mapToInt(Leaf::max).max().getAsInt();
        }

        int tab() {
            if(parent == null) return 0;
            return parent().tab() + 1;
        }
    }
}
