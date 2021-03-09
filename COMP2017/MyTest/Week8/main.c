
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/shm.h>

void sys_err(const char *str)
{
    perror(str);
    exit(1);
}

void testMyPipe(){
    pid_t pid;
    char buf[1024];
    int fd[2];
    char *p = "test for pipe\n";

    if (pipe(fd) == -1)
        sys_err("pipe");

    pid = fork();
    if (pid < 0) {
        sys_err("fork err");
    } else if (pid == 0) {
        close(fd[1]);
        printf("read 1\n");
        fflush(stdout);
        sleep(100);
        int len = read(fd[0], buf, sizeof(buf));
        write(STDOUT_FILENO, buf, len);
        printf("read 2\n");
        fflush(stdout);
        len = read(fd[0], buf, sizeof(buf));
        write(STDOUT_FILENO, buf, len);
        close(fd[0]);
    } else {
        close(fd[0]);
        int i = 0;
        //fcntl(fd[1], F_SETFL, O_NONBLOCK);
        while(1){
            write(fd[1], p, strlen(p));
            printf("%d\n", i++);
            printf("errno is %d\n", errno);
            fflush(stdout);
        }

        //wait(5);
        write(fd[1], p, strlen(p));
        close(fd[1]);
    }
}

void testPipeFilled(){
    int fds[2];
    if(pipe(fds) == -1){
        perror("pipe error");
        exit(EXIT_FAILURE);
    }
    int ret;
    int count = 0;
    int flags = fcntl(fds[1],F_GETFL);
    fcntl(fds[1],F_SETFL,flags|O_NONBLOCK);
    while(1){
        ret = write(fds[1],"A",1);//fds[1] is blocking mode by default
        if(ret == -1){
            printf("%d\n", errno);
            perror("write error");
            break;
        }
        count++;
    }
    printf("the pipe capcity is = %d\n",count);

}

void test_select(){
    int fdp = open("testFile.txt", O_RDONLY);
    int fdp2 = open("testFile.txt", O_WRONLY);

    struct timeval timeout = {3, 0};
    fd_set fds;

    FD_ZERO(&fds);
    FD_SET(fdp, &fds);
    FD_SET(fdp2, &fds);
    int result = select(fdp2+1, NULL, &fds, NULL, &timeout);
    printf("%d\n", result);
    if(FD_ISSET(fdp, &fds))
    {
        printf("it is writable");
    } else {
        printf("nothing is writable");
    }


    close(fdp);
}

void test_blocking(){
    char text[100];
    fcntl(0, F_SETFL,fcntl(0, F_GETFL) | O_NONBLOCK);
    int result = read(0, text, 95);
    printf("%s", text);
}

void parent(int writefd){
    char buffer[100];
    while(1){
        printf("> ");
        fflush(stdout);

        int nread = read(0, buffer, 100);
        if(-1 == nread){
            perror("could not read stdin");
        } else if (nread == 0) {
            perror("end of file for stdin?");
        } else {
            buffer[nread] = '\0';
            if(strncmp(buffer, "child", 5) == 0){
                if(strncmp(buffer, "child die", 9) == 0){
                    write(writefd, "kamikaze", 9);
                } else {
                    size_t blen = strlen(buffer) + 1;
                    size_t max_len = blen > 95? 95 : blen;
                    write(writefd, buffer+5, max_len);
                }
            } else if (strncmp(buffer, "quit", 4) == 0){
                printf("terminating child\n");
                write(writefd, "kamikaze", 8);
                printf("terminating self");
                break;
            } else {
                printf("%s ignored\n", buffer);
            }
        }
    }
}

void child(int readfd){
    fd_set allfds;
    char cbuffer[100];
    int nfds = readfd+1;
    long delay_sec = 5;
    struct timeval tv;
    while(1){
        FD_ZERO(&allfds);
        FD_SET(readfd, &allfds);
        tv.tv_sec = delay_sec;
        tv.tv_usec = 0;
        int ret = select(nfds, &allfds, NULL, NULL, &tv);
        if(-1 == ret){
            perror("select() failed");
        } else if(ret) {
            if(FD_ISSET(readfd, &allfds)){
                ssize_t nread;
                printf("message from the mothership: ");
                nread = read(readfd, cbuffer, 10);
                printf("%s\n", cbuffer);
                if(strncmp(cbuffer, "kamikaze", 8) == 0)
                    break;
            }
        }
        sleep(2);
        printf("CHILD: all is well\n");

    }
    printf("CHILD is no more\n");
}

void testPipeIPC(){
    int notify[2];
    int ret = pipe(notify);
    printf("pipe created: %d, %d", notify[0], notify[1]);
    int pid = fork();
    if(0 == pid){
        close(notify[1]);
        child(notify[0]);
        close(notify[0]);
    } else {
        close(notify[0]);
        parent(notify[1]);
        parent(notify[1]);
    }
}

void Client(char *addr){
    int i = 0;
    while(i < 26)
    {
        addr[i] = 'A' + i;
        i++;
        addr[i] = 0;
    }
}
void Server(char *addr){
    sleep(3);
    printf("%s", addr);
}

int main(void) {
    int key = ftok("/tmp", 66);
    int shmid = shmget(key, 4096, IPC_CREAT);
    printf("%d\n", shmid);
    char *addr = shmat(shmid, 0xafd08000, O_WRONLY);
    printf("%x", addr);

    if(fork() == 0){
        Client(addr);
    } else {
        Server(addr);
    }
    struct shmid_ds getInfo;
    shmctl(shmid, IPC_STAT, &getInfo);
    printf("now %ld is using shared memory\n", getInfo.shm_nattch);
    //perror("the error is:");
    shmctl(shmid, IPC_RMID, NULL);
}