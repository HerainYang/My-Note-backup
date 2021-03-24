class cuckoo(object):
    def __init__(self, size):
        self.size = size
        self.T1 = [None] * size
        self.T2 = [None] * size

    def h1(self, k):
        return k % self.size

    def h2(self, k):
        return (2*k + 1) % self.size

    def display(self):
        for i in range(self.size):
            print(self.T1[i], self.T2[i])

    def expand(self):
        self.size = self.size + 1
        newT1 = [None] * self.size
        newT2 = [None] * self.size
        for i in range(self.size - 1):
            newT1[i] = self.T1[i]
            newT2[i] = self.T2[i]
        self.T1 = newT1
        self.T2 = newT2

    def put(self, k):
        if self.T1[self.h1(k)] == k:
            return 0
        if self.T2[self.h2(k)] == k:
            return 0
        if self.T1[self.h1(k)] is None:
            self.T1[self.h1(k)] = k
            return 0
        if self.T2[self.h2(k)] is None:
            self.T2[self.h2(k)] = k
            return 0
        tryTime = 0
        while 1:
            print(k)

            if self.T1[self.h1(k)] is None:
                self.T1[self.h1(k)] = k
                return 0

            kickout = self.T1[self.h1(k)]
            self.T1[self.h1(k)] = k
            k = kickout

            if self.T2[self.h2(k)] is None:
                self.T2[self.h2(k)] = k
                return 0

            kickout = self.T2[self.h2(k)]
            self.T2[self.h2(k)] = k
            k = kickout

            tryTime+=1
            if tryTime > 3:
                print("expand")
                self.expand()
                tryTime = 0








if __name__ == '__main__':
    hashTable = cuckoo(5)
    hashTable.put(1)
    hashTable.put(11)
    hashTable.put(3)
    hashTable.put(5)
    hashTable.put(7)
    hashTable.put(9)
    hashTable.put(21)
    hashTable.display()