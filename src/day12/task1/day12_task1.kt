@file:Suppress("DuplicatedCode")

package day12.task1

import java.io.File

const val ioPath = "src//day12//task1"

fun main() {
    val links = File("$ioPath//_input.txt")
        .readLines()
        .map { parse(it) }
        .toList()
    val routes = findRoutes(links, Route())
    routes.forEach { println(it) }
    val result = routes.size
    File("$ioPath//_output.txt").writeText((result).toString())
    println(result)
}

fun parse(line: String): Link {
    val matchResult = Regex("(\\w+)-(\\w+)").find(line)
    return Link(Node(matchResult!!.groups[1]!!.value), Node(matchResult.groups[2]!!.value))
}

fun findRoutes(links: List<Link>, route: Route) : MutableList<Route> {
    val current = route.lastNode()
    val nextLinks = links.filter { it.has(current) && (it.otherTo(current).isBig || !it.otherTo(current).isBig && !route.has(it.otherTo(current))) }
    val result = mutableListOf<Route>()
    if(nextLinks.isEmpty())
        return result
    for(nextLink in nextLinks) {
        val copy = route.copy()
        val nextNode = nextLink.otherTo(current)
        copy.add(nextNode)
        if(nextNode.isEnd) {
            result.add(copy)
            continue
        }
        val routeResults = findRoutes(links, copy)
        result.addAll(routeResults)
    }
    return result
}

data class Node(val name: String) {
    val isBig = name[0].isUpperCase()

    val isEnd = name == "end"

    override fun toString() = name
}

data class Link (val p1: Node, val p2: Node) {
    fun has(node: Node) = p1 == node || p2 == node

    fun otherTo(node: Node) = if (p1 == node) p2 else p1

    override fun toString() = "$p1-$p2"
}

class Route() {
    private var nodes: MutableList<Node> = mutableListOf()

    init {
        nodes.add(Node("start"))
    }

    private constructor(nodes: List<Node>): this() {
        this.nodes = nodes.toMutableList()
    }

    fun has(node: Node) = nodes.contains(node)

    fun add(node: Node) = nodes.add(node)

    fun lastNode() = nodes.last()

    fun copy() = Route(nodes)

    override fun toString() = nodes.joinToString(",")
}