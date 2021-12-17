@file:Suppress("DuplicatedCode")

package day05.task1

import java.io.File

const val ioPath = "src//day05//task1"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
        .map { Line.fromString(it) }
        .filter { it.isHorizontalOrVertical() }
        .toList()
    val matrixSize = lines.maxOf { l -> arrayOf(l.start.x, l.start.y, l.end.x, l.end.y).maxOf { it } }
    val matrix = Matrix(matrixSize + 1)
    for (line in lines)
        matrix.drawLine(line)
    val result = matrix.getNumberOfCellsWithAtLeast(2)
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

class Matrix(private val size: Int) {
    private val cells = Array(size) { IntArray(size) }

    fun drawLine(line: Line) {
        if (line.isHorizontal())
            for (x in line.start.x onto line.end.x)
                cells[x][line.start.y]++
        if (line.isVertical())
            for (y in line.start.y onto line.end.y)
                cells[line.start.x][y]++
    }

    fun getNumberOfCellsWithAtLeast(n: Int): Int {
        var result = 0
        for (x in 0 until size)
            for (y in 0 until size)
                if (cells[x][y] >= n)
                    result++
        return result
    }

    override fun toString(): String {
        return buildString {
            for (x in 0 until size) {
                for (y in 0 until size)
                    append("$cells[x][y] ")
                append("\n")
            }
        }
    }
}

data class Line(val start: Point, val end: Point) {
    companion object {
        fun fromString(str: String): Line {
            val groups = Regex("(\\d+),(\\d+)\\s*->\\s*(\\d+),(\\d+)").find(str)!!.groups
            return Line(Point(groups[1]!!.value.toInt(), groups[2]!!.value.toInt()), Point(groups[3]!!.value.toInt(), groups[4]!!.value.toInt()))
        }
    }

    fun isHorizontal(): Boolean = start.y == end.y

    fun isVertical(): Boolean = start.x == end.x

    fun isHorizontalOrVertical(): Boolean = isHorizontal() || isVertical()

    override fun toString() = "${start.x},${start.y} -> ${end.x},${end.y}"
}

data class Point(val x: Int, val y: Int)

infix fun Int.onto(to: Int): IntRange {
    return if(this > to)
        to..this
    else
        this..to
}