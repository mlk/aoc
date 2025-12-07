package com.github.mlk.aoc2025

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }

    println(step(data.find('S'), data))
}

val prev = mutableMapOf<P, ULong>()

fun step(currentLocation: P, data: List<String>): ULong {
    if (currentLocation in prev) {
        return prev.getValue(currentLocation)
    }

    if(currentLocation.y == data.lastIndex) return 1UL
    if(data.charAt(currentLocation) == '.') {
        val r = step(currentLocation.add(P(0, 1)), data)
        prev[currentLocation] = r
        return r
    } else {
        val r = step(currentLocation.add(P(-1, 1)), data) + step(currentLocation.add(P(+1, 1)), data)
        prev[currentLocation] = r
        return r
    }
}
