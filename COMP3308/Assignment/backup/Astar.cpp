//
// Created by herain on 3/23/21.
//

#include "Astar.h"

Astar::Astar(Node *startNode, Node *goalNode) {
    this->fringe = new list<Node*>();
    this->expand = new list<Node*>();
    this->startNode = startNode;
    this->goalNode = goalNode;
    fringe->push_front(startNode);
    startNode->measuref(*goalNode);
}

bool Astar::_expand() {
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
        (*iter).measuref(*goalNode);
        listInOrderAddDepend(fringe, &(*iter), Node::getf);
        iter++;
    }
    return false;
}

void Astar::_run() {
    while (fringe->front() != nullptr){
        bool foundGoal = _expand();
        if(expand->size() >= 1000)
            break;
        if(foundGoal)
        {
            displaySolution();
            displayTrack(expand);
        }
    }
    printf("No solution found.\n");
    displayTrack(expand);
}


