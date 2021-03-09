

# Compiler

## 1. Overview of Compilation

### Introduction of Compiler

> The compiler has a front end to **deal with the source language.** A back end to **deal with the target language.** A intermediate form to **connect the front end and the back end.**

Compiler: It takes as input a **source program** and produces as its output an **equivalent program.**

Interpreter: It takes as input a **source program** and produces as its output a **results.**

Just-in-time compiler: Compiler that executes at runtime.

---

### The Fundamental Principles of Compilation

* The compiler must preserve the meaning of the program being compiled.
* The compiler must improve the input program in some discernible way.

---

### Compiler structure

![Screenshot from 2020-12-22 12-06-34](/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-22 12-13-32.png)

* The front end focuses on understanding the **source-language program**, ensuring that the source program is well formed.
* The back end focuses on mapping programs to the **target machine**, assuming that the IR contains no syntactic or semantic errors.
* The intermediate representation (IR) is the **compiler's definitive representation** for the code it is translating, might using several different IRs.
* The optimizer (not necessary) is an IR-to-IR transformer that tries to **improve the IR program** by analyzing the IR, rewriting the IR.

---

### Overview of Translation

#### The Front End

##### Checking Syntax

The compiler compare the program's structure against a definition for the language.

The source language is a **infinite set**, of strings defined by some **finite set** of rules, called **grammar**. **Scanner** and **parser** determine the validity.

* Scanner converts a string into a stream of words.
* Parser determines if the input stream is a sentence in the source language.

1. **Identify and classify** distinct words in the input program.

2. **Match** the stream against the rules.

   ![Screenshot from 2020-12-22 14-12-43](/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-22 14-12-43.png)

   Discover the **derivation** based on the rules.

##### Type Checking

Check for type-consistent uses of names in the input program.

##### Intermediate Representations

Final issue handled in the front end, generation of an IR.

---

#### The Optimizer

Rewrite the code and make it computation more efficient.

* Size
* Time
* Energy

Optimizations consist of an analysis and a transformation.

##### Analysis

1. **Data-flow analysis** solve a system of simultaneous set equations.
2. **Dependence analysis** uses number-theoretic tests to reason about the values that can be assumed by subscript expressions.

##### Transformation

---

#### The Back End

Traverses the IR form of the code and emits code for the target machine.

* Select target-machine operations to implement each IR operation.
* Choose an order in which the operations will execute efficiently.
* Decide values will reside in register or memory and inserts code to enforce those decisions.

> ILOC, intermediate language for an optimizing compiler.
>
> Assembly language for a simple RISC machine.

##### Instruction Selection

Map each IR operation into one or more target **machine operations.**

Compiler deliberately ignored that the register's number is limited, using virtual registers.

##### Register Allocation

Map virtual registers onto actual target-machine registers.

##### Instruction Scheduling

Reorder the code to produce code that executes quickly.

---



## 2. Scanner

> Is a lexical analyzer, reads a stream of characters and produces a stream of words.
>
> * Input: Consists of characters.
> * Output: Stream that contains words, labeled with its syntactic category.

* Microsyntax: specifies how to **group characters** into words and how to **separate words** that run together.

* Keyword: reserved for a particular syntactic purpose, **cannot** be used as an identifier, form their own syntactic categories.

---

### Recognizing words

#### A Formalism for Recognizers

Finite automaton (FA)
$$
(S,\Sigma,\delta,s_0,S_A)
$$

* $S$ is the finite set of **states**, along with an error state $s_e$
* $\Sigma$ is the finite **alphabet**, edge labels in the transition diagram.
* $\delta (s,c)$ is the **transition function**, $s \in S$ and $c\in \Sigma$.
* $s_0\in S$ is the **designated start state.**
* $S_A\in S$ is the set of **accepting states**, appears as a double circle in the transition diagram.

Complete FA: an FA that explicitly includes all error transitions.

#### Regular Expressions

The set of words accepted by a finite automaton, denoted $L(F)$, can be describe using a notation called a regular expression (RE).

An RE is built up from three basic operations:

1. **Alternation**, $R|S$ is $\{x|x\in R \ or \ x\in S\}$.
2. **Concatenation**, RS is $\{xy|x\in R \ and \ y\in S\}$.
3. **Closure**
   * The Kleene closure of a set R denoted $R^*$, is $\cup^\infin _{i=0} R^i$, zero or more times R.
   * Finite closure $R^3=(R|RR|RRR)$.
   * Positive closure $R^+=RR^*$.

Others:

* **Empty String**, $\epsilon$
* **Complement operator**, $\text{^c}$ specifies the set $\{\Sigma-c\}$

---

### From Regular Expression to Scanner

#### Nondeterministic Finite Automaton (NFAs)

An FA that allows transitions on the **empty string**, and states that have **multiple transitions** on the same character.

When making a nondeterministic choice:

1. Follows the transition that leads to an accepting state, using an omniscient NFA.

2. Clones itself to pursue each possible transition.

   > **Configuration**:
   >
   > In method 2, the set of concurrently active states of an NFA.
   >
   > **Valid configuration**:
   >
   > Configuration of an NFA that can be reached by some input string.

#### Deterministic Finite Automaton (DFAs)

An FA where the transition function is single-valued, do not allow empty transitions.

#### Equivalence of NFAs and DFAs

Any DFA is a special case of an NFA, any NFA can be  simulated by a DFA.

An NFA with n states produces at most $|\Sigma|^n$ configurations.

---

#### Thompson's Construction (RE to NFA)

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 14-19-08.png" alt="Screenshot from 2020-12-23 14-19-08" style="zoom:33%;" />

* One start state and one accepting state.
* No transition enters the start state and no transition leaves the accepting state.
* An **empty transition** always connects two states.
* Each state has at most **two entering and two exiting empty moves**, and at most one entering and **one exiting move on a symbol**.

---

#### The Subset Construction (NFA to DFA)

* Input: NFA $(N,\Sigma,\delta_N,n_0,N_A)$
* Output: DFA $(D,\Sigma,\delta_D,d_0,D_A)$

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 15-34-27.png" alt="Screenshot from 2020-12-23 15-34-27" style="zoom: 33%;" />

Every $d_i \in D$ and $q_i\in Q$ represents a set of states $n_i \in N$.

$T[q,c]$ means recording the transition $t$ in table, row $q$, column $c$.

Q can be as large as $2^N$, 2 means contain or not contain.

Example: $a(b|c)^*$

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 15-44-48.png" alt="Screenshot from 2020-12-23 15-44-48" style="zoom: 33%;" />

---

#### Computing Empty Closure Offline

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 16-42-15.png" alt="Screenshot from 2020-12-23 16-42-15" style="zoom: 50%;" />

1. Create a set $E(n)$ for each node and put every node into worklist.
2. For every node in worklist, take it out, find all empty transitions that leave $n$, add their empty transitions together, if $E(n)$ is changed, add its predecessor back to the worklist.

---

#### Hopcroft's Algorithm (DFA to Minimal DFA)

If two states produce the same behavior on any input, they are **equivalent**.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 18-29-12.png" alt="Screenshot from 2020-12-23 18-29-12" style="zoom: 50%;" />

1. Divide states into two partition, one for accepting states, one for others.
2. Refine the initial partition, each partition contains states that are equivalent.

Example: $fee|fie$

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-23 18-59-51.png" alt="Screenshot from 2020-12-23 18-59-51" style="zoom: 50%;" />

---

#### Using a DFA as a Recognizer

* If $s$ is an accepting state, report the word and its syntactic category.
* If $s$ is not an accepting state:
  * If the DFA passed through one or more accepting states,  back up to the most recent such state.
  * If the DFA never reached an accepting state, report an error.

---

### Implementing Scanners

#### Table-Driven Scanners

The table-driven approach uses a skeleton scanner for control and a set of generated tables that encoded language-specific knowledge.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-24 15-42-59.png" alt="Screenshot from 2020-12-24 15-42-59" style="zoom:50%;" />

1. Initialization routine must be called before `NextWord()` is invoked.
2. `CharCat[]`classifies `char` into one of a small set of categories.
3. The first `while` read the input stream until it reaches an error state or it is an dead-end.
4. If the state isn't an accepting state, the second `while` roll back to the most recent accepting state and **mark the rest position as dead-end**.

##### Generating the Transition and Classifier Tables

* The initial table has one column for every character and one row for each state in the DFA.
* The generator can collapse identical columns into a single instance.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-24 16-16-40.png" alt="Screenshot from 2020-12-24 16-16-40" style="zoom: 50%;" />

---

#### Direct-Coded Scanners

Direct-coded scanners reduce the cost of computing DFA transitions, eliminates the memory references.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-24 17-20-20.png" alt="Screenshot from 2020-12-24 17-20-20" style="zoom: 50%;" />

---

#### Hand-Coded Scanners

Use buffered I/O, each read operation returns a longer string of characters or buffer.

The scanner maintains a pointer into the buffer.

> Double buffering: uses two input buffers in a modulo fashion to provide bounded roll back.
>
> One buffer is used for current input, one for previous.

Roll back is bounded.



<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-25 14-16-12.png" alt="Screenshot from 2020-12-25 14-16-12" style="zoom:50%;" />

---

#### Handling Keywords

1. Recognizing keywords in the DFA, consuming more memory but not compile time.
2. Classifying keywords as identifiers, testing each identifier. 

---

## 3. Parsers

The parser derives a syntactic structure for the program, fitting the words into a grammatical model of the source programming language.

* Top-down begin with the root,  parsers match the input stream against the productions of the grammar by predicting the next word.
* Bottom-up begin with the leaves, parsers work from the sequence of the words and accumulate context until the derivation is apparent.

---

#### Context-Free Grammars

> For a language L, its CFG defines the sets of strings of symbols that are valid sentences in L.
>
> A **CFG** is a set of rules that describe how to form sentences.
>
> **Sentence** is a string of symbols that can be derived from the rules of a grammar.
>
> **Production** is each rule in a CFG.
>
> **Nonterminal symbol** is a syntactic variable used in a grammar's productions.
>
> **Terminal symbol** is a word that can occur in a sentence.

Derivation:

* **Rightmost derivation**: a derivation that rewrites the right most nonterminal.
* **Leftmost derivation**: a derivation that rewrites the left most nonterminal.

The parse trees for the two derivations are identical.

Ambiguity: Some sentence in $L(G)$ has more than one rightmost (leftmost) derivation.

---

### Top-Down Parsing

* Begins with the root of the parse tree, extends the tree down ward until its leaves match the classified words.

* At each point, it selects a nonterminal symbol on the lower fringe of the tree and extends it by adding children that correspond to the right-hand side of some production for that nonterminal.

* This process continues until:

  * The fringe of the parse tree contains only terminal symbols, the input stream has been exhausted.
  * a clear mismatch occurs between the fringe of the partially built parse tree and the input stream.

* If it select a wrong production, it can backtrack.

  > Backtrack will sets focus to its parent and disconnects its children, then try another rule, if no untried rule remains, the parser moves up another level and tries again.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-29 15-29-17.png" alt="Screenshot from 2020-12-29 15-29-17" style="zoom:50%;" />

---

#### Eliminating Left Recursion

> Left Recursion: The first symbol on its right-hand side is the symbol on its right-hand side.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-30 11-31-43.png" alt="Screenshot from 2020-12-30 11-31-43" style="zoom:50%;" />

Solution: Translate from left recursion to right recursion

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2020-12-30 11-37-35.png" alt="Screenshot from 2020-12-30 11-37-35" style="zoom:50%;" />

* The transformation introduces a new nonterminal, and transfers the recursion onto this nonterminal. It also adds the rule $\rightarrow \epsilon$.

##### Convert indirect left recursion to direct left recursion

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-19 14-43-16.png" alt="Screenshot from 2021-01-19 14-43-16" style="zoom: 50%;" />

---

#### Backtrack-Free Parsing

> A CFG for which the leftmost, top-down parser can always predict the correct rule with lookahead of the most one word.

##### Construct first set

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-19 15-13-50.png" alt="Screenshot from 2021-01-19 15-13-50" style="zoom: 50%;" />

* Terminals' first set is itself, $\epsilon$ or eof.
* Nonterminals' first set is at the start of a sentence derived from it.

---

##### Construct follow set

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-19 15-39-23.png" alt="Screenshot from 2021-01-19 15-39-23" style="zoom:50%;" />

If the lookahead symbol is in $First(\alpha)$, choose a rule that implement it, if it is in $Follow(\alpha)$, choose $\epsilon$ rule.

---

##### First+ set

For a production $A\rightarrow \beta$
$$
First^+(A\rightarrow\beta)=\left\{ \begin{array}{lcl}
First(\beta) & \mbox{} & if \ \epsilon \notin First(\beta) \\
First(\beta) \cup Follow(A) & \mbox{} & otherwise
\end{array}\right.
$$
For a production $A\rightarrow \beta_1 |\beta_2 |...|\beta_n$, if it has property that
$$
First^+(A\rightarrow \beta_i) \cap First^+(A\rightarrow \beta_j) = \empty \ for \ all \ 1 \leq i,j\leq n,i \neq j.
$$
then it is backtrack free

---

##### Table-Driven LL(1) Parsers

LL(1): the parsers scan input **left to right**, construct a **leftmost** derivation, and use a lookahead of 1 symbol.

1. Recursive-Descent Parser for Expression

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-21 13-00-39.png" alt="Screenshot from 2021-01-21 13-00-39" style="zoom: 33%;" />

2. LL(1) Parser for Expressions

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-21 13-00-57.png" alt="Screenshot from 2021-01-21 13-00-57" style="zoom:33%;" />

3. LL(1) Table-Construction Algorithm

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-21 13-01-07.png" alt="Screenshot from 2021-01-21 13-01-07" style="zoom:33%;" />

---

### Bottom-Up Parsing

> Handle: a pair, $<A\rightarrow\beta, k>$, such that $\beta$ appears in the frontier with its right end at position k and replacing $\beta$ with A is the next step in the parse.
>
> Reduction: reducing the frontier of a bottom-up parser by $A\rightarrow \beta$ replaces $\beta$ with A in the frontier.

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-22 16-37-34.png" alt="Screenshot from 2021-01-22 16-37-34" style="zoom: 50%;" />

<img src="/home/herain/Documents/Compiler/screenshot/Screenshot from 2021-01-22 16-39-00.png" alt="Screenshot from 2021-01-22 16-39-00" style="zoom:33%;" />

* When action instruction is **Shift**:
  * Push the word into the stack.
  * Push the state into the stack.
  * Get the next word.
* When action instruction is **Reduce**
  * Pop out words equivalent to twice the number of words on the right side of the expression.
  * Change the status to the top of the stack.
  * Push the word into the stack.
  * Push the keyword which is in the Goto table into the stack.