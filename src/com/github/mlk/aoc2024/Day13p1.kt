package com.github.mlk.aoc2024

import com.github.mlk.common.P
import java.io.File

fun main() {
    val data = File("d:/input.txt").readText()
        .split("\n\n")
        .map { section ->
            val lines = section.split("\n")
            val a = parseP(lines[0])
            val b = parseP(lines[1])
            val prize = parseP(lines[2])
            Pair(prize, Pair(a, b))
        }
        .map {
            val prize = it.first
            val a = it.second.first
            val b = it.second.second
            var lowest = Long.MAX_VALUE
            for (aPresses in 0 .. 100) {
                for (bPresses in 0 .. 100) {
                    val move = a.mul(aPresses).add(b.mul(bPresses))

                    if(move == prize) {
                        val currentCost = (aPresses * 3L) + (bPresses * 1L)
                        if(currentCost < lowest) lowest = currentCost
                    }
                }
            }
            return@map if (lowest == Long.MAX_VALUE) 0L else lowest
        }.sum()

    println(data)
}

private val pMatcher = Regex(".*X.(\\d+), Y.(\\d+)")

private fun parseP(line: String): P {
    val groups = pMatcher.find(line)?.groups
    return P(groups!![1]!!.value.toInt(), groups[2]!!.value.toInt())
}


