package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.find
import java.io.File
import kotlin.math.abs

fun main() {
    val data = File("c:/aocs/input.txt").readLines()
    val start = data.find('S')
    val end = data.find('E')
    val points = mutableMapOf<P, Int>()

    buildPoints(data, points, end)

    val saved = mutableMapOf<Int, Int>()
    var count = 0L
    points.forEach { (key, value) ->
        key.circle(20).forEach { jumpTo ->
            val jumpToLocation = points[jumpTo]
            if (jumpToLocation != null) {
                val jumpSize = jumpTo.sub(key)
                val skip = (jumpToLocation - value) - (abs(jumpSize.x) + abs(jumpSize.y))
                if (skip >= 100) {
                    saved[skip] = saved.getOrDefault(skip, 0) + 1
                    //println("$key to $jumpTo -> $skip")
                }
                if (skip >= 100) {
                    count++
                }
            }

        }
    }
    saved.toSortedMap().forEach { (key, value) -> println("$key -> $value") }
    println(count)
}

fun P.circle(size: Int): List<P> {
    val list = mutableListOf<P>()
    IntRange(-size, size).forEach { y ->
        IntRange(-(size - abs(y)), size - abs(y)).forEach { x ->
            list.add(P(this.x + x, this.y + y))
        }
    }
    return list
}