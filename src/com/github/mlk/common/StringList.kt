package com.github.mlk.common

fun List<String>.replace(locs: List<P>, default: Char = '.'): List<String> {
    return this.mapIndexed { y, line ->
        val chars = line.toCharArray()
        locs.forEach { loc ->
            if(loc.y == y) {
                chars[loc.x] = default
            }
        }
        return@mapIndexed chars.joinToString("")
    }.toList()
}

fun List<String>.charAt(loc: P, default: Char = '.'): Char {
    if(loc.y < 0 || loc.y >= this.size) return default
    if(loc.x < 0 || loc.x >= this[loc.y].length) return default
    return this[loc.y][loc.x]
}

fun List<String>.inBounds(loc: P): Boolean {
    if(loc.y < 0 || loc.y >= this.size) return false
    if(loc.x < 0 || loc.x >= this[loc.y].length) return false
    return true
}

fun List<String>.find(item: Char): P {
    this.forEachIndexed { y, line ->
        val x = line.indexOf(item)
        if(x != -1) {
            return P(x, y)
        }
    }
    throw RuntimeException("Can't find $item")
}

fun List<String>.forEachPoint(action: (P, Char) -> Unit) {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, item -> action(P(x, y), item) }
    }
}

fun <T> List<String>.mapPoints(action: (P, Char) -> T) =
    this.mapIndexed { y, line -> line.mapIndexed { x, item -> action(P(x, y), item) } }
        .flatten()

private val corners = listOf(Pair(P(1, 0), P(0, 1)), Pair(P(0, 1), P(-1, 0)), Pair(P(-1, 0), P(0, -1)), Pair(P(0, -1), P(1, 0)))
private val innerCorners = listOf(P(1, 1), P(-1, 1), P(-1, -1), P(1, -1))

fun List<String>.countCorners(loc: P) = corners
    .map { Pair(loc.add(it.first), loc.add(it.second)) }
    .count { charAt(loc) != charAt(it.first) && charAt(loc) != charAt(it.second) } +
        corners
            .map { Pair(loc.add(it.first), loc.add(it.second)) }
            .zip(innerCorners.map { loc.add(it) })
            .count { charAt(loc) == charAt(it.first.first) && charAt(loc) == charAt(it.first.second) && charAt(loc) != charAt(it.second) }
