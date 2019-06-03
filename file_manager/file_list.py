class DoubleLinkedListNode:
    def __init__(self, file, previous=None, next=None):
        self.data = file
        self.previous = previous
        self.next = next
    
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
    def __init__(self, firstNode = None):
        self.firstNode = firstNode
    
    def get_at_index(self, index):
        return None
    
    def get_last(self):
        return None

    def insert_at_index(self):
        return None
    
    def insert_first(self):
        return None
    
    def insert_last(self):
        return None
    
    def remove_at_index(self):
        return None
    
    def remove_first(self):
        return None
    
    def remove_last(self):
        return None
