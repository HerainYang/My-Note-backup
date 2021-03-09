#include <stdio.h>
#include <stdlib.h>

struct node{
    int data;
    struct node *next;
};

struct node* createNewNode(int data){
    struct node* newNode = malloc(sizeof(struct node));
    newNode->data = data;
    newNode->next = NULL;
    return newNode;
}

struct node* addToEnd(struct node* head, int newData){
    struct node* newNode = createNewNode(newData);
    if(head == NULL)
        return newNode;
    struct node* cursor = head;
    while(cursor->next!=NULL){
        cursor = cursor->next;
    }
    cursor->next = newNode;
    return head;
}

struct node* addToFront(struct node* head, int newData){
    struct node* newNode = createNewNode(newData);
    if(head != NULL){
        newNode->next = head;
    }
    return newNode;
}

struct node* deleteFront(struct node* head){
    if(head == NULL){
        return NULL;
    }
    struct node* next = head->next;
    free(head);
    return next;
}

void readAll(struct node* head){
    if(head == NULL){
        return;
    }
    struct node* cursor = head;
    do{
        printf("%d\n", cursor->data);
        cursor = cursor->next;
    } while (cursor != NULL);
    printf("\n");
}



int main() {
    struct node* head = createNewNode(10);
    head = addToEnd(head, 12);
    head = addToFront(head, 3);
    readAll(head);
    head = deleteFront(head);
    readAll(head);


    return 0;
}
