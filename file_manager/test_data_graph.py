import unittest
import data_graph as Graph

class GraphTest(unittest.TestCase):

    def test(self):
        graph = Graph.Graph()
        graph.add_data_from_file('processed_file.csv')
        #graph.print()

        # Most visited
        self.assertEqual(7, graph.most_visited())
        # To From DFS
        start = "Longitude: 122.3320708, Latitude: 7.6062095"
        end = "Longitude: 122.3320708, Latitude: 8.6062095"

        path = ['Longitude: 122.3320708, Latitude: 7.6062095', 'Longitude: 123.3320708, Latitude: 7.6062095', 'Longitude: 122.3320708, Latitude: 8.6062095']

        self.assertEqual(path, graph.path_from_to_dfs(start, end))
        # To From BFS
        self.assertEqual(path, graph.path_from_to_bfs(start, end))

if __name__ == '__main__':
    unittest.main()
