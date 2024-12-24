package com.github.mlk.aoc2024

import java.io.File

fun main() {

    val data = File("c:/aocs/input.txt").readLines()
        .map {
            val split = it.split("-")
            listOf(Pair(split[0], split[1]), Pair(split[1], split[0]))
        }.flatten().toHashSet()

    val keys = data.map { it.first }.toSet()
    val resultSet = mutableSetOf<String>()
    keys.forEachIndexed { idx, i1 ->
        keys.forEach { i2 ->
            if(i1 != i2) {
                keys.forEach { i3 ->
                    if(i1 != i3 && i3 != i2) {
                        if(i1.startsWith("t") || i2.startsWith("t") || i3.startsWith("t")) {
                            if (data.contains(Pair(i1, i2))
                                && data.contains(Pair(i1, i3))
                                && data.contains(Pair(i2, i3))) {
                                resultSet.add(listOf(i1, i2, i3).sortedDescending().joinToString())
                            }
                        }
                    }
                }
            }
        }
    }
    println(resultSet.filter { it.contains("t") }.count())
}