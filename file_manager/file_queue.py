# use the file list base class (doubly linked)
# If stack, implement pop and push as LIFO
# if queue, implement pop and push as FIFO
# peek
from file_list import DoubleLinkedList

class QueueStack:
    def __init__(self, fifo=True):
        self.linked_list = DoubleLinkedList()
        self.fifo = fifo

    def push(self, item):
        if(self.fifo):
            self.linked_list.insert_last(item)
        else:
            self.linked_list.insert_first(item)

    def pop(self):
        first = self.linked_list.get_first()
        self.linked_list.remove_first()
        return first

    def peek(self):
        return self.linked_list.get_first()

    def get_length(self):
        return self.linked_list.get_length()

    def invert_priority(self):
        self.fifo = not self.fifo
