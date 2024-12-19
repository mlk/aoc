package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.charAt
import com.github.mlk.common.find
import java.io.File
import kotlin.math.min
 
var dirMap = mutableMapOf<P, Set<P>>()
val totsVist = mutableMapOf<P, Int>()

fun main() {
    val map = File("/home/mlk/example.txt").readLines()
    dirMap[P(1, 0)] = setOf(P(0, 1), P(0, -1))
    dirMap[P(-1, 0)] = setOf(P(0, 1), P(0, -1))
    dirMap[P(0, 1)] = setOf(P(1, 0), P(-1, 0))
    dirMap[P(0, -1)] = setOf(P(1, 0), P(-1, 0))

    val start = map.find('S')
    println(move(map, start, P(1, 0), emptySet()))
}

fun move(map: List<String>, loc: P, dir: P, visited: Set<P>): Int {
    if(totsVist.contains(loc)) {
        println(loc)
        return totsVist[loc]!!
    }
    if(visited.contains(loc)) {
        println("in current tree $loc")
        return Int.MAX_VALUE
    }
    var c = map.charAt(loc)
    if(c == 'E') return 1
    if(c == '#') return Int.MAX_VALUE
    var s0 = 1 + move(map, loc.add(dir), dir, setOf(loc) + visited)
    val d1 =dirMap[dir]!!.first()
    var s1 = 1001+ move(map, loc.add(d1), d1, setOf(loc) + visited)
    val d2 =dirMap[dir]!!.last()
    var s2 = 1001 + move(map, loc.add(d2), d2, setOf(loc) + visited)
    if(s0 < 0) s0 = Int.MAX_VALUE
    if(s1 < 0) s1 = Int.MAX_VALUE
    if(s2 < 0) s2 = Int.MAX_VALUE
    val m = min(s0, min(s1, s2))
    totsVist[loc] = m
    return m
}
