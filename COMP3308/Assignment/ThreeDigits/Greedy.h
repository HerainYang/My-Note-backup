//
// Created by herain on 3/23/21.
//

#ifndef CODE_GREEDY_H
#define CODE_GREEDY_H


#include "BasicAlgorithm.h"

class Greedy : public BasicAlgorithm{

public:
    Greedy(Node* startNode, Node* goalNode, list<Node*>* forbidden);
    bool _expand();
    void _run();
};


#endif //CODE_GREEDY_H
