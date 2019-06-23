import unittest
from file_map import AnimalTracker, FileTuple
from file_queue import Queue, Stack
from tracking_file import TrackingFile

class MapTest(unittest.TestCase):

    def test_file_tuple(self):
        queue_tuple_default = FileTuple()
        queue_tuple = FileTuple(True)
        stack_tuple = FileTuple(False)

        self.assertEqual(type(queue_tuple_default.get_unprocessed()), type(Queue()))
        self.assertEqual(type(queue_tuple.get_unprocessed()), type(Queue()))
        self.assertEqual(type(stack_tuple.get_unprocessed()), type(Stack()))

    def test_tracker(self):
        tracker = AnimalTracker()

        self.assertEqual(tracker.get_animals(), [])

    def test_add_file(self):
        tracker = AnimalTracker()
        file_one = TrackingFile("turtle", "/tmp/", "turtle_file")
        file_two = TrackingFile("turtle", "/tmp/", "turtle_file2")
        file_three = TrackingFile("octopus", "/tmp", "octo_file")

        tracker.add_file(file_one)

        self.assertEqual(tracker.get_animals(), ["turtle"])
        self.assertEqual(tracker.get_animal("turtle").get_unprocessed().get_count(), 1)

        tracker.add_file(file_two)
        self.assertEqual(tracker.get_animals(), ["turtle"])
        self.assertEqual(tracker.get_animal("turtle").get_unprocessed().get_count(), 2)

        tracker.add_file(file_three)
        self.assertEqual(tracker.get_animals(), ["turtle", "octopus"])

if __name__ == '__main__':
    unittest.main()
