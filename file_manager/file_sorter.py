"""
Inputs to these sorting algorithms are tuples that look like this:
[timestamp, longitude, latitude]
Currently it only sorts by timestamp but this can be altered to sort by
longitude or latitude
"""
class FileSort:
    def insertion_sort(split_lines):
        # Create another list of the right size full of None to store the
        # sorted result
        sorted = [None] * len(split_lines)

        # Add the first item to the empty sorted list
        sorted[0] = split_lines[0]

        # We are sorted up to 1 item in the list now
        sorted_length = 1

        # For each unsorted piece of data
        for data in split_lines:
            # get the timestamp to sort by
            timestamp = data[0]

            # get the data for the last timestamp in the item in the sorted list
            # we have
            last_sorted = sorted[sorted_length - 1][0]

            # We're going to compare the new item to each one in the sorted list
            # This is the index of the item in the sorted list we are comparing to
            current_sorted_index = sorted_length - 1

            # If the item in the sorted list is bigger, shuffle it up in the
            # list to make space and keep going down the list to find where
            # to insert the new item
            while last_sorted > timestamp and current_sorted_index >= 0:
                # move up: 1, 2, 3 becomes 1, 2, None, 3
                sorted[current_sorted_index + 1] = sorted[current_sorted_index]
                sorted[current_sorted_index] = None

                # move down
                current_sorted_index = current_sorted_index - 1

                # As long as we're not at the start, get a new sorted value
                if current_sorted_index >= 0:
                    last_sorted = sorted[current_sorted_index][0]

            # insert the new value where we have a space
            sorted[current_sorted_index + 1] = data
            # increment the size of the list
            sorted_length = sorted_length + 1

        return sorted

    def merge_sort(lines):
        length = len(lines)

        # divide the list in half and recursively sort
        if length > 1:
            left = lines[0:int(length / 2)]
            right = lines[int(length / 2):]
            left = FileSort.merge_sort(left)
            right = FileSort.merge_sort(right)
            # This is the hard part
            return FileSort.merge(left, right)
        else:
            return lines

    def merge(left, right):
        # We are going to move through each half one by one so these will track
        # indexes
        result = [None] * (len(left) + len(right))
        left_index = 0
        right_index = 0
        result_index = 0

        # you can do this with nested while instead of this way but this is
        # a little easier to read
        # while there are still items in the two halves and we haven't
        # hit the end of the list
        while left_index < len(left) and right_index < len(right) and result_index < len(result):
            # is left or right bigger
            if(left[left_index][0] < right[right_index][0]):
                # if left is smaller, and the left element to sorted result
                result[result_index] = left[left_index]
                # Advance pointers
                result_index = result_index + 1
                left_index = left_index + 1
            else:
                # if right is smaller, add right to the sorted result
                result[result_index] = right[right_index]
                # Advance pointers
                result_index = result_index + 1
                right_index = right_index + 1

        # copy the rest of the left if there are leftovers in the left
        if left_index < len(left):
            while left_index < len(left):
                result[result_index] = left[left_index]
                result_index = result_index + 1
                left_index = left_index + 1

        # copy the rest of the right if there are leftovers in the right
        if right_index < len(right):
            while right_index < len(right):
                result[result_index] = right[right_index]
                result_index = result_index + 1
                right_index = right_index + 1

        return result

    def quick_sort(lines):
        if len(lines) <= 1:
            return lines

        # this will split into two new lists for undestandability
        # this would be done in place normally
        pivot = lines[-1]
        less = []
        greater = []

        # This might look a bit like magic
        # If you have a list 2,4,3
        # Pick 3 as a pivot
        # 2 goes in the less
        # 4 goes in the right
        # at the end we append: [2] + [3] + [4] = [2, 3, 4]
        for i in range(0, len(lines) - 1):
            if pivot[0] < lines[i][0]:
                greater.append(lines[i])
            else:
                less.append(lines[i])

        return FileSort.quick_sort(less) + [pivot] + FileSort.quick_sort(greater)
