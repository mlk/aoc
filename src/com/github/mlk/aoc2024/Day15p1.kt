package com.github.mlk.aoc2024

import com.github.mlk.common.P
import com.github.mlk.common.forEachPoint
import com.github.mlk.common.maxX
import com.github.mlk.common.maxY
import java.io.File

fun main() {
    val raw = File("/home/mlk/input.txt").readText().split("\n\n")
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
    val boxes = mutableSetOf<P>()
    var robot = P(0, 0)
    map.forEachPoint { loc, item ->
        when(item) {
            '@' -> robot = loc
            '#' -> walls.add(loc)
            'O' -> boxes.add(loc)
        }
    }

    println(boxes.sumOf { (it.x * 100) + it.y})
    println(boxes.size)

    directions.forEach { dir ->
        val newLocation = robot.add(dir)
        if(!walls.contains(newLocation)) {
            if(boxes.contains(newLocation)) {
                var lastBoxNewLocation = newLocation.add(dir)
                while(boxes.contains(lastBoxNewLocation)) {
                    lastBoxNewLocation = lastBoxNewLocation.add(dir)
                }
                if(!walls.contains(lastBoxNewLocation)) {
                    boxes.remove(newLocation)
                    boxes.add(lastBoxNewLocation)
                    robot = newLocation
                }
            } else {
                robot = newLocation
            }
        }
        //println("-------------")
        //display(robot, walls, boxes)
    }
    println(boxes.sumOf { (it.y * 100) + it.x})
}

private fun display(robot: P, walls: Set<P>, boxes: Set<P>) {
    val maxX = walls.maxX()
    val maxY = walls.maxY()
    for(y in 0..maxY) {
        for(x in 0..maxX) {
            if(walls.contains(P(x, y))) {
                print("#")
            } else if(boxes.contains(P(x, y))) {
                print("O")
            } else if(robot == P(x, y)) {
                print("@")
            } else {
                print(".")
            }
        }
        println()
    }
}
