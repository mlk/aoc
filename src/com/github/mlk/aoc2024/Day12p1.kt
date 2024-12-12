package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.cardinalDirections
import com.github.mlk.common.charAt
import com.github.mlk.common.forEachPoint
import java.io.File

val visited = mutableSetOf<P>()

fun main() {
    val data = File("d:/input.txt").readLines()
    val patches = findPatches(data)

    println(patches.map { patch ->
        patch.second.size * patch.second.sumOf { data.countNeighboursDifferentFromSelf(it) }
    }.sum())
}

fun findPatches(data: List<String>): MutableList<Pair<Char, Set<P>>> {
    val patches = mutableListOf<Pair<Char, Set<P>>>()
    data.forEachPoint { loc, _ ->
        if (!visited.contains(loc)) {
            val patch = data.findIdenticalNeighbours(loc)
            patches.add(Pair(data.charAt(loc), patch))
        }
    }
    return patches
}

fun List<String>.countNeighboursDifferentFromSelf(loc: P): Int {
    return cardinalDirections.map {
        loc.add(it)
    }.count {
        this.charAt(it) != this.charAt(loc)
    }
}

fun List<String>.findIdenticalNeighbours(loc: P): Set<P> {
    visited.add(loc)
    return setOf(loc) + cardinalDirections.map {
        loc.add(it)
    }.filter {
        (!visited.contains(it)) && this.charAt(it) == this.charAt(loc)
    }.map {
        findIdenticalNeighbours(it)
    }.flatten()
}

