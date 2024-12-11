package com.github.mlk.aoc2024

import java.io.File

val cache = mutableMapOf<Long, MutableMap<Int, Long>>()

fun main() {
    println(
        File("/home/mlk/input.txt").readText().replace("\n", "")
            .split(" ").sumOf { count(it.toLong(), 75) })

    println("$cacheHits / $totalCals - ${cacheHits.toFloat() / totalCals.toFloat()})")
}

var cacheHits = 0
var totalCals = 0

private fun count(num: Long, layer: Int): Long {
    totalCals++
    if (cache.containsKey(num) && cache[num]!!.containsKey(layer)) {
        cacheHits++
        return cache[num]!![layer]!!
    }

    val retunValue = if (layer == 0) {
        1
    } else if (num == 0L) {
        count(1, layer - 1)
    } else {
        val nstr = num.toString()
        if (nstr.length % 2 == 0) {
            count(nstr.substring(0, nstr.length / 2).toLong(), layer - 1) + count(
                nstr.substring(nstr.length / 2).toLong(), layer - 1
            )
        } else {
            count(num * 2024, layer - 1)
        }
    }
    cache.getOrPut(num) { mutableMapOf() }[layer] = retunValue
    return retunValue
}


