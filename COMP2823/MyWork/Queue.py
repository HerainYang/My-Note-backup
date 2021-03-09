class node(object):
    def __init__(self, item):
        self.item = item
        self.next = None

class queue(object):
    def __init__(self):
        self._size = 0
        self._first = None
        self._last = None

    def first(self):
        return self._first

    def size(self):
        return self._size

    def isEmpty(self):
        return self._size == 0

    def enqueue(self, e):
        newNode = node(e)
        if self.isEmpty():
            self._first = self._last = newNode
        else:
            self._last.next = newNode
            self._last = newNode
        self._size += 1

    def dequeue(self):
        if self.isEmpty():
            return
        else:
            returnNode = self._first
            self._first = self._first.next
            self._size -= 1
            if self.isEmpty():
                self._last = None
            return returnNode


    def display(self):
        if self.isEmpty():
            return None
        else:
            cur = self._first
            while cur is not None:
                print(cur.item)
                cur = cur.next

class static_queue(object):
    def __init__(self, maxSize):
        self.maxSize = maxSize
        self.array = [None] * self.maxSize
        self._first = self._last = self._size = 0

    def isEmpty(self):
        return self._size == 0

    def size(self):
        return self._size

    def first(self):
        return self.array[self.first]

    def enqueue(self, e):
        if(self._size == self.maxSize):
            return
        self.array[self._last] = e
        self._last = (self._last + 1) % self.maxSize
        self._size += 1

    def dequeue(self):
        if self.isEmpty():
            return
        returnVal = self.array[self._first]
        self.array[self._first] = None
        self._first = (self._first + 1) % self.maxSize
        self._size -= 1
        return returnVal

    def display(self):
        for i in self.array:
            print(i)






if __name__ == '__main__':
    # newQueue = queue()
    # newQueue.enqueue(1)
    # newQueue.enqueue(2)
    # newQueue.enqueue(3)
    # newQueue.enqueue(4)
    # newQueue.display()
    # newQueue.dequeue()
    # newQueue.dequeue()
    # newQueue.dequeue()
    # newQueue.dequeue()
    # newQueue.display()
    # print(newQueue._last)
    #
    # a = [None] * 10
    # print(a)

    newQueue = static_queue(5)
    newQueue.enqueue(1)
    newQueue.enqueue(2)
    newQueue.enqueue(3)
    newQueue.enqueue(4)
    newQueue.display()
    print()
    newQueue.dequeue()
    newQueue.enqueue(7)
    newQueue.enqueue(9)
    newQueue.dequeue()
    newQueue.display()

    for i in range(1, 5):
        print(i)
