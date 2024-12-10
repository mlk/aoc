package com.github.mlk.common

data class P(val x: Int, val y: Int) {
    fun add(other: P) = P(x + other.x, y + other.y)
    fun sub(other: P) = P(x - other.x, y - other.y)
    fun mul(other: Int) = P(x * other, y * other)
}

val directions = listOf(
    P(1, 1),  P(0, 1),  P(-1, 1),
    P(1, 0),            P(-1, 0),
    P(1, -1), P(0, -1), P(-1, -1),
)

val cardinalDirections = listOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))