package com.github.mlk.aoc2024

import com.github.mlk.common.*
import java.io.File

fun main() {
    val data = File("d:/input.txt").readLines()

    println(data.mapPoints { loc, item ->
        check(loc, item.toString().toInt(), -1, data, emptyList()).filter { it.second == 9 }.map { it.first }.distinct()
    }.flatten()
        .count())
}

fun check(loc: P, value: Int, previousValue: Int, data: List<String>, journey: List<Pair<P, Int>>): List<Pair<P, Int>> {
    return if(previousValue + 1 == value) {
        if(journey.map {it.first }.contains(loc)) {
            return journey
        }
        if (value == 9) {
            return journey + Pair(loc, value)
        } else {
            return cardinalDirections.map {
                val nextLoc = it.add(loc)
                if (data.inBounds(nextLoc)) check(nextLoc, data[nextLoc.y][nextLoc.x].toString().toInt(), value, data, journey + Pair(loc, value))
                else journey
            }.flatten()
        }
    } else journey
}
