package com.gitlab.mlk.aoc2023

import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/example.txt").readLines()
    val times = data[0].replace("Time:", "").split(" ").filter { !it.isEmpty() }.map { it.toInt() }
    val distances = data[1].replace("Distance:", "").split(" ").filter { !it.isEmpty() }.map { it.toInt() }
    var result = 1
    times.zip(distances).map { t ->
        IntRange(0, t.first).count { button ->
             (t.first - button) * button > t.second
        }
    }.forEach { t -> result = result * t  }
    println(result)
}
