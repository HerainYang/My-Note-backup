def quickSort(array, begin, end):
    if begin < end:
        pivot = array[begin]
        i = begin
        j = end
        while i < j:
            while i < j and array[j] > pivot:
                j-=1
            if i < j:
                array[i] = array[j]
                i+=1
            while i < j and array[i] < pivot:
                i+=1
            if i < j:
                array[j] = array[i]
                j-=1
        array[i] = pivot
        quickSort(array, begin, i-1)
        quickSort(array, i+1, end)

if __name__ == '__main__':
    a = [2, 3, 1, 5, 4, 6, 6, 3, 4, 2, 4]
    quickSort(a, 0, len(a)-1)
    print(a)
