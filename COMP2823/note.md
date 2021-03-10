# Week 1

Computational problem

> Defines a computational task, specifies what the input is and what the output should be.

Algorithm

> A step-by-step recipe to go from input to out put, different from implementation.

Correctness and complexity analysis

> A formal proof that the algorithm solves the problem, analytical bound on the resources it uses.

---

## Efficiency

> An algorithm is efficient if it runs in polynomial time.
>
> On any instance of size $n$, it performs no more than $p(n)$ steps for some polynomial $p(x)=a_dx^d+...+a_1x+a_0$.

### $O$-notation

$T(n)=O(f(n))$ if there exists $n_0$, $c>0$ such that $T(n)\leq cf(n)$ for all $n>0$.

### $\Omega$-notation

$T(n)=\Omega (f(n))$ if there exists $n_0$, $c>0$ such that $T(n)\geq cf(n)$ for all $n>0$.

### $\Theta$-notation

$T(n)=\Omega(f(n))$ if $T(n)=O(f(n))$ and $T(n)=\Omega(f(n))$.



* If $T (n) = \Theta(p(n))$ where p is a polynomial of degree d, then doubling the size of the input should roughly increase the running time by a factor of $2^d$.



Example of asymptotic growth:

* Constant, $\Omega(1)$
  * Assignments, Comparisons, Boolean operations, Basic mathematical operations, Constant sized combinations of the above.

* Polynomial, $O(n^c)$.

  * Linear, $O(n)$

  * Quadratic, $O(n^2)$
  * Cubic, $O(n^3)$

* Logarithmic, $O(\log n)$

* Quasi-linear, $O(n\log n)$

* Exponential, $O(2^n)$



### Properties of asymptotic growth

Transitivity:

* If $f=O(g)$ and $g=O(h)$ then $f=O(h)$.
* If $f=\Omega(g)$ and $g=\Omega(h)$ then $f=\Omega(h)$.
* If $f=\Theta(g)$ and $g=\Theta(h)$ then $f=\Theta(h)$.

Sums of functions:

* If $f=O(h)$ and $g = O(h)$ then $f+g=O(h)$.
* If $f=\Omega(h)$ and $g = \Omega(h)$ then $f+g=\Omega(h)$.



### A note on style

For assessments:

* Describe you algorithm.
* Prove its correctness.
* Analyze its time complexity.

---

# Week 2

## ADT (Abstract Data Type)

* Type defined in terms of its data items and, associated operations, not its implementation.

In `python`:

* ADT is given as an abstract base class.
* It declares methods, without providing code.
* Can be inherited by a **data structure implementation**, provides code for all the required methods and has a constructor.

---

## Array-based List

Just a static array, nothing to mention.

---

## Positional Lists

Just a linked list, nothing to mention.

---

### Doubly Linked List

nothing to mention

---

### Performance

nothing to mention

---

### Iterators in `Python`

* `iter(obj)` returns an iterator of the object collection.
* implement `__inter__(self)` and return `self`.
* implement `__next__(self)` to define the behavior of iterator, raise `StopIteration` to tell the end of the iteration.

---

## Stack and Queue

* Stacks follow last-in-first-out (LIFO).
* Queues follows first-in-first-out (FIFO).

---

### Double-ended queue

Allows insertions and deletions at both ends

---

### Amortized analysis

A sequence of $n$ operation has $O(f(n))$ amortized time complexity if in the worst-case the total amount of work done by then n operations is no more than $O(nf(n))$

---

# Week 3

## Tree

> A tree is an abstract model of a hierarchical structure

* A tree consists of nodes with a parent-child relation.
* A node has at most one parent in a tree.
* A node can have zero, one or more children.

### About node

Root: node without parent.

Internal node: node with at least one child.

External/leaf node: node without children.

Ancestors: parent, grandparent, great-grandparent, etc.

Descendant: children, grandchildren, great-grandchildren, etc.

Siblings: two nodes with the same parent.

### About Tree

Depth of a node: number of ancestors **not including itself**.

Level: set of nodes **with given depth**.

Height of a tree: maximum depth, **counting from 0**.

Subtree: tree made up of some node and its descendants.

Edge: pair of nodes (u, v) such that one is the parent of the other.

Path: sequence of nodes such that 2 consecutive nodes in the sequence have an edge.

---

### Ordered Tree

> There is a prescribed order for each node's children.

---

### Traversing Tree

* pre-order
* post-order
* in-order (for binary trees)

---

### Binary Trees

> An ordered tree with:
>
> * Each internal node has at most two children.
> * Each child node is labeled as a left child or a right child.
> * Child ordering is left followed by right.
> * Tree is **proper** if every internal node has two children.

* Sometime the method may call itself on all children, the total cost is linear in the number of nodes.
* Sometime the method may call itself on at most one child, the total cost is linear in the height of the tree.

---

#### Binary Search Tree

> For any node, it's left child is always small than it, and it's right child is always bigger then it.

##### Complexity

* Space used: $O(n)$.
* Get, put, remove $O(h)$, h is hight, the best one can take $O(\log n)$

---

#### B-Trees

* Group tree into chunks of size B and layout each chunk in its own external memory block.
* Number of I/Os equal number of chunks we need to fetch.

For chunks has close to B nodes, so each chunk has height close to $\log _2 B$.

---

## Balanced BST

> BST with height $O(\log n)$ at all times.

### Rank-balanced Trees

> Keeping a **"rank"** for every node, where r(v) acts as a proxy measure of the size of the subtree rooted at v.

---

### AVL Tree definition

r(v) is its height of the subtree rooted at v.

The height of an AVL tree storing n keys is at most $O(\log n)$

* Balance constraint: The ranks of the two children of every internal node differ by at most 1.

---

#### Insertion

1. If k is in the tree, do nothing.
2. If k is not in the tree, insert it.
   * Some ancestors may have increased their height by 1.
3. Re-arrange tree to re-establish AVL property.

---

#### Removal

1. If k is not in the tree, do nothing.
2. If k is in the tree, remove it.
   * Some ancestors may have decreased their height by 1.
3. Re-arrange tree to re-establish AVL property.

---

#### Re-establish

There are only 4 types of unbalance state could appear in AVL tree.

![Draft -142](/home/herain/Documents/COMP2823/notepic/Draft -142.jpg)

### Evaluation

* Space: $O(n)$
* Height: $O(\log n)$
* Searching: $O(\log n)$
* Insertion: $O(\log n)$
* Removal: $O(\log n)$

## The Map

* List-Based (unsorted) Map
* Tree-Based (sorted) Map

---

# Week 4

## Priority Queue

> Special type of ADT map to store a collection of key-value items where we can only remove smallest key.

* Unsorted list implementation

  `insert` in $O(1)$

  `remove_min` and `min` in $O(n)$

* Sorted list implementation

  `insert` in $O(n)$

  `remove_min` and `min` in $O(1)$

## Selection-Sort and Insertion-Sort

nothing to mention.

## Heap data structure (min-heap)

A heap is a binary tree storing items at its nodes:

* Heap-Order: for every node $m\neq root$, $key(m)\geq key(parent(m))$
* Complete Binary Tree: let h be the height
  * Every level i < h is full.
  * Remaining nodes take leftmost positions of level h.

Fact:

* The root always holds the smallest key in the heap.
* A heap storing n keys has height log n.

Implement:

* Since heap is a complete binary tree, it is better to use an **array** to store it.

---

### Insertion into a Heap

1. Create a new node with given key.
2. Find location for new node.
3. Restore the heap-order property (bottom up).

* $O(\log n)$

---

### Removal

1. Replace the root key with the key of the last node
2. Delete the last one.
3. Restore the heap-order property (Top down).

* $O(\log n)$

---

### Heap sort

Using an array to store heap:

* Parent of i: $(i-1)/2$
* Left child of i: $i*2+1$
* Right child of i: $i*2+2$

Main idea:

1. Construct a heap:
   * Max heap for ascending order
   * Min heap for descending order

2. Exchange the value of the `array[0]` and `array[i]`
   * `i = size - 1` at the beginning.
   * Decrease by 1 each time.
3. Re-establish max heap.

---

#### Heap in array

* A heap on n keys can be constructed in $O(n)$ time.
* The n remove_min still take $O(n log n)$ time

---

#### Heapify

Building a priority queue into a max or min heap only take $O(n)$.

For dequeuing, just remove the minimum number in the heap.

---

# Week 5

## Hash

* A **hash function h** is used to map keys to corresponding indices in an array A.
  * h is a mathematical function.
  * h is fairly efficient to compute.
* A **hash table** for a given key type K consists of:
  * Hash function h: $K \rightarrow [0, N-1]$
  * Array of size $N$
  * Ideally, item (x, o) is stored at $A[h(x)]$

---

### Hash Functions

A hash function h is usually the composition of two functions:

* Hash code:

  Transform keys to integers.

* Compression function:

  Transform integers to indices.

---

#### Common Hash Codes

* view the key k as a tuple of integers $(x_1,x_2,...x_d)$ with each being an integer in the range $[0, M-1]$ for some M.
* view the key k as nonnegative integer.

---

#### Summing components

Used for keys $k=(x_1,x_2,...x_d)$:

* $h(k)=\sum _i x_i$.
* $h(k)=\sum _i x_i \ mod \ p$ where p is a prime.
* $h(k)=\oplus _i x_i mod p$
* $h(k) = x_1a^{d-1}+x_2a^{d-2}+...+x_{d-1}a+x_d$

---

#### Modular division

Used on keys k that are positive integers $h(k) = k \ mod \ N$

---

#### Universal hash functions

Let H be a family of hash functions $[0, M]\rightarrow[0,N-1]$.

$H$ is **2-universal** if picking h uniformly at random (UAR) from H yields that for any two keys i and j:
$$
Pr[h(i) = h(j)] <= 1/N
$$
Fact: Let $h$ be a function chosen UAR from a 2-universal family then the expected number of collision for a given key k in a set S of n keys is $n/N$.

---

#### Random Linear Hash Function

Used on keys k that are positive integers:
$$
h(k) = ((ak+b)mod \ p)mod \ N
$$
For some primer number p, and a and b are chosen UAR from [0, p-1] with $a\neq 0$.

---

### Collision Handling

* Separate chaining
* Linear probing
* Cuckoo hashing

---

#### Separate chaining

If two or more element hash into  the same location, put they into the list in that location.

---

#### Load Factor

Assume $n$ keys are mapped to $[0, N-1]$, then **load factor**:
$$
\alpha = \frac{n}{N}
$$
In Separate chaining, The expected time for hash table operations is $O(1+\alpha)$, but when all the items collide into a single chain, it will become $O(n)$.

* In Java, $\alpha < 0.75$
* In Python, $\alpha < 0.66$

---

#### Open addressing using Linear Probing

Open addressing: the colliding item is placed in a different cell of the table.

Linear probing: handles collisions by placing the colliding item in the next available cell.

##### Updates with Linear Probing

To handle insertions and deletions, use `DEFUNCT` to replaces deleted elements. If target element is deleted directly, `get()` function might be interrupted.

* `get()`: Pass over cells with `DEFUNCT` and keep probing until the element is found or reaching an empty cell.
* `remove()`: If element is found, replace it with the special item DEFUNCT.
* `put()`: If element is found, update it. If element is not found, insert element into the first cell we find that has `DEFUNCT` or empty.

Evaluation:

* In the worst case, all operation take $O(n)$ time.
* Expected number of probes for each get and put is $\frac{1}{1-\alpha}$

---

#### Cuckoo hashing

<img src="/home/herain/Documents/COMP2823/notepic/Screenshot from 2021-03-10 15-06-09.png" alt="Screenshot from 2021-03-10 15-06-09" style="zoom:50%;" />

* Use two lookup, $T_1$ and $T_2$, each of size N.
* Use two hash functions for two lookup respectively.
* For key k, there are two possible places to store.
* The worst-case expected $O(1)$ for put and remove.

