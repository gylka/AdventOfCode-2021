@file:Suppress("DuplicatedCode")

package day03.task2

import java.io.File

const val ioPath = "src//day03//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val startPosition = lines[0].length - 1
    val values = lines
        .map { it.toInt(2) }
        .toList()
    val oxygenRating = bitCriteriaFilter(RatingType.OXYGEN, values, startPosition)
    val co2Rating = bitCriteriaFilter(RatingType.CO2, values, startPosition)
    val result = oxygenRating[0] * co2Rating[0]
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

enum class RatingType { OXYGEN, CO2 }


fun bitCriteriaFilter(ratingType: RatingType, values: List<Int>, position: Int): List<Int> {
    val newValues: List<Int>
    val numberOfOnes = values.count { it and (1 shl position) == 1 shl position }
    val expectedBitValue = when(ratingType) {
        RatingType.OXYGEN -> 1
        RatingType.CO2 -> 0
    }
    newValues = if (numberOfOnes >= values.size - numberOfOnes) {
        values
            .filter { it and (1 shl position) == expectedBitValue shl position }
            .toList()
    } else {
        values
            .filter { it and (1 shl position) == expectedBitValue.inverseBit() shl position }
            .toList()
    }
    if(newValues.size > 1)
        return bitCriteriaFilter(ratingType, newValues, position - 1)
    return newValues
}

fun Int.inverseBit(): Int = this.inv() and 1