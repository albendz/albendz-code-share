import unittest
from file_list import DoubleLinkedList, DoubleLinkedListNode

class ListTest(unittest.TestCase):

    def test_double_linked_list_node_no_next_prev(self):
        node = DoubleLinkedListNode(0)

        self.assertEqual(node.get_data(), 0)
        self.assertEqual(node.get_previous(), None)
        self.assertEqual(node.get_next(), None)

    def test_double_linked_list_node(self):

        previous = DoubleLinkedListNode(2)
        next = DoubleLinkedListNode(3)
        node = DoubleLinkedListNode(1, previous, next)

        self.assertEqual(node.get_previous(), previous)
        self.assertEqual(node.get_next(), next)

    def test_list_from_node(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        derived_list = third.get_list_from_node()

        self.assertEqual(derived_list.get_first(), first)

    def test_list_default(self):
        linked_list = DoubleLinkedList()

        self.assertEqual(linked_list.get_first(), None)
        self.assertEqual(linked_list.get_last(), None)

    def test_list_first_node(self):
        node = DoubleLinkedListNode(0)
        linked_list = DoubleLinkedList(node)

        self.assertEqual(linked_list.get_first(), node)

    def test_list_get_at_index(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)

        self.assertEqual(linked_list.get_at_index(2), third)

    def test_list_get_at_index_invalid(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)

        self.assertEqual(linked_list.get_at_index(5), None)

    def test_list_get_last(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)

        self.assertEqual(linked_list.get_last(), fourth)

    def test_list_get_first(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)

        self.assertEqual(linked_list.get_first(), first)

    def test_list_insert_at_index(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        linked_list.insert_at_index(1, 1.5) # 1.5 is the new data

        self.assertEqual(linked_list.get_at_index(1).get_data(), 1.5)

    def test_list_insert_last(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        linked_list.insert_last(4)

        self.assertEqual(linked_list.get_last().get_data(), 4)

    def test_list_insert_first(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        linked_list.insert_first(-1)

        self.assertEqual(linked_list.get_first().get_data(), -1)
        self.assertEqual(linked_list.get_at_index(1), first)

    def test_list_remove_at_index(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        removed = linked_list.remove_at_index(2)

        self.assertEqual(removed, third)
        self.assertEqual(linked_list.get_at_index(2), fourth)

    def test_list_remove_last(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        removed = linked_list.remove_last()

        self.assertEqual(removed, fourth)
        self.assertEqual(linked_list.get_last(), third)

    def test_list_remove_first(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)
        removed = linked_list.remove_first()

        self.assertEqual(removed, first)
        self.assertEqual(linked_list.get_first().data, second.data)
        self.assertEqual(second.get_previous(), None)

    def test_list_get_length(self):
        first = DoubleLinkedListNode(0)
        second = DoubleLinkedListNode(1, first)
        third = DoubleLinkedListNode(2, second)
        fourth = DoubleLinkedListNode(3, third)

        first.set_next(second)
        second.set_next(third)
        third.set_next(fourth)

        linked_list = DoubleLinkedList(first)

        self.assertEqual(linked_list.get_length(), 4)

        linked_list.remove_last()

        self.assertEqual(linked_list.get_length(), 3)

if __name__ == '__main__':
    unittest.main()
