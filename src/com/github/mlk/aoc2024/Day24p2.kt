package com.github.mlk.aoc2024

import java.io.File

fun main() {
    val data = File("c:/aocs/input.txt").readText().split("\n\n")
    var fixedValues = data[0].split("\n").map { it.split(": ")[0] }
    val cals = data[1].split("\n")
        .filter { it.isNotEmpty() }
        .map {
            val c = it.split(" ")
            Node24(c[1], c[0], c[2], c[4])
        }

    val z1 = buildN("z01", cals)
    val z2 = buildN("z02", cals)
    val z3 = buildN("z03", cals)
    val z4 = buildN("z04", cals)

}

fun buildN(item: String, cals: List<Node24>): N {
    if(item.startsWith("x") || item.startsWith("y")) {
        return N("fixed", null, null)
    } else {
        val item = cals.filter { it.target == item }.first()
        return N(item.type, buildN(item.p1, cals), buildN(item.p2, cals))
    }
}



data class Node24(val type: String, val p1: String, val p2: String, val target: String)

data class N(val type: String, var l: N?, var r: N?)


