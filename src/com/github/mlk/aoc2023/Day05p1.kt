package com.github.mlk.aoc2023
import java.io.File

fun main() {
    val data = File("/home/mlk/Downloads/input.txt").readLines()
    var from = ""
    var to = ""
    var map = mutableListOf<Mapping>()
    var seeds = mutableListOf<ULong>()
    var finder = mutableMapOf<String, MutableMap<String, List<Mapping>>>()
    data.forEach { line ->
        if (line.startsWith("seeds: ")) {
            seeds.addAll(line.replace("seeds: ", "").split(" ").map { it.toULong() })
        } else if(line.endsWith("map:")) {
            finder.getOrPut(from, {mutableMapOf()}).put(to, map)
            from = line.substring(0, line.indexOf('-'))
            to = line.substring(line.indexOf("-to-") + 4, line.indexOf(" map:"))
            map = mutableListOf()
        } else if(line != "") {
            val parts = line.split(" ")
            map.add(Mapping(parts[0].toULong(), parts[1].toULong(), parts[2].toULong()))
        }
    }
    finder.getOrPut(from, {mutableMapOf()}).put(to, map)

    println(seeds)
    all("seed", finder, seeds)

}

fun all(start: String, map: Map<String, MutableMap<String, List<Mapping>>>, vals: List<ULong>)  {
    if(start == "location") {
        println(vals.sorted().get(0))
        return
    }
    val dest = map.get(start)!!
    dest.forEach { to, replace ->
        val newList = vals.map { replace.replace(it) }
        all(to, map, newList)
    }
}

fun List<Mapping>.replace(previous: ULong): ULong {
    forEach { t ->
        val newVal = t.replace(previous)
        if (newVal != previous) return newVal
    }
    return previous
}

data class Mapping(val replaceStart: ULong, val start: ULong, val length: ULong) {
    fun replace(previous: ULong): ULong {
        if (ULongRange(start, start + (length-1u)).contains(previous)) { return replaceStart + (previous - start) }
        return previous
    }
}