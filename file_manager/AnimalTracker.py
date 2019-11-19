from datafile import DataFile
from file_list import DoubleLinkedList
from file_queue import QueueStack

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
            self.animal_map[animal] = AnimalData()
        files = self.animal_map[animal]
        files.add_new_file(file)

    def get_animals(self):
        return list(self.animal_map.keys())

    # Get an animal by its name as a String
    # If the animal name is not found, return None
    def get_animal(self, subject):
        if subject in self.animal_map:
            return self.animal_map[subject]
        else:
            return None

    def delete_animal(self):
        return # TODO

# Zip/unzip, compression, or serialization is a wishlist item if I get around to it
    def zip_animal(self):
        return # TODO: compress and zip all data related to the animal

    def unzip_animal(self):
        return # TODO: given a zipped set of animal data, unpack it into this structure

"""
This is a set of three file tracking structure: a queue or stack, a list, and
a binary search tree. The raw data is a queue or a stack returned the
next most important file to process. The sorted list contains all sorted
files and the binary search tree is a structure optimizing searching in the files.
"""
class AnimalData:

    def __init__(self, urgent=True):
        """ Create a collection of file sets for processing and search.
        If urgent is true (default), it will look at most recent files first.
        If urgent is false, it will look at files in order received."""
        self.urgent = urgent
        # All the unsorted files
        self.raw = QueueStack(not urgent) #if not urgent, treat as FIFO/queue
        # All the sorted files
        self.sorted = DoubleLinkedList()
        # Cosntructed BST as files are sorted
        self.search = None # None until files are processed

    def add_new_file(self, file):
        '''
        Add a new file to the set of raw data for this animal.
        '''
        # TODO: avoid exposing this structure
        self.raw.push(file)

    def get_raw(self):
        '''
        Get the queue of all unsorted data for this animal
        '''
        # TODO: avoid exposing this structure
        return self.raw

    def get_sorted(self):
        '''
        Get the list of sorted data for this animal
        '''
        return self.sorted

    def get_search(self):
        '''
        Get the search tree for this animal
        '''
        # TODO: ideally we don't expose this structure and instead have a set
        # of methods that use the search tree
        return self.search

    def sort_next(self, batch=1):
        # TODO
        # Sort the next batch of files in priority order
        # This will add them to the search tree
        return

    def sort_all(self):
        # TODO
        # sort all files in processed queue
        return

    def is_urgent(self):
        '''
        Check if the set of data is sent to "urgent", meaning newest files are
        looked at first.
        '''
        return self.urgent

    def set_urgent(self, urgent):
        '''
        If urgent, then newest files are processed first.
        If there are already files in this set, the processing order will be
        reversed.
        '''
        if(self.urgent != urgent):
            self.urgent = urgent
            self.raw.invert_priority()
