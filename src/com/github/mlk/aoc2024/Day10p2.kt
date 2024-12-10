package com.github.mlk.aoc2024

import com.github.mlk.common.*
import java.io.File

fun main() {
    val data = File("d:/input.txt").readLines()

    println(data.mapPoints { loc, item ->
        check(loc, item.toString().toInt(), -1, data, emptyList()).filter { it.second == 9 }
    }.flatten()
        .count())
}
