package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("c:/aocs/input.txt").readLines().filter { it.isNotBlank() }
        .map {
            if(it.contains(":")) {
                return@map Pair(it.substringBefore(":"), D24Value(it.substringAfter(": ") == "1"))
            } else {
                val target = it.substringAfter(" -> ")
                val rest =  it.substringBefore(" -> ").split(" ")
                if(rest[1] == "AND") {
                    return@map Pair(target, D24AND(rest[0], rest[2]))
                } else if(rest[1] == "XOR") {
                    return@map Pair(target, D24XOR(rest[0], rest[2]))
                } else if(rest[1] == "OR") {
                    return@map Pair(target, D24OR(rest[0], rest[2]))
                } else {
                    throw RuntimeException("unknown $it")
                }
            }
        }.toMap()

    val zNum = data.keys.filter { it.startsWith("z") }.sortedDescending()

    println(zNum.joinToString("") {
        if(data[it]!!.process(data)) "1" else "0"
    }.toLong(2))
}


interface D24Node {
    fun process(data: Map<String, D24Node>): Boolean
}

class D24Value(val value: Boolean) : D24Node {
    override fun process(data: Map<String, D24Node>): Boolean {
        return value
    }
}

class D24AND(val v1: String, val v2: String) : D24Node {
    override fun process(data: Map<String, D24Node>): Boolean {
        return data[v1]!!.process(data) && data[v2]!!.process(data)
    }
}

class D24XOR(val v1: String, val v2: String) : D24Node {
    override fun process(data: Map<String, D24Node>): Boolean {
        return data[v1]!!.process(data).xor(data[v2]!!.process(data))
    }
}

class D24OR(val v1: String, val v2: String) : D24Node {
    override fun process(data: Map<String, D24Node>): Boolean {
        return data[v1]!!.process(data) || data[v2]!!.process(data)
    }
}
