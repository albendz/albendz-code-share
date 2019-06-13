import unittest
import data_graph as Graph

class GraphTest(unittest.TestCase):

    def test(self):
        graph = Graph.Graph()
        graph.add_data_from_file()

        # Most visited
        # To From DFS
        # To From BFS

    def setUp(self):
        return True

    def tearDown(self):
        return True

if __name__ == '__main__':
    unittest.main()
