class linearProbing(object):
    def __init__(self, size):
        self.size = size
        self.array = [None] * size
        self.state = [None] * size  # None means empty, 1 means DEFUNCT, 0 means something here

    def h(self, k):
        return k % self.size

    def get(self, k):
        start = index = self.h(k)
        recorded = False
        firstDEFUNCT = 0
        if self.array[index] == k:
            return index
        else:
            while self.state[index] is not None and self.array[index] != k:
                if not recorded and self.state[index] != 0:
                    print(k, "record", index)
                    recorded = True
                    firstDEFUNCT = index
                index = (index + 1) % self.size
                if index == start:
                    break
            if self.array[index] == k:
                return index

            if recorded is True:
                return firstDEFUNCT
            else:
                if self.state[index] is None:
                    return index
                else:
                    return -1

    def remove(self, k):
        index = self.get(k)
        if self.array[index] == k:
            self.state[index] = 1
        else:
            print("No such k in array")

    def put(self, k):
        index = self.get(k)
        if index == -1:
            print("hash table is full")
            return
        if self.array[index] == k:
            if self.state[index] == 1:
                self.state[index] = 0
        else:
            self.array[index] = k
            self.state[index] = 0

    def display(self):
        for i in range(self.size):
            print(self.array[i], end=' ')
        print()
        for i in range(self.size):
            print(self.state[i], end=' ')
        print()


if __name__ == '__main__':
    hashTable = linearProbing(5)
    hashTable.put(1)
    hashTable.put(6)
    hashTable.put(11)
    hashTable.put(16)
    hashTable.put(21)
    hashTable.remove(6)
    hashTable.remove(21)
    hashTable.put(2)

    hashTable.display()
