package com.github.mlk.aoc2024

import com.github.mlk.common.*
import java.io.File

fun main() {
    val data = File("d:/input.txt").readLines()
    val patches = findPatches(data)

    println(patches.sumOf { patchA ->

        var lineCount = 0
        val patch = patchA.second
        for (y in patch.minY() - 1..patch.maxY() + 1) {
            var bottomLineStart = false
            var topLineStart = false
            for (x in patch.minX() - 1..patch.maxX() + 1) {
                val loc = P(x, y)
                if (patch.contains(loc) && data.charAt(loc.add(P(0, -1))) != patchA.first) {
                    bottomLineStart = true
                } else if (patch.contains(loc) && data.charAt(loc.add(P(0, -1))) == patchA.first ||
                    !patch.contains(loc)
                ) {
                    if (bottomLineStart) lineCount++
                    bottomLineStart = false
                }
                if (patch.contains(loc) && data.charAt(loc.add(P(0, 1))) != patchA.first) {
                    topLineStart = true
                } else if (patch.contains(loc) && data.charAt(loc.add(P(0, 1))) == patchA.first ||
                    !patch.contains(loc)
                ) {
                    if (topLineStart) lineCount++
                    topLineStart = false
                }
            }
        }
        for (x in patch.minX() - 1..patch.maxX() + 1) {
            var leftLineStart = false
            var rightLineStart = false
            for (y in patch.minY() - 1..patch.maxY() + 1) {
                val loc = P(x, y)
                if (patch.contains(loc) && data.charAt(loc.add(P(-1, 0))) != patchA.first) {
                    leftLineStart = true
                } else if (patch.contains(loc) && data.charAt(loc.add(P(-1, 0))) == patchA.first ||
                    !patch.contains(loc)
                ) {
                    if (leftLineStart) lineCount++
                    leftLineStart = false
                }
                if (patch.contains(loc) && data.charAt(loc.add(P(1, 0))) != patchA.first) {
                    rightLineStart = true
                } else if (patch.contains(loc) && data.charAt(loc.add(P(1, 0))) == patchA.first ||
                    !patch.contains(loc)
                ) {
                    if (rightLineStart) lineCount++
                    rightLineStart = false
                }
            }
        }

        patchA.second.size * lineCount
    })
}
