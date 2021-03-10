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

def upheapIter(array, index):
    i = index
    k = parent(index)
    temp = array[i]
    while(k > 0):
        if array[i] < array[k]:
            array[i] = array[k]
            i = k
        else:
            break
        k = parent(k)
    array[i] = temp

def insert(heap, key):
    if heap.size == 100:
        return
    lastIndex = heap.size
    heap.array[lastIndex] = key
    heap.size+=1
    upheap(heap.array, lastIndex)

def downheapRec(array, index, size):
    while index < size:
        if leftChild(index) < size and array[index] > array[leftChild(index)]:
            print(array[index], "is bigger than lc", array[leftChild(index)])
            swap(array, index, leftChild(index))
            downheapRec(array, leftChild(index), size)
        elif rightChild(index) < size and array[index] > array[rightChild(index)]:
            print(array[index], "is bigger than rc", array[rightChild(index)])
            swap(array, index, rightChild(index))
            downheapRec(array, rightChild(index), size)
        else:
            break

def downheapIter(array, index, size):
    i = index
    temp = array[i]
    k = leftChild(i)
    while k < size:
        if array[k] < temp:
            array[i] = array[k]
            i = k
        elif k + 1 < size and array[k] < temp:
            k+=1
            array[i] = array[k]
            i = k
        else:
            break
        k = leftChild(k)
    array[i] = temp


def removeMin(heap):
    if heap.size == 0:
        return
    if heap.size == 1:
        heap.size -= 1
        return
    heap.size -= 1
    last = heap.array[heap.size]
    heap.array[0] = last
    downheapRec(heap.array, 0, heap.size)


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

    # array = [1,3,5,7,9,2,4,6,8,10]
    # n = len(array)
    # for i in range(n-1, 0, -1):
    #     downheapIter(array, i, n)
    # for i in range(n):
    #     print(array[i])
