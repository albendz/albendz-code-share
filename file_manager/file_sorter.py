# implement insertion sort and merge sort on a single file
class FileSort:
    def insertion_sort(lines):
        split_lines = [None] * len(lines)
        for i in range(0, len(lines)):
            split_lines[i] = lines[i].strip().split(',')

        # Now there is a list of list where the inner list is
        # [timestamp, latitude, longitude]
        # sort by timestamp

        # Note: this does duplicate space needed
        sorted = [None] * len(lines)
        sorted[0] = split_lines[0]
        split_lines = split_lines[1:]

        sorted_length = 1

        for data in split_lines:
            timestamp = data[0]
            last_sorted = sorted[sorted_length - 1][0]
            current_sorted_index = sorted_length - 1

            while last_sorted > timestamp and current_sorted_index >= 0:
                sorted[current_sorted_index + 1] = sorted[current_sorted_index]
                current_sorted_index = current_sorted_index - 1
                if current_sorted_index >= 0:
                    last_sorted = sorted[current_sorted_index][0]

            sorted[current_sorted_index + 1] = data
            sorted_length = sorted_length + 1

        return sorted

    def merge_sort(file):
        return None

    def quick_sort(file):
        return None
