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
        System.out.println(distances.size());

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
        Leaf path = Leaf.create2(null, startingRoom, 30, roomsToVisit);

        System.out.println(path.max());

        /*Executor executor = Executors.newFixedThreadPool(5);

        //Leaf tree = Leaf.create(null, rooms.get("AA"), 30, executor);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //System.out.print(tree.max());


        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.print(tree.max());
            }
        });

         */
    }

    record Leaf(Leaf parent, Room room, int timeRemaining, int leafFlow, List<Leaf> children) {

        static Leaf create2(Leaf parent, Room room, int timeRemaining, List<Room> toVisit) {
            int runningTotal = parent == null ? 0 : parent.leafFlow();

            List<Leaf> children = new ArrayList<>();
            int flow = room.flow * timeRemaining;

            Leaf current = new Leaf(parent, room, timeRemaining, runningTotal + flow, children);

            for (Room childRoom : toVisit) {

                int distance = distances.get(Route.create(room, childRoom));
                if (distance < timeRemaining) {
                    List<Room> leftToVist = new ArrayList<>(toVisit);
                    leftToVist.remove(childRoom);
                    children.add(create2(current, childRoom, timeRemaining - distance - 1, leftToVist));
                }
            }

            return current;
        }

        static Leaf create(Leaf parent, Room room, int timeRemaining) {

            boolean valveAlreadyOpen = room.flow == 0 || (parent != null && parent.valveOpen(room));

            int runningTotal = parent == null ? 0 : parent.leafFlow();

            if (!valveAlreadyOpen) timeRemaining--;


            List<Leaf> children = new ArrayList<>();
            int flow = valveAlreadyOpen ? 0 : room.flow * timeRemaining;

            Leaf current = new Leaf(parent, room, timeRemaining, runningTotal + flow, children);

            if (timeRemaining >= 2) {
                int tr = timeRemaining;
                for (String childRoom : room.leadsTo()) {
                    Room kid = rooms.get(childRoom);
                    if (current.count(kid, 0) < 3) {
                        children.add(create(current, kid, tr - 1));
                    }
                }
            }

            return current;
        }

        int count(Room checking, int start) {
            int count = start;
            if(this.parent != null) {
                count = parent().count(room, start);
            }

            if(room == checking) {
                return count + 1;
            }
            return count;
        }

        int max() {
            if(children.isEmpty()) {
                return leafFlow;
            }
            return children.stream().mapToInt(Leaf::max).max().getAsInt();
        }

        int count(int value) {
            if(parent != null) {
                return parent.count(value + 1);
            }
            return value;
        }

        boolean valveOpen(Room room) {
            if(this.room == room) {
                return true;
            } else if(this.parent != null) {
                return this.parent.valveOpen(room);
            }
            return false;
        }
    }
}
