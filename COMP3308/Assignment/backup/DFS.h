//
// Created by herain on 3/23/21.
//

#ifndef CODE_DFS_H
#define CODE_DFS_H


#include "BasicAlgorithm.h"

class DFS : public BasicAlgorithm{
private:
    list<Node*>* fringe;
public:
    DFS(Node* startNode, Node* goalNode);
    bool _expand();
    void _run();
};


#endif //CODE_DFS_H
