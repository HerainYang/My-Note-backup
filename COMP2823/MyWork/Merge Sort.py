def merge(list, begin, mid, end):
    temp = []
    i = begin
    j = mid+1
    while i <= mid and j <=end:
        if(list[i] < list[j]):
            temp.append(list[i])
            i+=1
        else:
            temp.append(list[j])
            j+=1

    while i <= mid:
        temp.append(list[i])
        i+=1

    while j <= end:
        temp.append(list[j])
        j+=1

    for i in range(len(temp)):
        list[begin+i] = temp[i]

def mergeSort(list, begin, end):
    if begin < end:
        mid = int((begin + end) / 2)
        mergeSort(list, begin, mid)
        mergeSort(list, mid+1, end)
        merge(list, begin, mid, end)

if __name__ == '__main__':
    a = [3,2,6,1,8,7,4,-1,2,68,72,4,23,54,21]
    mergeSort(a, 0, len(a)-1)
    print(a)