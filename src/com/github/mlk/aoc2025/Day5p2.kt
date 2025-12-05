package com.github.mlk.aoc2025

import com.github.mlk.common.toULongRange
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n\n")
    var ranges = data[0].split("\n").map { it.toULongRange() }.distinct().toList()
    var size: Int
    do {
        size = ranges.size
        ranges = ranges.map { range ->
            ULongRange(ranges.filter { it.contains(range.first) }.minOf { it.first }, range.endInclusive)
        }.map { range ->
            ULongRange(range.first, ranges.filter { it.contains(range.endInclusive) }.maxOf { it.endInclusive })
        }.distinct()
        ranges = ranges.filterIndexed { index, range ->
            ranges.forEachIndexed { otherIndex, otherRange ->
                if (otherIndex != index) {
                    if (range.first >= otherRange.first && range.first <= otherRange.endInclusive) {
                        if (range.endInclusive >= otherRange.first && range.endInclusive <= otherRange.endInclusive) {
                            return@filterIndexed false
                        }
                    }
                }
            }
            true
        }

    } while (ranges.size != size)
    println(ranges.sumOf { it.endInclusive - it.first + 1U })
}

