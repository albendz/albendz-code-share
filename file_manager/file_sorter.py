# implement insertion sort and merge sort on a single file
class FileSort:
    def insertion_sort(file):
        # assumes no extension
        name = file.get_filename() + "_processed"
        lines = []

        with open(file.get_path() + file.get_filename(), 'r') as f:
            lines = f.read().splitlines()

        for i in range(0, len(lines)):
            lines[i] = lines[i].split(',')

        # Now there is a list of list where the inner list is
        # [timestamp, latitude, longitude]
        # sort by timestamp

        # Note: this does duplicate space needed
        sorted = None * len(lines)
        end_index = 0

        for line in lines:
            timestamp_to_sort = line[i][0]
            index = end_index
            timestamp_sorted = sorted[end_index]

            # For the first line sorted[0] will be none
            if timestamp_sorted == None:
                sorted[end_index] = timestamp_to_sort
                continue

            # get the right most timestampe
            timestamp_sorted = sorted[index][0]

            # Go backwards through the list looking for the first value
            # less than the value to insert
            while(timestamp_to_sort < timestamp_sorted and index >= 0):
                # shift the current value right
                sorted[index + 1] = sorted[index]

                # move to the next left element to compare
                index = index - 1
                timestamp_sorted = sorted[index][0]

            sorted[index] = timestamp_to_sort
            end_index = end_index + 1

        return sorted

    def merge_sort(file):
        return None

    def quick_sort(file):
        return None
