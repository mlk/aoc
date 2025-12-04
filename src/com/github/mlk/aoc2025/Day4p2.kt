package com.github.mlk.aoc2025

import com.github.mlk.common.charAt
import com.github.mlk.common.mapPoints
import com.github.mlk.common.replace
import java.io.File

fun main() {

    var data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n")
    var stop: Boolean
    var total = 0
    do {
        val toRemove = data.mapPoints { p, c ->
            if (c == '@') {
                Pair(p, p.neighbours().filter { data.charAt(it) == '@' }.size < 4)
            } else Pair(p, false)
        }.filter { it.second }.map { it.first }.toList()
        data = data.replace(toRemove, '.')
        total = total + toRemove.size
        stop = toRemove.isEmpty()
    } while (!stop)

    println(total)
}
