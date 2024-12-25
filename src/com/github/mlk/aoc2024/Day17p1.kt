package com.github.mlk.aoc2024

import java.io.File
import kotlin.math.pow

fun main() {
    val data = File("c:/aocs/input.txt").readLines()

    val m = Machine(data.findNum("Register A"), data.findNum("Register B"), data.findNum("Register C"), 0, data.findNums("Program"))
    m.run()
    //m.display()
}

private fun List<String>.findNum(s: String): Int {
    return this.findLast { it.startsWith(s) }!!.split(": ")[1].toInt()
}

private fun List<String>.findNums(s: String): List<Int> {
    return this.findLast { it.startsWith(s) }!!.split(": ")[1].split(",").map { it.toInt() }
}

class Machine(var a: Int, var b: Int, var c: Int, var pc: Int, val program: List<Int>) {

    fun display() {
        println("A=$a, B=$b, C=$c, PC=$pc")
    }

    fun run() {
        while(pc < program.size) {
            val instruction = program[pc]
            //println("ins: $instruction")
            //display()
            if(instruction == 3) {

                if(a == 0) {
                    pc += 2
                } else {
                    pc = literalOperand()
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
        a = (a / 2.0.pow(comboOperand())).toInt()
    }

    fun bdv() {
        //println("bdv")
        b = (a / 2.0.pow(comboOperand())).toInt()
    }

    fun cdv() {
        //println("cdv")
        c = (a / 2.0.pow(comboOperand())).toInt()
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



    fun literalOperand(): Int {
        return program[pc + 1]
    }

    fun comboOperand(): Int {
        val current = program[pc + 1]
        if(current <= 3) { return current }
        if(current == 4) { return a }
        if(current == 5) { return b }
        if(current == 6) { return c }
        throw RuntimeException("$current > 6")
    }
}
