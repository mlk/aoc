package com.github.mlk.aoc2022;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 {
    record Location(int x, int y, int z) {
        boolean inBounds(boolean[][][] map) {
            return (x >= 0 && x < map.length)
                    && (y >= 0 && y < map[0].length)
                    && (z >= 0 && z < map[0][0].length);
        }

        Location add(Location other) {
            return new Location(x + other.x, y + other.y, z + other.z);
        }
    }

    public static void main(String... arg) {
        List<Location> records  = Arrays.stream(Day18Data.data.split("\n"))
                .map(x -> x.split(","))
                .map(l -> new Location(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]))).toList();

        int maxX = records.stream().mapToInt(x -> x.x).max().getAsInt() + 1;
        int maxY = records.stream().mapToInt(x -> x.y).max().getAsInt() + 1;
        int maxZ = records.stream().mapToInt(x -> x.z).max().getAsInt() + 1;

        boolean[][][] map = new boolean[maxX][maxY][maxZ];
        boolean[][][] routeToEdge = new boolean[maxX][maxY][maxZ];
        for(Location r : records) {
            map[r.x][r.y][r.z] = true;
        }

        for(int x = 0; x<maxX; x++) {
            for(int y = 0; y<maxY; y++) {
                for(int z = 0; z<maxZ; z++) {
                    if(hasRouteToEdge(new Location(x, y, z), map, routeToEdge, new HashSet<>())) {
                        routeToEdge[x][y][z] = true;
                    }
                }
            }
        }

        // Fill in...
        for(int x = 0; x<maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                for (int z = 0; z < maxZ; z++) {
                    if(!routeToEdge[x][y][z] && !map[x][y][z]) {
                        map[x][y][z] = true;
                    }
                }
            }
        }

        System.out.println(records.stream().mapToInt(r -> count(map, r)).sum());
    }


    static boolean hasRouteToEdge(Location location, boolean[][][] worldMap, boolean[][][] routeExists, Set<Location> checked) {
        if(checked.contains(location)) {
            return false;
        }
        checked.add(location);
        if(!location.inBounds(worldMap)) {
            return true;
        }

        if(worldMap[location.x][location.y][location.z]) {
            return false;
        }
        if(routeExists[location.x][location.y][location.z]) return true;
        for(Location p : pointsToCheck) {
            if(hasRouteToEdge(p.add(location), worldMap, routeExists, checked)) {
                routeExists[location.x][location.y][location.z] = true;
                return true;
            }
        }

        return false;
    }

    static Location[] pointsToCheck = new Location[]{
            new Location(-1, 0, 0), new Location(1, 0, 0),
            new Location(0, -1, 0), new Location(0, 1, 0),
            new Location(0, 0, -1), new Location(0, 0, 1),
    };

    static int count(boolean[][][] map, Location location) {
        int total = 0;
        for(Location dir : pointsToCheck) {
            Location check = dir.add(location);

            if (check.inBounds(map)) {
                if (!map[check.x][check.y][check.z]) {
                    total++;
                }
            } else {
                total++;
            }
        }
        return total;
    }
}
