@file:Suppress("DuplicatedCode")

package day10.task2

import java.io.File
import java.util.*

const val ioPath = "src//day10//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
        .map { it.toList() }
        .toList()
    val scores = mutableListOf<Long>()
    outer@ for (line in lines) {
        val stack = Stack<Char>()
        for (char in line) {
            when {
                isOpenBracket(char) -> stack.push(char)
                isCloseBracket(char) -> {
                    if(stack.isEmpty() || !isValid(stack.pop(), char))
                        continue@outer
                }
                else -> throw Exception()
            }
        }
        val remainder = remainderFromStack(stack)
        scores.add(remainder.map { pointsFor(it) }.fold(0L) { agg, e -> agg * 5L + e })
    }
    val result = scores.sorted()[scores.size / 2]
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun remainderFromStack(stack: Stack<Char>) = stack.asReversed().map { respective(it) }

fun isOpenBracket(char: Char) = "([{<".contains(char)

fun isCloseBracket(char: Char) = ")]}>".contains(char)

fun isValid(open: Char, close: Char): Boolean {
    return when(Pair(open, close)) {
        Pair('(', ')') -> true
        Pair('{', '}') -> true
        Pair('[', ']') -> true
        Pair('<', '>') -> true
        else -> false
    }
}

fun respective(open: Char): Char {
    return when(open) {
        '(' -> ')'
        '{' -> '}'
        '[' -> ']'
        '<' -> '>'
        else -> throw Exception()
    }
}

fun pointsFor(char: Char): Int {
    return when (char) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw Exception()
    }
}