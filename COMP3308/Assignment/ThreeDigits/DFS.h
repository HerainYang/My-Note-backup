//
// Created by herain on 3/23/21.
//

#ifndef CODE_DFS_H
#define CODE_DFS_H


#include "BasicAlgorithm.h"

class DFS : public BasicAlgorithm{
public:
    DFS(Node* startNode, Node* goalNode, list<Node*>* forbidden);
    bool _expand();
    void _run();
};


#endif //CODE_DFS_H
