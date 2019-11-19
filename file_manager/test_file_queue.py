import unittest
from file_queue import QueueStack

# TODO tests for reversal
class QueueTest(unittest.TestCase):

    def test_queue(self):
        queue = QueueStack()

        self.assertEqual(queue.get_length(), 0)

    def test_queue_push(self):
        queue = QueueStack()
        queue.push(1)

        self.assertEqual(queue.peek().get_data(), 1)
        self.assertEqual(queue.get_length(), 1)

    def test_queue_pop(self):
        queue = QueueStack()
        queue.push(1)
        queue.push(2)
        queue.push(3)

        self.assertEqual(queue.get_length(), 3)
        self.assertEqual(queue.pop().get_data(), 1)
        self.assertEqual(queue.get_length(), 2)
        self.assertEqual(queue.peek().get_data(), 2)

    def test_stack(self):
        stack = QueueStack(False)

        self.assertEqual(stack.get_length(), 0)

    def test_stack_push(self):
        stack = QueueStack(False)
        stack.push(1)

        self.assertEqual(stack.peek().get_data(), 1)
        self.assertEqual(stack.get_length(), 1)

    def test_stack_pop(self):
        stack = QueueStack(False)
        stack.push(1)
        stack.push(2)
        stack.push(3)

        self.assertEqual(stack.get_length(), 3)
        self.assertEqual(stack.pop().get_data(), 3)
        self.assertEqual(stack.get_length(), 2)
        self.assertEqual(stack.peek().get_data(), 2)

    def setUp(self):
        return True

    def tearDown(self):
        return True

if __name__ == '__main__':
    unittest.main()
