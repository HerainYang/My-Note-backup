#include <stdio.h>
#include <pthread.h>
#include <malloc.h>
#include <sys/time.h>

#define LENGTH 10

void printArray(int *array){
    for(int i = 0; i < LENGTH; i ++){
        printf("[%d]", array[i]);
    }
    printf("\n");
}

void merge(int *array, int left, int mid, int right){
    int temp[LENGTH] = {0};
    int index_1 = left;
    int index_2 = mid;
    int index_t = 0;

    while(index_1 < mid && index_2 < right){
        if(array[index_1] < array[index_2])
        {
            temp[index_t++] = array[index_1++];
        } else {
            temp[index_t++] = array[index_2++];
        }
    }

    while(index_1 < mid){
        temp[index_t++] = array[index_1++];
    }

    while(index_2 < right){
        temp[index_t++] = array[index_2++];
    }

    for(int i = 0; i < index_t; i ++){
        array[left + i] = temp[i];
    }
}



void mergeSort(int *array, int left, int right){
    if(left < right - 1){
        int mid = (left + right) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid, right);
        merge(array, left, mid, right);
    }
}

void ParallelMergeSort(int array[], int left, int right, int depth, int max_depth);

struct arg{
    int * array;
    int left;
    int right;
    int depth;
    int max_depth;
};

void * PMSort(void * ptr){
    struct arg * Arg = (struct arg *) ptr;
    ParallelMergeSort(Arg->array, Arg->left, Arg->right, Arg->depth, Arg->max_depth);
}

void ParallelMergeSort(int array[], int left, int right, int depth, int max_depth){
    printf("new thread\n");
    if(depth == max_depth){
        mergeSort(array, left, right);
    } else {
        if(left < right - 1){
            int mid = (left + right) / 2;
            struct arg LeftChildArg, RightChildArg;
            LeftChildArg.array = array;
            RightChildArg.array = array;

            LeftChildArg.left = left;
            LeftChildArg.right = mid;

            RightChildArg.left = mid;
            RightChildArg.right = right;

            RightChildArg.depth = depth + 1;
            LeftChildArg.depth = depth + 1;

            RightChildArg.max_depth = LeftChildArg.max_depth = max_depth;

            pthread_t *pthread_l, *pthread_r;

            pthread_l = (pthread_t*) malloc (sizeof(pthread_t));
            pthread_r = (pthread_t*) malloc (sizeof(pthread_t));

            pthread_create(pthread_l, NULL, PMSort, (void *)&LeftChildArg);
            pthread_create(pthread_r, NULL, PMSort, (void *)&RightChildArg);
            pthread_join(*pthread_l, NULL);
            pthread_join(*pthread_r, NULL);
            merge(array, left, mid, right);
        }
    }
}

pthread_t myThread;

testMerge(){
    clock_t start, finish;
    int array[LENGTH] = {1,7,2,5,3,7,8,3,5,6};
    printArray(array);
    start = clock();
    //1
    //mergeSort(array, 0, LENGTH);
    //1 end

    //2
    struct arg Arg;
    Arg.array = array;
    Arg.left = 0;
    Arg.right = LENGTH;
    Arg.depth = 0;
    Arg.max_depth = 2;
    pthread_create(&myThread, NULL, PMSort, &Arg);
    pthread_join(myThread, NULL);
    //2 end
    finish = clock();
    printArray(array);
    printf("duration is %f\n", (double)(finish - start)/CLOCKS_PER_SEC);
}

pthread_mutex_t myMutex = PTHREAD_MUTEX_INITIALIZER;

struct reduction{
    int * array;
    int id;
    int duration;
    int * total;
};

struct padded_int{
    unsigned long long value;
    char padding[56];
}private_sum[12] = {0};

//int private_sum[12];

void* Sum(void* arg){
    struct reduction reductionThis = *((struct reduction*) arg);

    for(int i = reductionThis.id; i < LENGTH; i+=reductionThis.duration){
        private_sum[reductionThis.id].value += reductionThis.array[i];
    }

    pthread_mutex_lock(&myMutex);
    *reductionThis.total += private_sum[reductionThis.id].value;
    pthread_mutex_unlock(&myMutex);
}

void test_induction(int duration){
    int array[LENGTH] = {1,7,2,5,3,7,8,3,5,6};
    int total = 0;

    clock_t start, end;
    pthread_t mThread[duration];
    struct reduction reduction[duration];
    start = clock();
    for(int i = 0; i < duration; i++){
        //printf("for %d\n",i);
        reduction[i].duration = duration;
        reduction[i].array = array;
        reduction[i].id = i;
        reduction[i].total = &total;
        pthread_create(&mThread[i], NULL, Sum, (void*) &reduction[i]);
    }
    for(int i = 0; i < duration; i++){
        pthread_join(mThread[i], NULL);
    }
    end = clock();
    printf("duration is %d, use time %f\n", duration, (double) (end - start)/CLOCKS_PER_SEC);
}



int main() {
    test_induction(1);
    test_induction(2);
    test_induction(3);
    test_induction(4);
    test_induction(5);
    test_induction(6);
    test_induction(7);
    test_induction(8);
    test_induction(9);
    test_induction(10);
    test_induction(11);

    return 0;
}
