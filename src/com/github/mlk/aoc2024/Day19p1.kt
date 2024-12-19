package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("/home/mlk/example-2024-d19.txt").readLines()
    val towelsReg = Regex("(${data[0].split(", ").joinToString("|")})+")
    val possible = data.drop(2).count { design -> towelsReg.matches(design) }
    println(possible)
}


