#include <stdio.h>
#include <error.h>
#include <pthread.h>
#include <errno.h>
#include <zconf.h>
#include <semaphore.h>
#include <sys/shm.h>
#include <fcntl.h>
#include <malloc.h>

#define MAX 2
#define MAX_ITER 10000000

void* withdraw_money(void* input){
    int total = 100;
    int withdrawal = *((int*) input);
    if(total < withdrawal) {
        printf("You do not have that much money!\n");
        pthread_exit(1);
    }

    total -= withdrawal;
    printf("%d$ remains\n", total);
    pthread_exit(0);
}

pthread_t thread_1, thread_2;

void testbank(){
    printf("Hello, bank!\n");
    int i = 50;
    int j = 120;
    void* return_1;
    void* return_2;
    pthread_create(&thread_1, NULL, withdraw_money, &i);
    pthread_create(&thread_2, NULL, withdraw_money, &j);
    pthread_join(thread_1, &return_1);
    pthread_join(thread_2, &return_2);
    printf("return 1 is %d, return 2 is %d\n", return_1, return_2);
    printf("finish!");
}

int counter = 0;
pthread_mutex_t mylock = PTHREAD_MUTEX_INITIALIZER;
//pthread_mutex_t mylock;

void * thread_function(void * arg) {
    long l;
    for (l = 0; l < MAX_ITER; l++) {
        pthread_mutex_lock(&mylock);
        int errVal = pthread_mutex_lock(&mylock);
        printf("%d\n", errVal);
        if(errVal == EDEADLK) {
            printf("ERROR HERE!\n");
            fflush(stdout);
            pthread_exit(1);
        }
        printf("%d\n", pthread_self());
        counter = counter + 1; // critical section
        pthread_mutex_unlock(&mylock);
    }
}

void testType(){
    pthread_t mythread[MAX];
    pthread_mutexattr_t mutexattr;
    pthread_mutexattr_settype(&mutexattr, PTHREAD_MUTEX_ERRORCHECK_NP);
    pthread_mutex_init(&mylock, &mutexattr);

    int t = 0;
    for (t = 0; t < MAX; t++)
        pthread_create( &mythread[t], NULL, thread_function, NULL);
    for (t = 0; t < MAX; t++)
        pthread_join ( mythread[t], NULL);
    printf("Expected counter value: %lld, observed counter value: %lld\n",
           MAX_ITER * MAX, counter);
    printf("You experienced %lld race conditions, or %d%% of all accesses to the shared variable.\n",
           MAX_ITER * MAX - counter, 100 - 100 * counter / (MAX_ITER * MAX));
    pthread_mutex_destroy(&mylock);
}

int count = 0;
int total = 0;


void produce(){
    count++;
    printf("producer produce one and now it have %d, total %d\n", count, total);
    fflush(stdout);
}

void consume(){
    count--;
    total++;
    printf("consumer consume one and now it have %d, total %d\n", count, total);
    fflush(stdout);
}

void* producer(void* args){
    while(1){
        pthread_mutex_lock(&mylock);
        if(total == 10) {
            printf("producer out!\n");
            pthread_mutex_unlock(&mylock);
            break;
        }
        if(count != 5)
            produce();
        pthread_mutex_unlock(&mylock);
    }

}

void* consumer(void* args){
    while(1) {
        pthread_mutex_lock(&mylock);
        if (count != 0)
            consume();
        if(total == 10) {
            printf("consumer out!\n");
            pthread_mutex_unlock(&mylock);
            break;
        }
        pthread_mutex_unlock(&mylock);
    }
}

void producer_and_consumer(){
    pthread_create(&thread_1, NULL, producer, NULL);
    pthread_create(&thread_2, NULL, consumer, NULL);
    pthread_join(thread_1, NULL);
    pthread_join(thread_2, NULL);
    printf("end of the program!\n");
    fflush(stdout);
}

#define PHI 5
pthread_t thr[PHI];
pthread_mutex_t m[MAX];

void * tfunc(void *arg){
    int i = * (int*) arg; // thread id: 0..4
    printf("receive %d start\n", i);
    while(1){
        if ( i < ((i + 1) % MAX) ) { //also can judge if the id is odd or even
            pthread_mutex_lock(&m[i]);
            pthread_mutex_lock(&m[(i + 1) % MAX]);
        } else {
            pthread_mutex_lock(&m[(i + 1) % MAX]);
            pthread_mutex_lock(&m[i]);
        }
        printf("Philosopher %d is eating...\n", i);
        fflush(stdout);
        pthread_mutex_unlock(&m[i]);
        pthread_mutex_unlock(&m[(i + 1) % MAX]);
        sleep(1);
    }
    printf("Philosopher %d exits the room\n", i);
}

void test_PHI(){
    int num[5];
    for(int i = 0; i < PHI; i++){
        pthread_mutex_init(&m[i], NULL);
    }

    for(int i = 0; i < PHI; i++){
        num[i] = i;
        printf("%d is started\n", num[i]);
        fflush(stdout);
        pthread_create(&thr[i], NULL, tfunc, (void*) &num[i]);
    }

    pthread_exit(NULL);
}

sem_t sem;

void * tfuncSemaphore1(void *arg){
    int i;
    sem_getvalue(&sem, &i);
    printf("first is %d\n", i);
    sem_post(&sem);
    sem_getvalue(&sem, &i);
    printf("now is %d\n", i);
    sem_wait(&sem);
    printf("1 hold the lock.\n");
    fflush(stdout);
}

void * tfuncSemaphore2(void *arg){

    sem_wait(&sem);
    printf("2 hold the lock.\n");
    fflush(stdout);
    sem_post(&sem);
}

void testAttrSem(){
    sem_init(&sem, 0, 2);
    pthread_create(&thread_1, NULL, tfuncSemaphore1, NULL);
    pthread_create(&thread_2, NULL, tfuncSemaphore2, NULL);

    pthread_join(thread_1, NULL);
    pthread_join(thread_2, NULL);
}

void Client(sem_t *addr){
    sem_wait(addr);
    perror("");
    printf("Client is holding the lock\n");
    fflush(stdout);
    sem_post(addr);
}
void Server(sem_t *addr){
    sem_wait(addr);
    int i;
    sem_getvalue(&sem, &i);
    printf("first is %d\n", i);
    printf("Server is holding the lock\n");
    fflush(stdout);
    sem_post(addr);
}

void testSemBetPro(){
    key_t tid = ftok("/tmp", 66);
    int shmid = shmget(tid, 4096, IPC_CREAT);
    sem_t * addr = shmat(shmid, NULL, O_WRONLY);
    sem_init(addr, 1, 1);
    if(fork() == 0){
        Client(addr);
    } else {

        Server(addr);
    }
    sleep(5);
}

sem_t pingpong;

void * T1(void * arg){
    for(;;){
        sem_wait(&pingpong);
        printf("ping\n");
        fflush(stdout);
        sem_post(&pingpong);
        sleep(1);
    }
}

void * T2(void * arg){
    for(;;){
        sem_wait(&pingpong);
        printf("pong\n");
        fflush(stdout);
        sem_post(&pingpong);
        sleep(1);
    }
}

pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

int main() {
    pthread_cond_signal()
    sem_init(&pingpong, 0, 1);
    pthread_create(&thread_1, NULL, T1, NULL);
    pthread_create(&thread_2, NULL, T2, NULL);

    pthread_join(thread_1, NULL);
    pthread_join(thread_2, NULL);
    return 0;
}
