package com.github.mlk.aoc2022;

import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.reverseOrder;

public class Day16 {


    record Room (String name, int flow, List<String> leadsTo, int id) {

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

        int index = 0;
        for(String line : Day16Data.exampleData.split("\n")) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String name = matcher.group(1);
            int flow = Integer.parseInt(matcher.group(2));
            String[] leadsTo = matcher.group(3).split(", ");
            rooms.put(name, new Room(name, flow, List.of(leadsTo), index));
            index++;
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
        BitSet vistedTheRoom = new BitSet();
        System.out.println(vistedTheRoom.get(0));

        Room startingRoom = rooms.get("AA");
        Leaf path = Leaf.create2(null, 27, vistedTheRoom, roomsToVisit, new Person(startingRoom, Action.VALVE, 0), new Person(startingRoom, Action.VALVE, 0));

        System.out.println(path.max());
    }

    enum Action {WALK, VALVE, IDLE}

    static Room EMPTY = new Room("<EMPTY>", 0, List.of(), -1);

    record Person(Room room, Action action, int stepsRemaining) {
        int flow(int timeRemaining) {
            if(timeRemaining <= 0) return 0;

            if(action == Action.VALVE) {
                return room.flow * (timeRemaining);
            }// 2209

            return 0;
        }



        List<Person> nextStates(int timeRemaining, List<Room> toVisit, BitSet bitSet, String tabs) {
            if(action == Action.IDLE) {
                //System.out.println(tabs + " idle");
                return List.of(new Person(EMPTY, Action.IDLE, 0));
            }
            if(action == Action.WALK) {
                if(stepsRemaining -1 != 0) {
                    return List.of(new Person(room, Action.WALK, stepsRemaining - 1));
                } else {
                    return List.of(new Person(room, Action.VALVE, 0));
                }
            }

            List<Person> nextStep = new ArrayList<>();

            for (Room childRoom : toVisit) {
                //System.out.println(childRoom);
                //System.out.println(bitSet.get(idx));
                if(!bitSet.get(childRoom.id) && room != childRoom) {
                    //System.out.println(room.name + " " + childRoom.name);
                    int distance = distances.get(Route.create(room, childRoom));
                    if (distance <= (timeRemaining - 1)) {

                        nextStep.add(new Person(childRoom, Action.WALK, distance));
                    }
                }
            }
            if(nextStep.isEmpty()) {
                //System.out.println(tabs + " idle");
                return List.of(new Person(EMPTY, Action.IDLE, 0));
            }
            return nextStep;
        }
    }

    record Leaf(Leaf parent, int timeRemaining, int leafFlow, List<Leaf> children, Person me, Person elly) {

        static Leaf create2(Leaf parent, int timeRemaining, BitSet vistedTheRoom, List<Room> toVisit, Person me, Person elly) {

            int runningTotal = parent == null ? 0 : parent.leafFlow();

            //System.out.println(me.room.name + " " + me.action + me.stepsRemaining + " " + runningTotal + " " + timeRemaining);
            //System.out.println(elly.room.name + " " + elly.action + elly.stepsRemaining + " " + runningTotal + " " + timeRemaining);
            // Cheating here a little, only the first call can be in a zero time location.

            timeRemaining--;

            runningTotal += me.flow(timeRemaining);
            runningTotal += elly.flow(timeRemaining);

            List<Leaf> children = new ArrayList<>();

            Leaf current = new Leaf(parent, timeRemaining, runningTotal, children, me, elly);

            if(timeRemaining > 0) {
                for (Person newMe : me.nextStates(timeRemaining, toVisit, vistedTheRoom, "")) {
                    BitSet bitSet = (BitSet) vistedTheRoom.clone();
                    int idx = newMe.room.id;
                    if(idx != -1) {
                        bitSet.set(idx);
                    }
                    for (Person newEl : elly.nextStates(timeRemaining, toVisit, bitSet, "")) {
                        //System.out.println(current.tabString() +  "< " + me.room.name + " " + elly.room.name);
                        //System.out.println(current.tabString() + "> " + newMe.room.name + " " + newEl.room.name + " - " + current.leafFlow);
                        BitSet bitSet2 = (BitSet) bitSet.clone();
                        idx = newEl.room.id;
                        if(idx != -1) {
                            bitSet2.set(idx);
                        }

                        if(newEl.room == EMPTY && newMe.room == EMPTY) {
                            //System.out.println("w00t" + current.leafFlow);
                        } else {
                            Leaf l = create2(current, timeRemaining, bitSet2, toVisit, newMe, newEl);
                            if(parent == null) {
                                int myMax = l.max();
                                int max = children.stream().mapToInt(Leaf::max).max().orElse(0);
                                if(max < myMax){
                                    children.clear();
                                    children.add(l);
                                    System.gc();
                                }

                            } else {


                                children.add(l);
                            }
                        }
                    }
                }
            }
            return current;
        }

        String tabString() {
            char[] g =new char[tab()];
            Arrays.fill(g, ' ');
            return new String(g);
        }

        int max() {
            /*
            char[] spaces = new char[tab()];
            Arrays.fill(spaces, ' ');
            System.out.println(new String(spaces) + me.room.name + " - " + me.action + " " + leafFlow);
            System.out.println(new String(spaces) + elly.room.name + " - " + elly.action );
            System.out.println();
            */
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
