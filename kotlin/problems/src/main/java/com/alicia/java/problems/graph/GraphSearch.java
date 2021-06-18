package com.alicia.java.problems.graph;

import com.alicia.problems.com.alicia.problems.graph.Node;

import java.util.*;

public class GraphSearch {

    public static Optional<Node> bfsFindNode(Node root, String target) {
        Queue<Node> next = new LinkedList<>();
        List<Node> visited = new LinkedList<>();
        Node current;

        next.add(root);
        visited.add(root);

        while(!next.isEmpty()) {
            current = next.remove();

            if (current.getData().equals(target)) {
                return Optional.of(current);
            } else {
                current.getNeighbours()
                        .forEach(node -> {
                            if (!visited.contains(node)) {
                                next.add(node);
                                visited.add(node);
                            }
                        });
            }

        }

        return Optional.empty();
        // return null;
    }
}
