package com.github.mlk.aoc2024

import com.github.mlk.common.P
import java.io.File

fun main() {
    val mem = File("/home/mlk/input.txt").readLines()
        .map {
            val s = it.split(",").map { i -> i.toInt() }
            return@map P(s[0], s[1])
        }

    val start = P(0, 0)
    val end = P(70, 70)

    var currentPath = findPath(start, end, mem.slice(IntRange(0, 1024)))
    var idx = 1024
    while(currentPath != null) {
        idx++
        val newMem = mem[idx]
        if(currentPath.hasP(newMem)) {
            currentPath = findPath(start, end, mem.slice(IntRange(0, idx)))
        }
    }
    println("${mem[idx].x},${mem[idx].y}")
}




