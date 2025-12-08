package com.github.mlk.aoc2025

import com.github.mlk.common.P
import com.github.mlk.common.P3D
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import com.github.mlk.common.toP3D
import java.io.File

fun main() {
    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
        .split("\n").filter { !it.isEmpty() }
        .map {it.toP3D()}

    val connections = data.mapIndexed { i, p ->
        data.drop(i).filter { it != p }.map { Pair(Pair(p,it), p.distanceSquared(it)) }
    }.flatten().sortedBy { it.second }

    val circuits = mutableListOf<MutableSet<P3D>>()
    circuits.addAll( data.map { mutableSetOf(it) }.toList())

    connections.forEach { pair ->

        val inCircuits = circuits.filter { it.contains(pair.first.first) || it.contains(pair.first.second)  }

        var inCircuit = inCircuits.firstOrNull()
        if (inCircuit == null) {
            inCircuit = mutableSetOf()
            circuits.add(inCircuit)
        } else if (inCircuits.size > 1) {
            circuits.removeAll(inCircuits)
            inCircuit = mutableSetOf()
            inCircuit.addAll(inCircuits.flatten())
            circuits.add(inCircuit)
        }
        inCircuit.add(pair.first.first)
        inCircuit.add(pair.first.second)
        if(circuits.size == 1) {
            println("${pair.first.first} - ${pair.first.second}")
            println(pair.first.first.x.toULong() * pair.first.second.x.toULong())
            return@main
        }
    }

}