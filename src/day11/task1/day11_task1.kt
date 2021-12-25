@file:Suppress("DuplicatedCode")

package day11.task1

import java.io.File

const val ioPath = "src//day11//task1"

fun main() {
    val areaValues = File("$ioPath//_input.txt")
        .readLines()
        .map { it.map { c -> c.digitToInt() }.toMutableList() }
        .toMutableList()
    val area = Area(areaValues)
    val result = area.addStepAndReturnFlashCount(100)
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

class Area(private val area: MutableList<MutableList<Int>>) {
    private fun isValid(point: Point) = point.x in area.indices && point.y in area[0].indices

    private fun adjacentPoints(point: Point): List<Point> {
        val result = mutableListOf<Point>()
        for(x in -1..1) {
            for (y in -1..1) {
                val adjacentPoint = Point(point.x + x, point.y + y)
                if(isValid(adjacentPoint))
                    result.add(adjacentPoint)
            }
        }
        result.remove(point)
        return result
    }

    fun addStepAndReturnFlashCount(steps: Int): Int {
        var result = 0
        for (step in 1..steps) {
            println("----------------   $step         -------------------")
            println(toString())
            val alreadyFlashed = mutableListOf<Point>()
            for (x in area.indices) {
                for (y in area[x].indices) {
                    val point = Point(x, y)
                    println(point)
                    handlePointDuringStep(point, alreadyFlashed)
                }
            }
            alreadyFlashed.forEach { set(it, 0) }
            result += alreadyFlashed.size
            println("***********   $step    ***********************")
            println(toString())
        }
        return result
    }

    private fun handlePointDuringStep(point: Point, alreadyFlashed: MutableList<Point>) {
        set(point, get(point) + 1)
        if(get(point) < 10 || point in alreadyFlashed)
            return
        alreadyFlashed.add(point)
        val adjacentPoints = adjacentPoints(point)
        for (p in adjacentPoints) {
            handlePointDuringStep(p, alreadyFlashed)
        }
    }

    fun get(x: Int, y: Int) = area[x][y]

    fun get(point: Point) = area[point.x][point.y]

    fun set(point: Point, value: Int) {
        area[point.x][point.y] = value
    }

    override fun toString() = area.joinToString("\n") { it.joinToString("") }
}

data class Point(val x: Int, val y: Int) {
    override fun toString() = "[$x,$y]"
}