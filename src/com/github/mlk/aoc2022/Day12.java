package com.github.mlk.aoc2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {
    static String testInput = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi""";

    static String input = """
            abaaaaaccccccccccccccccccaaaaaaaaaaaaaccccaaaaaaaccccccccccccccccccccccccccccaaaaaa
            abaaaaaaccaaaacccccccccccaaaaaaaaacaaaacaaaaaaaaaacccccccccccccccccccccccccccaaaaaa
            abaaaaaacaaaaaccccccccccaaaaaaaaaaaaaaacaaaaaaaaaacccccccccccccaacccccccccccccaaaaa
            abaaaaaacaaaaaacccccccccaaaaaaaaaaaaaaccaaacaaaccccccccccccccccaacccccccccccccccaaa
            abccaaaccaaaaaacccaaaaccaaaaaaaaaaaaaccccaacaaacccccccccaacaccccacccccccccccccccaaa
            abcccccccaaaaaccccaaaacccccaaaaacccaaaccaaaaaaccccccccccaaaaccccccccccccccccccccaac
            abcccccccccaaaccccaaaacccccaaaaacccccccccaaaaaccccccccccklllllccccccccccccccccccccc
            abcccccccccccccccccaaccccccccaaccccccccaaaaaaaccccccccckklllllllcccccddccccaacccccc
            abaccccccccccccccccccccccccccaaccccccccaaaaaaaaccccccckkkklslllllcccddddddaaacccccc
            abacccccccccccccccccccccccccccccccaaaccaaaaaaaaccccccckkkssssslllllcddddddddacccccc
            abaccccccccccccccccccccccccccccccccaaaaccaaacaccccccckkksssssssslllmmmmmdddddaacccc
            abcccccccccccccccaaacccccccccccccaaaaaaccaacccccccccckkkssssusssslmmmmmmmdddddacccc
            abcccccccaaccccaaaaacccccccccccccaaaaaccccccaaaaaccckkkrssuuuussssqmmmmmmmmdddccccc
            abcccccccaaccccaaaaaacccccccaaccccaaaaacccccaaaaacckkkkrruuuuuussqqqqqqmmmmdddccccc
            abccccaaaaaaaacaaaaaacccccccaaaaccaaccaccccaaaaaacjkkkrrruuuxuuusqqqqqqqmmmmeeccccc
            abcaaaaaaaaaaacaaaaaccccccaaaaaacccccaaccccaaaaajjjjrrrrruuuxxuvvvvvvvqqqmmmeeccccc
            abcaacccaaaaccccaaaaaaacccaaaaacccacaaaccccaaaajjjjrrrrruuuxxxxvvvvvvvqqqmmeeeccccc
            abaaaaccaaaaacccccccaaaccccaaaaacaaaaaaaacccaajjjjrrrrtuuuuxxxyvyyyvvvqqqnneeeccccc
            abaaaaaaaaaaacccaaaaaaaccccaacaacaaaaaaaacccccjjjrrrttttuxxxxxyyyyyvvvqqnnneeeccccc
            abaaaaaaaccaacccaaaaaaaaacccccccccaaaaaaccccccjjjrrrtttxxxxxxxyyyyyvvvqqnnneeeccccc
            SbaaaaaacccccccccaaaaaaaaaccccccccaaaaacccccccjjjrrrtttxxxEzzzzyyyvvrrrnnneeecccccc
            abaaaaacccccccccccaaaaaaacccccccccaaaaaaccccccjjjqqqtttxxxxxyyyyyvvvrrrnnneeecccccc
            abaaacccccccccccaaaaaaaccaaccccccccccaaccaaaaajjjqqqttttxxxxyyyyyyvvrrrnnneeecccccc
            abaaacccccccccccaaaaaaaccaaacaaacccccccccaaaaajjjjqqqtttttxxyywyyyywvrrnnnfeecccccc
            abcaaacccccccaaaaaaaaaaacaaaaaaaccccccccaaaaaaciiiiqqqqtttxwyywwyywwwrrrnnfffcccccc
            abcccccccccccaaaaaaaaaaccaaaaaacccccccccaaaaaacciiiiqqqqttwwywwwwwwwwrrrnnfffcccccc
            abccccccccccccaaaaaacccaaaaaaaacccccccccaaaaaaccciiiiqqqttwwwwwswwwwrrrrnnfffcccccc
            abccccccccccccaaaaaacccaaaaaaaaacccccccccaaacccccciiiqqqtswwwwssssrrrrrroofffcccccc
            abccccccaaaaacaaaaaacccaaaaaaaaaaccccccccccccccccciiiqqqssswsssssssrrrrooofffaccccc
            abccccccaaaaacaaccaaccccccaaacaaacccccccccccccccccciiiqqssssssspoorrrooooofffaacccc
            abcccccaaaaaacccccccccccccaaacccccccccccccccccccccciiiqppssssspppooooooooffffaacccc
            abcccccaaaaaacccccccccccccaacccccccccccccccccccccccciipppppppppppoooooooffffaaccccc
            abcccccaaaaaaccccccccccccccccccccccccccccccccccccccciihppppppppgggggggggfffaaaccccc
            abccccccaaacccccccccccccccccccccccaccccccccccccccccchhhhpppppphggggggggggfaaaaccccc
            abaaaccccccccccccccccccccccaccccaaacccccccccccccccccchhhhhhhhhhgggggggggcaacccccccc
            abaaccaaaccaccccccccccccccaaacccaaacaacccaaaaacccccccchhhhhhhhhgaaccccccccccccccccc
            abaaacaaacaacccccccccaaaccaaaacaaaaaaaaccaaaaaccccccccchhhhhhaaaaacccccccccccccccca
            abaaaccaaaaaccccccccccaaacaaaaaaaacaaaaccaaaaaaccccccccccaaacccaaaacccccccccccaccca
            abcccaaaaaaccccccccccaaaaaaaaaaaaacaaaaccaaaaaaccccccccccaaaccccaaaccccccccccaaaaaa
            abcccaaaaaaaacccccccaaaaaaaaaaaaaaaaaccccaaaaaacccccccccccccccccccccccccccccccaaaaa
            abcccaacaaaaaccccccaaaaaaaaaaaaaaaaaaacccccaacccccccccccccccccccccccccccccccccaaaaa""";

    public static void main(String... args) {
        String[] data = input.split("\n");
        List<Point> starts = new ArrayList<>();
        Point end = new Point(-1, -1);

        int[][] heightMap = new int[data.length][data[0].length()];
        for (int x = 0; x < data.length; x++) {
            char[] heights = data[x].toCharArray();
            for (int y = 0; y < heights.length; y++) {

                if (heights[y] >= 'a' && heights[y] <= 'z') {
                    heightMap[x][y] = heights[y] - 'a';
                    if (heightMap[x][y] == 0) {
                        starts.add(new Point(x, y));
                    }
                } else if (heights[y] == 'S') {
                    heightMap[x][y] = '0';
                    starts.add(new Point(x, y));
                } else {
                    heightMap[x][y] = 25;
                    end = new Point(x, y);
                }
            }
        }

        Set<Point> previouslyVisited = new HashSet<>();
        Set<Point> currentLevel = new HashSet<>(starts);
        Set<Point> nextLevel = new HashSet<>();

        int level = 0;

        gotoFun: while (true) {
            for (Point p : currentLevel) {

                if (p.equals(end)) {
                    break gotoFun;
                }
                nextLevel.addAll(p.validRoutes(heightMap).stream().filter(x -> !previouslyVisited.contains(x)).toList());
                previouslyVisited.add(p);
            }
            level++;
            currentLevel = nextLevel;
            nextLevel = new HashSet<>();
            if (currentLevel.size() == 0) {
                break gotoFun;
            }
        }

        System.out.println(level);
    }

    record Point(int x, int y) {
        static List<Point> dirs = List.of(new Point(-1, 0),
                new Point(0, -1),  new Point(0, 1),
                new Point(1, 0));

        List<Point> validRoutes(int[][] heightMap) {
            return dirs.stream().map(x -> x.add(this))
                    .filter(x -> x.inBounds(heightMap.length, heightMap[0].length))
                    .filter(x -> x.heightAcceptable(this, heightMap))
                    .toList();
        }

        boolean heightAcceptable(Point previousPoint, int[][] heightMap) {
            int currentHeight = heightMap[previousPoint.x()][previousPoint.y()];
            int newHeight = heightMap[x()][y()];
            return newHeight<=currentHeight+1;
        }

        boolean inBounds(int maxX, int maxY) {
            return (x>=0 && x<maxX && y>0 && y<maxY);
        }

        Point add(Point p) {
            return new Point(x + p.x(), y + p.y());
        }
    }

}
