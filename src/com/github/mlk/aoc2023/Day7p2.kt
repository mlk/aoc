package com.github.mlk.aoc2023

import java.io.File

fun main() {
    var total: ULong = 0u
    File("/home/mlk/Downloads/input.txt").readLines().map {
        val k = it.split(" ")
        Pair(k[0], k[1].toULong())
    }.sortedWith { o1, o2 ->
        val s1 = o1.first.score()
        val s2 = o2.first.score()
        if(s1 == s2) {
            return@sortedWith o1.first.compare(o2.first)
        }
        return@sortedWith (s1 - s2) * 100
    }.forEachIndexed { index, pair ->
        total = total + (pair.second * (index.toULong() + 1u))
    }
    println(total)
}

private val order = charArrayOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

private fun String.compare(other: String): Int {
    other.forEachIndexed { i, ch ->
        val c = ch.compare(this[i])
        if (c != 0) return c
    }
    return 0
}

private fun Char.compare(other: Char): Int =
    order.indexOf(other) - order.indexOf(this)

private fun Map<Char, Int>.score(): Int {
    var score = this.values.maxOf { it }
    if (score == 5 || score == 4) return score + 2
    if (score == 3 && this.values.contains(2)) return 5
    if (score == 3 && !this.values.contains(2)) return 4
    if (score == 2 && this.values.count { i -> i == 2 } == 2) return 3
    return score
}

private fun Map<Char, Int>.score(j: Int): Int {
    if(j == 0) return score()
    return keys.map { i ->

        val c = mutableMapOf<Char, Int>()
        c.putAll(this.toList())
        c.put('J', c.getOrDefault('J', 0) - 1)
        c.put(i, c.getOrDefault(i, 0) + 1)
        c.score(j - 1)
    }.maxOf { it }
}

private fun String.score(): Int {
    val charMap = mutableMapOf<Char, Int>()
    this.forEach { c ->
        charMap.compute(c) { k, v -> (v ?: 0) + 1 }
    }
    val j = charMap.getOrDefault('J', 0)
    return charMap.score(j)
}
