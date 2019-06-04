from datetime import date

class BinarySearchNode:
    def __init__(self, file, parent = None, left = None, right = None):
        self.data = file
        self.parent = None
        self.left = None
        self.right = None

    def find_file_with_timestamp(self, timestamp):
        """ get the file that contains the given timestamp """

        timestamps = self.get_range_from_data()

        if timestamp < timestamps[0] and self.left != None:
            # The search is for a file on the left of this node
            return self.left.find_file_with_timestamp(timestamp)
        elif timestamp > timestamps[1] and self.right != None:
            # The search is for a file on the right of this node
            return self.right.find_file_with_timestamp(timestamp)
        else:
            return self.data

        # This will return None if the timestamp is not in the tree
        return None

    def get_range_from_data(self):
        # None check would be nice
        metadata = self.data.get_first_line().split(',')
        metadata[0] = date.fromtimestamp(metadata[0])
        metadata[1] = date.fromtimestamp(metadata[1])
        return metadata
