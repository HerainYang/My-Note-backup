//
// Created by herain on 3/23/21.
//

#include "BasicAlgorithm.h"

BasicAlgorithm::BasicAlgorithm(list<Node*>* forbidden) {
    this->forbidden = forbidden;
}

bool BasicAlgorithm::expandContain(Node node) {
    auto iter = expand->begin();
    while(iter != expand->end()){
        if(Node::sameNode(*(*iter), node)){
            return true;
        } else {

        }
        iter++;
    }
    return false;
}

bool BasicAlgorithm::forbiddenContain(Node node) {
    auto iter = forbidden->begin();
    while(iter != forbidden->end()){
        if(Node::sameDigit(*(*iter), node)){
            return true;
        }
        iter++;
    }
    return false;
}

void BasicAlgorithm::listInOrderAddDepend(list<Node *> *list, Node *newNode, int (*funPtr)(Node *)) {
    if(list->empty())
    {
        list->insert(list->begin(), newNode);
        return;
    }
    auto iter = list->begin();
    Node* currentNode = (*iter);
    while(funPtr(newNode) > funPtr(currentNode) && iter!=list->end()){
        iter++;
    }
    list->insert(iter, newNode);
}

void BasicAlgorithm::displaySolution() {
    list<Node*>* result = new list<Node*>();
    Node* cursor = expand->back();
    while(cursor!= nullptr){
        result->push_front(cursor);
        cursor = cursor->getParent();
    }
    auto iter = result->begin();
    while(iter!=result->end()){
        (*iter)->_toString();
        if((++iter)!=result->end())
            printf(",");
    }
    printf("\n");
}

void BasicAlgorithm::displayTrack(list<Node *> *targetList) {
    auto iter = targetList->begin();
    while(iter!=targetList->end()){
        (*iter)->_toString();
        if((++iter)!=targetList->end())
            printf(",");
    }
    printf("\n");
    return;
}




Node::Node(int firstDigit, int secondDigit, int thirdDigit, int lastChangeDigit, Node *parent, int depth) {
    this->digit = new int[3];
    this->digit[0] = firstDigit;
    this->digit[1] = secondDigit;
    this->digit[2] = thirdDigit;
    this->lastChangeDigit = lastChangeDigit;
    this->parent = parent;
    this->depth = depth;
}

void Node::_toString() const {
    printf("%d%d%d", digit[0], digit[1], digit[2]);
//    printf("(%d%d%d, %d)", digit[0], digit[1], digit[2], lastChangeDigit);
}



std::list<Node>* Node::generateChildren(BasicAlgorithm* algorithm){
    auto* children = new std::list<Node>;
    Node* newNode;
    for(int i = 0; i < 3; i++){
        newNode = changeDigit(i, -1, algorithm);
        if(newNode != nullptr) {
            children->push_back(*newNode);
        }

        newNode = changeDigit(i, 1, algorithm);
        if(newNode != nullptr) {
            children->push_back(*newNode);
        }
    }
    return children;
}

/**
 *
 * @param position
 * @param operation -1 or 1
 * @param algorithm
 * @return
 */
Node* Node::changeDigit(int position, int operation, BasicAlgorithm* algorithm) {
    if(position == lastChangeDigit)
        return nullptr;
    int tempDigit[3];
    tempDigit[0] = digit[0];
    tempDigit[1] = digit[1];
    tempDigit[2] = digit[2];
    tempDigit[position] += operation;
    if(tempDigit[position]>9 || tempDigit[position]<0)
        return nullptr;

    Node* newNode = new Node(tempDigit[0], tempDigit[1], tempDigit[2], position, this, this->depth + 1);
    if(algorithm->expandContain(*newNode)){
        return nullptr;
    }
    if(algorithm->forbiddenContain(*newNode)){
        return nullptr;
    }
    return newNode;
}

bool Node::sameDigit(Node nodeA, Node nodeB) {
    return nodeA.digit[0] == nodeB.digit[0] && nodeA.digit[1] == nodeB.digit[1] && nodeA.digit[2] == nodeB.digit[2];
}

bool Node::sameNode(Node nodeA, Node nodeB) {
    return nodeA.digit[0] == nodeB.digit[0] && nodeA.digit[1] == nodeB.digit[1] && nodeA.digit[2] == nodeB.digit[2] && nodeA.lastChangeDigit == nodeB.lastChangeDigit;
}

Node* Node::getParent(){
    return parent;
}

int Node::getDepth() {
    return depth;
}

void Node::measureManhattanHeuristic(Node targetNode) {
    h = abs(targetNode.digit[0] - digit[0]) + abs(targetNode.digit[1] - digit[1]) + abs(targetNode.digit[2] - digit[2]);
}

int Node::getManhattanHeuristic(Node *node) {
    return node->h;
}

void Node::measuref(Node targetNode) {
    measureManhattanHeuristic(targetNode);
    f = h + depth;
}

int Node::getf(Node *node) {
    return node->f;
}

int *Node::getDigits() {
    return digit;
}

