package com.github.mlk.aoc2023

import com.github.mlk.devterm.ThermalPrinter
import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readLines()
    val path = data[0]
    val map = data.drop(2).associate { line ->
        val p = line.split(" = ")
        val t = p[1].replace("(", "").replace(")", "").replace(",", "").split(" ")
        Pair(p[0], Pair(t[0], t[1]))
    }
    var locations = map.keys.filter { it.endsWith("A") }.toSet()

    var counter = 0u

    while (!locations.all { it.endsWith("Z") }) {
        val oldLocations = locations
        locations = mutableSetOf()
        for(location in oldLocations) {
            val pair = map[location] !!
            val dir = path[(counter % path.length.toUInt()).toInt()]
            locations.add(if (dir == 'L') pair.first else if (dir == 'R') pair.second else "fuck")
        }
        counter ++
    }

    println(counter)
    //ThermalPrinter.print("Answer!")
}