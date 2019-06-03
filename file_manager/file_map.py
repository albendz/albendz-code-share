from tracking_file import TrackingFile
from file_list import DoubleLinkedList
from file_queue import Stack, Queue

class AnimalTracker:
    def __init__(self):
        self.animal_map = {}
    
    def add_file(self, file):
        animal = file.get_subject()

        if(animal in self.animal_map):
            files = self.animal_map[animal]
            files.add_new_file(file)
        else:
            self.animal_map[animal] = FileTuple()
    
    def get_animals(self):
        return list(self.animal_map.keys())

class FileTuple:
    
    def __init__(self, fifo=True):
        """ Create a collection of file sets for processing and search. If FIFO (first in first out) is true (default), it will process files by time received starting with
        the first received. If FIFO is false, it will process starting with the most recently received or LIFO (last in first out)."""
        self.unprocessed = Queue() if fifo else Stack()
        self.processed = DoubleLinkedList()
        self.search_tree = [] # add search tree object
    
    def add_new_file(self, file):
        #self.unprocessed.push()
        return None