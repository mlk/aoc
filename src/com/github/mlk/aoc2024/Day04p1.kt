package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.directions
import java.io.File

fun main() {
    val data = File("C:\\Users\\Michael Lee\\Downloads\\input.txt").readLines()
    val count = data.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == 'X') {
                val start = P(x, y)
                directions.map { dir ->
                    var check = start
                    (1..3).map {
                        check = check.add(dir)
                        data.charAt(check)
                    }.joinToString(separator = "")
                }.count {
                    it == "MAS"
                }
            } else 0
        }.sum()
    }.sum()
    println(count)
}
