@file:Suppress("DuplicatedCode")

package day06.task1

import java.io.File

const val ioPath = "src//day06//task1"

fun main() {
    val numbers = File("$ioPath//_input.txt")
        .readLines()
        .first()
        .split(",")
        .map { it.toInt() }
        .toMutableList()
    val result = passDays(80, numbers)
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun passDays(days: Int, state: MutableList<Int>): Int {
    for(i in 1..days) {
        for (k in 0 until state.size) {
            when (state[k]) {
                0 -> {
                    state[k] = 6
                    state.add(8)
                }
                else -> state[k]--
            }
        }
        println("[${state.size}]   ${state.joinToString(",")}")
    }
    return state.size
}