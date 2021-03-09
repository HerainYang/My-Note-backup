#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <errno.h>
#include <fcntl.h>

void hello(int x){
    printf("%d\n", x);
    printf("Hello, World!\n");
}

void goodbye(int x){
    printf("%d\n", x);
    printf("Goodbye, World!\n");
}

void saySomething(void (*sayHello)(int), void (*sayGoodbye)(int), int sayWhat){
    if(sayWhat){
        sayHello(sayWhat);
    } else {
        sayGoodbye(sayWhat);
    }
}

volatile int flag = 0;
void interrupted(int sig) {
    flag = 1;
    printf("Interrupt signal\n");
}

void signalTest(){
    signal(SIGINT, interrupted);
    printf("Now we wait...\n");
    fflush(stdout);
    while (!flag)
        usleep(10);
    printf("Oh..you didn't like waiting\n");
    printf("Program terminated\n");
}

void errnoTest(){
    FILE *fptr = fopen("doesn't exist file", "r");
    if(fptr == NULL){
        printf("this is NULL\n");
    }
    printf("errno: %d", errno);
}

void readTest(){
    char buffer[10];
    ssize_t result = read(0, buffer, 10);
    for(int i = 0; i < 10; i++){
        printf("%c", buffer[i]);
    }
}

void readWriteFile(){
    int fd = open("test.txt", O_RDWR);
    write(fd, "this is a test A", 16);
    lseek(fd, 0, SEEK_SET);
    char buffer[14];
    read(fd, buffer, 14);
    for(int i = 0; i < 14; i++){
        printf("%c", buffer[i]);
    }
}

void ouch(int sig){
    printf("I got signal %d\n", sig);

    for(int i = 0; i < 5; i++){
        printf("signal func %d\n", i);
        sleep(1);
    }
}

void ouch2(int sig){
    printf("2:I got signal %d\n", sig);

    for(int i = 0; i < 5; i++){
        printf("2:signal func %d\n", i);
        sleep(1);
    }
}

void testSigaction(){
    struct sigaction act, act2,act_o;
    act.sa_handler = ouch;
    sigemptyset(&act.sa_mask);
    sigaddset(&act.sa_mask, SIGINT);
    act.sa_flags = 0;

    act2.sa_handler = ouch2;
    sigemptyset(&act2.sa_mask);
    sigaddset(&act2.sa_mask, SIGINT);
    act2.sa_flags = 0;

    sigaction(SIGINT, &act, NULL);

    printf("%p\n", act_o.sa_handler);

    sigaddset(&act2.sa_mask, SIGQUIT);
    act2.sa_flags = SA_NODEFER;
    sigaction(SIGINT, &act2, &act_o);
    printf("%p\n", act.sa_handler);

    //alarm(5);

    while(1)
    {
        printf("now is %p\n", act_o.sa_handler);
        fflush(stdout);
        sleep(1);
    }
}

void ouchh(int sig){
    printf("\n OUCH !");
}

void test_SA_RESETHAND(){
    struct sigaction act;
    act.sa_handler = ouchh;

    // 创建空的信号屏蔽字，即不屏蔽任何信息
    sigemptyset(&act.sa_mask);

    // 使sigaction函数重置为默认行为
    act.sa_flags = SA_RESETHAND;

    sigaction(SIGQUIT, &act, 0);

    while(1)
    {
        printf("Hello World!\n");
        sleep(1);
    }
}

void show_handler(int sig)
{
    printf("I got signal %d\n", sig);
    int i;
    for(i = 0; i < 5; i++)
    {
        printf("i = %d\n", i);
        sleep(1);
    }
}

void test_SA_NODEFER(){
    int i = 0;
    struct sigaction act, oldact;
    act.sa_handler = show_handler;
    //sigaddset(&act.sa_mask, SIGQUIT);
    act.sa_flags = SA_NODEFER;
    //act.sa_flags = 0;

    sigaction(SIGINT, &act, &oldact);
    while(1)
    {
        sleep(1);
        printf("sleeping %d\n", i);
        fflush(stdout);
        i++;
    }
}

void oldmain(){
    struct sigaction new_sig_int;
    new_sig_int.sa_handler = interrupted;
    new_sig_int.sa_flags = 0;

    sigaction(SIGINT, &new_sig_int, NULL);

    char buffer[100];
    ssize_t result = read(0, buffer, 100);
    int error_val = errno;
    printf("%d\n", error_val);
}

int main(void)
{
    test_SA_RESETHAND();
}
