package com.github.mlk.aoc2018

import java.io.File
import kotlin.system.exitProcess

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    val boxCodes = data.split("\n")
        .filter { !it.isEmpty() }
        .toList()
    boxCodes.forEach{ boxCode ->
        val length = boxCode.length
        boxCodes.forEach { nextBoxCode ->
            if (boxCode.zip(nextBoxCode).count { it.first == it.second } == length - 1) {
                println(boxCode.zip(nextBoxCode).filter { it.first == it.second }.joinToString("") { it.first.toString() })
                exitProcess(0)
            }
        }
    }
}