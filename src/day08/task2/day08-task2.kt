@file:Suppress("DuplicatedCode")

package day08.task2

import includes
import java.io.File

const val ioPath = "src//day08//task2"

fun main() {
    val rows = File("$ioPath//_input.txt")
        .readLines()
        .map { parseRow(it) }
        .toList()
    val result = rows.sumOf { r -> getOutput(r, buildGlossary(r.pattern)) }
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

fun parseRow(str: String): Row {
    val split = str.split('|')
    return Row(split[0].trim().split(" "), split[1].trim().split(" "))
}

fun buildGlossary(pattern: List<String>): Map<String, Int> {
    val sign = mutableMapOf(
        1 to pattern.single { it.length == 2 },
        4 to pattern.single { it.length == 4 },
        7 to pattern.single { it.length == 3 },
        8 to pattern.single { it.length == 7 }
    )
    val a = (sign[7]!! exclude sign[1]!!).single()
    // lengthOf6 is [0, 6, 9]
    val lengthOf6 = pattern.filter { it.length == 6 }
    /*
    9 exclude 4 = ag
    6 exclude 4 = aeg
    0 exclude 4 = aeg
     */
    val temp = lengthOf6
        .map { it exclude sign[4]!! }
        .distinctBy { it.length }
        .sortedBy { it.length }
    val e = temp[1] exclude temp[0]
    val g = (temp[0] exclude a).first()
    sign[9] = pattern
        .filter { it.length == 6 }
        .single { it notIncludes e }
    // lengthOf5 is [2, 3, 5]
    val lengthOf5 = pattern.filter { it.length == 5 }
    sign[2] = lengthOf5.single { it includes e }
    // 1 exclude 2 = f
    val f = (sign[1]!! exclude sign[2]!!).single()
    val c = (sign[1]!! exclude f).single()
    sign[0] = lengthOf6
        .filter { it notIncludes sign[9]!! }
        .single { it.contains(c) }
    sign[6] = lengthOf6
        .single { it notIncludes sign[9]!! && it notIncludes sign[0]!! }
    val d = (sign[4]!! exclude sign[0]!!).single()
    val b = (sign[8]!! exclude sign[2]!! exclude sign[1]!!).single()
    sign[3] = charArrayOf(a, c, d, f, g).concatToString()
    sign[5] = charArrayOf(a, b, d, f, g).concatToString()
    return sign
        .map { Pair(it.value, it.key) }
        .toMap()
}

fun getOutput(row: Row, glossary: Map<String, Int>) : Int {
    return row.output
        .map { glossary.getForPattern(it).toString() }
        .joinToString("") { it }
        .toInt()
}

data class Row(val pattern: List<String>, val output: List<String>)

infix fun CharSequence.includes(str: CharSequence): Boolean = str.all { this.contains(it) }

infix fun CharSequence.notIncludes(str: CharSequence): Boolean = !str.all { this.contains(it) }

infix fun CharSequence.exclude(str: CharSequence): CharSequence = this.filterNot { str.contains(it) }

infix fun CharSequence.exclude(c: Char): CharSequence = this.filter { it != c }

fun Map<String, Int>.getForPattern(pattern: String): Int
    = this[keys.first { it includes pattern && it.length == pattern.length }]!!

