class node(object):
    def __init__(self, key, val):
        self.key = key
        self.val = val

class separateChaining(object):
    def __init__(self, size):
        self.size = size
        self.array = [dict()] * size

    def h(self, k):
        return k % self.size

    def get(self, k):
        return self.array[self.h(k)].get(k)

    def put(self, k, v):
        self.array[self.h(k)][k] = v

    def remove(self, k):
        return self.array[self.h(k)].remove(k)

    def displayADict(self, k):
        for i in self.array[self.h(k)]:
            print(self.array[self.h(k)].get(i))

if __name__ == '__main__':
    hashTable = separateChaining(10)
    hashTable.put(1, "Hello")
    hashTable.put(11, "World")
    hashTable.displayADict(1)
