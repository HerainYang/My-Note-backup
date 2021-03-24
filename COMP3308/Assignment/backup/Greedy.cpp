//
// Created by herain on 3/23/21.
//

#include "Greedy.h"

Greedy::Greedy(Node *startNode, Node *goalNode) {
    this->fringe = new list<Node*>();
    this->expand = new list<Node*>();
    this->startNode = startNode;
    this->goalNode = goalNode;
    fringe->push_front(startNode);
    startNode->measureManhattanHeuristic(*goalNode);
}

bool Greedy::_expand() {
    Node* expandNode = fringe->front();

    fringe->pop_front();
    expand->push_back(expandNode);
    if(expand->size() >= 1000)
        return false;
    if(Node::sameDigit(*goalNode, *expandNode)){
        return true;
    }
    list<Node>* children = expandNode->generateChildren(this);

    auto iter = children->begin();
    while(iter != children->end()){
        (*iter).measureManhattanHeuristic(*goalNode);
        listInOrderAddDepend(fringe, &(*iter), Node::getManhattanHeuristic);
        iter++;
    }
    return false;
}


void Greedy::_run() {
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

