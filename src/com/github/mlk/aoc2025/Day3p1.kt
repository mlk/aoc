package com.github.mlk.aoc2025

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(
        data.split("\n")
            .map { it.trim() }
            .filter { !it.isEmpty() }.sumOf { currrentNumber ->

                val numbers = currrentNumber.toCharArray().map { it.digitToInt() }.toList()
                val maxPair =
                    numbers.subList(0, numbers.size - 1).mapIndexed { index, i -> Pair(index, i) }.maxBy { it.second }
                val secondMax = numbers.subList(maxPair.first + 1, numbers.size).max()
                maxPair.second * 10 + secondMax
            }
    )
}
