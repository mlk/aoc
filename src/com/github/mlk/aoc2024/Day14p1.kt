package com.github.mlk.aoc2024

import com.github.mlk.common.P
import java.io.File

fun main() {
    val size = P(101, 103)
    val parser = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
    val data = File("/home/mlk/input.txt").readLines()
        .map {
            val results = parser.find(it)!!.groups
            Pair(P(results[1]!!.value.toInt(), results[2]!!.value.toInt()), P(results[3]!!.value.toInt(), results[4]!!.value.toInt()))
        }.map {
            var r = it
            for(i in 0..99) {
                r = r.move(size)
        }
            r
        }
    val hor = size.x / 2
    val ver = size.y / 2
    val q1 = Pair(P(0,0),  P(hor, ver))
    val q2 = Pair(P(hor + 1, 0),  P(size.x, ver))
    val q3 = Pair(P(0, ver + 1),  P(hor, size.y))
    val q4 = Pair(P(hor + 1, ver + 1),  P(size.x, size.y))

    var quarts = listOf(q1, q2, q3, q4)

    data.display(size)
    println(quarts.map { q ->
        println(data.count { it.first.inScope(q.first, q.second) })
        data.count { it.first.inScope(q.first, q.second) }
    }.fold(1) { x, y -> x * y})
 }

fun P.inScope(tl: P, br: P): Boolean = this.x >= tl.x && this.x < br.x && this.y >= tl.y && this.y < br.y

fun List<Pair<P, P>>.display(size: P) {
    val locations = this.map{ it.first }.toSet()
    println(locations)
    for(y in 0..size.y - 1) {
        for(x in 0..size.x - 1) {
            if(locations.contains(P(x, y))) print("*") else print(".")
        }
        println()
    }
}

fun Pair<P,P>.move(size: P): Pair<P, P> {
    var nX = (first.x + second.x) % size.x
    if (nX < 0) nX = size.x + nX
    var nY = (first.y + second.y) % size.y
    if (nY < 0) nY = size.y + nY

    return Pair(P(nX, nY), second)
}