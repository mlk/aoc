package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.forEachPoint
import com.github.mlk.common.maxX
import com.github.mlk.common.maxY
import java.io.File

fun main() {
    val raw = File("/home/mlk/input.txt").readText()
        .replace(".", "..")
        .replace("#", "##")
        .replace("O", "[]")
        .replace("@", "@.")
        .split("\n\n")
    val map = raw[0].split("\n")
    val directions = raw[1]
        .filter { it != '\n' }
        .map {
        when(it) {
            '^' -> P(0, -1)
            'v' -> P(0, 1)
            '<' -> P(-1, 0)
            '>' -> P(1, 0)
            else -> throw RuntimeException("unknown $it")
        }
    }
    val walls = mutableSetOf<P>()
    val boxes = mutableSetOf<Box>()
    var robot = P(0, 0)
    map.forEachPoint { loc, item ->
        when(item) {
            '@' -> robot = loc
            '#' -> walls.add(loc)
            '[' -> boxes.add(Box(loc))
        }
    }

    directions.forEach { dir ->
        val newLocation = robot.add(dir)
        if(!walls.contains(newLocation)) {
            if(boxes.any { it.collides(newLocation)}) {
                val box = boxes.first { it.collides(newLocation) }
                val moveTheseGuys = tryMove(box, dir, walls, boxes, setOf(box))
                if (!moveTheseGuys.isEmpty()) {
                    robot = newLocation
                    moveTheseGuys.forEach {
                        it.loc = it.loc.add(dir)
                    }
                }
            } else {
                robot = newLocation
            }
        }
    }
    println(boxes.sumOf { (it.loc.y * 100) + it.loc.x})
}

fun tryMove(box: Box, dir: P, walls: Set<P>, boxes: Set<Box>, visited: Set<Box>): Set<Box> {
    val newLoc = box.newLocation(dir)
    if(newLoc.any { walls.contains(it) } ) {
        return emptySet()
    } else if(boxes.filter { !visited.contains(it) }.any { it.collides(newLoc) } ) {
        val moveTheseAsWell = boxes.filter { !visited.contains(it) } .filter { it.collides(newLoc) }
            .map { tryMove(it, dir, walls, boxes, setOf(box) + visited) }
        if(moveTheseAsWell.any { it.isEmpty() }) {
            return emptySet()
        } else {
            return setOf(box) + moveTheseAsWell.flatten().toSet()
        }
    } else {
        return setOf(box)
    }
}

class Box(var loc: P) {
    fun locs() = setOf(loc, loc.add(P(1, 0)))
    fun collides(l: P) = locs().contains(l)
    fun collides(l: Set<P>) = locs().any { l.contains(it) }
    fun newLocation(dir: P): Set<P> {
        val n = loc.add(dir)
        return setOf(n, n.add(P(1, 0)))
    }
}


private fun display(robot: P, walls: Set<P>, boxes: Set<Box>) {
    val maxX = walls.maxX()
    val maxY = walls.maxY()
    for(y in 0..maxY) {
        for(x in 0..maxX) {
            if(walls.contains(P(x, y))) {
                print("#")
            } else if(boxes.map { it.loc }.contains(P(x, y))) {
                print("[")
            } else if(boxes.map { it.loc.add(P(1, 0)) }.contains(P(x, y))) {
                print("]")
            } else if(robot == P(x, y)) {
                print("@")
            } else {
                print(".")
            }
        }
        println()
    }
}

