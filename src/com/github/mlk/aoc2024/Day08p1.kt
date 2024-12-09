package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.forEachPoint
import com.github.mlk.common.inBounds
import com.github.mlk.common.pair
import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    val ants = mutableMapOf<Char, MutableList<P>>()
    val expected = mutableListOf<P>()
    data.forEachPoint { loc, item ->
        if (item.isLetterOrDigit()) {
            ants.getOrPut(item) { mutableListOf() }.add(loc)
        } else if (item == '#') {
            expected.add(loc)
        }
    }

    println(ants.flatMap { ant ->
        ant.value.pair()
            .map { it.first.sub(it.second).add(it.first) }
            .filter { data.inBounds(it)}
    }.distinct().count())
}

