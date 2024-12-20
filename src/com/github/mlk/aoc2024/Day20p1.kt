package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File

fun main() {
    val data = File("c:/aocs/input.txt").readLines()
    val start = data.find('S')
    val end = data.find('E')
    val points = mutableMapOf<P, Int>()

    buildPoints(data, points, end)

    println(points)
    println(start)
    val saved = mutableMapOf<Int, Int>()
    var count = 0
    points.forEach { (key, value) ->
        directionsJump.zip(directions).forEach { (dir,d1) ->

            if (!points.containsKey(key.add(d1))) {
                val jumpTo = key.add(dir)
                val jumpToLocation = points[jumpTo]
                if (jumpToLocation != null) {
                    val skip = (jumpToLocation - value) - 2
                    if (skip > 0) {
                        saved[skip] = saved.getOrDefault(skip, 0) + 1
                        println("$key to $jumpTo -> $skip")
                    }
                    if(skip >= 100) { count++ }
                }
            }
        }
    }
    saved.toSortedMap().forEach { (key, value) -> println("$key -> $value") }
    println(count)
}

val directions = listOf(P(1, 0), P(-1, 0), P(0, 1), P(0, -1))
val directionsJump = listOf(P(2, 0), P(-2, 0), P(0, 2), P(0, -2))

fun buildPoints(map: List<String>, points: MutableMap<P, Int>, loc1: P) {
    var stepsAway = 1
    var loc = loc1
    while(map.charAt(loc) != 'S') {
        points[loc] = stepsAway

        stepsAway++
        loc = directions.map { loc.add(it) }
            .filter { !points.containsKey(it) }
            .first { map.charAt(it) != '#' }
    }
    points[loc] = stepsAway
}

