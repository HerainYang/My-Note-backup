//
// Created by herain on 3/23/21.
//

#ifndef CODE_BASICALGORITHM_H
#define CODE_BASICALGORITHM_H
#include <queue>
#include <array>
#include <list>
using namespace std;

class BasicAlgorithm;
class Node;

class Node{
private:
    int* digit;
    int lastChangeDigit;
    int depth;
    int h;
    int f;
    Node* parent;
    Node* changeDigit(int position, int operation, BasicAlgorithm* algorithm);
public:
    Node(int firstDigit, int secondDigit, int thirdDigit, int lastChangeDigit, Node *parent, int depth);

    void _toString() const;

    list<Node>* generateChildren(BasicAlgorithm* algorithm);

    static bool sameDigit(Node nodeA, Node nodeB);

    static bool sameNode(Node nodeA, Node nodeB);

    void measureManhattanHeuristic(Node targetNode);

    static int getManhattanHeuristic(Node *node);

    void measuref(Node targetNode);

    static int getf(Node *node);

    Node* getParent();

    int getDepth();
};

class BasicAlgorithm {
public:

    list<Node*>* expand;

    Node* startNode;
    Node* goalNode;

    BasicAlgorithm();

    bool expandContain(Node node);

    void listInOrderAddDepend(list<Node*>* list, Node* newNode, int (*funPtr)(Node*));

    void displaySolution();

    void displayTrack(list<Node*>* targetList);
};



#endif //CODE_BASICALGORITHM_H
