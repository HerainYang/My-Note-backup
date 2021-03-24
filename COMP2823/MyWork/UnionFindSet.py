class Node(object):
    def __init__(self, value):
        self.father = self
        self.size = 1
        self.value = value

class UnionFindSet(object):
    def find(self, node):
        father = node.father
        if node != father:
            if father != father.father:
                father.size -= node.size # might have problem here
            father = self.find(father)
        node.father = father
        return father

    def is_same_set(self, node_a, node_b):
        return self.find(node_a) == self.find(node_b)

    def union(self, node_a, node_b):
        if node_a is None and node_b is None:
            return

        a_head = self.find(node_a)
        b_head = self.find(node_b)

        if a_head != b_head:
            if a_head.size <= b_head.size:
                b_head.father = a_head
                b_head.size += a_head.size
            else:
                a_head.father = b_head
                a_head.size += b_head.size

if __name__ == '__main__':
    set = UnionFindSet()
    NodeA = Node('A')
    NodeB = Node('B')
    NodeC = Node('C')
    NodeD = Node('D')
    NodeE = Node('E')
    set.union(NodeA, NodeB)
    set.union(NodeA, NodeC)
    print(set.find(NodeC).value)