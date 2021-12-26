@file:Suppress("DuplicatedCode")

package day13.task1

import java.io.File

const val ioPath = "src//day13//task1"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val paper = Paper.parse(lines.takeWhile { it.isNotEmpty() })
    val instructions = lines
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .map { Instruction.parse(it) }
    paper.fold(instructions[0])
    val result = paper.dots.size
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

class Paper private constructor(var dots: Set<Dot>){
    private val sizeX get() = dots.maxOf { it.x + 1 }
    private val sizeY get() = dots.maxOf { it.y + 1 }

    companion object {
        fun parse(lines: List<String>) = Paper(lines
            .map { l -> l.split(",") }
            .map { Dot(it[0].toInt(), it[1].toInt()) }
            .toSet())
    }

    fun fold(instruction: Instruction) {
        val result = mutableSetOf<Dot>()
        when(instruction.direction) {
            Direction.ALONG_X -> {
                val affectedDots = dots.filter { it.x >= instruction.coordinate }
                val remainingDots = dots.minus(affectedDots.toSet())
                for (dot in affectedDots) {
                    val shift = dot.x - instruction.coordinate + 1
                    result.add(Dot(instruction.coordinate + 1 - shift, dot.y))
                }
                result.addAll(remainingDots)
                dots = result.toSet()
            }
            Direction.ALONG_Y -> {
                val affectedDots = dots.filter { it.y >= instruction.coordinate }
                val remainingDots = dots.minus(affectedDots.toSet())
                for (dot in affectedDots) {
                    val shift = dot.y - instruction.coordinate + 1
                    result.add(Dot(dot.x, instruction.coordinate + 1 - shift))
                }
                result.addAll(remainingDots)
                dots = result.toSet()
            }
        }
    }

    override fun toString(): String {
        val paper = MutableList(this.sizeY) { ".".repeat(sizeX) }
        for (dot in dots)
            paper[dot.y] = paper[dot.y].replaceRange(dot.x, dot.x + 1, "#")
        return paper.joinToString("\n")
    }
}

enum class Direction(val string: String) { ALONG_X("x"), ALONG_Y("y") }

data class Instruction(val direction: Direction, val coordinate: Int) {
    companion object {
        fun parse(line: String): Instruction {
            val matchResult = Regex("fold along (\\w)=(\\d+)").find(line)
            return Instruction(Direction.values().single { it.string == matchResult!!.groups[1]!!.value }, matchResult!!.groups[2]!!.value.toInt())
        }
    }
}

data class Dot(val x: Int, val y: Int) {
    override fun toString() = "[$x,$y]"
}