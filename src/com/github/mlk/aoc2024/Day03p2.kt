package com.github.mlk.aoc2024

import com.github.mlk.devterm.ThermalPrinter
import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readText()

    val regex = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")

    var enabled = true
    ThermalPrinter.print("${regex.findAll(data).map { match ->
        if(match.groups[0]!!.value == "do()") enabled = true
        else if(match.groups[0]!!.value == "don't()") enabled = false
        else if (enabled) return@map match.groups[1]!!.value.toLong() * match.groups[2]!!.value.toLong()
        0
    }.sum()}")
}