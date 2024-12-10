package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val forwards = mutableMapOf<Int, MutableList<Int>>()
    val backwards = mutableMapOf<Int, MutableList<Int>>()
    val data = File("/home//mlk/input.txt").readLines()
    data.filter { it.contains('|') }.forEach { line ->
        val pairing = line.split("|").map { it.toInt() }
        val f = forwards.getOrPut(pairing[0]) { mutableListOf() }
        val b = backwards.getOrPut(pairing[1]) { mutableListOf() }
        f.add(pairing[1])
        b.add(pairing[0])
    }
    data.filter { it.contains(",") }.filter { line ->
        val pages = line.split(",").map { it.toInt() }
        pages.forEachIndexed { i, num ->
            
        }
        true
    }
}