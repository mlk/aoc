package com.github.mlk.aoc2024

import com.github.mlk.common.*
import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readLines()
    val patches = findPatches(data)

    println(patches.sumOf { patchA ->
        patchA.second.size * patchA.second.sumOf { data.countCorners(it)}
    })
}
