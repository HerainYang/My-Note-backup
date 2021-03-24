class treeNode(object):
    def __init__(self, value, weight):
        self.leftChild = None
        self.rightChild = None
        self.value = value
        self.weight = weight
        self.code = []


def getWeight(elem):
    return elem.weight


class tree(object):
    def __init__(self):
        self.head = []

    def addNode(self, value):
        for element in self.head:
            if element.value == value:
                element.weight += 1
                return
        self.head.append(treeNode(value, 1))

    def printHead(self):
        for element in self.head:
            print("( value =", element.value, ", weight =", element.weight, ")")

    def sortTree(self):
        self.head.sort(key=getWeight)

    def constructHuffmanTree(self):
        while len(self.head) > 1:
            left = self.head.pop(0)
            right = self.head.pop(0)
            newNode = treeNode(None, left.weight + right.weight)
            newNode.leftChild = left
            newNode.rightChild = right
            self.head.append(newNode)
            self.head.sort(key=getWeight)

    def preOrder(self, node, code):
        print("node:", node.value, ", weight", node.weight, end='')

        print(node.code)

        if node.leftChild is not None:
            node.leftChild.code = node.code.copy()
            node.leftChild.code.append('0')
            self.preOrder(node.leftChild, node.code)


        if node.rightChild is not None:
            node.rightChild.code = node.code.copy()
            node.rightChild.code.append('1')
            self.preOrder(node.rightChild, node.code)


if __name__ == '__main__':
    inputString = "AFTERDATAEARAREARTAREA"
    newTree = tree()
    for c in inputString:
        newTree.addNode(c)
    newTree.sortTree()
    newTree.printHead()
    newTree.constructHuffmanTree()
    newTree.preOrder(newTree.head[0], [])
