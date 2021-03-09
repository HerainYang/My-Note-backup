#include <stdio.h>
#include <pthread.h>
#include "threadpool.h"


void* hellotest(void* arg){
    printf("hello world\n");
    fflush(stdout);
}

void testPool(){
    threadpool_t* pool = threadpool_init(2, 2);
    threadpool_add_workitem(pool, hellotest, NULL);
    threadpool_add_workitem(pool, hellotest, NULL);

    sleep(5);

    threadpool_destroy(pool);

    for(int i = 0; i < pool->threadCount; i++){
        pthread_join(pool->threads[i], NULL);
    }
}

int main() {
    int b[10] = {1,3,2,5,6,8,3,4,2,5};
    int c[11] = {1,3,2,5,6,8,3,4,2,5,0};

    for(int i = 9; i >= 0; i --){
        c[i] = c[i+1]>b[i]?c[i+1]:b[i];
    }
    for(int i = 0; i < 10; i++){
        printf("[%d]", c[i]);
    }
    return 0;
}
