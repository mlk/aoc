package com.github.mlk.aoc2025

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }
    var currentDrips = setOf(data.find('S'))

    var split = 0
    data.forEachIndexed { y, line ->
        currentDrips = currentDrips.map { println(data.charAt(P(it.x, y))); P(it.x, y) }
            .map {
                if (data.charAt(it) == '^') {
                    split++
                    listOf(P(it.x-1, it.y), P(it.x+1, it.y))
                }
                else listOf(it)
            }.flatten().toSet()
    }
    println(split)
}