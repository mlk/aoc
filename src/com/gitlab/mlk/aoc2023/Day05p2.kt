package com.gitlab.mlk.aoc2023
import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readLines()
    var map = mutableListOf<Mapping>()
    val seeds = mutableListOf<ULongRange>()
    val finder = mutableListOf<List<Mapping>>()
    data.forEach { line ->
        if (line.startsWith("seeds: ")) {
            val s = line.replace("seeds: ", "").split(" ").map { it.toULong() }
            for(i in 0..(s.size-1) step 2) {
                seeds.add(ULongRange(s[i], s[i] + s[i+1] - 1u))
            }
        } else if(line.endsWith("map:")) {
            finder.add(map)
            map = mutableListOf()
        } else if(line != "") {
            val parts = line.split(" ")
            map.add(Mapping(parts[0].toULong(), parts[1].toULong(), parts[2].toULong()))
        }
    }
    finder.add(map)

    var smallest = ULong.MAX_VALUE
    for(r in seeds) {
        for(i in r) {
            val v = single(0, finder, i)
            if(v < smallest) smallest = v
        }
    }
    println(smallest)
}

fun single(start: Int, map: List<List<Mapping>>, value: ULong): ULong {
    if(start >= map.size) {
        return value
    }
    val dest = map[start]

    return single(start + 1, map, dest.replace(value))
}

