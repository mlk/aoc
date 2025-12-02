package com.github.mlk.aoc2025

import com.github.mlk.common.toULongRange
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(data.split(",").map { it.toULongRange() }.sumOf {
        it.sumOf { v ->
            val value = v.toString()
            if ((value.length % 2) == 0) {
                val half = value.length / 2
                if (value.substring(half) == value.substring(0, half)) v else 0UL
            } else 0UL
        }
    })
}