# use the file list base class (doubly linked)
# If stack, implement pop and push as LIFO
# if queue, implement pop and push as FIFO
# peek
from file_list import DoubleLinkedList

class Queue:
    def __init__(self):
        self.linked_list = DoubleLinkedList()

    def push(self, item):
        self.linked_list.insert_last(item)

    def pop(self):
        first = self.linked_list.get_first()
        self.linked_list.remove_first()
        return first

    def peek(self):
        return self.linked_list.get_first()

    def get_count(self):
        return self.linked_list.get_length()

class Stack:
    def __init__(self):
        self.linked_list = DoubleLinkedList()

    def push(self, item):
        self.linked_list.insert_first(item)

    def pop(self):
        first = self.linked_list.get_first()
        self.linked_list.remove_first()
        return first

    def peek(self):
        return self.linked_list.get_first()

    def get_count(self):
        return self.linked_list.get_length()
