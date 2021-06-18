package com.alicia.problems.com.alicia.problems.graph


data class Node(
    val data: String,
    val neighbours: MutableList<Node>,
    var source: Node?= null
) {
    override fun toString(): String = "$data: [${neighbours.map { n -> n.data }.joinToString(", ")}]"
}