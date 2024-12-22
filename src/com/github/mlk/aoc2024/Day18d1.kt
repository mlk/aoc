package com.github.mlk.aoc2024

import com.github.mlk.common.P
import java.io.File
import java.util.PriorityQueue

fun main() {
    val mem = File("/home/mlk/input.txt").readLines()
        .map {
            val s = it.split(",").map { i -> i.toInt() }
            return@map P(s[0], s[1])
        }.filterIndexed { i, _ -> i < 1024}
    println(mem.size)
    val start = P(0, 0)
    val end = P(70, 70)

    println(findPath(start, end, mem)!!.shortestDistStart)
}

fun findPath(start:P, end:P, mem: List<P>): Node? {
    val toProcess = PriorityQueue<Node>()
    toProcess.add(Node(start, 0, start.manhattanDistance(end), null))
    val nodes = mutableMapOf<P, Node>()
    var current = toProcess.remove()
    while(current.p != end) {
        current.p.cardinalNeighbours()
            .filter { it != current.prev && it.inBounds(end.x, end.y) && !mem.contains(it) }
            .forEach { n ->
                if(nodes.containsKey(n)) {
                    val node = nodes[n]!!
                    val newDist = current.shortestDistStart + 1
                    if(node.shortestDistStart > newDist) {
                        //println(node.p)
                        if (toProcess.contains(node)) {
                            toProcess.remove(node)
                        }
                        node.shortestDistStart = newDist
                        node.prev = current
                        toProcess.add(node)
                    }
                } else {
                    val newNode = Node(n, current.shortestDistStart + 1, end.manhattanDistance(n), current)
                    nodes[n] = newNode
                    toProcess.add(newNode)
                }
            }
        if(toProcess.isEmpty()) return null
        current = toProcess.remove()
    }
    return current
}


data class Node(val p: P, var shortestDistStart: Int, val hDist: Int, var prev: Node?) : Comparable<Node> {
    fun totalDist() = shortestDistStart + hDist

    override fun compareTo(other: Node) = totalDist() - other.totalDist()

    fun hasP(loc: P): Boolean {
        val lPrev = prev
        if(p == loc) return true
        if(lPrev == null) return false
        return lPrev.hasP(loc)
    }

    fun toPointList(): List<P> {
        val lPrev = prev
        return if(lPrev == null) listOf(p)
        else lPrev.toPointList() + listOf(p)
    }
}
