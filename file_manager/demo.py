# Start the program watching a directory for new files and polling
# Create a new file in location
# Examine file header to create file
# add file to file map
# For demo purposes, do not auto process files
# Call process next batch (batch size of 1)
# Before process batch, query for status of tracked item
# Call process
# retrive state again
# add another item
# same demo as above
# Print list of files shown in time received sequence
# Add file for each and show difference between queue add and stack add
# Process file and show list method "get files sent Monday" and BST "get file with data at 3 pm Monday"
# Create graph and find most visited node
from tracking_file import TrackingFile
from file_map import AnimalTracker

fluffy_data = TrackingFile('Fluffy', 'file.csv', './')

animal_data = AnimalTracker()
animal_data.add_file(fluffy_data)
animal_list = animal_data.get_animals()

for animal in animal_list:
    print(animal)

# Watch for new files
# Determine name by first line of file
# add file
# check list for processed vs unprocessed
# process batch
# check that processed file is now in list
# add files
# check where files are in relation to appearance in queue or stack
# process files
# binary search for set of files with range
