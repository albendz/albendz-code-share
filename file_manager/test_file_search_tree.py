import unittest
from file_search_tree import BinarySearchNode
from tracking_file import TrackingFile
from datetime import datetime

class TreeTest(unittest.TestCase):

    def test_search_node(self):
        file = TrackingFile('Brunswick', 'processed_file.csv', './')
        node = BinarySearchNode(file)
        start = '2019-06-07 06:28:22.897527'
        end = '2019-06-10 06:28:22.897527'

        self.assertEqual(node.parent, None)
        self.assertEqual(node.left, None)
        self.assertEqual(node.right, None)

        range = node.get_range_from_data()

        self.assertEqual(range[0], datetime.fromisoformat(start))
        self.assertEqual(range[1], datetime.fromisoformat(end))

        self.assertEqual(node.get_root(), node)

    def test_print_tree(self):
        return True

    def test_find_present_root(self):
        file = TrackingFile('Brunswick', 'processed_root.csv', './')
        root = BinarySearchNode(file)
        node_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootleft.csv', './'))
        node_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootright.csv', './'))
        node_right_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightleft.csv', './'))
        node_right_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightright.csv', './'))

        root.add_node(node_left)
        root.add_node(node_right)
        root.add_node(node_right_left)
        root.add_node(node_right_right)

        found = root.find_node_with_timestamp(datetime.fromisoformat('2019-06-05 12:28:22.897527'))
        self.assertEqual(root.data, found)

    def test_find_present_leaf(self):
        file = TrackingFile('Brunswick', 'processed_root.csv', './')
        root = BinarySearchNode(file)
        node_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootleft.csv', './'))
        node_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootright.csv', './'))
        node_right_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightleft.csv', './'))
        node_right_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightright.csv', './'))

        root.add_node(node_left)
        root.add_node(node_right)
        root.add_node(node_right_left)
        root.add_node(node_right_right)

        found = root.find_node_with_timestamp(datetime.fromisoformat('2019-06-07 07:28:22.897527'))
        self.assertEqual(node_right_left.data, found)

    def test_find_present_inner(self):
        file = TrackingFile('Brunswick', 'processed_root.csv', './')
        root = BinarySearchNode(file)
        node_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootleft.csv', './'))
        node_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootright.csv', './'))
        node_right_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightleft.csv', './'))
        node_right_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightright.csv', './'))

        root.add_node(node_left)
        root.add_node(node_right)
        root.add_node(node_right_left)
        root.add_node(node_right_right)

        found = root.find_node_with_timestamp(datetime.fromisoformat('2019-06-11 07:28:22.897527'))
        self.assertEqual(node_right.data, found)

    def test_find_not_present(self):
        file = TrackingFile('Brunswick', 'processed_root.csv', './')
        root = BinarySearchNode(file)
        node_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootleft.csv', './'))
        node_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootright.csv', './'))
        node_right_left = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightleft.csv', './'))
        node_right_right = BinarySearchNode(TrackingFile('Brunswick', 'processed_rootrightright.csv', './'))

        root.add_node(node_left)
        root.add_node(node_right)
        root.add_node(node_right_left)
        root.add_node(node_right_right)

        found = root.find_node_with_timestamp(datetime.fromisoformat('2019-05-11 07:28:22.897527'))
        self.assertEqual(None, found)

if __name__ == '__main__':
    unittest.main()
