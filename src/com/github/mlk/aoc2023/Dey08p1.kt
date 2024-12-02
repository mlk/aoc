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
    var location = "AAA"
    val target = "ZZZ"
    var counter = 0

    while (location != target) {

        val pair = map[location]!!
        val dir = path[counter % path.length]
        location = if (dir == 'L') pair.first else pair.second
        counter ++
    }

    println(counter)
    //ThermalPrinter.print("Answer!")
}