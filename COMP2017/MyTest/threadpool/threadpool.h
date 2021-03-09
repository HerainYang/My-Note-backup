//
// Created by herain on 3/3/21.
//

#ifndef THREADPOOL_THREADPOOL_H
#define THREADPOOL_THREADPOOL_H

#endif //THREADPOOL_THREADPOOL_H

#include <zconf.h>

typedef struct _workitem workitem_t;
typedef struct _threadpool threadpool_t;

struct _workitem{
    void *(*action)(void*);
    void *arg;
};

struct _threadpool{
    pthread_mutex_t mutex;
    pthread_cond_t cond;
    pthread_t* threads;
    int threadCount;

    workitem_t* queue;
    int queue_size;
    int currentCount;
    int head;
    int tail;

    int shutdown;
};



extern threadpool_t * threadpool_init(int max_threads, int queue_size);

extern void* threadpool_thread(void* args);

extern void threadpool_add_workitem(threadpool_t* pool, void* (func)(void* arg), void* arg);

extern void threadpool_destroy(threadpool_t* pool);


