import sys


class Node(object):
    def __init__(self, val):
        self.edgesArray = []
        self.val = val
        self.index = None

class Edge(object):
    def __init__(self, name):
        self.head = None
        self.tail = None
        self.name = name
        self.index = None
        # head is also the destination
        # tail is also the source

class Graph(object):
    def __init__(self):
        self.verticesArray = []
        self.edgesArray = []
        self.visit = []

    def numVertices(self):
        return len(self.verticesArray)

    def vertices(self):
        result = []
        for i in range(self.numVertices()):
            result.append(self.verticesArray[i])
        return result

    def numEdges(self):
        return len(self.edgesArray)

    def edges(self):
        result = []
        for i in range(self.numEdges()):
            result.append(self.edgesArray[i])
        return result

    def getEdge(self, u, v):
        for i in range(len(u.edgesArray)):
            if u.edgesArray[i].head == v and u.edgesArray[i].tail == u:
                return u.edgesArray[i]
        return None

    def endVertices(self, e):
        result = []
        if e is not None:
            result.append(e.tail)
            result.append(e.head)
        return result

    def opposite(self, v, e):
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i] == e:
                if v.edgesArray[i].head == v:
                    return v.edgesArray[i].tail
                if v.edgesArray[i].tail == v:
                    return v.edgesArray[i].head
                print("e is not a incident to v")
                break
        return None

    def outDegree(self, v):
        counter = 0
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i].tail == v:
                counter += 1
        return counter

    def inDegree(self, v):
        counter = 0
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i].head == v:
                counter += 1
        return counter

    def outgoingEdges(self, v):
        result = []
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i].tail == v:
                result.append(v.edgesArray[i])
        return result

    def incomingEdges(self, v):
        result = []
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i].head == v:
                result.append(v.edgesArray[i])
        return result

    def insertVertex(self, x):
        newNode = Node(x)
        self.verticesArray.append(newNode)
        return newNode

    def insertEdge(self, u, v, x):
        if self.getEdge(u, v) is not None:
            return None
        newEdge = Edge(x)
        newEdge.tail = u
        newEdge.head = v

        u.edgesArray.append(newEdge)
        if u != v:
            v.edgesArray.append(newEdge)

        self.edgesArray.append(newEdge)
        return newEdge

    def removeVertex(self, v):
        for i in range(len(v.edgesArray)):
            if v.edgesArray[i].tail == v:
                v.edgesArray[i].head.edgeArray.remove(v.edgesArray[i])
            else:
                v.edgesArray[i].tail.edgeArray.remove(v.edgesArray[i])
            self.edgesArray.remove(v.edgesArray[i])
        self.verticesArray.remove(v)
        v = None

    def removeEdge(self, e):
        e.head.edgesArray.remove(e)
        e.tail.edgesArray.remove(e)
        self.edgesArray.remove(e)
        e = None

def DFS(G):
    G.visit = [False] * G.numVertices()

    for u in G.vertices():
        u.index = G.verticesArray.index(u)

    for u in G.vertices():
        if not G.visit[u.index]:
            DFS_visit(G, u)

def DFS_visit(G, u):
    G.visit[u.index] = True

    print(u.val)

    for e in u.edgesArray:
        v = G.opposite(u, e)
        if not G.visit[v.index]:
            print("from", u.val, "to", v.val)
            DFS_visit(G, v)

def BFS(G):
    G.visit = [False] * G.numVertices()

    for u in G.vertices():
        u.index = G.verticesArray.index(u)

    if G.verticesArray[0] is None:
        return
    nextList = [G.verticesArray[0]]

    while len(nextList) != 0:
        todo = nextList.pop(0)
        if not G.visit[todo.index]:
            print("BFS", todo.val)
        G.visit[todo.index] = True
        for e in todo.edgesArray:
            oppositeVertex = G.opposite(todo, e)
            if not G.visit[oppositeVertex.index]:
                nextList.append(oppositeVertex)

def topo(G):
    degreeZeroQueue = []
    for v in G.verticesArray:
        if G.inDegree(v) == 0:
            degreeZeroQueue.append(v)

    count = 0
    while len(degreeZeroQueue) != 0:
        v = degreeZeroQueue.pop(0)
        print(v.val)
        for e in v.edgesArray:
            u = G.opposite(v, e)
            u.edgesArray.remove(e)
            if G.inDegree(u) == 0:
                degreeZeroQueue.append(u)

    if count < G.numVertices():
        return False
    return True

def remove_min(list, distance):
    smallest = None
    smallestIndex = None
    for i in range(len(list)):
        if smallest is None or distance[list[i].index] < distance[smallest.index]:
            smallestIndex = i
            smallest = list[i]
    return smallestIndex

def Dijkstra(G, w, s):
    for u in G.vertices():
        u.index = G.verticesArray.index(u)
    for e in G.edges():
        e.index = G.edgesArray.index(e)
    distance = [None] * G.numVertices()
    todoList = []
    todoList.append(w)
    distance[w.index] = 0
    while len(todoList) != 0:
        todo = todoList.pop(remove_min(todoList, distance))
        for e in todo.edgesArray:
            if e.tail is todo:
                head = e.head
                if distance[head.index] is None or distance[head.index] > distance[todo.index] + s[e.index]:
                    distance[head.index] = distance[todo.index] + s[e.index]
                    todoList.append(head)
    return distance

def addEdgeToPQ(G, u, pq, known):
    known.append(u)
    for e in u.edgesArray:
        if not G.opposite(u, e) in known:
            print(e.tail.val, "to", e.head.val, "is added")
            pq.append(e)


    i = 0
    while i < len(pq):
        if pq[i].head in known and pq[i].tail in known:
            pq.remove(pq[i])
        else:
            i+=1

def remove_min_prim(pq, c):
    smallest = None
    smallestIndex = None
    for i in range(len(pq)):
        if smallest is None or c[pq[i].index] < c[smallest.index]:
            smallestIndex = i
            smallest = pq[i]
    return smallestIndex

def prim(G, c):
    for u in G.vertices():
        u.index = G.verticesArray.index(u)
    for e in G.edges():
        e.index = G.edgesArray.index(e)
    result = []
    known = []
    pq = []
    u = G.verticesArray[0]
    addEdgeToPQ(G, u, pq, known)
    while len(pq) != 0:
        minEdge = pq.pop(remove_min_prim(pq, c))
        if minEdge.head in known:
            u = minEdge.head
        else:
            u = minEdge.tail
        v = G.opposite(u, minEdge)
        addEdgeToPQ(G, v, pq, known)
        result.append(minEdge)
    return result

def sortEdge(c):
    tempList = c.copy()
    result = []
    for i in range(len(tempList)):
        smallest = None
        smallIndex = None
        for j in range(len(tempList)):
            if (smallest is None) or (tempList[j] is not None and tempList[j] < smallest):
                smallest = tempList[j]
                smallIndex = j
        tempList[smallIndex] = None
        result.append(smallIndex)
    return result


def kruskal(G, c):
    for u in G.vertices():
        u.index = G.verticesArray.index(u)
    for e in G.edges():
        e.index = G.edgesArray.index(e)
    sortList = sortEdge(c)
    result = []
    i = 0
    simpleUnionFind = [n for n in range(len(G.verticesArray))]
    i = 0
    while i < len(sortList):
        checkEdge = G.edgesArray[sortList[i]]
        u = checkEdge.tail
        v = checkEdge.head
        print("checking edge from", u.val, "to", v.val)
        if simpleUnionFind[u.index] != simpleUnionFind[v.index]:
            print(u.val, "merge with:", end=' ')
            beMergedSet = simpleUnionFind[v.index]
            for j in range(len(G.verticesArray)):
                if simpleUnionFind[j] == beMergedSet:
                    simpleUnionFind[j] = simpleUnionFind[u.index]
                    print(G.verticesArray[j].val, end=' ')
            print()
            result.append(checkEdge)
        else:
            print("Circle")

        i += 1


    return result


if __name__ == '__main__':
    newGraph = Graph()
    A = newGraph.insertVertex('A')
    B = newGraph.insertVertex('B')
    C = newGraph.insertVertex('C')
    D = newGraph.insertVertex('D')
    E = newGraph.insertVertex('E')
    F = newGraph.insertVertex('F')
    G = newGraph.insertVertex('G')
    H = newGraph.insertVertex('H')

    newGraph.insertEdge(A, B, '')
    newGraph.insertEdge(A, F, '')
    newGraph.insertEdge(A, H, '')
    newGraph.insertEdge(B, F, '')
    newGraph.insertEdge(B, C, '')
    newGraph.insertEdge(B, E, '')
    newGraph.insertEdge(B, H, '')
    newGraph.insertEdge(C, D, '')
    newGraph.insertEdge(C, F, '')
    newGraph.insertEdge(D, E, '')
    newGraph.insertEdge(D, F, '')
    newGraph.insertEdge(D, G, '')
    newGraph.insertEdge(E, G, '')
    newGraph.insertEdge(E, F, '')
    newGraph.insertEdge(G, H, '')

    lenList = [8, 10, 4, 7, 4, 10, 9, 3, 3, 25, 18, 2, 7, 2, 3]

    # for i in newGraph.edges():
    #     print(i.name, "from", i.tail.val, "to", i.head.val)

    # result = prim(newGraph, lenList)
    # for i in result:
    #     print(i.name, "from", i.tail.val, "to", i.head.val)

    result = kruskal(newGraph, lenList)
    for i in result:
        print(i.name, "from", i.tail.val, "to", i.head.val)
    # result = Dijkstra(newGraph, s, lenList)
    # for i in result:
    #     print(i)

    #
    # for i in newGraph.vertices():
    #     print(i.val)
    #
    # for i in newGraph.edges():
    #     print(i.name, "from", i.tail.val, "to", i.head.val)
    #
    # print(newGraph.verticesArray[0].val)


