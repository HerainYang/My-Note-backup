def leftChild(index):
    return index * 2 + 1

def rightChild(index):
    return index * 2 + 2

def parent(index):
    return int((index - 1)/2)

def swap(heap, index1, index2):
    temp = heap[index1]
    heap[index1] = heap[index2]
    heap[index2] = temp

def upheap(array, index):
    while index > 0:
        if array[index] < array[parent(index)]:
            print(array[index], "is smaller than parent", array[parent(index)])
            swap(array, index, parent(index))
            upheap(array, int(parent(index)))
        else:
            break

def insert(heap, key):
    if heap.size == 100:
        return
    lastIndex = heap.size
    heap.array[lastIndex] = key
    heap.size+=1
    upheap(heap.array, lastIndex)

def downheap(array, index, size):
    while index < size:
        if array[leftChild(index)] is not None and array[index] > array[leftChild(index)]:
            print(array[index], "is bigger than lc", array[leftChild(index)])
            swap(array, index, leftChild(index))
            downheap(array, leftChild(index), size)
        elif array[rightChild(index)] is not None and array[index] > array[rightChild(index)]:
            print(array[index], "is bigger than rc", array[rightChild(index)])
            swap(array, index, rightChild(index))
            downheap(array, rightChild(index), size)
        else:
            break

def removeMin(heap):
    if heap.size == 0:
        return
    if heap.size == 1:
        heap.size -= 1
        return
    heap.size -= 1
    last = heap.array[heap.size]
    heap.array[0] = last
    downheap(heap.array, 0, heap.size)


class heap(object):
    def __init__(self):
        self.array = [None] * 100
        self.size = 0

if __name__ == '__main__':
    newHeap = heap()
    insert(newHeap, 4)
    insert(newHeap, 5)
    insert(newHeap, 6)
    insert(newHeap, 15)
    insert(newHeap, 9)
    insert(newHeap, 7)
    insert(newHeap, 20)
    insert(newHeap, 16)
    insert(newHeap, 25)
    insert(newHeap, 14)
    insert(newHeap, 12)
    insert(newHeap, 11)
    insert(newHeap, 13)

    print("last is", newHeap.array[newHeap.size - 1])

    removeMin(newHeap)

    for i in range(newHeap.size):
        print(newHeap.array[i])
