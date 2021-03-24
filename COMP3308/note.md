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
* For two nodes with the same path cost, the last added node have a higher priority.

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
* For two nodes with the estimated cost, the last added node have a higher priority.

Evaluation:

* Complete: 

  In finite space, Yes.

  In infinite spaces, No.

* Optimal: No.

* Time: $O(b^m)$, but a good heuristic can give dramatic improvement.

* Space: $O(b^m)$, keeps every node in memory.

---

# Week 3

## A* Algorithm

Evaluation function: $f(n)=g(n)+h(n)$

* $g(n)$ is the cost so far to reach n.
* $h(n)$ is the estimated cost from n to the goal.
* $f(n)$ is the estimated total cost of path through n to the goal.

When there are same priority nodes, A* will expand the last added first.

* BFS is a special case of A* when $f(n)=depth(n)$.
* BFS is also a special case of USC when $g(n)=depth(n)$.
* UCS is a special case of A* when $h(n)=0$.

---

## Admissible Heuristic

> For every node n it is true that $h(n)\leq h^*(n)$ where $h^*(n)$ is the true cost to reach a goal from n.

The admissible heuristics are always optimistic.

If $h$ is an admissible heuristic, then $A^*$ is complete and optimal.

---

## Dominant Heuristic

> Given 2 admissible heuristics $h1$ and $h2$.
>
> $h2$ dominates $h1$ if for all nodes $n$ $h_2(n)\geq h_2(n)$.

$A*$ using $h2$ will expand fewer nodes than $A*$ using $h1$.

Often we can't find a single heuristic that is clearly the best but we have a set of heuristics $h1, h2, h3, ...,hm$ but none of them dominates any of the others:

* $h(n)=max(h1(n),h2(n),h3(n),...,hm(n))$.

---

## Consistent (Monotonic) Heuristic

Consider a pair of nodes $n_i$ and $n_j$, where $n_i$ is the parent of $n_j$.

$h$ is consistent (monotonic) heuristic if for all such pairs in the search graph that (triangle equation):
$$
h(n_i)\leq cost(n_i, n_j)+h(n_j)
$$

* $f$ is **non-decreasing** along any path.

If h(n) is consistent, then $f(n_j)\geq f(n_i)$, vice versa.

<img src="/home/herain/Documents/MATH1002/Notepic/Screenshot from 2021-03-15 15-42-17.png" alt="Screenshot from 2021-03-15 15-42-17" style="zoom: 50%;" />

* If a heuristic is consistent, it is also admissible.
* If a heuristic is admissible, there is no guarantee that it is consistent.

If $h$ is a consistent heuristic, then A* is optimally efficient among all optimal search algorithms using h.

Evaluation:

Complete: Yes, unless there are infinitely many nodes with $f\leq f(G)$, $G$ is the optimal goal state.

Optimal: Yes.

Time: $O(b^d)$

Space: Exponential.

---

## Optimization Problems

* Each state has a value v.
* Find the optimal state with the highest or lowest v score.
* The path is not important.

### V-value Landscape

* Each state has a value v.
* This value is defined by a heuristic evaluation function.
* **Complete** local search: finds a goal state if one exists.
* **Optimal** local search: finds the best goal state.

---

## Hill-Climbing Algorithm

Idea: keep only a single state in memory, try to improve it.

At each state, only neighboring state is visible.

1. Set current node $n$ to the initial state $s$.
2. Generate the successors of $n$. Select the best successor $n_{best}$.
3. If successor is better, set $n_{best}$ and go to step 2. If successor is not better, return $n$.

Weaknesses:

* Not a very clever algorithm, can easily get stuck in a local optimum.
* But some local optimum may be reasonably good.

Advantages:

* Use very little memory.
* Finds reasonable solutions in large spaces.

---

## Beam Search

It keeps `k` best states.

* Starts with 1 state or $k$ randomly generated states.
* At each level, generate all successors of all given state.
* If any one is a goal state, stop, else select the $k$ best successors (no matter if it is better than parent or not) from the list and go to the next level.

---

### Beam Search with A*

Idea: keep only the best $k$ nodes in the fringe using a priority queue of size $k$.

Disadvantage: neither complete, nor optimal.

---

## Simulated Annealing

Similar to hill-climbing but **selects a random successor** instead of the best successor.

1. Set current node n to the initial state $s$.

2. If $v(m)$ is better than $v(n)$, n=m, accept the child.

   If v(m) is not better, accept the child m with **probability p**.

3. Go to step 2 until a predefined number of iterations is reached or the state reached is good enough.

For child m and parent n:
$$
p=e^{\frac{v(n)-v(m)}{T}}
$$
Main ideas:

* $p$ decreases exponentially with the badness of the child.
* Bad children are more likely to be allowed at the beginning than at the end.

* Parameter $T$ that decreases over time based on a schedule:
  * High T - bad moves are more likely to be allowed.
  * Low T - more unlikely.
  * T decreases with time and depends on the number of iterations completed.

---

## Genetic Algorithms

* Each state is called an **individual**, it is coded as a string.
* Each state `n` has a fitness score `f(n)`, the higher the value, the better the state.
* Goal: starting with $k$ randomly generated individuals, find the optimal state.
* Successors are produced by **selection, crossover and mutation**.
* At any time keep a fixed number of states (the population).

Main idea:

* Select some individuals for reproduction based on the fitness function.
  * The higher the fitness function, the higher the probability to be selected.
* Crossover: random selection of crossover point, crossing over the parents strings.
* Mutation: random change of bits.

Evaluation:

* Complete: No.
* Optimal: No.

---

# Week 4

## Game

### Characteristics

* Deterministic and chance:
  * If there is no chance element, it is deterministic.
* Perfect and imperfect information:
  * If there is no hidden information, each player can see the complete game, it is perfect.
* Zero-sum and non zero-sum:
  * If one player's win is the other player's loss, it is zero-sum.

---

### Game Playing as Search

* State: board configuration.
* Initial state: initial board configuration + who goes first.
* Terminal states: board configuration where the game is over.
* Operators: legal moves a player can make.
* Utility function (or payoff function): numeric value associated with **terminal states** representing the **game outcome**.
* Evaluation function: numeric value associated with **non-terminal states**, shows how good the state is, it gives the expected outcome of the game from the current position.
* Game tree: represents all possible game scenarios.

---

## Minimax Algorithm

### Idea

* Given: a 2-player, deterministic, perfect information game.
* Minimax gives the `perfect(best, optimal) move` for a player, assuming that its opponent plays optimally (always selects the best move based on the evaluation function), but if its opponent does not play optimally, it will do even better.
* Minimax computes a value for each non-terminal node, called a minimax value.
* It then chooses the move to the position with the best minimax value for the current player.

---

### Implement

1. Generate the game tree.
   * Create start node as a MAX node.
   * Expand nodes down to terminal states.
2. Evaluate the utility of the terminal states.
3. Starting at the leaf nodes and going back to the root of the tree, compute recursively the minimax-value of each node:
   * at MIN nodes, take the min of the children's values.
   * at MAX nodes, take the max of the children's values.
4. When the root node is reached, select the best move for MAX.

---

### Multiplayer Games

We can use a vector to represent the output of evaluation function, and each player choose the best move.

---

### Properties

* Implements as DFS.
* Optimal:
  * Optimal score is stand for the score of the terminal node that will be reached if both players players play optimally.
  * Yes, against an optimal opponent.
* Time complexity: $O(b^m)$.
* Space complexity: $O(bm)$.

### Problems

But minimax assumes that the program has time to search all the way to terminal states, which is not always possible.

---

## Alpha-Beta Pruning

### Idea

No need to examine every node, prune the nodes that do not influence the decision.

### Implement

* Alpha value: the best value for MAX so far along the path.
* Beta value: the best value for MIN so far along the path.
* If a Max node exceeds beta, prune the sub-tree below.
  * Child's beta $\leq$ parent's alpha.
* If a MIN node is smaller than alpha, prune the sub-tree below.
  * Child's alpha $\geq$ parent's beta.

### Properties

* Pruning does not affect the final result.
* Good move ordering improves effectiveness of pruning:
  * Worst case: no pruning, examine $b^d$ nodes.
  * Best case: examine only $b^{d/2}$ nodes.
* In deterministic games, the evaluation functions need not to be precise as long as the comparison of values is correct.

### Problems

Both minimax and alpha-beta require too many evaluations.

* Cut off the search earlies before reaching the terminal nodes.

---

## Cutting Off Search

### Idea

* Expand the tree only to a given depth.
* Apply heuristic evaluation function to the leaf nodes.
* Return values to the parents of the leaf nodes as in minimax and alpha-beta.

Different:

* Replace the terminal test by a cutoff test.
* Replace utility function by a heuristic evaluation function.

---

## More on Evaluation Functions

### Idea

Typical evaluation function:
$$
Eval(s)=w_1f_1(s)+w_2f_2(s)+w_3f_3(s)+...+w_nf_n(s)
$$

* $f_i$ is feature i.
* $w_i$ is weight of feature i.

Example in Chess:

$f$ can be the number of different pieces on the board.

$w$ can be the material value of the piece.

### Problems

The weight of some piece might be changed during the game:

* A pawn is more powerful when there are many other pawns on the board.
* Bishops are more powerful at the end of the game.

---

### Horizon Effect

Hidden pitfall not captured by the heuristic, there might be a significant change just outside the search window that we can't see.

**Quiescent**: A state that is unlikely to change extremely in near future.

To avoid horizon effect, the evaluation function should be applied **only to positions** which are quiescent. Secondary search extends the search for the selected move to make sure that there is no hidden pitfall.

---

### Other Refinements

* Iterative deepening search: start depth 1, 2, 3 and so on.

* Book moves: pre-computed sequences of moves for a particular board configuration.
* Heuristic pruning.
* Alternative to minimax: risking a bad move.

---

## Non-Deterministic Games

* Just like `MINIMAX`, except we must also handle chance nodes:
  * At terminal state nodes: utility function.
  * At `MIN` nodes: minimum of the children node values.
  * At `MAX` nodes: maximum of the children node values.
  * At `CHANCE` nodes: weight average of `EXPECTIMINIMAX`value resulting from all possible dice rolls.
* Time complexity: $O(b^mn^m)$, n being the number of distinct dice rolls.

---

## Learning Evaluation Functions

* Learner: neural network trained with the back propagation algorithm.
* Input: backgammon position.
* Output: value of the position.