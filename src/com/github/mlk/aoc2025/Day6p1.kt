package com.github.mlk.aoc2025

import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }.map { it.split("\\s+".toRegex()).filter { !it.isEmpty() }}
    val cal = data.last()
    println(cal)
    val nums = data.dropLast(1).map { it.map { it.toULong() } }


    var grandTotal = 0UL
    for (i in 0..cal.size-1) {
        var total = 0UL
        if(cal[i] == "*") {
            total = 1UL
        }
        for(item in nums) {
            if(cal[i] == "+") {
                total += item[i]
            } else {
                total *= item[i]
            }
        }
        println("$total")
        grandTotal += total
    }
    println(grandTotal)
}