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
        .filter { pages -> !pages.isOrdered(ordering) }
        .map { it.sortedWith { o1, o2 -> ordering.order(o1, o2) }}
        .sumOf {
            it[it.size / 2]
        })
}

fun List<Pair<Int, Int>>.order(x: Int, y: Int): Int {
    if (this.contains(Pair(x, y))) return 1
    if (this.contains(Pair(y, x))) return -1
    return 0
}

fun List<Int>.isOrdered(ordering: List<Pair<Int, Int>>): Boolean {
    this.forEachIndexed { i, v ->
        for (otherIndex in 0..i) {
            if (ordering.contains(Pair(v, this[otherIndex]))) {
                return false
            }
        }
    }
    return true
}
