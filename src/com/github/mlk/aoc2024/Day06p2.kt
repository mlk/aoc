package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File

fun main() {
    val data = File("d:/input.txt").readLines()
    val directions = listOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))

    var count = 0

    for(ny in data.indices) {
        for (nx in data[ny].indices) {
            var currentLocation = data.find('^')
            var currentDirection = 0
            val previousLocation = mutableSetOf<Pair<P, Int>>()
            val newBlock = P(nx, ny)


            if (newBlock != currentLocation) {

                while (data.charAt(currentLocation, '-') != '-' ) {
                    val locationPair = Pair(currentLocation, currentDirection)
                    if (previousLocation.contains(locationPair)) {
                        count++
                        break
                    } else {
                        previousLocation.add(locationPair)
                        var hasMoved = false
                        while (!hasMoved) {
                            val nextLocation = currentLocation.add(directions[currentDirection])
                            if (data.charAt(nextLocation, '-') != '#' && nextLocation != newBlock) {
                                currentLocation = nextLocation
                                hasMoved = true
                            } else {
                                currentDirection = (currentDirection + 1) % directions.size
                            }
                        }
                    }
                }
            }
        }
    }
    println(count)
}
