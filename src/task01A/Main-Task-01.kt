package task01A

import java.io.File
import java.nio.file.Paths

fun main() {
    println(Paths.get("").toAbsolutePath().toString())
    val lines = File("src//task01A//_input.txt")
        .readLines()
        .filter { l -> l.isNotEmpty() }
        .map { l -> l.toInt() }
        .toList()
    var previous = lines[0]
    var counter = 0
    for (i in 1 until lines.size) {
        if(previous <= lines[i])
            counter++
        previous = lines[i]
    }
    File("src//task01A//_output.txt").writeText(counter.toString())
    println(counter)
}