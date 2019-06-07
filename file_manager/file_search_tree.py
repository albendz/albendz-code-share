from datetime import datetime

class BinarySearchNode:
    def __init__(self, file, parent = None, left = None, right = None):
        self.data = file
        self.parent = None
        self.left = None
        self.right = None

    def find_node_with_timestamp(self, timestamp):
        """ get the file that contains the given timestamp """

        timestamps = self.get_range_from_data()

        if timestamp < timestamps[0] and self.left != None:
            # The search is for a file on the left of this node
            return self.left.find_node_with_timestamp(timestamp)
        elif timestamp > timestamps[1] and self.right != None:
            # The search is for a file on the right of this node
            return self.right.find_node_with_timestamp(timestamp)
        elif timestamp <= timestamps[1] and timestamp >= timestamps[0]:
            return self.data
        else:
            return None

    def get_range_from_data(self):
        # None check would be nice
        metadata = self.data.get_first_line().split(',')

        metadata[0] = datetime.fromisoformat(metadata[0])
        metadata[1] = datetime.fromisoformat(metadata[1])
        return metadata

    def print_tree(self):
        nodes = []
        queue = []
        root = self

        if self.parent != None:
            root = self.get_root()

        queue.append(root)
        nodes.append(root)
        while len(queue) > 0:
            next = queue.remove(0)

            if next.right != None:
                nodes.append(next.right)
                queue.append(next.right)
            if next.left != None:
                nodes.append(next.left)
                queue.append(next.left)

        # nodes is now a list of all nodes
        # This will print top to bottom, left to right
        for node in nodes:
            data = node.data
            metadata = node.get_range_from_data()
            print(file.get_subject() + " : " + str(metadata[0]) + " : " + str(metadata[1]))

    def get_root(self):
        node = self

        while node != None and node.parent != None:
            node = node.parent

        return node

    def add_node(self, new_node):

        data = self.get_range_from_data()
        new_data = new_node.get_range_from_data()

        # with no overlap, compare only the first timestamps
        if data[0] < new_data[0]:
            if self.right == None:
                new_node.parent = self
                self.right = new_node
            else:
                self.right.add_node(new_node)
        else:
            if self.left == None:
                new_node.parent = self
                self.left = new_node
            else:
                self.left.add_node(new_node)

        return True
