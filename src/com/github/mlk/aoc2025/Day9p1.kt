package com.github.mlk.aoc2025

import com.github.mlk.common.P3D

import com.github.mlk.common.toP3D
import com.github.mlk.common.toPL
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }
        .map {it.toPL()}

    println(data.mapIndexed { i, p ->
        data.drop(i).filter { it != p }.map { p.area(it) }
    }.flatten().maxOf { it })

}