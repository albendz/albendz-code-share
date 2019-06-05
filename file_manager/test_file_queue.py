import unittest
from file_queue import Queue, Stack

class QueueTest(unittest.TestCase):

    def test_queue(self):
        queue = Queue()

        self.assertEqual(queue.get_count(), 0)

    def test_queue_push(self):
        queue = Queue()
        queue.push(1)

        self.assertEqual(queue.peek().get_data(), 1)
        self.assertEqual(queue.get_count(), 1)

    def test_queue_pop(self):
        queue = Queue()
        queue.push(1)
        queue.push(2)
        queue.push(3)

        self.assertEqual(queue.get_count(), 3)
        self.assertEqual(queue.pop().get_data(), 1)
        self.assertEqual(queue.get_count(), 2)
        self.assertEqual(queue.peek().get_data(), 2)

    def test_stack(self):
        stack = Stack()

        self.assertEqual(stack.get_count(), 0)

    def test_stack_push(self):
        stack = Stack()
        stack.push(1)

        self.assertEqual(stack.peek().get_data(), 1)
        self.assertEqual(stack.get_count(), 1)

    def test_stack_pop(self):
        stack = Stack()
        stack.push(1)
        stack.push(2)
        stack.push(3)

        self.assertEqual(stack.get_count(), 3)
        self.assertEqual(stack.pop().get_data(), 3)
        self.assertEqual(stack.get_count(), 2)
        self.assertEqual(stack.peek().get_data(), 2)

    def setUp(self):
        return True

    def tearDown(self):
        return True

if __name__ == '__main__':
    unittest.main()
