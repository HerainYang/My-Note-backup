class node(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

def findMax(list):
    max = None
    for i in list:
        if max is None or i.y > max:
            max = i.y
    return max

def display(list):
    for i in list:
        print('[', i.x, i.y, ' ]', end=' ')
    print()

def maximaSet(array, begin, end):
    if begin < end:
        mid = int((begin + end)/2)
        LMS = maximaSet(array, begin, mid)
        RMS = maximaSet(array, mid+1, end)
        RMAX = findMax(RMS)
        display(LMS)
        display(RMS)
        print(RMAX)

        temp = []

        for i in LMS:
            if i.y > RMAX:
                temp.append(i)

        for i in RMS:
            temp.append(i)

        print("temp:", end='')
        display(temp)
        return temp
    return array[begin:end+1]

if __name__ == '__main__':
    a = node(1, 13)
    b = node(3, 3)
    c = node(4, 14)
    d = node(5, 9)
    e = node(9, 11)
    f = node(10, 5)
    g = node(12, 12)
    h = node(15, 7)
    i = node(16, 7)
    j = node(18, 6)
    k = node(21, 2)
    l = node(21, 4)
    list = []
    list.append(a)
    list.append(b)
    list.append(c)
    list.append(d)
    list.append(e)
    list.append(f)
    list.append(g)
    list.append(h)
    list.append(i)
    list.append(j)
    list.append(k)
    list.append(l)

    maximaSet(list, 0, len(list) - 1)
