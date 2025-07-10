package com.github.mlk.aoc2015

import java.io.File

fun main() {
    val regex = "(.*) would.* (gain|lose) (.*) happiness units by sitting next to (.*).".toRegex()

    val data = File("C:\\Users\\SR116\\Downloads\\input.txt").readText()
    val map = mutableMapOf<String, MutableMap<String, Int>>()
    for(line in data.split("\n")) {
        val bits = regex.find(line)
        val name = bits!!.groups[1]!!.value
        val gain = bits.groups[2]!!.value == "gain"
        val happyUnit = if(gain) bits.groups[3]!!.value.toInt() else - bits.groups[3]!!.value.toInt()
        val otherName = bits.groups[4]!!.value
        map.getOrPut(name) { mutableMapOf() }[otherName] = happyUnit
    }

    val names = map.keys.toList()
    val max = names.permutations().maxOfOrNull {
        var happy = 0
        for (i in it.indices) {
            val p1 = it[i]
            val p2 = it[(i + 1) % it.size]
            happy += map.getOrDefault(p1, mutableMapOf()).getOrDefault(p2, 0) + map.getOrDefault(p2, mutableMapOf()).getOrDefault(p1, 0)
        }
        happy
    }
    println(max)

}

fun <T> List<T>.permutations(): List<List<T>> = if(isEmpty()) listOf(emptyList()) else  mutableListOf<List<T>>().also{result ->
    for(i in this.indices){
        (this - this[i]).permutations().forEach{
            result.add(it + this[i])
        }
    }
}

