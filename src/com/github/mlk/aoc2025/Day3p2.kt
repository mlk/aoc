package com.github.mlk.aoc2025

import java.io.File

fun main() {

    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(
        data.split("\n")
            .map { it.trim() }
            .filter { !it.isEmpty() }.sumOf { currentNumber ->

                val numbers = currentNumber.toCharArray().map { it.digitToInt() }.toList()
                val numList = mutableListOf<Int>()
                var startPoint = 0
                for (i in 11 downTo 0) {
                    val maxPair =
                        numbers.subList(startPoint, numbers.size - i).mapIndexed { index, i -> Pair(index, i) }
                            .maxBy { it.second }
                    numList.add(maxPair.second)
                    startPoint = maxPair.first + 1 + startPoint
                }
                numList.joinToString("") { it.toString() }.toULong()
            }
    )
}
