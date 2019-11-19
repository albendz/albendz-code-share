''' This is pseudocode and doesn't work yet '''

from datafile import DataFile
from AnimalTracker import AnimalTracker
from file_sorter import FileSort
from file_search_tree import BinarySearchNode
from datetime import datetime
from data_graph import Graph

# Get some files for our Sharks
thomas_data = DataFile('thomas', 'file.csv')
brunswick_data = DataFile('brunswick', 'file.csv')

# Add three files for two animals
# One animal uses a stack and the other a queue
animal_data = AnimalTracker()
animal_data.add_file(DataFile('thomas', 'thomas_0.csv'))
animal_data.add_file(DataFile('thomas', 'thomas_1.csv'))
animal_data.add_file(DataFile('thomas', 'thomas_2.csv'))

animal_data.add_file(DataFile('brunswick', 'brunswick_0.csv'))
animal_data.add_file(DataFile('brunswick', 'brunswick_1.csv'))
animal_data.add_file(DataFile('brunswick', 'brunswick_2.csv'))

animal_data.get('brunswick').set_urgent(true) # set this to use a stack or LIFO

# list animals in our map
animal_list = animal_data.get_animals()

for animal in animal_list:
    print(animal)

# Get queue for animal to check which data was just received
brunswick = animal_data.get_animal("brunswick")
queue_node = brunswick.get_unprocessed().peek()

# How do you traverse a linked list
print(queue_node)
print(queue_node.next)
print(queue_node.previous)
print(queue_node.next.next)

# Queues vs Stacks
thomas = animal_data.get_animal("thomas")

# Note that this node is the last one added
# the queue_node was the first one added
stack_node = thomas.get_unprocessed().peek()
print(stack_node)

# Sorting: sort a file contents and add it to processed
print(brunswick.get_unprocessed().get_count())
queue_node = brunswick.get_unprocessed().pop()
print(brunswick.get_unprocessed().get_count())

with open(queue_node.data.get_full_path(), 'r') as f:
    lines = f.readlines()

split_lines = []
for line in lines[1:]:
    split_lines.append(line.strip().split(','))

print(split_lines)
sorted = FileSort.quick_sort(split_lines)
print(sorted)

start_range = sorted[0][0]
end_range = sorted[-1][0]

sorted_file_name = queue_node.data.get_full_path() + "_sorted"

with open(sorted_file_name, 'w') as f:
    f.write(start_range + ',' + end_range + '\n')
    for line in sorted:
        f.write(','.join(line) + '\n')

sorted_file = DataFile("brunswick", queue_node.data.get_filename() + "_sorted", queue_node.data.get_path())
brunswick.get_processed().insert_last(sorted_file)

# Add sorted files into binary search tree
brunswick.search_tree = (BinarySearchNode(sorted_file))
brunswick.get_search_tree().add_node(BinarySearchNode(sorted_file))
brunswick.get_search_tree().add_node(BinarySearchNode(sorted_file))

# Search for the file for a specific time
brunswick.get_search_tree().find_node_with_timestamp(datetime.fromisoformat('2019-06-08 23:28:22.897527'))

# Create a graph from the animals tracking information
graph = Graph()
graph.add_data_from_file(sorted_file_name)

# Determinine the location the animal visits most often
print(graph.most_visited())

# How can the animal get from A to B?
# This will give shortest path
print(graph.path_from_to_bfs("", ""))
# This will give any path
print(graph.path_from_to_dfs("", ""))
