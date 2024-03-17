package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.List;

public class Day20 {
    record DataPoint(long value, int originalIndex) {}

    static class ListItem {
        DataPoint data;
        ListItem next;
        ListItem previous;

        void display() {
            System.out.print("[ ");
            System.out.print(data.value + ", ");
            next.display(this);
        }

        ListItem findValue(long value) {
            if(value == data.value) {
                return this;
            }
            return next.findValue(value, this);
        }

        ListItem findValue(long value, ListItem first) {
            if(value == data.value) {
                return this;
            }

            if(first == this) {
                return null;
            }

            return next.findValue(value, first);
        }

        ListItem find(DataPoint data) {
            if(data.equals(this.data)) {
                return this;
            }
            return next.find(data, this);
        }

        ListItem find(DataPoint data, ListItem start) {
            if(start == this) {
                return null;
            }
            if(data.equals(this.data)) {
                return this;
            }
            return next.find(data, start);
        }

        void moveForward() {
            ListItem myNext = next.next;
            ListItem myPrevious = next;

            previous.next = next;
            next.previous = previous;

            next.next.previous = this;
            next.next = this;
            next = myNext;
            previous = myPrevious;
        }


        void moveBackward() {

            ListItem startingPrevious = previous;
            ListItem startingNext = next;

            startingNext.previous = startingPrevious;
            startingPrevious.next = startingNext;

            ListItem previousPrevious = startingPrevious.previous;

            startingPrevious.previous.next = this;
            startingPrevious.previous = this;
            this.previous = previousPrevious;
            this.next = startingPrevious;
        }


        void display(ListItem item) {
            if(item == this) {
                System.out.println("]");
            } else {
                System.out.print(data.value + ", ");
                next.display(item);
            }
        }
    }

    public static void main(String... arg) {
        String[] data = Day1Data.data.split("\n");
        List<DataPoint> originalList = new ArrayList<>();
        ListItem first = new ListItem();
        ListItem previous = first;
        first.data = new DataPoint(Long.parseLong(data[0]) * 811589153, 0);
        originalList.add(first.data);

        for(int i = 1; i< data.length; i++) {
            DataPoint value = new DataPoint(Long.parseLong(data[i]) * 811589153, i);
            originalList.add(value);
            ListItem current = new ListItem();
            current.data = value;
            current.previous = previous;
            previous.next = current;
            previous = current;
        }
        previous.next = first;
        first.previous = previous;

        for(int t = 0; t<10;t ++ ) {
            for (DataPoint d : originalList) {
                long dValue = d.value % (originalList.size()-1);
                ListItem item = first.find(d);

                if (d.value > 0) {
                    for (int i = 0; i < dValue; i++) {
                        item.moveForward();
                    }
                } else if (d.value < 0) {
                    for (int i = 0; i < Math.abs(dValue); i++) {
                        item.moveBackward();
                    }
                }
            }
            //first.display();
        }
        ListItem n = first.findValue(0);


        long total = 0;
        for(int i = 0; i<3; i++) {
            for(int x = 0; x<1000;x++) {
                if(n.next == null) {
                    System.out.println(n);
                }
                n = n.next;
            }
            total += (n.data.value);
            System.out.println(n.data.value);
        }
        System.out.println(" >> " + total);
    }
}

