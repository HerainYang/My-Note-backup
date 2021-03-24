def add_pq(pq, k):
    pq.append(k)
    pq.sort()
if __name__ == '__main__':
    start = [9,9,9,11,11,13,13,14,15,15]
    end = [10.5, 12.30, 10.30, 12.5, 14, 14.5, 14.5, 16.5, 16.5, 16.5]
    pq = []
    d = 0
    for i in range(len(start)):
        j = i - 1
        k = 0
        add_pq(pq, end[i])

        while len(pq) > 0 and pq[0] <= start[i]:
            pq.pop()

        k = len(pq)

        if d < k:
            d += 1
    print(d)