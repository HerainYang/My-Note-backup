class node(object):
    def __init__(self, val):
        self.leftChild = None
        self.rightChild = None
        self.parent = None
        self.value = val

    def addLChild(self, n):
        self.leftChild = n
        n.parent = self
        return n

    def addRChild(self, n):
        self.rightChild = n
        n.parent = self
        return n

    def is_External(self):
        return self.leftChild is None and self.rightChild is None

    def add(self, v):
        if self.value > v:
            if self.leftChild is not None:
                return self.leftChild.add(v)
            else:
                newNode = node(v)
                return self.addLChild(newNode)
        if self.value < v:
            if self.rightChild is not None:
                return self.rightChild.add(v)
            else:
                newNode = node(v)
                return self.addRChild(newNode)

    def search(self, v):
        if self.value > v:
            print(v, "is smaller than", self.value)
            if self.leftChild is not None:
                return self.leftChild.search(v)
            else:
                return None
        if self.value < v:
            print(v, "is larger than", self.value)
            if self.rightChild is not None:
                return self.rightChild.search(v)
            else:
                return None
        if self.value == v:
            print(v, "is equal to", self.value)
            return self

    def delete(self, v):
        node = v
        if node is None:
            raise Exception("no such node")
        if node.leftChild is None and node.rightChild is None:
            if node.parent.value < node.value:
                node.parent.rightChild = None
                node.parent = None
            elif node.parent.value > node.value:
                node.parent.leftChild = None
                node.parent = None
        elif node.leftChild is None:
            if node.parent.value < node.value:
                node.parent.rightChild = node.rightChild
                node.rightChild.parent = node.parent
                node.parent = None
            elif node.parent.value > node.value:
                node.parent.leftChild = node.rightChild
                node.rightChild.parent = node.parent
                node.parent = None
        elif node.rightChild is None:
            if node.parent.value < node.value:
                node.parent.rightChild = node.leftChild
                node.leftChild.parent = node.parent
                node.parent = None
            elif node.parent.value > node.value:
                node.parent.leftChild = node.leftChild
                node.leftChild.parent = node.parent
                node.parent = None
        else:
            nodeToInsert = self.searchInOrderNext(node.rightChild)
            node.value = nodeToInsert.value
            self.delete(nodeToInsert)


    def searchInOrderNext(self, n):
        if n.leftChild is not None:
            return self.searchInOrderNext(n.leftChild)
        return n

    def rangeSearch(self, start, end):
        if self.rightChild is not None and self.value < start:
            self.rightChild.rangeSearch(start, end)
        elif self.leftChild is not None and self.value > end:
            self.leftChild.rangeSearch(start, end)
        else:
            if self.leftChild is not None:
                self.leftChild.rangeSearch(start, end)
            print(self.value)
            if self.rightChild is not None:
                self.rightChild.rangeSearch(start, end)

def inOrder(n):
    if n.leftChild is not None:
        inOrder(n.leftChild)
    print(n.value, end='')
    if n.rightChild is not None:
        inOrder(n.rightChild)

if __name__ == '__main__':
    tree = node(1)
    node3 = tree.add(3)
    node2 = tree.add(2)
    tree.add(8)
    tree.add(6)
    node5 = tree.add(5)
    node9 = tree.add(9)

    tree.delete(node3)

    print(node3.leftChild.value)

    inOrder(tree)

    print()

    tree.rangeSearch(3, 6)