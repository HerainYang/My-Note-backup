//
// Created by herain on 3/23/21.
//

#ifndef CODE_BFS_H
#define CODE_BFS_H

#include "BasicAlgorithm.h"

using namespace std;
class BFS : public BasicAlgorithm{
public:
    BFS(Node* startNode, Node* goalNode, list<Node*>* forbidden);
    bool _expand();
    void _run();
};


#endif //CODE_BFS_H
