package com.github.mlk.common

fun List<String>.charAt(loc: P): Char {
    if(loc.y < 0 || loc.y >= this.size) return '.'
    if(loc.x < 0 || loc.x >= this[loc.y].length) return '.'
    return this[loc.y][loc.x]
}
