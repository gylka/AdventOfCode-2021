@file:Suppress("DuplicatedCode")

package day03.task1

import java.io.File

const val ioPath = "src//day03//task1"

@Suppress("RemoveRedundantCallsOfConversionMethods")
fun main() {
    val lines = File("$ioPath//_input.txt").readLines()
    val onesCount = IntArray(lines[0].length)
    for (i in onesCount.indices) {
        onesCount[i] = lines.sumOf { if (it[i] == '1') 1.toInt() else 0 }
    }
    var gamma = 0
    var epsilon = 0
    for (i in onesCount.indices) {
        if(onesCount[i] > lines.size - onesCount[i])
            gamma = gamma or (1 shl onesCount.size - i - 1)
        else
            epsilon = epsilon or (1 shl onesCount.size - i - 1)
    }
    val result = gamma * epsilon
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}
