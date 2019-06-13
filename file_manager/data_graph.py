# This Graph will track how many times a piece of data is inserted
# Each node is a location and routes are determined by chronoligical locations
# It will then search for the most popular node

class Graph:
    def __init__(self):
        self.nodes = {}

    def __init__(self, file):
        self.nodes = {}
        # iterate through files, adding each line location

    def add_occurrence(self, data, from_node=None):
        if data in self.nodes:
            node = self.nodes[data]
            node.count = node.count + 1

            # add the path from the previous location to this location
            if (from_node != None) and (node not in from_node.neighbors):
                from_node.neighbors.append(node)
            return node
        else:
            node = GraphNode(data)
            self.nodes[data] = node
            if from_node != None:
                from_node.neighbors.append(node)
            return node

    def add_data_from_file(self, file):
        # Files are a list of locations sorted by time
        # add each location if not already added and link to previous

        with open(file, 'r') as f:
            lines = f.readlines()

        lines = lines[1:]
        split_lines = [None] * len(self.lines)
        for i in range(0, len(self.lines)):
            split_lines[i] = self.lines[i].strip().split(',')[1:]

        previous_node = None
        for line in split_lines:
            data = "Longitude: " + str(line[]) + ", Latitude: " + str(line[1])
            node = self.add_occurrence(data, previous_node)
            previous_node = node

    def path_from_to_bfs(self, from, to):
        # If we don't know about either of these locations, return None
        if from not in self.nodes or to not in self.nodes:
            return None

        start = self.nodes[from]
        end = self.nodes[to]

        ## BFS ##
        queue = []
        visited = []
        path = []

        queue.append(start)
        while len(queue) > 0 and end.previous == None:
            current_node = queue.pop(0)

            for neighbor in current_node.neighbors:
                if neighbor == end:
                    # WHen you find the target, set it's path and exit
                    end.previous = current_node
                    break
                if neighbor not in visited:
                    neighbor.previous = current_node
                    queue.append(neighbor)
                    visited.append(neighbor)

        if end.previous != None:
            # Track the path backwards
            current_node = end
            while current_node != None:
                path.append(current_node)
                current_node = current_node.previous

        # Clearing tracking state
        clear_previous()
        return path

    def path_from_to_dfs(self, from, to):
        # If we don't know about either of these locations, return None
        if from not in self.nodes or to not in self.nodes:
            return None

        start = self.nodes[from]
        end = self.nodes[to]

        stack = []
        visited = []
        path = []

        while len(stack) > 0 and end.previous == None:
            current_node = stack.pop()

            for neighbor in current_node.neighbors:
                if neighbor == end:
                    # WHen you find the target, set it's path and exit
                    end.previous = current_node
                    break
                if neighbor not in visited:
                    neighbor.previous = current_node
                    queue.append(neighbor)
                    visited.append(neighbor)

        if end.previous != None:
            # Track the path backwards
            current_node = end
            while current_node != None:
                path.append(current_node)
                current_node = current_node.previous

        # Clearing tracking state
        clear_previous()
        return path

    def clear_previous(self):
        for node in self.nodes:
            node.previous = None

    def print(self):
        for node in self.nodes:
            print(node.data + " has neighbors " + node.neighbors)

    def most_visited(self):
        max = 0

        for node in self.nodes:
            if node.count > max:
                max = node.count

        return max

class GraphNode:
    def __init__(self, data):
        self.data = data
        self.neighbors = []
        self.count = 0
        self.previous = None # just a shortcut for search
        # previous should be separate from this structure

    def add_neighbor(self, node):
        self.neighbors.append(node)

    def get_neighbors(self):
        return neighbors

    def get_data(self):
        return self.data
