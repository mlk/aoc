package com.github.mlk.common

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
    this.forEachIndexed { x, line ->
        line.forEachIndexed { y, item -> action(P(x, y), item) }
    }
}
