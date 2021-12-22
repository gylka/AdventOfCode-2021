@file:Suppress("DuplicatedCode")

package day07.task2

import java.io.File
import kotlin.math.abs

const val ioPath = "src//day07//task2"

fun main() {
    val positions = File("$ioPath//_input.txt")
        .readLines()
        .first()
        .split(",")
        .map { it.toInt() }
        .sorted()
        .toList()
    val result = dumbestPassToHalfway(positions).second
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun dumbestPassToHalfway(positions: List<Int>): Pair<Int, Int> {
    var bestHalfWay = 0
    var leastFuelSpent = Int.MAX_VALUE
    for (m in positions.first()..positions.last()) {
        var fuelSpent = 0
        for (position in positions) {
            if(fuelSpent >= leastFuelSpent)
                break
            fuelSpent += fuelFor(abs(position - m))
        }
        if (fuelSpent <= leastFuelSpent) {
            leastFuelSpent = fuelSpent
            bestHalfWay = m
        }
    }
    return Pair(bestHalfWay, leastFuelSpent)
}

// arithmetic progression. Sum = ((a1 + an) * n) / 2, where n-th element is an = a1 + d * (n -1), and d - delta
fun fuelFor(distance: Int) = (distance + 1) * distance / 2
