@file:Suppress("DuplicatedCode")

package day14.task2

import java.io.File

const val ioPath = "src//day14//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val template = lines[0]
    val rules = parseRules(lines.drop(2))
    var duosCount = template
        .windowed(2)
        .groupingBy { it }
        .eachCount()
        .map { Pair(it.key, it.value.toLong()) }
        .toMap()
    val charsCount = template
        .groupingBy { it }
        .eachCount()
        .map { Pair(it.key, it.value.toLong()) }
        .toMap()
        .toMutableMap()
    for (i in 1..40) {
        println("********      after step $i   ****** ")
        duosCount = step(duosCount, charsCount, rules)
        printCharsCount(charsCount)
    }
    val result = charsCount.maxOf { it.value } - charsCount.minOf { it.value }
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun parseRules(lines: List<String>): List<Rule> {
    val result = mutableListOf<Rule>()
    for (line in lines) {
        val matchResult = Regex("(\\w{2}) -> (\\w)").find(line)
        result.add(Rule(matchResult!!.groups[1]!!.value, matchResult.groups[2]!!.value[0]))
    }
    return result
}

fun step(duosCount: Map<String, Long>, charsCount: MutableMap<Char, Long>, rules: List<Rule>): Map<String, Long> {
    val result = mutableMapOf<String, Long>()
    for ((duo, count) in duosCount) {
        val rule = rules.singleOrNull { it.duo == duo }
        if(rule != null) {
            val duo1 = duo[0] + rule.insert.toString()
            val duo2 = rule.insert.toString() + duo[1]
            result[duo1] = (result[duo1] ?: 0)  + count
            result[duo2] = (result[duo2] ?: 0) + count
            charsCount[rule.insert] = (charsCount[rule.insert] ?: 0) + count
        } else
            result[duo] = (result[duo] ?: 0) + 1
    }
    return result
}

fun printCharsCount(charsCount: MutableMap<Char, Long>) {
    println(charsCount
        .map { it }
        .sortedBy { it.value }
        .joinToString("\n") { "${it.key} -> ${it.value}" })
    println()
}

data class Rule(val duo: String, val insert: Char) {
    override fun toString() = "$duo -> $insert"
}