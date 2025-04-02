package com.github.mlk.aoc2024

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.system.exitProcess

suspend fun main() {
    coroutineScope {
        var expected = listOf(2, 4, 1, 7, 7, 5, 0, 3, 4, 0, 1, 7, 5, 5, 3, 0).map { it.toLong() }
        //var expected = listOf(2, 1, 4, 0, 7, 4, 0, 2, 3).map { it.toLong() }

        val totalChecks = (8.0.pow(16).toLong() - 8.0.pow(15).toLong()) / 8

        var checks = 0L
        val showPercentage = totalChecks / 10_000



        println("Start Up")
        //for(startA in 62761524L..62777524L step 8 ) {
        for (startA in 8.0.pow(15).toLong() + 2 + (8 * (totalChecks / 2)) ..8.0.pow(16).toLong() step 8) {
            launch(Dispatchers.Default) {
                var a = startA
                var b = 0L
                var c = 0L
                var counter = 0
                var found = true

                while (a != 0L) {
                    b = a % 8L
                    b = b.xor(7)
                    c = (a / 2.0.pow(b.toInt())).toLong()
                    a = a / 8
                    b = b.xor(c)
                    b = b.xor(7)
                    if ((b % 8) != expected[counter]) {
                        found = false
                        a = 0
                        break
                    }
                    counter++
                }
                if (found) {
                    println("Found: $startA")
                    exitProcess(1)
                }
            }
            checks++
            if (checks % showPercentage <= 8) {
                println("$checks/$totalChecks (${(checks / totalChecks.toFloat()) * 100}%)")
            }
        }
    }
}