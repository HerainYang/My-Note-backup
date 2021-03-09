class node(object):
    def __init__(self, item):
        self.item = item
        self.previous = None

class stack(object):
    def __init__(self):
        self._size = 0
        self.top = self.bottom = None

    def top(self):
        return self.top()

    def size(self):
        return self._size

    def isEmpty(self):
        return self.size() == 0

    def push(self, e):
        newNode = node(e)
        if self.isEmpty():
            self.top = node(e)
        else:
            newNode.previous = self.top
            self.top = newNode
        self._size += 1

    def pop(self):
        if self.isEmpty():
            return None
        else:
            returnNode = self.top
            self.top = self.top.previous
            self._size -= 1
            return returnNode

    def display(self):
        if self.isEmpty():
            return None
        else:
            cur = self.top
            while cur is not None:
                print(cur.item)
                cur = cur.previous

if __name__ == '__main__':
    stackIns = stack()
    stackIns.push(1)
    stackIns.push(2)
    stackIns.push(3)
    stackIns.push(4)
    stackIns.display()
    print(stackIns.pop().item)
    stackIns.display()