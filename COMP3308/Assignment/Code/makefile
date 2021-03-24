CC=g++
CFLAGS=-DDEBUG

ThreeDigits: ThreeDigits.o BasicAlgorithm.o Astar.o BFS.o DFS.o IDS.o Greedy.o HillClimbing.o
	g++ ThreeDigits.o BasicAlgorithm.o Astar.o BFS.o DFS.o IDS.o Greedy.o HillClimbing.o -o ThreeDigits

Astar.o: Astar.cpp Astar.h

BFS.o: BFS.cpp BFS.h

DFS.o: DFS.cpp DFS.h

IDS.o: IDS.cpp IDS.h

Greedy.o: Greedy.cpp Greedy.h

HillClimbing.o: HillClimbing.cpp HillClimbing.h

BasicAlgorithm.o: BasicAlgorithm.cpp BasicAlgorithm.h

ThreeDigits.o: ThreeDigits.cpp BasicAlgorithm.cpp BFS.cpp DFS.cpp Greedy.cpp HillClimbing.cpp IDS.cpp







clean:
	rm *.o
