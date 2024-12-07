package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File

fun main() {
    val data = File("d:/example.txt").readLines()
    var currentLocation = data.find('^')
    println(currentLocation)
    val directions = listOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))
    var currentDirection = 0
    val previousLocation = mutableSetOf<P>()
    while(data.charAt(currentLocation, '-') != '-') {
        previousLocation.add(currentLocation)
        var hasMoved = false
        while(!hasMoved) {
            val nextLocation = currentLocation.add(directions[currentDirection])
            if (data.charAt(nextLocation, '-') != '#') {
                currentLocation = nextLocation
                hasMoved = true
            } else {
                currentDirection = (currentDirection + 1) % directions.size
            }
        }

    }
    println(previousLocation.size)
}

