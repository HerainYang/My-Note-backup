cmake_minimum_required(VERSION 3.16)
project(Code)

set(CMAKE_CXX_STANDARD 14)

add_executable(Code main.cpp BFS.cpp BFS.h BasicAlgorithm.cpp BasicAlgorithm.h DFS.cpp DFS.h IDS.cpp IDS.h Greedy.cpp Greedy.h HillClimbing.cpp HillClimbing.h Astar.cpp Astar.h)