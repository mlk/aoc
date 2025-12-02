package com.github.mlk.aoc2025

import java.io.File

fun main() {
    var c = 50
    var counter = 0
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    data.split("\n").map { it.trim() }.forEach { line ->
        if (!line.isEmpty()) {

            println(line)
            val data = line.replace("L", "").replace("R", "").toInt()
            for (i in 1..data) {
                if (line.startsWith("R")) c++
                else if (line.startsWith("L"))  c--
                if (c == -1) c = 99
                if (c == 100) c = 0
                if (c == 0) counter++
            }
            println("  > $c, $counter")
        }
    }
    println(counter)
}