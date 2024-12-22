package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File
import java.util.PriorityQueue
import kotlin.system.exitProcess

fun main() {
    val map = File("/home/mlk/input.txt").readLines()
    //map.forEach { t -> println(t) }
    val dirMap = mutableMapOf<P, Set<P>>()
    dirMap[P(1, 0)] = setOf(P(0, 1), P(0, -1))
    dirMap[P(-1, 0)] = setOf(P(0, 1), P(0, -1))
    dirMap[P(0, 1)] = setOf(P(1, 0), P(-1, 0))
    dirMap[P(0, -1)] = setOf(P(1, 0), P(-1, 0))

    val start = map.find('S')
    val end = map.find('E')

    println(findPath(start, P(1, 0), end, dirMap, map)!!.shortestDistStart)
}
fun findPath(start:P, startDir: P, end:P, dirMap: Map<P, Set<P>>, map: List<String>): Node16? {
    val toProcess = PriorityQueue<Node16>()
    toProcess.add(Node16(Pair(start, startDir), 0, start.manhattanDistance(end), null))
    val nodes = mutableMapOf<Pair<P, P>, Node16>()
    var current = toProcess.remove()
    while(current.p.first != end) {
        val options = listOf(Pair(current.p.second, 1)) + dirMap[current.p.second]!!.map { Pair(it, 1001) }
        options.forEach { n ->
                val newKey = Pair(current.p.first.add(n.first), n.first)
                if(map.charAt(newKey.first, '#') != '#') {
                    if (map.charAt(newKey.first, '#') == '#') exitProcess(1)
                    if (nodes.containsKey(newKey)) {
                        val node = nodes[newKey]!!
                        val newDist = current.shortestDistStart + n.second
                        if (node.shortestDistStart > newDist) {
                            if (toProcess.contains(node)) {
                                toProcess.remove(node)
                            }
                            node.shortestDistStart = newDist
                            node.prev = current
                            toProcess.add(node)
                        }
                    } else {
                        val newNode = Node16(
                            newKey,
                            current.shortestDistStart + n.second,
                            end.manhattanDistance(newKey.first),
                            current
                        )
                        nodes[newKey] = newNode
                        toProcess.add(newNode)
                    }
                }
            }
        if(toProcess.isEmpty()) return null
        current = toProcess.remove()
    }
    return current
}

data class Node16(val p: Pair<P, P>, var shortestDistStart: Int, val hDist: Int, var prev: Node16?) : Comparable<Node16> {
    private fun totalDist() = shortestDistStart + hDist

    override fun compareTo(other: Node16) = totalDist() - other.totalDist()
}