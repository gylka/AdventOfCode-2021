@file:Suppress("DuplicatedCode")

package day11.task2

import java.io.File

const val ioPath = "src//day11//task2"

fun main() {
    val areaValues = File("$ioPath//_input.txt")
        .readLines()
        .map { it.map { c -> c.digitToInt() }.toMutableList() }
        .toMutableList()
    val area = Area(areaValues)
    val result = area.findStepWhenAllFlash()
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

    fun findStepWhenAllFlash(): Int {
        var step = 0
        do {
            val flashes = addStepAndReturnFlashCount()
            step++
        } while (flashes != area.size * area[0].size)
        return step
    }

    fun addStepAndReturnFlashCount(): Int {
        val alreadyFlashed = mutableListOf<Point>()
        for (x in area.indices) {
            for (y in area[x].indices)
                handlePointDuringStep(Point(x, y), alreadyFlashed)
        }
        alreadyFlashed.forEach { set(it, 0) }
        return alreadyFlashed.size
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