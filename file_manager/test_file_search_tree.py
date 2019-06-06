import unittest
from file_search_tree import BinarySearchNode

class TreeTest(unittest.TestCase):

    def test_search_node(self):
        node = BinarySearchNode('processed_file.csv')

        self.assertEqual(node.parent, None)
        self.assertEqual(node.left, None)
        self.assertEqual(node.right, None)

        range = node.get_range_from_data()

        self.assertEqual(range[0], "")
        self.assertEqual(range[1], "")

        self.assertEqual(node.get_root(), node)

    def test_get_root(self):
        return True

    def test_print_tree(self):
        return True

    def test_find_present_root(self):
        return True

    def test_find_present_leaf(self):
        return True

    def test_find_present_inner(self):
        return True

    def test_find_not_present(self):
        return True

    def setUp(self):
        return True

    def tearDown(self):
        return True

if __name__ == '__main__':
    unittest.main()
