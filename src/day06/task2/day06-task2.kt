@file:Suppress("DuplicatedCode")

package day06.task2

import java.io.File

const val ioPath = "src//day06//task2"

fun main() {
    val state = File("$ioPath//_input.txt")
        .readLines()
        .first()
        .split(",")
        .groupingBy {it.toInt()}
        .fold(0L) {agg, _ -> agg + 1L}
        .toMutableMap()
    for (i in 0..8)
        if (!state.containsKey(i))
            state[i] = 0
    for (day in 1..256L) {
        printState(state)
        var new8 = 0L
        var new6 = 0L
        for (i in 0..8) {
            if(state[i] == 0L)
                continue
            when(i) {
                0 -> {
                    new8 = state[0]!!
                    new6 = state[0]!!
                    state[0] = 0
                }
                else -> {
                    state[i - 1] = state[i]!!
                    state[i] = 0
                }
            }
        }
        state[6] = state[6]!! + new6
        state[8] = state[8]!! + new8
    }
    val result = state.values.sum()
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun printState(state: Map<Int, Long>) {
    println(state.entries
        .sortedBy { it.key }
        .map { "[${it.key}] = ${it.value}" }
        .foldRight("") { e, s -> "$e , $s" }
    )
}
