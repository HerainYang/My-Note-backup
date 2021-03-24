//
// Created by herain on 3/23/21.
//

#include "BFS.h"

BFS::BFS(Node* startNode, Node* goalNode) {
    this->fringe = new queue<Node*>();
    this->expand = new list<Node*>();
    this->startNode = startNode;
    this->goalNode = goalNode;
    fringe->push(startNode);
}

bool BFS::_expand() {
    Node* expandNode = fringe->front();

    fringe->pop();
    expand->push_back(expandNode);
    if(expand->size() >= 1000)
        return false;
    if(Node::sameDigit(*goalNode, *expandNode)){
        return true;
    }
    list<Node>* children = expandNode->generateChildren(this);

    auto iter = children->begin();
    while(iter != children->end()){
        fringe->push(&(*iter));
        iter++;
    }
    return false;
}

void BFS::_run() {
    while (fringe->front() != nullptr){
        bool foundGoal = _expand();
        if(expand->size() >= 1000)
            break;
        if(foundGoal)
        {
            displaySolution();
            displayTrack(expand);
            return;
        }
    }
    printf("No solution found.\n");
    displayTrack(expand);
}

