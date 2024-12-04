package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import java.io.File

fun main() {
    val data = File("C:\\Users\\Michael Lee\\Downloads\\input.txt").readLines()
    val count = data.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == 'A') {
                val start = P(x, y)
                if (listOf(
                        listOf(start.add(P(1, 1)), start.add(P(-1, -1))),
                        listOf(start.add(P(1, -1)), start.add(P(-1, 1)))
                    ).map {
                        it.map { l ->
                            data.charAt(l)
                        }.sorted().joinToString(separator = "")
                    }.all { it == "MS" }
                ) 1 else 0
            } else 0
        }.sum()
    }.sum()
    println(count)
}
