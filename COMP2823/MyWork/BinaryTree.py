
class node(object):
    def __init__(self, type, val):
        self.leftChild = None
        self.rightChild = None
        self.parent = None
        self.type = type
        self.value = val

    def addLChild(self, n):
        self.leftChild = n
        n.parent = self

    def addRChild(self, n):
        self.rightChild = n
        n.parent = self

    def is_External(self):
        return self.leftChild is None and self.rightChild is None

    def is_Symbol(self):
        return self.type == 's'

def preOrder(n):
    print(n.value)
    if n.leftChild is not None:
        preOrder(n.leftChild)
    if n.rightChild is not None:
        preOrder(n.rightChild)

def postOrder(n):
    if n.leftChild is not None:
        postOrder(n.leftChild)
    if n.rightChild is not None:
        postOrder(n.rightChild)
    print(n.value)

def inOrder(n):
    if n.leftChild is not None:
        print('(', end='')
        inOrder(n.leftChild)
    print(n.value, end='')
    if n.rightChild is not None:
        inOrder(n.rightChild)
        print(')', end='')

def eval_expr(v):
    if v.is_External():
        return v.value
    else:
        if v.is_Symbol():
            x = eval_expr(v.leftChild)
            y = eval_expr(v.rightChild)
            if v.value == '+':
                return x + y
            if v.value == '-':
                return x - y
            if v.value == '*':
                return x * y
            if v.value == '/':
                return x / y
        else:
            raise Exception

def depth(v):
    if v.parent == None:
        return 0
    else:
        return depth(v.parent) + 1

def height(v):
    if v.is_External() or v is None:
        return 0
    else:
        left = height(v.leftChild)
        right = height(v.rightChild)
        return max(left, right) + 1

if __name__ == '__main__':
    n1 = node('s', '+')
    n2 = node('s', '*')
    n3 = node('s', '*')
    n4 = node('v', 2)
    n5 = node('s', '-')
    n6 = node('v', 3)
    n7 = node('v', 2)
    n8 = node('v', 5)
    n9 = node('v', 1)

    n1.addLChild(n2)
    n1.addRChild(n3)

    n2.addLChild(n4)
    n2.addRChild(n5)

    n5.addLChild(n8)
    n5.addRChild(n9)

    n3.addLChild(n6)
    n3.addRChild(n7)

    inOrder(n1)
    print('=', end='')
    print(eval_expr(n1))

    print("depth =", depth(n9))
    print("height =", height(n1))