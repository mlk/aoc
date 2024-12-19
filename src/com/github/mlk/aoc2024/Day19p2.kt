package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    val towelsReg = Regex("(${data[0].split(", ").joinToString("|")})+")
    val towels = data[0].split(", ")
    val possible = data.drop(2).sumOf { design ->
        countPossibleDesigns(design, towels, towelsReg) }
    println(possible)
}

private val d19cache = mutableMapOf<String, Long>()

fun countPossibleDesigns(remainder: String, towels: List<String>, towelsReg: Regex): Long {
    if (d19cache.contains(remainder)) return d19cache[remainder]!!
    if(remainder.isEmpty()) return 1L
    if(!towelsReg.matches(remainder)) {
        return 0L
    }
    val c = towels.filter { remainder.startsWith(it) }.sumOf { countPossibleDesigns(remainder.drop(it.length), towels, towelsReg) }
    d19cache[remainder] = c
    return c
}

