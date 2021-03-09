//
// Created by herain on 2/2/21.
//
#include <stdio.h>
enum day_week{
    Sunday, Monday, Tuesday, Wednesday, Thurs, Friday, Saturday
};
int main(){
    int a[] = {1, 2, 3, 4};
    printf("%x\n", *a);
    printf("%x\n", &a[0]);
    int *b = a;
    printf("%x\n", b);
    printf("%x\n", b+1);
    char c[] = {'a', 'b', 'c'};
    b = &c;
    printf("%x\n", b);
    printf("%x\n", b+1);

    printf("%lu\n", sizeof(int*));
    printf("%lu\n", sizeof(double*));

    char fileheader[] = {'P', '1'};
    const char *dataptr = (char*)fileheader;
    char *p = (char*)dataptr;
    p[1] = '3';
    printf("%c\n", *(dataptr+1));

    enum day_week day = Wednesday;
    day++;
    printf("%d\n", day);
}
