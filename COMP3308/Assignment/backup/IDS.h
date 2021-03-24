//
// Created by herain on 3/23/21.
//

#ifndef CODE_IDS_H
#define CODE_IDS_H


#include "BasicAlgorithm.h"

class IDS : public BasicAlgorithm{
private:
    list<Node*>* fringe;
    list<Node*>* tracking;
public:
    IDS(Node* startNode, Node* goalNode);
    bool _expand(int currentDepth);
    void _run();
};


#endif //CODE_IDS_H
