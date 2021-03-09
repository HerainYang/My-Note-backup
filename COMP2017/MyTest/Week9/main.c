#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <malloc.h>


pthread_t my_thread;

pthread_t my_thread_2;

pthread_t my_thread_3;

void* threadF_2 (void *arg) {
    printf("Hello world from our first POSIX thread 2.\n");
    fflush(stdout);

    sleep(5);
    printf("after 5 second 2!\n");
    return 0;
}

void* threadF (void *arg) {
    printf("Hello world from our first POSIX thread.\n");
    fflush(stdout);

    pthread_create(&my_thread_2, NULL, threadF_2, NULL);

    sleep(5);
    printf("after 5 second!\n");
    pthread_exit(NULL);
    return 0;
}

int *a;
int *c;

void * HelloFromThread(void* arg){
    pthread_detach(pthread_self());
    printf("Hello world from thread.\n");
    printf("%d\n", *c);
    fflush(stdout);
    sleep(5);
}

void testThread(){
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
    pthread_attr_setstack(&attr, NULL, 1024);
    pthread_create(&my_thread, &attr, threadF, NULL);


    pthread_attr_destroy(&attr);

    printf("Hello, World from the main thread.\n");
    pthread_join(my_thread, NULL);
}

void fakeMain(){
    printf("Hello, World from the main thread.\n");
    pthread_create(&my_thread, NULL, threadF, NULL);
    pthread_cancel(my_thread);
    pthread_exit(NULL);
}



int main() {
    a = malloc(sizeof(int));
    *a = 2;
    static int b = 4;
    c = &b;
    pthread_create(&my_thread_3, NULL, HelloFromThread, NULL);
    printf("Hello, World from the main thread.\n");
    sleep(6);
    return 0;
}
