package com.github.mlk.aoc2018

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    var current = 0
    val existing = mutableSetOf<Int>()
    var result: String?
    do {
        result = data.split("\n")
            .filter { !it.isEmpty() }
            .find {
                current = current + it.toInt()
                if (existing.contains(current)) return@find true
                existing.add(current)
                false
            }
    } while (result == null)
    println(current)

}