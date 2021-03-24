import random
class Node(object):
    def __init__(self, value):
        self.value = value
        self.above = None
        self.below = None
        self.before = None
        self.after = None

class SkipList(object):
    def __init__(self, level):
        self.left = [Node(-100) for n in range(level)]
        self.right = [Node(100) for n in range(level)]
        self.topleft = self.left[level-1]
        self.level = level
        for i in range(len(self.left)):
            self.left[i].after = self.right[i]
            self.right[i].before = self.left[i]

            if i != 0:
                self.left[i].below = self.left[i - 1]
                self.right[i].below = self.right[i - 1]

            if i != level - 1:
                self.left[i].above = self.left[i + 1]
                self.right[i].above = self.right[i + 1]

    def search(self, k):
        p = self.topleft
        while p.below is not None:
            p = p.below
            while p.after.value <= k:
                p = p.after
        return p

    def insertAfterAbove(self, p, q, k):
        newNode = Node(k)
        p_after = p.after
        p.after = newNode
        newNode.after = p_after

        p_after.before = newNode
        newNode.before = p


        if q is not None:
            q.above = newNode
            newNode.below = q

        return newNode

    def expandLevel(self):
        print("expand")
        newLeftNode = Node(-100)
        newRightNode = Node(100)
        lastLeftNode = self.left[-1]
        lastRightNode = self.right[-1]
        newLeftNode.after = newRightNode
        newRightNode.before = newLeftNode
        newLeftNode.below = lastLeftNode
        newRightNode.below = lastRightNode
        lastLeftNode.above = newLeftNode
        lastRightNode.above = newRightNode
        self.left.append(newLeftNode)
        self.right.append(newRightNode)

    def insert(self, k):

        p = self.search(k)

        q = self.insertAfterAbove(p, None, k)

        while random.random() < 0.5:
            while p.above is None:
                if p.value == -100:
                    self.expandLevel()
                    break
                p = p.before
                if p is None:
                    return

            p = p.above

            q = self.insertAfterAbove(p, q, k)

    def removal(self, k):
        p = self.search(k)
        if p.value != k:
            return
        while p is not None:
            print(p.before is None)
            p_above = p.above
            p.before.after = p.after
            p.after.before = p.before
            p.below = None
            p.above = None
            p = p_above


    def display(self):
        cursor = self.left[-1]
        while cursor is not None:
            linecursor = cursor
            while linecursor is not None:
                print(linecursor.value, end=' ')
                linecursor = linecursor.after
            cursor = cursor.below
            print()
if __name__ == '__main__':
    skiplist = SkipList(1)

    skiplist.display()
    print()
    skiplist.insert(1)
    skiplist.insert(2)
    skiplist.insert(3)
    skiplist.insert(4)
    skiplist.insert(5)
    skiplist.display()
    print()


