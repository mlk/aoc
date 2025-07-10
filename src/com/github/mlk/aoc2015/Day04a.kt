package com.github.mlk.aoc2015

import java.security.MessageDigest
import kotlin.system.exitProcess

fun main() {
    val key = "KEY"
    val required = "0".repeat(5)
    for(i in 0 until Integer.MAX_VALUE) {
        if((key + i).md5().startsWith(required)) {
            println(i)
            exitProcess(0)
        }
    }

}

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}