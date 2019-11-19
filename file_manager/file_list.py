class DoubleLinkedListNode:
    # TODO change from node to element
    # Node is a graphy term
    # DoubleLinkedListElement is also a good way to name this class
    def __init__(self, file, previous=None, next=None):
        self.data = file
        self.previous = previous
        self.next = next

    def get_data(self):
        return self.data

    def get_previous(self):
        return self.previous

    def get_next(self):
        return self.next

    def set_previous(self, node):
        self.previous = node

    def set_next(self, node):
        self.next = node

    def get_list_from_node(self):
        """ Given any node, this will return the list reference for the list that contains that node """
        if self.previous == None :
            return DoubleLinkedList(self)

        currentNode = self
        while(currentNode.previous != None):
            # This assumes no circular lists
            currentNode = currentNode.previous

        return DoubleLinkedList(currentNode)

class DoubleLinkedList:
    """
    The linked list is a reference to the first node of the list.
    The first node then has links to refer to the rest of the list.
    The operations traverse these links to access the list.
    """
    def __init__(self, first_node = None):
        self.first_node = first_node

    def get_at_index(self, index):
        """
        Find the node at a given index or the (index + 1)th node
        """
        # This is an empty list, return None
        if self.first_node == None:
            return None

        count = 0
        current = self.first_node

        # Take one step at a time through the list, counting your steps
        # Stop when you've hit the index you are looking for or when there
        # are no more nodes left
        while count < index and current != None and current.next != None:
            current = current.next
            count = count + 1

        # If you stopped counting when you hit the number you were looking for
        # Return the current node as the answer. Otherwise, the list is too short
        # Return None.
        if count == index:
            return current
        else:
            return None

    def get_last(self):
        # An option is to keep a reference to the last node
        # Another option is to keep track of the length and use get_at_index
        # Here it iterates until it finds the last one
        current = self.first_node

        # If the list is empty this will not excute and instead return None
        # Keep going until you reach the node that has no next
        while current != None and current.next != None:
            #print(current.data)
            current = current.next


        return current

    def get_first(self):
        if self.first_node == None:
            return None
        else:
            return self.first_node

    def insert_at_index(self, index, data):
        if index == 0:
            return self.insert_first(data)

        if index == self.get_length():
            return self.insert_last(data)

        node = self.get_at_index(index)
        if node == None:
            return False

        # Create a node to take the place of the old node at get_at_index
        # It will point to node's previous but use node as the next
        new_node = DoubleLinkedListNode(data, node.previous, node)

        # The tricky part is updating references
        # If you have 1, 3, 4
        # You want to insert 2 at index 1
        # 3 is at index 1
        # set 2's previous to 3's previous (i.e. 1)
        # set 3's previous to 2
        # set 2's next to 3
        node.previous.next = new_node
        node.previous = new_node

        # We successfully added the node at the right index
        return True

    def insert_first(self, data):
        original = self.first_node
        new_node = DoubleLinkedListNode(data, None, original)

        if original != None:
            original.previous = new_node

        self.first_node = new_node

    def insert_last(self, data):
        last = self.get_last()

        if last == None:
            self.first_node = DoubleLinkedListNode(data)
        else:
            # Create a new node with no next node and add it to the end of the list
            new_node = DoubleLinkedListNode(data, last, None)
            last.next = new_node

    def remove_at_index(self, index):
        if index == 0:
            return self.remove_first()

        delete_me = self.get_at_index(index)
        # Updating the links  again
        # If you have 1, 5, 2, 3 and you want to remove 5 at index 1
        # we get 5 and update 2's previous to 1
        # we update 1's next to 2
        # we remove references from 5
        previous = delete_me.previous
        next = delete_me.next
        delete_me.previous = None
        delete_me.next = None

        if previous != None:
            previous.next = next
        if next != None:
            next.previous = previous

        # Return the deleted node
        return delete_me

    def remove_first(self):
        first = self.first_node
        second = first.next

        first.next = None
        second.previous = None
        self.first_node = second

        return first

    def remove_last(self):
        last = self.get_last()
        previous = last.previous

        if previous != None:
            previous.next = None

        return last

    def get_length(self):
        # This method will traverse and count each item
        # A cheaper way is to just keep track of the length as we add and remove
        # elements
        current = self.first_node
        length = 0

        if current == None:
            return 0

        while current != None and current.next != None:
            current = current.next
            length = length + 1

        return length + 1
