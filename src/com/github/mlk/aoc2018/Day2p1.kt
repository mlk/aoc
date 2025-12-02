package com.github.mlk.aoc2018

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    val maps = data.split("\n")
        .filter { !it.isEmpty() }
        .map { m ->
            m.toCharArray().map { Pair(it, 1) }
                .groupBy { it.first }
                .map { Pair(it.key, it.value.count()) }
                .filter { it.second == 2 || it.second == 3 }
                .map { it.second }
                .distinct()
        }


    println(maps.count { it.contains(2) } * maps.count { it.contains(3) })
}