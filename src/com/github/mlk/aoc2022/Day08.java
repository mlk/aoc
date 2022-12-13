package com.github.mlk.aoc2022;

public class Day08 {
    static String testData = """
            <retracted>""";

    static String data = """
            <retracted>""";

    public static void main(String... arg) {
        String[] lines = data.split("\n");
        int[][] trees = new int[lines.length][lines[0].length()];
        for(int x = 0; x< lines.length; x++) {
            char[] currentLine = lines[x].toCharArray();
            for(int y = 0; y< currentLine.length; y++) {
                trees[x][y] = Integer.parseInt(currentLine[y] + "");
            }
        }

        int highScore = 0;
        for(int x = 1; x< trees.length-1; x++) {
            for(int y = 1; y< trees[x].length-1; y++) {
                if(score(trees, x, y) > highScore) {
                    highScore = score(trees, x, y);
                }
            }

        }
        System.out.println(highScore);
    }

    static int score(int[][] trees, int x, int y) {

        int total = 0;

        int l = scoreL(trees, x, y);
        total = l;
        //System.out.println("L: " + l + " T " + total);

        int r = scoreR(trees, x, y);
        total = l * r;
        //System.out.println("R: " + r + " T " + total);

        int u = scoreU(trees, x, y);
        total = l * r * u;
        //System.out.println("U: " + u + " T " + total);

        int d = scoreD(trees, x, y);
        total = l * r * u * d;
        //System.out.println("D: " + d + " T " + total);

        return total;
    }

    static int scoreL(int[][] trees, int x, int y) {
        int score = 0;
        for(int i = x-1; i >= 0; i--) {
            score++;
            if(trees[x][y] <= trees[i][y]) {
               break;
            }
        }
        return score;
    }

    static int scoreD(int[][] trees, int x, int y) {
        int score = 0;
        for(int i = y-1; i >= 0; i--) {
            score++;
            if(trees[x][y] <= trees[x][i]) {
                break;
            }
        }

        return score;
    }

    static int scoreR(int[][] trees, int x, int y) {
        int score = 0;
        for(int i = x+1; i < trees.length; i++) {
            score++;
            if(trees[x][y] <= trees[i][y]) {
                break;
            }
        }
        return score;
    }

    static int scoreU(int[][] trees, int x, int y) {
        int score = 0;
        for(int i = y+1; i < trees.length; i++) {
            score++;
            if(trees[x][y] <= trees[x][i]) {
                break;
            }
        }

        return score;
    }

    static boolean isVisibleL(int[][] trees, int x, int y) {
        boolean lowest = true;
        for(int i = 0; i < x; i++) {
            if(trees[x][y] <= trees[i][y]) {
                return false;
            }
        }
        return lowest;
    }
    static boolean isVisibleR(int[][] trees, int x, int y) {
        boolean lowest = true;
        for(int i = trees.length-1; i > x; i--) {
            if(trees[x][y] <= trees[i][y]) {
                return false;
            }
        }
        return lowest;
    }
    static boolean isVisibleD(int[][] trees, int x, int y) {
        boolean lowest = true;
        for(int i = 0; i < y; i++) {
            if(trees[x][y] <= trees[x][i]) {
                return false;
            }
        }
        return lowest;
    }

    static boolean isVisibleU(int[][] trees, int x, int y) {
        boolean lowest = true;
        for(int i = trees[x].length-1; i > y; i--) {
            if(trees[x][y] <= trees[x][i]) {
                return false;
            }
        }
        return lowest;
    }
}
