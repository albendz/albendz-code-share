package com.alicia.problems.graph

import com.alicia.problems.com.alicia.problems.graph.Node
import org.junit.jupiter.api.Test

class GraphSearchTest {

    @Test
    fun `WHEN search with BFS THEN get shortest path`() {}

    @Test
    fun `WHEN search with DFS THEN get a path`() {}


    private fun getExampleGraph(): Node {
        val a = Node("A", mutableListOf())
        val b = Node("B", mutableListOf())
        val c = Node("C", mutableListOf())
        val d = Node("D", mutableListOf())
        val e = Node("E", mutableListOf())
        val f = Node("F", mutableListOf())
        val g = Node("G", mutableListOf())

        a.neighbours.add(b)
        a.neighbours.add(d)
        a.neighbours.add(f)

        b.neighbours.add(a)
        b.neighbours.add(c)

        c.neighbours.add(b)
        c.neighbours.add(e)
        c.neighbours.add(g)

        d.neighbours.add(a)
        d.neighbours.add(f)

        e.neighbours.add(f)
        e.neighbours.add(c)

        f.neighbours.add(a)
        f.neighbours.add(d)
        f.neighbours.add(e)

        g.neighbours.add(c)

        return a
}