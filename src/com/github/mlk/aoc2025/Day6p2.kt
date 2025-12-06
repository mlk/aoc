package com.github.mlk.aoc2025

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }

    val cal = data.last()
    val nums = data.dropLast(1)

    val columnSizes = cal.split("(?=\\S\\s+)".toRegex()).map { it.length }.filter { it != 0 }.toList()

    var start = 0

    println(columnSizes.sumOf { i ->
        val current = IntRange(0, i -1).map { j ->
            nums.joinToString("") { it.substring(start + j,start + j + 1) }.trim()
        }.filter { !it.isEmpty()}

        val result = if(cal[start] == '+'){
            current.sumOf { it.toULong() }
        } else {
            current.map { it.toULong() }.reduce { acc, i -> acc * i }
        }

        start += i
        result
    })
}