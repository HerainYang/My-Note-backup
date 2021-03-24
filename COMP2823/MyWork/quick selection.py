def findkth(array, begin, end, k):
    pivot = array[begin]
    i = begin
    j = end
    while i < j:
        while i < j and array[j] > pivot:
            j -= 1
        if i < j:
            array[i] = array[j]
            i += 1
        while i < j and array[i] < pivot:
            i += 1
        if i < j:
            array[j] = array[i]
            j -= 1
    array[i] = pivot
    index = i
    if index == k:
        return array[index]
    if index < k:
        return findkth(array, index + 1, end, k)
    else:
        return findkth(array, begin, index - 1, k)


pai = [2, 3, 1, 5, 4, 6, 6, 3, 4, 2, 4]
# quicksort(pai, 0, len(pai) - 1)

print(findkth(pai, 0, len(pai) - 1, 7))
