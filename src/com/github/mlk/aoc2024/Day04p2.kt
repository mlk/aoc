package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    var count = 0
    data.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if(c == 'A') {
                val start = P(x, y)
                if (listOf(listOf(start.add(P(1, 1)), start.add(P(-1, -1))),
                    listOf(start.add(P(1, -1)), start.add(P(-1, 1)))).map {
                        it.map { l ->
                            data.charAt(l)
                        }.sorted().joinToString(separator = "")
                }.all { it == "MS"}) count++
            }
        }
    }
    println(count)
}