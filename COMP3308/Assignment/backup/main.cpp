#include <iostream>
#include <cstdio>
#include "BFS.h"
#include "DFS.h"
#include "IDS.h"
#include "Greedy.h"
#include "HillClimbing.h"
#include "Astar.h"

void B(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    BFS* bfs = new BFS(&startNode, &goalNode);
    bfs->_run();
}

void D(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    DFS* dfs = new DFS(&startNode, &goalNode);
    dfs->_run();
}

void I(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    IDS* ids = new IDS(&startNode, &goalNode);
    ids->_run();
}

void G(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    Greedy* greedy = new Greedy(&startNode, &goalNode);
    greedy->_run();
}

void H(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    HillClimbing* hillClimbing = new HillClimbing(&startNode, &goalNode);
    hillClimbing->_run();
}

void A(){
    int start;
    int goal;
    scanf("%d", &start);
    scanf("%d", &goal);
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    Astar* astar = new Astar(&startNode, &goalNode);
    astar->_run();
}

int main(int argc, char* argv[]) {
    switch (argv[2][0]) {
        case 'A':{
            A();
            break;
        }
        case 'B':{
            B();
            break;
        }
        case 'I':{
            I();
            break;
        }
        case 'H':{
            H();
            break;
        }
        case 'G':{
            G();
            break;
        }
        case 'D':{
            D();
            break;
        }
    }
    return 0;
}
