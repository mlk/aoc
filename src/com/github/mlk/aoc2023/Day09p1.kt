package com.github.mlk.aoc2023

import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readLines()
    println(data.sumOf { line ->
        var cur = line.split(" ").map { it -> it.toInt() }
        var rows = mutableListOf<List<Int>>()
        rows.add(cur)
        while (!cur.all { it == 0 }) {
            cur = cur.zipWithNext().map { it.second - it.first }
            rows.add(cur)
        }
        rows.sumOf { it.last() }
    })
}
