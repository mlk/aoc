package com.github.mlk.aoc2024

import com.github.mlk.devterm.ThermalPrinter
import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/example-d3.txt").readText()

    val regex = Regex("mul\\((\\d+),(\\d+)\\)")

    ThermalPrinter.print("${regex.findAll(data).map { match ->
        match.groups[1]!!.value.toLong() * match.groups[2]!!.value.toLong()
    }.sum()}")
}