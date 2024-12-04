package com.github.mlk.common

data class P(val x: Int, val y: Int) {
    fun add(other: P) = P(x + other.x, y + other.y)
}

val directions = listOf(
    P(1, 1),  P(0, 1),  P(-1, 1),
    P(1, 0)         ,   P(-1, 0),
    P(1, -1), P(0, -1), P(-1, -1),
)