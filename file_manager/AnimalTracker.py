from datafile import DataFile
from file_list import DoubleLinkedList
from file_queue import Stack, Queue

"""
This Animal tracker will contain information about several animals. Given their
name, you can add files, sort them in two separate structures for data that has
been sorted and data that has not, and add a binary search tree for optimization.
Under the key, which is the animal name, we will store all three of these
structures in a FileTuple.

Changes in behavior:
In contrast to the Python implementation, collisions append a new file to the
list of files under the key and when a key is not present, it will return None.
"""

class AnimalTracker:
    def __init__(self):
        self.animal_map = {}

    # The queue parameter specifies if we want this file to be added as a queue
    # or a stack. This is not a good way to do this because it exposes
    # internal implementation but for the exercise, we want to be explicit
    # about describing behavior so it's okay here.
    def add_file(self, file, queue=True):
        animal = file.get_name()

        # If we already have this member, just add a new file to the
        # unprocessed list. Otherwise, create a new tuple
        if(animal not in self.animal_map):
            # use the provided queue parameter to decide priority or processing
            self.animal_map[animal] = FileTuple(queue)
        files = self.animal_map[animal]
        files.add_new_file(file)

    def get_animals(self):
        return list(self.animal_map.keys())

    def get_animal(self, subject):
        if subject in self.animal_map:
            return self.animal_map[subject]
        else:
            return None

"""
This is a set of three file tracking structure: a queue or stack, a list, and
a binary search tree. The unprocessed member is a queue or a stack returned the
next most important file to process. The processed list contains all sorted
files and the binary search tree is a structure optimizing searching in the files.
"""
class FileTuple:

    def __init__(self, fifo=True):
        """ Create a collection of file sets for processing and search. If FIFO (first in first out) is true (default), it will process files by time received starting with
        the first received. If FIFO is false, it will process starting with the most recently received or LIFO (last in first out)."""

        # All the unsorted files
        self.unprocessed = Queue() if fifo else Stack()
        # All the sorted files
        self.processed = DoubleLinkedList()
        # Cosntructed BST as files are sorted
        self.search_tree = None # None until files are processed

    def add_new_file(self, file):
        self.unprocessed.push(file)

    def get_unprocessed(self):
        return self.unprocessed

    def get_processed(self):
        return self.processed

    def get_search_tree(self):
        return self.search_tree
