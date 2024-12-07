package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("d:/example.txt").readLines()

    val funs = listOf<(Long, Long) -> Long>({x, y -> x + y},
        {x, y -> x * y},
    )

    doTheThing(data, funs)
}

fun doTheThing(data: List<String>, funs: List<(Long, Long) -> Long>) {
    println(data.sumOf {
        val line = it.split(":")
        val total = line[0].toLong()
        val numbers = line[1].split(" ").filter { n -> n.isNotEmpty() }
            .map { n -> n.toLong() }
        val options = calc(numbers, funs)
        if (options.count { option -> option == total } >= 1) total else 0
    })
}

fun calc(nums: List<Long>, funs: List<(Long, Long) -> Long>): List<Long> {
    if (nums.size == 1) return listOf(nums[0])
    val result = mutableListOf<Long>()
    for(func in funs) {
        val currentResult = func(nums[0], nums[1])
        val newList = mutableListOf<Long>()
        newList.add(currentResult)
        newList.addAll(nums.drop(2))
        result.addAll(calc(newList, funs))
    }
    return result
}

