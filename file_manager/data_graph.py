# This Graph will track how many times a piece of data is inserted
# Each node is a location and routes are determined by chronoligical locations
# It will then search for the most popular node

class Graph:
    def __init__(self):
        self.nodes = {}

    def __init__(self, file):
        self.nodes = {}
        # iterate through files, adding each line location

    def add_occurrence(self, data, from_node):
        if data in self.nodes:
            node = self.nodes[data]
            node.count = node.count + 1

            # add the path from the previous location to this location
            if node not in from_node.neighbors:
                from_node.neighbors.append(node)
        else:
            node = GraphNode(data)
            self.nodes[data] = node
            from_node.neighbors.append(node)

    def add_data_from_file(self, file):
        return None

    def most_visited_bfs(self):
        return None

    def most_visited_dfs(self):
        return None

class GraphNode:
    def __init__(self, data):
        self.data = data
        self.neighbors = []
        self.count = 0

    def add_neighbor(self, node):
        self.neighbors.append(node)

    def get_neighbors(self):
        return neighbors

    def get_data(self):
        return self.data
