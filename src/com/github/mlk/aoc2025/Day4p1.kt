package com.github.mlk.aoc2025

import com.github.mlk.common.charAt
import com.github.mlk.common.mapPoints
import java.io.File

fun main() {

    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n")
    println(data.mapPoints { p, c ->
        if (c == '@') p.neighbours().filter { data.charAt(it) == '@' }.size < 4 else false
    }.count { it })
}
