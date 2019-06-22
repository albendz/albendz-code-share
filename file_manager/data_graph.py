# This Graph will track how many times a piece of data is inserted
# Each node is a location and routes are determined by chronoligical locations
# It will then search for the most popular node

class Graph:
    def __init__(self):
        # This is a map of long-lat to the node that holds that data
        # Graphs don't have a 'start' or 'root' node so we need a way to get
        # a specific node
        self.nodes = {}

    def add_occurrence(self, data, from_node=None):
        """
            This function will add a piece of data (i.e. long + lat) to the
            graph given the node that it's coming from. If this data already
            exists, connect the two nodes and increment the count of times
            this location has been visited
        """
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
                node.neighbors.append(from_node)
            return node

    def add_data_from_file(self, file):
        # Files are a list of locations sorted by time
        # add each location if not already added and link to previous

        with open(file, 'r') as f:
            lines = f.readlines()

        lines = lines[1:]
        split_lines = [None] * len(lines)
        for i in range(0, len(lines)):
            split_lines[i] = lines[i].strip().split(',')[1:]

        previous_node = None
        for line in split_lines:
            data = "Longitude: " + str(line[0]) + ", Latitude: " + str(line[1])
            node = self.add_occurrence(data, previous_node)
            previous_node = node

    def path_from_to_bfs(self, source, target):
        # If we don't know about either of these locations, return None
        if source not in self.nodes or to not in self.nodes:
            return None

        if source.data == target.data:
            return [source]

        # Get the nodes for start and end
        start = self.nodes[source]
        end = self.nodes[target]

        ## BFS ##
        queue = []
        visited = []
        path = []

        # Add our start node to the queue for exploration
        queue.append(start)
        # Mark start as visited so we don't end up in a cycle when we see it
        # again
        visited.append(start)
        while len(queue) > 0 and end.previous == None:
            # get the next node to explore and remove it from the explore queue
            current_node = queue.pop(0)

            # Get each neighbor and check if it is our goal
            # if not, add it to the explore list but only if we haven't
            # seen it before
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
                path.append(current_node.data)
                current_node = current_node.previous

        # Clearing tracking state
        self.clear_previous()
        path.reverse()
        return path

    def path_from_to_dfs(self, source, target):
        # If we don't know about either of these locations, return None
        if source not in self.nodes or to not in self.nodes:
            return None

        if source.data == target.data:
            return [source]

        start = self.nodes[source]
        end = self.nodes[target]

        stack = []
        visited = []
        path = []
        # add start to the stack for exploration
        stack.append(start)
        # mark the start as visited so we don't end up in a cycle if we see it
        # again
        visited.append(start)

        while len(stack) > 0 and end.previous == None:
            # get and remove the first node in our stack for exploration
            current_node = stack.pop()

            for neighbor in current_node.neighbors:
                if neighbor == end:
                    # WHen you find the target, set it's path and exit
                    end.previous = current_node
                    break
                # add each neighbor to the exploration stack if we haven't seen
                # it before
                if neighbor not in visited:
                    neighbor.previous = current_node
                    stack.append(neighbor)
                    visited.append(neighbor)

        if end.previous != None:
            # Track the path backwards
            current_node = end
            while current_node != None:
                path.append(current_node.data)
                current_node = current_node.previous

        # Clearing tracking state
        self.clear_previous()
        path.reverse()
        return path

    def clear_previous(self):
        for node in self.nodes:
            self.nodes[node].previous = None

    def print(self):
        for node in self.nodes:
            print(node + " has neighbors" + Graph.print_neighbors(self.nodes[node].neighbors))

    def print_neighbors(nodes):
        print_output = ""

        for node in nodes:
            print_output = print_output + ", (" + node.data + ")"

        return print_output

    def most_visited(self):
        max = 0

        for node in self.nodes:
            if self.nodes[node].count > max:
                max = self.nodes[node].count

        return max

class GraphNode:
    """
    The graph node contains data, lists its neighbors and counts times visited.
    The "previous" member is a way to keep track of exploration path when
    searching.
    """
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
