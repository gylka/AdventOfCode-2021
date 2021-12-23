@file:Suppress("DuplicatedCode")

package day09.task1

import java.io.File

const val ioPath = "src//day09//task1"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val area = Area.fromStringList(lines)
    val result = area.calculateRiskLevel()
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

class Area private constructor(private val cells: Array<Array<Int>>) {
    companion object {
        fun fromStringList(strList: Collection<String>): Area {
            return Area(strList
                .map {
                    s -> s.map { it.digitToInt() }
                    .toTypedArray()
                }.toTypedArray())
        }
    }

    fun calculateRiskLevel(): Int {
        var result = 0
        for (x in cells.indices)
            for (y in cells[x].indices)
                if (isLowPoint(x, y))
                    result += cells[x][y] + 1
        return result
    }

    private fun isLowPoint(x: Int, y: Int): Boolean {
        if(x !in cells.indices || y !in cells[0].indices)
            throw ArrayIndexOutOfBoundsException("$x, $y")
        val value = cells[x][y]
        if(x + 1 in cells.indices && y in cells[0].indices && cells[x + 1][y] <= value)
            return false
        if(x in cells.indices && y + 1 in cells[0].indices && cells[x][y + 1] <= value)
            return false
        if(x - 1 in cells.indices && y in cells[0].indices && cells[x - 1][y] <= value)
            return false
        if(x in cells.indices && y - 1 in cells[0].indices && cells[x][y - 1] <= value)
            return false
        return true
    }

    override fun toString(): String = cells.joinToString("\n") { it.joinToString(", ") }
}
