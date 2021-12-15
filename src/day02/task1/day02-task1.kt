package day02.task1

import java.io.File

const val ioPath = "src//day02//task1"

fun main() {
    var horizontal = 0
    var depth = 0
    val lines = File("$ioPath//_input.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .toList()
    for (line in lines) {
        val move = parseMovement(line)
        when(move.direction) {
            Direction.FORWARD -> horizontal += move.distance
            Direction.UP -> depth -= move.distance
            Direction.DOWN -> depth += move.distance
        }
    }
    File("$ioPath//_output.txt").writeText((horizontal * depth).toString())
    println(horizontal * depth)
}

enum class Direction {
    FORWARD, UP, DOWN
}

data class Move (val direction: Direction, val distance: Int)

fun parseMovement(str: String): Move {
    val direction = when(str.substringBefore(" ")) {
        "forward" -> Direction.FORWARD
        "up" -> Direction.UP
        "down" -> Direction.DOWN
        else -> throw Exception()
    }
    return Move(direction, str.substringAfter(" ").toInt())
}