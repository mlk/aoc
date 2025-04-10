package com.github.mlk.aoc2024

import java.io.File
import kotlin.math.pow

fun main() {
    val data = File("c:/aocs/input.txt").readLines()

    val m = Machine(62769496, data.findNum("Register B"), data.findNum("Register C"), 0, data.findNums("Program"))
    m.run()
    println()
    val m2 = Machine(62769520, data.findNum("Register B"), data.findNum("Register C"), 0, data.findNums("Program"))
    m2.run()
    //m.display()
}

private fun List<String>.findNum(s: String): Long {
    return this.findLast { it.startsWith(s) }!!.split(": ")[1].toLong()
}

private fun List<String>.findNums(s: String): List<Int> {
    return this.findLast { it.startsWith(s) }!!.split(": ")[1].split(",").map { it.toInt() }
}

class Machine(var a: Long, var b: Long, var c: Long, var pc: Int, val program: List<Int>) {

    fun display() {
        println("A=$a, B=$b, C=$c, PC=$pc")
    }

    fun run() {
        while(pc < program.size) {
            val instruction = program[pc]
            //println("ins: $instruction")
            //display()
            if(instruction == 3) {

                if(a == 0L) {
                    pc += 2
                } else {
                    pc = literalOperand().toInt()
                }
                //return
            } else {
                when(instruction) {
                    0 -> adv()
                    1 -> bxl()
                    2 -> bst()
                    4 -> bxc()
                    5 -> out()
                    6 -> bdv()
                    7 -> cdv()
                    else -> throw RuntimeException("Invalid opcode $instruction")
                }
                pc += 2
            }
        }
    }


    fun adv() {
        //println("DIV: $a by ${comboOperand()} POW ${comboOperand().toDouble().pow(2)} == ${a / comboOperand().toDouble().pow(2)}")
        a = (a / 2.0.pow(comboOperand().toDouble())).toLong()
    }

    fun bdv() {
        //println("bdv")
        b = (a / 2.0.pow(comboOperand().toDouble())).toLong()
    }

    fun cdv() {
        //println("cdv")
        c = (a / 2.0.pow(comboOperand().toDouble())).toLong()
    }

    fun bxl() {
        b = b.xor(literalOperand())
    }

    fun bst() {
        b = comboOperand() % 8
    }

    fun bxc() {
        b = b.xor(c)
    }

    fun out() {
        print((comboOperand() % 8).toString() + ",")
    }



    fun literalOperand(): Long {
        return program[pc + 1].toLong()
    }

    fun comboOperand(): Long {
        val current = program[pc + 1]
        if(current <= 3) { return current.toLong() }
        if(current == 4) { return a }
        if(current == 5) { return b }
        if(current == 6) { return c }
        throw RuntimeException("$current > 6")
    }
}
