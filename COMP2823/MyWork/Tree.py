class node(object):
    def __init__(self, value):
        self.parent = None
        self.children = []
        self.value = value

    def addParent(self, p):
        self.parent = p
        p.children.append(self)

    def __iter__(self):
        self.iter = 0
        return self

    def __next__(self):
        if self.iter < len(self.children):
            val = self.children[self.iter].value
            self.iter += 1
            return val
        else:
            raise StopIteration

def isInternal(p):
    return len(p.children) != 0

def isExternal(p):
    return len(p.children) == 0

def isRoot(p):
    return p.parent is None

def pre_order(v):
    print(v.value)
    for child in v.children:
        pre_order(child)

def post_order(v):
    for child in v.children:
        pre_order(child)
    print(v.value)

if __name__ == '__main__':
    n1 = node(12)
    n2 = node(14)
    n3 = node(13)
    n4 = node(20)
    n5 = node(18)
    n6 = node(23)
    n7 = node(31)
    n8 = node(34)
    n9 = node(31)
    n10 = node(16)
    n11 = node(11)

    n10.addParent(n6)
    n11.addParent(n6)

    n5.addParent(n2)
    n6.addParent(n2)
    n7.addParent(n2)

    n8.addParent(n3)
    n9.addParent(n3)

    n2.addParent(n1)
    n3.addParent(n1)
    n4.addParent(n1)

    pre_order(n1)

    print()

    post_order(n1)