class Node(object):
    def __init__(self, item):
        self.item = item
        self.next = None


class linkList(object):
    def __init__(self):
        self.head = None
        self._size = 0

    def isEmpty(self):
        return self.head is None

    def append(self, item):
        if item is None:
            return
        node = Node(item)
        if self.isEmpty():
            self.head = node
        else:
            cur = self.head
            while cur.next is not None:
                cur = cur.next
            cur.next = node
        self._size += 1

    def display(self):
        cur = headpt.head
        while cur != None:
            print("[", cur.item, "]")
            cur = cur.next

    def getSize(self):
        return self._size

    def fist(self):
        if self.isEmpty():
            return None
        return self.head

    def last(self):
        if self.isEmpty():
            return None
        cur = self.head
        while cur.next is not None:
            cur = cur.next
        return cur

    def before(self, p):
        if self.isEmpty() or p is None or p == self.fist():
            return None
        cur = self.head
        while cur.next is not None:
            if cur.next is p:
                return cur
            cur = cur.next
        return None

    def after(self, p):
        if self.isEmpty() or p is None or p == self.last():
            return None
        cur = self.head
        while cur is not None:
            if cur is p:
                return cur.next
            cur = cur.next
        return None

    def insertBefore(self, p, e):
        if self.isEmpty() or p is None or e is None:
            return
        newNode = Node(e)
        if p is self.head:
            newNode.next = self.head
            self.head = newNode
        else:
            beforeP = self.before(p)
            beforeP.next = newNode
            newNode.next = p
        self._size += 1

    def insertAfter(self, p, e):
        if self.isEmpty() or p is None or e is None:
            return
        newNode = Node(e)
        if p is self.last():
            self.last().next = newNode
        else:
            afterP = self.after(p)
            p.next = newNode
            newNode.next = afterP
        self._size+=1

    def remove(self, p):
        if self.isEmpty() or p is None:
            return
        if p is self.head:
            self.head = p.next
        elif p is self.last():
            beforeP = self.before(p)
            beforeP.next = None
        else:
            beforeP = self.before(p)
            afterP = self.after(p)
            beforeP.next = afterP
        self._size -= 1

    def __iter__(self):
        self.cur = self.head
        return self

    def __next__(self):
        val = self.cur.item
        self.cur = self.cur.next
        return val






if __name__ == '__main__':
    headpt = linkList()
    headpt.append(1)
    headpt.append(3)
    headpt.append(6)
    headpt.append(7)
    headpt.display()
    headpt.remove(headpt.before(headpt.last()))
    headpt.display()

    print(headpt.getSize())