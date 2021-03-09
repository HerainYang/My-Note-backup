#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <wait.h>

void testProcessHello(){
    printf("this is file main.c!\n");
    printf("before execl!\n");
    int pid;
    if((pid = fork()) == 0){
        execl("./hello", 0);
    }

    printf("after execl!\n");
}
int tellMeChildrenEnd(int sig){
    printf("A Children Is End\n");
    return sig;
}

void handler(int signal)
{
    //printf("recv the signal from parent process\n");
}

void testEndProcess(){
    int pid;
    struct sigaction newAction;
    newAction.sa_handler = tellMeChildrenEnd;
    sigaction(SIGCHLD, &newAction, NULL);
    if((pid = fork()) == 0){
        printf("child: inside the child and pid = %d\n", getpid());
        signal(SIGALRM, handler);
        printf("child: going to stop\n");
        fflush(stdout);
        kill(getpid(), SIGSTOP);
        //pause();
        printf("child: %d continue!\n", getpid());
        fflush(stdout);
        //exit(0);
    } else {
        int status;
        printf("parent: into the parent\n");
        fflush(stdout);
        waitpid(pid, &status, WUNTRACED);
        printf("parent: no waiting and return status is %d\n", status);
        fflush(stdout);
        if(WIFSTOPPED(status)){
            printf("parent: children is stop\n");
        }
        printf("parent: pid = %d\n", pid);
        //kill(pid, SIGCONT);
        waitpid(-1, NULL, 0);
//    while(1)
//    {
//        printf("inside the parent and pid = %d\n", getpid());
//        printf("***************************************\n");
//        fflush(stdout);
//        sleep(1);
//    }
    }
}

void testSleepAndResume(){
    int pid;
    struct sigaction newAction;
    newAction.sa_handler = tellMeChildrenEnd;
    sigaction(SIGCHLD, &newAction, NULL);
    if((pid = fork()) == 0){
        printf("child: inside the child and pid = %d\n", getpid());
        signal(SIGALRM, handler);
        printf("child: going to sleep\n");
        fflush(stdout);
        pause();
        printf("child: %d continue!\n", getpid());
        fflush(stdout);
        exit(0);
    }
    sleep(3);
    kill(pid, SIGALRM);
    fflush(stdout);
}

void testStopAndResume(){
    int pid;
    struct sigaction newAction;
    newAction.sa_handler = tellMeChildrenEnd;
    sigaction(SIGCHLD, &newAction, NULL);
    if((pid = fork()) == 0){
        printf("child: inside the child and pid = %d\n", getpid());
        signal(SIGALRM, handler);
        printf("child: going to stop\n");
        fflush(stdout);
        kill(getpid(), SIGSTOP);
        printf("child: %d continue!\n", getpid());
        fflush(stdout);
    } else {
        int status;
        printf("parent: into the parent\n");
        fflush(stdout);
        waitpid(pid, &status, WUNTRACED);
        printf("parent: no waiting and return status is %d\n", status);
        fflush(stdout);
        if (WIFSTOPPED(status)) {
            printf("parent: children is stop\n");
            fflush(stdout);
            kill(pid, SIGCONT);
        }
    }
}


int main(int argc, char *argv[], char *envp[])
{
    //testStopAndResume();
    /*
    int i = 0;
    printf("string %p\n", "constant string");
    printf("address of argv[1] is %p\n", argv[1]);
    printf("address of i %p\n", &i);
    while (1)
    {
        printf("%s\n", envp[i]);
        i++;
        if (envp[i] == NULL)
            break;
    }
    return 0;
     */
    testProcessHello();
}
