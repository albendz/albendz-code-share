# implement insertion sort and merge sort on a single file
class FileSort:
    def insertion_sort(split_lines):
        # Note: this does duplicate space needed
        sorted = [None] * len(split_lines)
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

    def merge_sort(lines):
        length = len(lines)

        if length > 1:
            left = lines[0:int(length / 2)]
            right = lines[int(length / 2):]
            left = FileSort.merge_sort(left)
            right = FileSort.merge_sort(right)
            return FileSort.merge(left, right)
        else:
            return lines

    def merge(left, right):
        result = [None] * (len(left) + len(right))
        left_index = 0
        right_index = 0
        result_index = 0

        # you can do this with nested while instead of this way
        while left_index < len(left) and right_index < len(right) and result_index < len(result):
            if(left[left_index][0] < right[right_index][0]):
                result[result_index] = left[left_index]
                result_index = result_index + 1
                left_index = left_index + 1
            else:
                result[result_index] = right[right_index]
                result_index = result_index + 1
                right_index = right_index + 1

        if left_index < len(left): # copy the rest of the left
            while left_index < len(left):
                result[result_index] = left[left_index]
                result_index = result_index + 1
                left_index = left_index + 1

        if right_index < len(right): # copy the rest of the left
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

        for i in range(0, len(lines) - 1):
            if pivot[0] < lines[i][0]:
                greater.append(lines[i])
            else:
                less.append(lines[i])

        return FileSort.quick_sort(less) + [pivot] + FileSort.quick_sort(greater)
