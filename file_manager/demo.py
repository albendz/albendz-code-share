from tracking_file import TrackingFile
from file_map import AnimalTracker

fluffy_data = TrackingFile('Fluffy', 'file.csv', './')
brunswick_data = TrackingFile('Brunswick', 'file.csv', './')

# Add three files for two animals
# One animal uses a stack and the other a queue
animal_data = AnimalTracker()
animal_data.add_file(fluffy_data)
animal_data.add_file(fluffy_data)
animal_data.add_file(fluffy_data)

animal_data.add_file(brunswick_data)
animal_data.add_file(brunswick_data)
animal_data.add_file(brunswick_data)

# list animals
animal_list = animal_data.get_animals()

for animal in animal_list:
    print(animal)

# Get queue for animal to check which data was just received
brunswick = animal_data.get_animal("Brunswick")
queue_node = brunswick.get_unprocessed().peek()

# How do you traverse a linked list
print(queue_node)
print(queue_node.next)
print(queue_node.previous)
print(queue_node.next.next)

# Queues vs Stacks
fluffy = animal_data.get_animal("Fluffy")

# Note that this node is the last one added
# the queue_node was the first one added
stack_node = fluffy.get_unprocessed.peek()
print(stack_node)

# Sorting: sort a file contents and add it to processed
print(brunswick.get_unprocessed().get_length())
queue_node = brunswick.get_unprocessed().pop()
print(brunswick.get_unprocessed().get_length())

with open(queue_node.data.get_path() + queue_node.data.get_filename(), 'r') as f:
    lines = f.readlines()

split_lines = []
for line in lines[1:]:
    split_lines.append(line.strip().split(','))

print(split_lines)
sorted = FileSort.quick_sort(split_lines)
print(sorted)

start_range = sorted[0][0]
end_range = sorted[-1][0]

sorted_file = queue_node.data.get_path() + queue_node.data.get_filename() + "_sorted")

with open(sorted_file, 'w') as f:
    f.write(start_range + ',' + end_range)
    for line in sorted:
        f.write(line)

sorted_file = TrackingFile("Brunswick", , queue_node.data.get_filename() + "_sorted", queue_node.data.get_path())
brunswick.get_processed().insert_last(sorted_file)

# Add sorted files into binary search tree
brunswick.get_search_tree().add_node(new BinarySearchNode(sorted_file))
brunswick.get_search_tree().add_node(new BinarySearchNode(sorted_file))
brunswick.get_search_tree().add_node(new BinarySearchNode(sorted_file))

# Search for the file for a specific time
brunswick.get_search_tree().find_node_with_timestamp("")

# Create a graph from the animals tracking information
graph = Graph()
graph.add_data_from_file(sorted_file)

# Determinine the location the animal visits most often
print(graph.most_visited())

# How can the animal get from A to B?
# This will give shortest path
print(graph.path_from_to_bfs("", ""))
# This will give any path
print(graph.path_from_to_dfs("", ""))
