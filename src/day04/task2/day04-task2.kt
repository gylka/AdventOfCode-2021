@file:Suppress("DuplicatedCode")

// deliberately super-over-engineered :)

package day04.task2

import java.io.File

const val ioPath = "src//day04//task2"

fun main() {
    val lines = File("$ioPath//_input.txt")
        .readLines()
    val numbers = lines[0]
        .split(",")
        .map { it.toInt() }
        .toList()
    val tables = parseTables(lines.drop(2))
    val result = findResult(numbers, tables)
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun parseTables(lines: Collection<String>): Array<Table> {
    val tables = mutableListOf<Table>()
    for ((i, line) in lines.withIndex()) {
        if ((i + 1) % 6 == 1)
            tables.add(Table())
        if (line.isEmpty())
            continue
        val rowValues = line
            .trim()
            .split("\\s+".toRegex())
            .map { it.toInt() }
        tables[i / 6].fillRow(i - (i / 6) * 6, rowValues)
    }
    return tables.toTypedArray()
}

fun findResult(numbers: Collection<Int>, tables: Array<Table>): Int {
    val tablesLeft = tables.toMutableList()
    var lastResult = 0
    for (number in numbers) {
        var i = 0
        while (i < tablesLeft.size) {
            val position = tablesLeft[i].find(number)
            if (position == null) {
                i++
                continue
            }
            if (tablesLeft[i].markAndCheck(position)) {
                lastResult = tablesLeft[i].sumOfUnmarked() * number
                tablesLeft.removeAt(i)
            } else
                i++
        }
        if(tablesLeft.size == 0)
            break
    }
    return lastResult
}

class Table {
    private val cells: Array<Array<Cell>> = Array(5) { Array(5) { Cell(0, false) } }

    fun fillRow(rowIndex: Int, values: Collection<Int>) {
        for((i, value) in values.withIndex())
            cells[rowIndex][i] = Cell(value, false)
    }

    fun find(number: Int): Position? {
        for (i in cells.indices)
            for (j in cells[i].indices)
                if(cells[i][j].value == number)
                    return Position(i, j)
        return null
    }

    fun markAndCheck(position: Position): Boolean {
        cells[position.x][position.y].isMarked = true
        var isFinished = true
        for (i in 0 until 5)
            if(!cells[i][position.y].isMarked) {
                isFinished = false
                break
            }
        if (isFinished)
            return true
        for (i in 0 until 5)
            if(!cells[position.x][i].isMarked)
                return false
        return true
    }

    fun sumOfUnmarked(): Int = cells.sumOf { row -> row.sumOf { if (it.isMarked) 0 else it.value } }

    override fun toString(): String {
        return buildString {
            for (row in cells) {
                for (cell in row) {
                    append(cell.value)
                    if(cell.isMarked)
                        append("*")
                    append(" ")
                }
                append("\n")
            }
        }
    }
}

data class Cell(val value: Int, var isMarked: Boolean)

data class Position(val x: Int, val y: Int)