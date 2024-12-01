package com.github.mlk.aoc2023

import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readLines()
    val times = data[0].replace("Time:", "").replace(" ", "").split(" ").filter { !it.isEmpty() }.map { it.toULong() }
    val distances = data[1].replace("Distance:", "").replace(" ", "").split(" ").filter { !it.isEmpty() }.map { it.toULong() }
    var result: ULong = 1u
    times.zip(distances).map { t ->
        ULongRange(0u, t.first).count { button ->
             (t.first - button) * button > t.second
        }
    }.forEach { t -> result = result * t.toULong()  }
    println(result)
}
