package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    val ordering = data.filter { it.contains('|') }.map { line ->
        val l = line.split("|")
        Pair(l[0].toInt(), l[1].toInt())
    }
    println(data.filter { it.contains(",") }
        .map { line -> line.split(",").map { it.toInt() } }
        .filter { pages -> pages.isOrdered(ordering) }
        .sumOf { it[it.size / 2] })
}