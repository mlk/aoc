package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("c:/aocs/input.txt").readText().split("\n\n").map { it.trim() }

    val locks = data.filter { it.startsWith("#####") }
    val keys = data.filter { it.endsWith("#####") }

    var counter = 0
    for (lock in locks) {
        for (key in keys) {
            if (fits(lock, key)) {
                counter++
            }
        }
    }
    println(counter)
}

fun fits(lock: String, key: String): Boolean {
    lock.forEachIndexed { i, c ->
        if (c == '#' && key[i] == '#') return false
    }
    return true
}
