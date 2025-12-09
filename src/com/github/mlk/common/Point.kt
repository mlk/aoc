package com.github.mlk.common

import kotlin.math.abs

data class P(val x: Int, val y: Int) {
    fun add(other: P) = P(x + other.x, y + other.y)
    fun sub(other: P) = P(x - other.x, y - other.y)
    fun mul(other: Int) = P(x * other, y * other)
    fun manhattanDistance(other: P): Int = abs(x-other.x) + abs(y-other.y)
    fun cardinalNeighbours() = cardinalDirections.map { this.add(it) }
    fun neighbours() = directions.map { this.add(it) }
    fun inBounds(h: Int, w: Int) = x >= 0 && x <= h && y >= 0 && y <= w
}

val directions = listOf(
    P(1, 1),  P(0, 1),  P(-1, 1),
    P(1, 0),                  P(-1, 0),
    P(1, -1), P(0, -1), P(-1, -1),
)

val cardinalDirections = listOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))

fun Collection<P>.minX(): Int {
    return this.minOfOrNull { it.x } ?: 0
}

fun Collection<P>.minY(): Int {
    return this.minOfOrNull { it.y } ?: 0
}

fun Collection<P>.maxX(): Int {
    return this.maxOfOrNull { it.x } ?: 0
}

fun Collection<P>.maxY(): Int {
    return this.maxOfOrNull { it.y } ?: 0
}


data class PL(val x: Long, val y: Long) {
    fun add(other: PL) = PL(x + other.x, y + other.y)
    fun area(other: PL) = ((abs(y - other.y)) + 1) * ((abs(x- other.x)) + 1)
    fun toRect(other: PL): Shape {
        val width = abs(x- other.x)
        val height = abs(y - other.y)
        val x = minOf(x, other.x)
        val y = minOf(y, other.y)

        return Shape(listOf(PL(x, y), PL(x+width, y), PL(x+width, y+height), PL(x, y+height)))
    }
}

fun String.toPL() = PL(this.split(",")[0].toLong(), this.split(",")[1].toLong())

data class Shape(val points: List<PL>) {
    fun fitsWithin(other: Shape): Boolean {
        return !points.any { p -> other.points.any { o -> if(p.area(o) > 1) true else false } }
    }
}
