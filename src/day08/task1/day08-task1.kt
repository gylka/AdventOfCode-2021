@file:Suppress("DuplicatedCode")

package day08.task1

import java.io.File

const val ioPath = "src//day08//task1"

fun main() {
    val rows = File("$ioPath//_input.txt")
        .readLines()
        .map { parseRow(it) }
        .toList()
    val result = rows.sumOf { countOf1478InOutput(it) }
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

/*
0 - abcefg
1 - cf
2 - acdeg
3 - acdfg
4 - bcdf
5 - abdfg
6 - abdefg
7 - acf
8 - abcdefg
9 - abcdfg
 */

fun countOf1478InOutput(row: Row) = row.output.count { listOf(2, 3, 4, 7).contains(it.length) }

fun parseRow(str: String): Row {
    val split = str.split('|')
    return Row(split[0].trim().split(" "), split[1].trim().split(" "))
}

data class Row(val pattern: List<String>, val output: List<String>)