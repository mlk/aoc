package com.github.mlk.aoc2024

import com.github.mlk.common.PL
import java.io.File
import kotlin.math.abs

fun main() {
    //val addTo = PL(0L, 0L)
    val addTo = PL(10000000000000, 10000000000000)

    val data = File("d:/input.txt").readText()
        .split("\n\n")
        .map { section ->
            val lines = section.split("\n")
            val a = parsePL(lines[0])
            val b = parsePL(lines[1])
            val prize = parsePL(lines[2]).add(addTo)
            Pair(prize, Pair(a, b))
        }
        .map {
            val prizeX = it.first.x.toDouble()
            val prizeY = it.first.y.toDouble()
            val aX = it.second.first.x.toDouble()
            val aY = it.second.first.y.toDouble()
            val bX = it.second.second.x.toDouble()
            val bY = it.second.second.y.toDouble()
            val bPresses = (prizeX * aY - prizeY * aX) / (bX * aY - bY * aX)
            if(bPresses < 0) return@map 0L
            else {
                val bPressesAsLong = bPresses.toLong()
                if(abs( bPressesAsLong.toDouble() - bPresses) > 0.0000001) {
                    return@map 0L
                }
                val aPresses = (prizeX - bPresses * bX) / aX
                if(aPresses < 0) return@map 0L
                val aPressesAsLong = aPresses.toLong()
                if(abs( aPressesAsLong.toDouble() - aPresses) > 0.0000001) {
                    return@map 0L
                }
                else {
                    return@map (aPressesAsLong * 3) + bPressesAsLong
                }
            }
        }.sum()

    println(data)
}

private val pMatcher = Regex(".*X.(\\d+), Y.(\\d+)")
private fun parsePL(line: String): PL {
    val groups = pMatcher.find(line)?.groups
    return PL(groups!![1]!!.value.toLong(), groups[2]!!.value.toLong())
}


