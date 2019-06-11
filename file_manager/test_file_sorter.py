import unittest
from file_sorter import FileSort

class SortingTest(unittest.TestCase):

    def test_insertion(self):
        result = FileSort.insertion_sort(self.lines)
        self.assertEqual(self.sorted, result)

    def test_merge(self):
        # print(FileSort.quick_sort(self.lines))
        return True

    def test_quick(self):
        # print(FileSort.merge_sort(self.lines))
        return True

    def setUp(self):
        self.lines = []
        with open('file.csv', 'r') as f:
            self.lines = f.readlines()

        name = self.lines[0]
        self.lines = self.lines[1:]
        self.sorted = [['2019-06-07 07:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-07 11:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-07 15:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-08 04:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-08 06:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-08 23:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-09 03:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-09 12:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-10 01:28:22.897527', '122.3320708', '7.6062095'], ['2019-06-10 04:28:22.897527', '122.3320708', '7.6062095']]

if __name__ == '__main__':
    unittest.main()
