package com.github.mlk.common

fun <T> List<T>.pair(): List<Pair<T, T>> {
    val returnList = mutableListOf<Pair<T, T>>()
    forEach { x ->
        forEach { y ->
            if (x != y) returnList.add(Pair(x, y))
        }
    }
    return returnList
}
