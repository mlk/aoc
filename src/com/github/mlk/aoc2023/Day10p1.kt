package com.github.mlk.aoc2023

import java.io.File

fun main() {
    val p = Point(0, 0)
    val data = File("/home/mlk/Downloads/example3.txt").readLines()
    data.forEach { line ->
        println(line)
    }
}
