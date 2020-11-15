package com.alicia.problems.com.alicia.problems.graph

import java.util.*

object GraphSearch {

    fun bfs(start: Node, target: String): List<Node>? {
        val next: Queue<Node> = LinkedList<Node>()
        val visited: MutableList<Node> = mutableListOf()

        next.add(start)
        visited.add(start)

        while (!next.isEmpty()) {
            val current = next.remove()
            println(current)

            if (current.data == target) {
                println("Found!")
                return getPathFromNode(current)
            }

            current.neighbours.forEach {
                if (it !in visited) {
                    it.source = current
                    next.add(it)
                    visited.add(it)
                }
            }
        }

        return null
    }

    fun dfs(start: Node, target: String): List<Node>? {
        val next: Stack<Node> = Stack()
        val visited: MutableList<Node> = mutableListOf()

        next.add(start)
        visited.add(start)

        while (!next.isEmpty()) {
            val current = next.pop()
            println(current)

            if (current.data == target) {
                println("Found!")
                return getPathFromNode(current)
            }
            
            current.neighbours.forEach {
                if (it !in visited) {
                    it.source = current
                    next.push(it)
                    visited.add(it)
                }
            }
        }

        return null
    }

    private fun getPathFromNode(node: Node): List<Node> {
        val path = mutableListOf<Node>()

        var current: Node? = node
        while (current != null) {
            path.add(current)
            current = current.source
        }

        path.reverse()

        return path
    }
}