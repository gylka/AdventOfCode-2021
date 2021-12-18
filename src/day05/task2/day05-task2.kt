@file:Suppress("DuplicatedCode")

package day05.task2

import java.io.File
import kotlin.math.abs

const val ioPath = "src//day05//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
        .map { Line.fromString(it) }
        .filter { it.isProper }
        .toList()
    val matrixSize = lines.maxOf { l -> arrayOf(l.start.x, l.start.y, l.end.x, l.end.y).maxOf { it } }
    val matrix = Matrix(matrixSize + 1)
    for (line in lines) {
        matrix.drawLine(line)
    }
    val result = matrix.getNumberOfCellsWithAtLeast(2)
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

@Suppress("unused")
class Matrix(private val size: Int) {
    private val cells = Array(size) { IntArray(size) }

    fun drawLine(line: Line) {
        if (line.isHorizontal)
            for (x in line.start.x between line.end.x)
                cells[x][line.start.y]++
        if (line.isVertical)
            for (y in line.start.y between line.end.y)
                cells[line.start.x][y]++
        if (line.isDiagonal) {
            line.normalize()
            val delta = abs(line.end.x - line.start.x)
            if (line.start.y < line.end.y)
                for (i in 0..delta)
                    cells[line.start.x + i][line.start.y + i]++
            else
                for (i in 0..delta)
                    cells[line.start.x + i][line.start.y - i]++
        }
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
                    append("${if (cells[y][x] == 0) "." else cells[y][x]} ")
                append("\n")
            }
        }
    }

    private val debuggerInfo get() = toString()
}

class Line(start: Point, end: Point) {
    var start: Point = start
        private set
    var end: Point = end
        private set

    val isHorizontal get() = start.y == end.y

    val isVertical get() = start.x == end.x

    val isDiagonal get() = abs(start.x - end.x) == abs(start.y - end.y)

    val isProper get() = isHorizontal || isVertical || isDiagonal

    companion object {
        fun fromString(str: String): Line {
            val groups = Regex("(\\d+),(\\d+)\\s*->\\s*(\\d+),(\\d+)").find(str)!!.groups
            return Line(Point(groups[1]!!.value.toInt(), groups[2]!!.value.toInt()), Point(groups[3]!!.value.toInt(), groups[4]!!.value.toInt()))
        }
    }

    fun normalize() {
        if (start.x > end.x)
            swap()
    }

    private fun swap() {
        val temp = end
        end = start
        start = temp
    }

    override fun toString() = "${start.x},${start.y} -> ${end.x},${end.y}"
}

data class Point(val x: Int, val y: Int)

infix fun Int.between(to: Int): IntProgression {
    return if(this < to) IntProgression.fromClosedRange(this, to, 1)
    else this downTo to
}