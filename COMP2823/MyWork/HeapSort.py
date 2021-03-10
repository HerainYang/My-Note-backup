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
        if array[index] > array[parent(index)]:
            print(array[index], "is bigger than parent", array[parent(index)])
            swap(array, index, parent(index))
            upheap(array, int(parent(index)))
        else:
            break

def downheap(array, index, size):
    while index < size:
        if leftChild(index) < size and rightChild(index) < size:
            print("inside")
            if array[leftChild(index)] > array[index] and array[rightChild(index)] > array[index]:
                if array[leftChild(index)] < array[rightChild(index)]:
                    swap(array, index, rightChild(index))
                    downheap(array, rightChild(index), size)
                else:
                    swap(array, index, leftChild(index))
                    downheap(array, leftChild(index), size)
            elif array[leftChild(index)] > array[index]:
                swap(array, index, leftChild(index))
                downheap(array, leftChild(index), size)
            elif array[index] and array[rightChild(index)] > array[index]:
                swap(array, index, rightChild(index))
                downheap(array, rightChild(index), size)
            else:
                break
        elif leftChild(index) < size and array[index] > array[leftChild(index)]:
            print(array[index], "is bigger than lc", array[leftChild(index)])
            swap(array, index, leftChild(index))
            downheap(array, leftChild(index), size)
        elif rightChild(index) < size and array[index] > array[rightChild(index)]:
            print(array[index], "is bigger than rc", array[rightChild(index)])
            swap(array, index, rightChild(index))
            downheap(array, rightChild(index), size)
        else:
            break

def fasterDownheap(array, index, size):
    i = index
    temp = array[i]
    k = leftChild(i)
    while k < size:
        if k + 1 < size and array[k] < array[k+1]:
            k += 1

        if array[k] > temp:
            array[i] = array[k]
            i = k
        else:
            break
        k = leftChild(k)
    array[i] = temp


def constructMaxHeap(array):
    last = len(array) - 1
    for i in range(last, -1, -1):
        upheap(array, i)

def heapSort(array):
    last = len(array) - 1
    for i in range(last, -1, -1):
        swap(array, i, 0)
        fasterDownheap(array, 0, i)
        #downheap(array, 0, i)

if __name__ == '__main__':
    array = [57, 40, 38, 11, 13, 34, 48, 75, 6, 19, 9, 7]
    constructMaxHeap(array)
    heapSort(array)
    for i in range(len(array)):
        print(array[i])