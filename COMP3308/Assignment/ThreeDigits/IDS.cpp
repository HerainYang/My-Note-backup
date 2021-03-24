//
// Created by herain on 3/23/21.
//

#include "IDS.h"

IDS::IDS(Node *startNode, Node *goalNode, list<Node*>* forbidden) : BasicAlgorithm(forbidden){
    this->fringe = new list<Node*>();
    this->expand = new list<Node*>();
    this->startNode = startNode;
    this->goalNode = goalNode;
    this->tracking = new list<Node*>();
    fringe->push_front(startNode);
}

bool IDS::_expand(int currentDepth) {
    Node* expandNode = fringe->front();
    fringe->pop_front();
    if(expandContain(*expandNode)){
        return false;
    }
    expand->push_back(expandNode);
    tracking->push_back(expandNode);
    if(tracking->size() >= 1000){
        return false;
    }
    if(Node::sameDigit(*goalNode, *expandNode)){
        return true;
    }
    list<Node>* children = expandNode->generateChildren(this);
    auto iter = children->rbegin();
    while(iter != children->rend()){
        if((*iter).getDepth() > currentDepth)
            fringe->push_back(&(*iter));
        else
            fringe->push_front(&(*iter));
        iter++;
    }
    return false;
}

void IDS::_run() {
    int i = 0;
    while(true){
        while (fringe->front() != nullptr){
            bool foundGoal = _expand(i);
            if(tracking->size() >= 1000)
                break;
            if(foundGoal)
            {
                displaySolution();
                displayTrack(tracking);
                return;
            }

            if(fringe->front() == nullptr)
                break;
            if(fringe->front()->getDepth()>i){
                expand->clear();
                fringe->clear();
                fringe->push_front(startNode);
                break;
            }
        }
        if(tracking->size() >= 1000)
            break;
        i++;
    }
    printf("No solution found.\n");
    displayTrack(tracking);
}
