package com.github.mlk.aoc2024

import java.io.File

fun main() {

    val edges = File("c:/aocs/input.txt").readLines()
        .map {
            val split = it.split("-")
            listOf(Pair(split[0], split[1]), Pair(split[1], split[0]))
        }.flatten()

    val neighbours = mutableMapOf<String, MutableSet<String>>()
    edges.forEach {
        neighbours.getOrPut(it.first) { mutableSetOf() }.add(it.second)
    }

    val nodes = edges.map { listOf(it.first, it.second) }.flatten().toSet()

    val results = mutableSetOf<Set<String>>()
    borsKerboosch(emptySet(), nodes.toMutableSet(), mutableSetOf(), neighbours, results)

    println(results.sortedBy { it.size }.reversed().first().sorted().joinToString(","))
}

fun borsKerboosch(r: Set<String>, p: Set<String>, x: MutableSet<String>, neighbours: Map<String, Set<String>>, results: MutableSet<Set<String>>) {
    if(p.isEmpty() && x.isEmpty()) {
        results.add(r)
        return
    }
    val mp = p.toMutableSet()
    p.forEach { v ->
        borsKerboosch(r.union(setOf(v)), mp.intersect(neighbours[v]!!).toMutableSet(), x.intersect(neighbours[v]!!).toMutableSet(), neighbours, results)
        mp.remove(v)
        x.add(v)
    }
}
