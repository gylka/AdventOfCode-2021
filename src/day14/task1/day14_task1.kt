@file:Suppress("DuplicatedCode")

package day14.task1

import java.io.File

const val ioPath = "src//day14//task1"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    var template = lines[0]
    val rules = parseRules(lines.drop(2))
    for (i in 1..10) {
        template = step(template, rules)
        println("$i: $template")
    }
    val stats = template
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedBy { it.second }
    val result = stats.last().second - stats.first().second
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

fun step(template: String, rules: List<Rule>): String {
    val result = buildString {
        append(template[0])
        for (i in 1 until template.length) {
            val duo = template.substring(i - 1..i)
            val rule = rules.singleOrNull() { it.duo == duo }
            if(rule != null)
                append(rule.insert, duo[1])
            else
                append(duo[1])
        }
    }
    return result
}

data class Rule(val duo: String, val insert: Char) {
    override fun toString() = "$duo -> $insert"
}