//
// Created by herain on 3/3/21.
//

#include "threadpool.h"
#include <malloc.h>
#include <pthread.h>

extern threadpool_t * threadpool_init(int max_threads, int queue_size){
    threadpool_t * pool = (threadpool_t*) malloc (sizeof(threadpool_t));

    pool->threadCount = max_threads;
    pool->queue_size = queue_size;
    pool->head = pool->tail = pool->currentCount = 0;


    pool->shutdown = 0;

    pthread_mutex_init(&pool->mutex, NULL);
    pthread_cond_init(&pool->cond, NULL);


    pool->queue = (workitem_t*) malloc(sizeof(workitem_t) * queue_size);


    pthread_attr_t pthreadAttr;
    pthread_attr_init(&pthreadAttr);
    pthread_attr_setdetachstate(&pthreadAttr, PTHREAD_CREATE_JOINABLE);

    pool->threads = (pthread_t*) malloc(sizeof(pthread_t) * max_threads);

    for(int i = 0; i < max_threads; i++){
        pthread_create(&pool->threads[i], &pthreadAttr, threadpool_thread, (void*) pool);
    }
    return pool;
}

extern void* threadpool_thread(void* args){
    threadpool_t* pool = (threadpool_t*) args;
    workitem_t workitem_temp;
    while (1){
        pthread_mutex_lock(&pool->mutex);
        while(pool->currentCount == 0 && pool->shutdown == 0){
            pthread_cond_wait(&pool->cond, &pool->mutex);
        }

        if(pool->shutdown == 1) {
            break;
        }

        workitem_temp.action = pool->queue[pool->head].action;
        workitem_temp.arg = pool->queue[pool->head].arg;

        if(pool->queue[pool->head].action == NULL){
            printf("NULL\n");
            fflush(stdout);
        }


        pool->head = (pool->head + 1) % pool->queue_size;
        printf("A function is gonna run, current count %d\n", pool->currentCount);
        fflush(stdout);
        pool->currentCount -= 1;

        pthread_mutex_unlock(&pool->mutex);

        workitem_temp.action(workitem_temp.arg);
    }
    printf("function end\n");
    fflush(stdout);
    pthread_mutex_unlock(&pool->mutex);
    pthread_exit(NULL);
}

extern void threadpool_add_workitem(threadpool_t* pool, void* (func)(void* arg), void* arg){
    if(pool == NULL || func == NULL){
        printf("error NULL\n");
        return;
    }

    pthread_mutex_lock(&pool->mutex);

    if(pool->queue_size == pool->currentCount)
    {
        printf("queue MAX\n");
        fflush(stdout);
        pthread_mutex_unlock(&pool->mutex);
        return;
    }

    pool->queue[pool->tail].action = func;
    pool->queue[pool->tail].arg = arg;

    pool->tail = (pool->tail+1) % pool->queue_size;
    pool->currentCount++;

    pthread_cond_signal(&pool->cond);
    pthread_mutex_unlock(&pool->mutex);

    printf("item add! current count %d\n", pool->currentCount);
    fflush(stdout);
}

extern void threadpool_destroy(threadpool_t* pool){
    if(pool == NULL){
        printf("error NULL");
        return;
    }
    pthread_mutex_lock(&pool->mutex);

    pool->shutdown = 1;

    pthread_cond_broadcast(&pool->cond);

    pthread_mutex_unlock(&pool->mutex);
}