def selectionSort(array):
    n = len(array)
    for i in range(n):
        s = array[i]
        s_i = i
        for j in range(i+1, n):
            if array[j] < s:
                s = array[j]
                s_i = j
        array[s_i] = array[i]
        array[i] = s
        print(array[i])

def insertionSort(array):
    n = len(array)
    for i in range(n):
        j = i
        val = array[i]
        while j > 0 and val < array[j - 1]:
            array[j] = array[j-1]
            j = j - 1
        array[j] = val
    for i in range(n):
        print(array[i])

if __name__ == '__main__':
    array = [2, 1, 3, 7, 5]
    #selectionSort(array)
    insertionSort(array)