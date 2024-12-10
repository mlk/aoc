package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("d:/input.txt").readText().replace("\n", "")

    val disk = data.toList().map{ i -> i.toString().toInt() }.zip(IntRange(0, (data.length / 2) + 1).zip(List((data.length / 2)+1) { _ -> -1 }).flatMap {p -> p.toList()})
        .flatMap { file -> List(file.first) { file.second } }.toIntArray()

    for(i in IntRange(1, disk.max()).reversed()) {
        val firstIndex = disk.indexOfFirst { it == i }
        val lastIndex = disk.lastIndexOf(i)
        val length = (lastIndex - firstIndex)


        var startIndex = -1
        var emptyLength = 0
        for(j in disk.indices){
            if(startIndex == -1) {
                if(disk[j] == -1) {
                    startIndex = j
                    emptyLength = 0
                }
            } else if(disk[j] == -1) {
                emptyLength++
            } else {
                startIndex = -1
            }
            if(startIndex != -1 && emptyLength == length){
                break
            }
        }
        if(startIndex != -1 && emptyLength == length && startIndex < firstIndex) {
            for(x in 0..emptyLength){
                disk[startIndex + x] = disk[firstIndex + x]
                disk[firstIndex + x] = -1
            }
        }
    }

    println(disk.mapIndexed { i, v ->
        if(v == -1) 0 else v.toLong()*i.toLong()
    }.sum())
}

private fun List<Int>.display() {
    this.forEach { e ->
        if(e == -1) {
            print("_")
        } else {
            print(e)
        }
    }
    println()
}