package com.github.mlk.aoc2025

import com.github.mlk.common.toULongRange
import java.io.File
import kotlin.collections.map

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(data.split(",").map { it.toULongRange() }.sumOf {
        it.sumOf { v ->
            if (v.toString().matches("(.+)(\\1)+".toRegex())) v else 0UL
        }
    })
}