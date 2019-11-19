import unittest
from AnimalTracker import AnimalTracker, AnimalData
from file_queue import QueueStack
from datafile import DataFile

class MapTest(unittest.TestCase):

    def test_file_tuple(self):
        queue_tuple = AnimalData()
        stack_tuple = AnimalData()
        stack_tuple.set_urgent(True)
        queue_tuple.set_urgent(False)

        self.assertEqual(queue_tuple.is_urgent(), False)
        self.assertEqual(stack_tuple.is_urgent(), True)

    def test_tracker(self):
        tracker = AnimalTracker()

        self.assertEqual(tracker.get_animals(), [])

    def test_add_file(self):
        tracker = AnimalTracker()
        file_one = DataFile("turtle", "/tmp/", "turtle_file")
        file_two = DataFile("turtle", "/tmp/", "turtle_file2")
        file_three = DataFile("octopus", "/tmp", "octo_file")

        tracker.add_file(file_one)

        self.assertEqual(tracker.get_animals(), ["turtle"])
        self.assertEqual(tracker.get_animal("turtle").get_raw().get_length(), 1)

        tracker.add_file(file_two)
        self.assertEqual(tracker.get_animals(), ["turtle"])
        self.assertEqual(tracker.get_animal("turtle").get_raw().get_length(), 2)

        tracker.add_file(file_three)
        self.assertEqual(tracker.get_animals(), ["turtle", "octopus"])

if __name__ == '__main__':
    unittest.main()
