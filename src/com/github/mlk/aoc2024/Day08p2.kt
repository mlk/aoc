package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.forEachPoint
import com.github.mlk.common.inBounds
import com.github.mlk.common.pair
import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    val ants = mutableMapOf<Char, MutableList<P>>()

    data.forEachPoint { loc, item ->
        if (item.isLetterOrDigit()) {
            ants.getOrPut(item) { mutableListOf() }.add(loc)
        }
    }

    println(ants.flatMap { ant ->
        ant.value.pair()
            .flatMap {
                val dir = it.first.sub(it.second)
                IntRange(0, data.size * 2).map { i ->  it.first.add(dir.mul(i)) }}
            .filter { data.inBounds(it)}
    }.distinct().count())
}

