package com.github.mlk.aoc2024

import java.io.File

fun main() {
    println(
        File("/home/mlk/input.txt").readText().replace("\n", "")
            .split(" ").sumOf { count(it.toLong(), 25) })

}

fun count(num: Long, layer: Int): Long {
    if(layer == 0) {
        return 1
    }
    if(num == 0.toLong()) return count(1, layer - 1)
    val nstr = num.toString()
    if (nstr.length % 2 == 0) {
        return count(nstr.substring(0, nstr.length / 2).toLong(), layer - 1) + count(nstr.substring(nstr.length / 2).toLong(), layer - 1)
    }
    return count(num * 2024, layer -1)
}
