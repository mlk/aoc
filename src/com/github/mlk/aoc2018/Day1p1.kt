package com.github.mlk.aoc2018

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    println(data.split("\n")
        .filter { !it.isEmpty() }
        .sumOf { it.toInt() })
}