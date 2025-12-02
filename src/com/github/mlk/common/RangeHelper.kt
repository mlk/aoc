package com.github.mlk.common

fun String.toULongRange(separator: String = "-"): ULongRange {
    val numbers = this.split(separator)
    return ULongRange(numbers[0].trim().toULong(), numbers[1].trim().toULong())
}
