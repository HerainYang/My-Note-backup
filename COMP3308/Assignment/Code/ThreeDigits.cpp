#include <iostream>
#include <cstdio>
#include <cstring>
#include <sstream>
#include "BFS.h"
#include "DFS.h"
#include "IDS.h"
#include "Greedy.h"
#include "HillClimbing.h"
#include "Astar.h"

void B(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    BFS* bfs = new BFS(&startNode, &goalNode, forbidden);
    bfs->_run();
}

void D(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    DFS* dfs = new DFS(&startNode, &goalNode, forbidden);
    dfs->_run();
}

void I(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    IDS* ids = new IDS(&startNode, &goalNode, forbidden);
    ids->_run();
}

void G(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    Greedy* greedy = new Greedy(&startNode, &goalNode, forbidden);
    greedy->_run();
}

void H(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    HillClimbing* hillClimbing = new HillClimbing(&startNode, &goalNode, forbidden);
    hillClimbing->_run();
}

void A(int start, int goal, list<Node*>* forbidden){
    Node startNode = *new Node(start / 100, (start / 10) % 10, start % 10, -1, nullptr, 0);
    Node goalNode = *new Node(goal / 100, (goal / 10) % 10, goal % 10, -1, nullptr, 0);
    Astar* astar = new Astar(&startNode, &goalNode, forbidden);
    astar->_run();
}

list<Node*>* split(char* content){
    int start = 0;
    list<Node*>* forbidden = new list<Node*>();
    while (content[start]!='\0'&&content[start]!='\n'){
        Node* newNode = new Node(content[start]-'0', content[start+1]-'0', content[start+2]-'0', -1, nullptr, 0);
        forbidden->push_back(newNode);
        start += 4;
    }
    return forbidden;
}

int main(int argc, char *argv[]) {
    FILE* file = fopen(argv[2], "r");
    char content[1000];
    int start;
    int goal;
    list<Node*>* forbidden;
    fscanf(file, "%d", &start);
    fscanf(file, "%d", &goal);
    while(fgets(content, 1000, file)!=nullptr){
        forbidden = split(content);
        auto iter = forbidden->begin();
    }
    switch (argv[1][0]) {
        case 'A':{
            A(start, goal, forbidden);
            break;
        }
        case 'B':{
            B(start, goal, forbidden);
            break;
        }
        case 'I':{
            I(start, goal, forbidden);
            break;
        }
        case 'H':{
            H(start, goal, forbidden);
            break;
        }
        case 'G':{
            G(start, goal, forbidden);
            break;
        }
        case 'D':{
            D(start, goal, forbidden);
            break;
        }
    }
    return 0;
}
