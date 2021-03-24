//
// Created by herain on 3/23/21.
//

#ifndef CODE_ASTAR_H
#define CODE_ASTAR_H


#include "BasicAlgorithm.h"

class Astar : public BasicAlgorithm{
private:
    list<Node*>* fringe;
public:
    Astar(Node* startNode, Node* goalNode);
    bool _expand();
    void _run();
};


#endif //CODE_ASTAR_H
