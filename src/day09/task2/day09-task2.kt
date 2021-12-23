@file:Suppress("DuplicatedCode")

package day09.task2

import java.io.File

const val ioPath = "src//day09//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val area = Area.fromStringList(lines)
    val lowPoints = area.findLowPoints()
    val basins = lowPoints
        .map { area.findBasin(it) }
        .toList()
    val result = basins
        .sortedBy { it.size }
        .takeLast(3)
        .map { it.size }
        .fold(1) { acc, e -> acc * e}
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

class Area private constructor(private val cells: Array<Array<Int>>) {
    companion object {
        fun fromStringList(strList: Collection<String>)
            = Area(strList
                .map { s -> s.map { it.digitToInt() }.toTypedArray() }
                .toTypedArray())
    }

    fun findLowPoints(): List<Point> {
        val result = mutableListOf<Point>()
        for (x in cells.indices) {
            for (y in cells[x].indices) {
                val point = Point(x, y)
                if (isLowPoint(point)) {
                    result.add(point)
                }
            }
        }
        return result
    }

    fun findBasin(point: Point) : Set<Point> {
        return findBasinRecursive(point, mutableSetOf(point)).toSet()
    }

    private fun findBasinRecursive(point: Point, visited: MutableSet<Point>) : MutableSet<Point> {
        val result = mutableSetOf(point)
        val newPoints = arrayOf(point.Xplus1(), point.Xminus1(), point.Yplus1(), point.Yminus1())
        for (p in newPoints) {
            if(isValid(p) && !visited.contains(p)) {
                visited.add(p)
                if (get(p) != 9)
                    result.addAll(findBasinRecursive(p, visited))
            }
        }
        return result
    }

    private fun isLowPoint(point: Point): Boolean {
        if(!isValid(point))
            throw ArrayIndexOutOfBoundsException("${point.x}, ${point.y}")
        val value = get(point)
        if(isValid(point.Xplus1()) && get(point.Xplus1()) <= value
            || isValid(point.Xminus1()) && get(point.Xminus1()) <= value
            || isValid(point.Yplus1()) && get(point.Yplus1()) <= value
            || isValid(point.Yminus1()) && get(point.Yminus1()) <= value)
            return false
        return true
    }

    private fun isValid(point: Point) = point.x in cells.indices && point.y in cells[0].indices

    fun get(point: Point) = cells[point.x][point.y]

    override fun toString(): String = cells.joinToString("\n") { it.joinToString(", ") }
}

@Suppress("SpellCheckingInspection", "FunctionName")
data class Point(val x: Int, val y: Int) {
    fun Xplus1() = Point(this.x + 1, this.y)
    
    fun Xminus1() = Point(this.x -1, this.y)

    fun Yplus1() = Point(this.x, this.y + 1)
    
    fun Yminus1() = Point(this.x, this.y - 1)

    override fun toString() = "[$x, $y]"
}