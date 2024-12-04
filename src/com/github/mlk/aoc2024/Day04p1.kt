package com.github.mlk.aoc2024

import java.io.File

data class P(val x: Int, val y: Int) {
    fun add(other: P) = P(x + other.x, y + other.y)
}

val directions = listOf(
    P(1, 1),  P(0, 1),  P(-1, 1),
    P(1, 0)         ,   P(-1, 0),
    P(1, -1), P(0, -1), P(-1, -1),
    )

val mas = "MAS"

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    var count = 0
    data.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if(c == 'X') {
                val start = P(x, y)
                count += directions.map { dir ->
                    var check = start
                    (1..3).map {
                        check = check.add(dir)
                        data.charAt(check)
                    }.joinToString(separator = "")
                }.count {
                    it == mas
                }
            }
        }
    }
    println(count)
}
fun List<String>.charAt(loc: P): Char {
    if(loc.y < 0 || loc.y >= this.size) return '.'
    if(loc.x < 0 || loc.x >= this[loc.y].length) return '.'
    return this[loc.y][loc.x]
}
