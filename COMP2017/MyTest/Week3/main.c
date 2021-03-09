#include <stdio.h>
#include <stdlib.h>
enum day_name
{
    Sun, Mon, Tue, Wed, Thu, Fri, Sat, day_undef
};

enum month_name
{
    Jan, Feb, March, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
};

struct date {
    enum day_name day;
    int day_num;
    enum month_name month;
    int year;
} Big_day = {
    Mon, 8, Jan, 2000,
};

union try{
    int a;
    double b;
    char c[9];
};

struct test{
    unsigned R_W:1;
    unsigned Dirn:8;
    unsigned mode:3;
};

void delay(unsigned long long ctr)
{
    while (ctr > 0)
        --ctr;
}

int main(void) {
    FILE *file = fopen("testfile.txt", "a+");
    if(file == NULL){
        printf("NULL");
    }
    //fprintf(file, "hello you!");
    int num = 1;
    char *ch;
    ch = malloc(sizeof(char)*100);
    rewind(file);
    while(!feof(file)){
        fscanf(file, "%s", ch);
        printf("%s\n", ch);
    }
    printf("*************\n");
    rewind(file);
    fgets(ch, 100, file);
    printf("%s\n", ch);
    fclose(file);

    int a;
    char c;
    scanf("%d", &a);
    fflush(stdout);
    c = getchar();
    printf("a = %d, c = %c \n", a, c);
    return 0;
}
