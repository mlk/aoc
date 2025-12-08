package com.github.mlk.common

import kotlin.math.sqrt

data class P3D(val x: Double, val y: Double, val z: Double) {
    fun distanceSquared(other: P3D): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }
    fun distance(other: P3D): Double = sqrt(distanceSquared(other))
}

fun String.toP3D() = P3D(this.split(",")[0].toDouble(), this.split(",")[1].toDouble(), this.split(",")[2].toDouble())