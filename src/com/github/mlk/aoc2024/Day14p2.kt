package com.github.mlk.aoc2024

import com.github.mlk.common.P
import java.io.File

fun main() {
    val size = P(101, 103)
    val parser = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
    var data = File("/home/mlk/input.txt").readLines()
        .map {
            val results = parser.find(it)!!.groups
            Pair(
                P(results[1]!!.value.toInt(), results[2]!!.value.toInt()),
                P(results[3]!!.value.toInt(), results[4]!!.value.toInt())
            )
        }

    for (i in 0..100000) {
        data = data.map { it.move(size) }
        val asString = data.displayString(size).split("\n").filter { it.isNotEmpty() }
        val lookFor = listOf("BBFFFBB", "BFFFFFB", "FFFFFFF")
        var l = 0
        var offset = -1
        asString.forEach { t ->
            if (offset == -1) {
                if (t.contains(lookFor[0])) {
                    offset = t.indexOf(lookFor[0])
                    l = 1
                }
            } else {
                if (t.substring(offset, offset + lookFor[l].length) == lookFor[l]) {
                    l++
                    if (l >= lookFor.size) {
                        println(" --- ${i + 1}")
                        data.display(size)
                        return
                        offset = -1
                    }
                } else {
                    l = 0
                    offset = -1
                }
            }
        }
    }
}

fun List<Pair<P, P>>.displayString(size: P): String {
    val locations = this.map{ it.first }.toSet()
    val buff = StringBuilder()
    for(y in 0..size.y - 1) {
        for(x in 0..size.x - 1) {
            if(locations.contains(P(x, y))) buff.append('F') else buff.append('B')
        }
        buff.append('\n')
    }
    return buff.toString()
}