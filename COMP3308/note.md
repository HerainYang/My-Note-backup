# Week 1

## What is AI?

* Think like humans or think rationally
* Act like humans or act rationally

Rationality: ideal performance given a performance measure.

---

### Acting Humanly

The Turing test approach:

* A machine is intelligent if it passes the test.
* Natural language processing, knowledge representation, automated reasoning and machine learning are need fro the Turing test.

Total Turing test:

* The computer needs:
  * Computer vision to perceive objects.
  * **Robotics to move them around**.

Reverse Turing test -- CAPTCHA

---

### Thinking Humanly

The cognitive modeling approach:

* Given a problem, it is not enough that it is solved correctly by the computer, the same reasoning steps as in humans should be followed
* Cognitive science: constructs and tests theories of how the human mind works using methods from psychology and computer models

---

### Thinking Rationally

The "Laws of Thought" Approach:

* Use logic to build intelligent systems.
  * Take a description of a problem in logical notation.
  * Find the solution using correct inference.
* Problems:
  * Take informal knowledge and represent it in formal notation is difficult.
  * This is especially difficult when knowledge is uncertain.
  * Limited time and memory.

---

### Acting Rationally

Intelligent agent:

* Has in-built knowledge and goals.
* Perceives the environment.
* It acts rationally (does the right thing) to achieve its goals by using its knowledge and the percept sequence from the environment

Problem:

* Impossible to always doing the right thing.
* Limited rationality.

---

## Foundations of AI

* Philosophy
* Mathematics
* Psychology
* Computer Engineering
* Linguistics

---

# Week 2

## Search Problem Formulation

A search problem is defined by 4 items:

* Initial state.
* Goal state, can be 1 or more:
  * Stated explicitly or implicitly.
* Operators (actions): A set of possible actions transforming 1 state to another.
* Path cost function: assigns a numeric value to each path.

A solution is a path from the initial to a goal state.

* Optimal solution is the one with **the lowest path cost**.

State space: all states reachable from the initial state by operators.

---

## Tree-Search algorithm

* Choose a node for expansion based on the search strategy.

* Check if it is a goal node:

  Yes -> return solution.

  No -> expand it by generating its children.

* Keep two lists:

  * Expanded: For nodes that have been expanded.
  * Fringe: For nodes that have been generated but not expanded yet.

### Nodes vs States

* A node is different than a state.
* A node:
  * represents a state.
  * is a data structure used in the search tree.
  * includes **parent, children, and other relevant information (depth and path cost g)**.

---

## Search Strategies

> Defines which node from the fringe is most promising and should be expanded next.

Evaluation criteria:

* Completeness: is it guaranteed to find a solution if one exists?
* Optimality: is it guaranteed to find an optimal (least cost path) solution?
* Time complexity: how long does it take?
* Space complexity: what is the maximum number of nodes in memory?

Time and space complexity are measured in terms of:

* b - max **branching** factor of the search tree (finite).
* d - depth of the optimal (least cost) solution.
* m - maximum depth of the state space (finite or not finite).

Two types of search methods:

* uninformed (blind)
* informed (heuristic)

## Uniformed Search Strategies

* Do **not** use problem-specific heuristic knowledge.
* Generate children in a systematic way.
* Know if a child node is a goal or non-goal node.
* Do not know if one non-goal child is better than another one (Except UCS).

### Breadth-First Search (BFS)

* Expands the shallowest unexpanded node.
* Implementation: insert children at the end of the fringe.

Evaluation: 

* Complete: Yes.

* Optimal: 

  step costs are the same, Yes

  step costs are different, No

* Time: generated $nodes = 1+b+b^2+b^3+...+b^d=O(b^d)$, **exponential**.

* Space: $O(b^d)$

---

### Uniform Cost Search (UCS)

* Considers the step cost. It expands the least-cost unexpanded node.
* Implementation: insert node in the fringe in order of **increasing path cost from the root**.

Evaluation:

* Complete: Yes.
* Optimal: Yes.
* Time: $O(b^{1+[C*/\epsilon]})$, typically > $O(b^d)$.
* Space: $O(b^{1+[C*/\epsilon]})$
  * $C^*$ - cost of optimal solution.
  * $\epsilon$ - the smallest step cost.

---

### Depth-First Search (DFS)

* Expands the deepest unexpanded node.
* Implementation: insert children at the front of the fringe.

Evaluation:

* Complete: 

  No if it is infinite-depth.

  Yes if it is.

* Optimal: No.

* Time: $nodes = 1+b+b^2+b^3+...+b^m=O(b^m)$, m is much higher than d.

* Space: $O(bm)$

---

### Depth-Limited Search

* DFS with depth limit l.

Evaluation:

* Complete: Yes.
* Optimal: No.
* Time: $nodes = 1+b+b^2+b^3+...+b^l=O(b^l)$.
* Space: $O(b^l).$

---

### Iterative Deepening Search (IDS)

* Sidesteps the issue of choosing the best depth limit by trying all possible depth limits in turn and applying DFS.
* The overhead is small.

Evaluation:

* Complete: Yes.

* Optimal:

   if step cost = 1, Yes

  otherwise, No

* Time: $(d+1)b^0+db^1+(d-1)b^2=O(b^d)$

* Space: $O(bd)$

---

## Informed Search Strategies

* Use problem-specific heuristic knowledge to select the order of node expansion.
* Can compare non-goal nodes.
* Are typically more efficient.

Best-first Search:

* Using domain specific knowledge to devise an evaluation function.
* The evaluation function assigns a value to each node.
* At each step, the best node is expanded.
* insert children in decreasing order of desirability.

---

### Greedy Search (GS)

* It uses the so called h value as an evaluation function.
* The h(n) for a node n is the estimated cost from n to a goal node.
* The h value of a goal node is 0.

Evaluation:

* Complete: 

  In finite space, Yes.

  In infinite spaces, No.

* Optimal: No.

* Time: $O(b^m)$, but a good heuristic can give dramatic improvement.

* Space: $O(b^m)$, keeps every node in memory.