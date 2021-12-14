package task01B

import java.io.File

const val ioPath = "src//task01B"

fun main() {
    val lines = File("${ioPath}//_input.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toList()
    var previous = lines[0] + lines[1] + lines[2]

    var counter = 0
    for (i in 3 until lines.size) {
        val current = lines[i] + lines[i - 1] + lines[i - 2]
        if(current > previous)
            counter++
        previous = current
    }
    File("${ioPath}//_output.txt").writeText(counter.toString())
    println(counter)
}