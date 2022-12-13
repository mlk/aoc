package com.github.mlk.aoc2022;

import java.util.Arrays;
import java.util.Map;

public class Day02 {
    static String input = """
            <retracted>""";

    enum Bits {
        ROCK(1, 'X', 'A'),
        PAPER(2, 'Y', 'B'),
        SCISSORS(3, 'Z', 'C');

        final int score;
        final char me;
        final char them;

        Bits(int score, char me, char them) {
            this.score = score;
            this.me = me;
            this.them = them;
        }

        public boolean wins(Bits other) {
            return (this == ROCK && other == SCISSORS)
                    || (this == SCISSORS && other == PAPER)
                    || (this == PAPER && other == ROCK);
        }

        static Bits parseThem(char v) {

            return Arrays.stream(Bits.values()).filter(x -> x.them == v).findFirst().get();
        }
        Bits parseMe(char v) {
            if(v == 'X') {
                return wins.get(this);
            } else if(v == 'Y') {
                return this;
            }
            return loss.get(this);
        }
    }

    private static Map<Bits, Bits> wins = Map.of(Bits.ROCK, Bits.SCISSORS,
            Bits.SCISSORS, Bits.PAPER,
            Bits.PAPER, Bits.ROCK);

    private static Map<Bits, Bits> loss = Map.of(Bits.SCISSORS, Bits.ROCK,
            Bits.PAPER,Bits.SCISSORS,
            Bits.ROCK, Bits.PAPER);

    public static void main(String... argv) {
        int score = 0;
        for(String line : input.split("\n")) {
            Bits them = Bits.parseThem(line.charAt(0));
            Bits me = them.parseMe(line.charAt(2));
            System.out.println(them + " "  + me);
            score += me.score;

            if(me.wins(them)) {
                score +=6;
            } else if(me == them) {
                score +=3;
            }
        }
        System.out.println(score);
    }

}
