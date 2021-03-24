import random

class Node(object):
    def __init__(self, value):
        self.value = value
        self.priority = random.random()
        self.leftChild = None
        self.rightChild = None

    def left_rotate(self):
        a = self
        b = a.rightChild
        a.rightChild = b.leftChild
        b.leftChild = a
        return b

    def right_rotate(self):
        a = self
        b = a.leftChild
        a.leftChild = b.rightChild
        b.rightChild = a
        return b

class Treap(object):
    def __init__(self):
        self.root = None

    def insert(self, value):
        self.root = self._insert(self.root, value)

    def _insert(self, node, value):
        if node is None:
            node = Node(value)
            print("create", node.value)
            return node
        if value < node.value:
            node.leftChild = self._insert(node.leftChild, value)
            if node.leftChild.priority < node.priority:
                node = node.right_rotate()
        elif value > node.value:
            node.rightChild = self._insert(node.rightChild, value)
            if node.rightChild.priority < node.priority:
                node = node.left_rotate()
        return node

    def delete(self, value):
        self.root = self._delete(self.root, value)

    def _delete(self, node, value):
        if node is not None:
            if value < node.value:
                node.leftChild = self._delete(node.leftChild, value)
            elif value > node.value:
                node.rightChild = self._delete(node.rightChild, value)
            else:
                if node.leftChild is None and node.rightChild is None:
                    return None
                if node.leftChild is None:
                    node = node.rightChild
                elif node.rightChild is None:
                    node = node.leftChild
                else:
                    if node.leftChild.priority < node.rightChild.priority:
                        node = node.right_rotate()
                        node.rightChild = self._delete(node.rightChild, value)
                    else:
                        node = node.left_rotate()
                        node.leftChild = self._delete(node.leftChild, value)
        return node



    def _preOrder(self):
        preOrder(self.root)

def preOrder(n):
    print(n.value, ':', n.priority)
    if n.leftChild is not None:
        print(n.value, "left is", n.leftChild.value)
        preOrder(n.leftChild)
    if n.rightChild is not None:
        print(n.value, "right is", n.rightChild.value)
        preOrder(n.rightChild)

if __name__ == '__main__':
    treap = Treap()
    treap.insert('G')
    treap.insert('B')
    treap.insert('H')
    treap.insert('A')
    treap.insert('E')
    treap.insert('C')
    treap.insert('K')
    treap.insert('I')


    preOrder(treap.root)

    treap.delete('I')
    print("delete:")
    preOrder(treap.root)