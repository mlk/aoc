package com.github.mlk.aoc2025

import com.github.mlk.common.toULongRange
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n\n")
    val ranges = data[0].split("\n").map {it.toULongRange()}.toList()

    println(data[1].split("\n")
            .filter { !it.isEmpty() }
            .map { it.toULong() }
            .count { item -> ranges.any { range -> range.first <= item && item <= range.endInclusive } })
}