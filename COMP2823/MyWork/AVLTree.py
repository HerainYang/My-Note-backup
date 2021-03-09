class node(object):
    def __init__(self, key):
        self.key = key
        self.leftChild = None
        self.rightChild = None
        self.height = 0


def avltree_insert(tree, key):
    if tree is None:
        newNode = node(key)
        newNode.height = 1
        return newNode
    else:
        if key < tree.key:
            tree.leftChild = avltree_insert(tree.leftChild, key)
            if Height(tree.leftChild) - Height(tree.rightChild) == 2:
                if key < tree.leftChild.key:
                    tree = L_L_Rotation(tree)
                else:
                    tree = L_R_Rotation(tree)
        elif key > tree.key:
            tree.rightChild = avltree_insert(tree.rightChild, key)
            if Height(tree.rightChild) - Height(tree.leftChild) == 2:
                if key > tree.rightChild.key:
                    tree = R_R_Rotation(tree)
                else:
                    tree = R_L_Rotation(tree)
        else:
            print("Same in the tree")

        tree.height = max(Height(tree.leftChild), Height(tree.rightChild)) + 1
        return tree

def avltree_delete(tree, key):
    if tree is None:
        return None
    if key < tree.key:
        tree.leftChild = avltree_delete(tree.leftChild, key)
        if Height(tree.rightChild) - Height(tree.leftChild) == 2:
            if Height(tree.rightChild.leftChild) > Height(tree.rightChild.rightChild):
                tree = R_R_Rotation(tree)
            else:
                tree = R_L_Rotation(tree)
    elif key > tree.key:
        tree.rightChild = avltree_delete(tree.rightChild, key)
        if Height(tree.leftChild) - Height(tree.rightChild) == 2:
            if Height(tree.leftChild.rightChild) > Height(tree.leftChild.leftChild):
                tree = L_R_Rotation(tree)
            else:
                tree = L_L_Rotation(tree)
    else:
        if tree.leftChild is not None and tree.rightChild is not None:
            if Height(tree.leftChild) > Height(tree.rightChild):
                maxNode = avltree_maximum(tree.leftChild)
                maxKey = maxNode.key
                print("Max key is", maxKey)
                tree = avltree_delete(tree, maxKey)
                tree.key = maxKey
            else:
                minNode = avltree_minmum(tree.rightChild)
                minKey = minNode.key
                tree = avltree_delete(tree, minKey)
                tree.key = minKey
        else:
            if tree.leftChild is None:
                tree = tree.rightChild
            else:
                tree = tree.leftChild
    if tree is not None:
        tree.height = max(Height(tree.leftChild), Height(tree.rightChild)) + 1
    return tree


def avltree_maximum(node):
    if node.rightChild is not None:
        return avltree_maximum(node.rightChild)
    return node

def avltree_minmum(node):
    if node.leftChild is not None:
        return avltree_minmum(node.leftChild)
    return node

def Height(node):
    if node is None:
        return 0
    return node.height

def L_L_Rotation(nowRoot):
    newRoot = nowRoot.leftChild
    nowRoot.leftChild = newRoot.rightChild
    newRoot.rightChild = nowRoot

    nowRoot.height = max(Height(nowRoot.leftChild), Height(nowRoot.rightChild)) + 1
    newRoot.height = max(Height(newRoot.leftChild), Height(nowRoot)) + 1
    return newRoot

def R_R_Rotation(nowRoot):
    newRoot = nowRoot.rightChild
    nowRoot.rightChild = newRoot.leftChild
    newRoot.leftChild = nowRoot

    nowRoot.height = max(Height(nowRoot.leftChild), Height(nowRoot.rightChild)) + 1
    newRoot.height = max(Height(newRoot.rightChild), Height(nowRoot)) + 1
    return newRoot

def L_R_Rotation(nowRoot):
    nowRoot.leftChild = R_R_Rotation(nowRoot.leftChild)
    return L_L_Rotation(nowRoot)

def R_L_Rotation(nowRoot):
    nowRoot.rightChild = L_L_Rotation(nowRoot.rightChild)
    return R_R_Rotation(nowRoot)

def preOrder(n):
    print(n.key)
    if n.leftChild is not None:
        preOrder(n.leftChild)
    if n.rightChild is not None:
        preOrder(n.rightChild)

def inOrder(n):
    if n.leftChild is not None:
        inOrder(n.leftChild)
    print(n.key)
    if n.rightChild is not None:
        inOrder(n.rightChild)

if __name__ == '__main__':
    tree = None
    tree = avltree_insert(tree, 3)
    tree = avltree_insert(tree, 2)
    tree = avltree_insert(tree, 1)
    tree = avltree_insert(tree, 4)
    tree = avltree_insert(tree, 5)
    tree = avltree_insert(tree, 6)
    tree = avltree_insert(tree, 7)
    tree = avltree_insert(tree, 16)
    tree = avltree_insert(tree, 15)
    tree = avltree_insert(tree, 14)
    tree = avltree_insert(tree, 13)
    tree = avltree_insert(tree, 12)
    tree = avltree_insert(tree, 11)
    tree = avltree_insert(tree, 10)
    tree = avltree_insert(tree, 8)
    tree = avltree_insert(tree, 9)

    inOrder(tree)

    tree = avltree_delete(tree, 7)

    print("************************")

    preOrder(tree)