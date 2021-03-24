//
// Created by herain on 3/23/21.
//

#include "HillClimbing.h"

HillClimbing::HillClimbing(Node *startNode, Node *goalNode, list<Node*>* forbidden) : BasicAlgorithm(forbidden){
    this->expand = new list<Node*>();
    this->startNode = startNode;
    this->goalNode = goalNode;
    current = startNode;
    current->measureManhattanHeuristic(*goalNode);
}

bool HillClimbing::_expand() {
    Node* expandNode = current;
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
        if(compareHeuristic(&(*iter), current))
            current = &(*iter);
        iter++;
    }
    if(current == expandNode)
        return false;
    return false;
}

bool HillClimbing::compareHeuristic(Node *nodeA, Node *nodeB) {
    return Node::getManhattanHeuristic(nodeA) <= Node::getManhattanHeuristic(nodeB);
}

void HillClimbing::_run() {
    while (current != expand->back()){
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


