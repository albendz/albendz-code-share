package com.alicia.problems.graph

import com.alicia.problems.com.alicia.problems.graph.GraphSearch
import com.alicia.problems.com.alicia.problems.graph.Node
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class GraphSearchTest {

    @Test
    fun `WHEN search with BFS THEN get shortest path`() {
        val path = GraphSearch.bfs(getExampleGraph(), "G")?.joinToString(", ")

        assertEquals("A: [B, D, F], B: [A, C], C: [B, E, G], G: [C]", path)
    }

    @Test
    fun `WHEN search with DFS THEN get a path`() {
        val path = GraphSearch.dfs(getExampleGraph(), "G")?.joinToString(", ")

        assertEquals("A: [B, D, F], F: [A, D, E], E: [F, C], C: [B, E, G], G: [C]", path)
    }

    @Test
    fun `WHEN search with BFS no solution THEN return null`() {
        val path = GraphSearch.bfs(getExampleGraph(), "W")?.joinToString(", ")

        assertNull(path)
    }

    @Test
    fun `WHEN search with DFS no solution THEN return null`() {
        val path = GraphSearch.dfs(getExampleGraph(), "W")?.joinToString(", ")

        assertNull(path)
    }

    @Test
    fun `WHEN search BFS root is target THEN return list of target`() {
        val path = GraphSearch.bfs(getExampleGraph(), "A")?.joinToString(", ")

        assertEquals("A: [B, D, F]", path)
    }

    @Test
    fun `WHEN search DFS root is target THEN return list of target`() {
        val path = GraphSearch.dfs(getExampleGraph(), "A")?.joinToString(", ")

        assertEquals("A: [B, D, F]", path)
    }

    @Test
    fun `WHEN search BFS root only node THEN return null`() {
        val path = GraphSearch.bfs(Node("A", mutableListOf()), "G")?.joinToString(", ")

        assertNull(path)
    }

    @Test
    fun `WHEN search DFS root is only node THEN return null`() {
        val path = GraphSearch.dfs(Node("A", mutableListOf()), "G")?.joinToString(", ")

        assertNull(path)
    }

    @Test
    fun `WHEN search BFS find node THEN return node`() {
        val node = GraphSearch.bfsFindNode(getExampleGraph(), "G")

        node?.let { print(it.data) } ?: print("Node wasn't found")

        if (node == null) {
            print(node?.data)
        } else {
            print("Node wasn't found")
        }
    }

    @Test
    fun `WHEN Java search BFS find node THEN return node`() {
        val node = com.alicia.java.problems.graph.GraphSearch.bfsFindNode(getExampleGraph(), "G")

        node.ifPresentOrElse({ n -> print(n.data) }, { print("Node wasn't found")})
    }

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
}