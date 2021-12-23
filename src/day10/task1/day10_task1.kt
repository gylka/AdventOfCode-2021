@file:Suppress("DuplicatedCode")

package day10.task1

import java.io.File
import java.util.*

const val ioPath = "src//day10//task1"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
        .map { it.toList() }
        .toList()
    var result = 0
    for (line in lines) {
        val stack = Stack<Char>()
        for (char in line) {
            when {
                isOpenBracket(char) -> stack.push(char)
                isCloseBracket(char) -> {
                    if(stack.isEmpty() || !isValid(stack.pop(), char)) {
                        result += pointsFor(char)
                        break
                    }
                }
                else -> throw Exception()
            }
        }
        println()
        stack.clear()
    }
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

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

fun pointsFor(char: Char): Int {
    return when (char) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw Exception()
    }
}