package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("d:/input.txt").readLines()
    val funs = listOf<(Long, Long) -> Long>({x, y -> x + y},
        {x, y -> x * y},
        {x, y -> "$x$y".toLong()},
    )

    doTheThing(data, funs)
}

