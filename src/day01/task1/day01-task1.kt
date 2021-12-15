package day01.task1

import java.io.File

const val ioPath = "src//day01//task1"

fun main() {
    val lines = File("${ioPath}//_input.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toList()
    var previous = lines[0]
    var counter = 0
    for (i in 1 until lines.size) {
        if(previous <= lines[i])
            counter++
        previous = lines[i]
    }
    File("${ioPath}//_output.txt").writeText(counter.toString())
    println(counter)
}