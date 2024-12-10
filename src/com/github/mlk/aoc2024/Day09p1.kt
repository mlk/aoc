package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("/home/mlk/input.txt").readText().replace("\n", "")

    val disk = data.toList().map{ i -> i.toString().toInt() }.zip(IntRange(0, (data.length / 2) + 1).zip(List((data.length / 2)+1) { _ -> -1 }).flatMap {p -> p.toList()})
        .flatMap { file -> List(file.first) { file.second } }.toIntArray()


    var end = disk.size - 1
    disk.forEachIndexed { i, v ->
        if(end < i) return@forEachIndexed
        if (v == -1 && disk[end] != -1) {
            disk[i] = disk[end]
            disk[end] = -1
            end--
            while(disk[end] == -1) end--
        }
    }
    /*
    disk.forEach { e ->
        if(e == -1) {
            print("_")
        } else {
            print(e)
        }
    }
    println()*/
    println(disk.mapIndexed { i, v ->
        if(v == -1) 0 else v.toLong()*i.toLong()
    }.sum())
}
