//
// Created by herain on 3/23/21.
//

#ifndef CODE_HILLCLIMBING_H
#define CODE_HILLCLIMBING_H


#include "BasicAlgorithm.h"

class HillClimbing : public BasicAlgorithm{
private:
    Node* current;
public:
    HillClimbing(Node* startNode, Node* goalNode, list<Node*>* forbidden);
    bool _expand();
    static bool compareHeuristic(Node *nodeA, Node *nodeB);
    void _run();
};


#endif //CODE_HILLCLIMBING_H
