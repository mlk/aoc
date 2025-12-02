package com.github.mlk.aoc2025

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(data.split(",").map { ULongRange(it.split("-")[0].trim().toULong(), it.split("-")[1].trim().toULong()) }.sumOf {
        it.sumOf { v ->
            if (v.toString().matches("(.+)(\\1)+".toRegex())) v else 0UL
        }
    })
}