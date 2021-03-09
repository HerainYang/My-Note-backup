class node(object):
    def __init__(self, key):
        self.key = key
        self.parent = None
        self.leftChild = None
        self.rightChild = None

class tree(object):
    def __init__(self):
        self.head = None
        self.last = None

def leftmostChild(node):
    if node.leftChild is not None:
        return leftmostChild(node.leftChild)
    return node

def findNextNodeParent(tree, node):
    while node.parent is not tree.head:
        node = node.parent
        if node is node.parent.leftChild:
            return leftmostChild(node.parent.rightChild)
    if node.parent is tree.head and node is tree.head.rightChild:
        return leftmostChild(tree.head)
    elif node.parent is tree.head and node is tree.head.leftChild:
        return leftmostChild(tree.head.rightChild)

def uphead(node):
    while node.parent is not None:
        if node.key < node.parent.key:
            temp = node.key
            node.key = node.parent.key
            node.parent.key = temp
            uphead(node.parent)
        else:
            break

def insert(tree, key):
    if tree.head is None:
        tree.head = node(key)
        tree.last = tree.head
        return
    newNode = node(key)
    if tree.head.leftChild is None:
        tree.head.leftChild = newNode
        newNode.parent = tree.head
        tree.last = newNode
    elif tree.head.rightChild is None:
        tree.head.rightChild = newNode
        newNode.parent = tree.head
        tree.last = newNode
    elif tree.last.parent.leftChild is tree.last:
        tree.last.parent.rightChild = newNode
        newNode.parent = tree.last.parent
        tree.last.parent.rightChild = newNode
        tree.last = newNode
    elif tree.last.parent.rightChild is tree.last:
        nextNodeParent = findNextNodeParent(tree, tree.last)
        newNode.parent = nextNodeParent
        nextNodeParent.leftChild = newNode
        tree.last = newNode
    uphead(newNode)

def preOrder(n):
    print(n.key)
    if n.leftChild is not None:
        preOrder(n.leftChild)
    if n.rightChild is not None:
        preOrder(n.rightChild)

if __name__ == '__main__':
    newTree = tree()
    insert(newTree, 4)
    insert(newTree, 5)
    insert(newTree, 6)
    insert(newTree, 15)
    insert(newTree, 9)
    insert(newTree, 7)
    insert(newTree, 20)
    insert(newTree, 16)
    insert(newTree, 25)
    insert(newTree, 14)
    insert(newTree, 12)
    insert(newTree, 11)
    insert(newTree, 13)
    insert(newTree, 2)
    preOrder(newTree.head)
