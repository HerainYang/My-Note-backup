#include <stdio.h>
#include "headerB.h"
#include "decs.h"
#include <math.h>


extern int tellMin(int a, int b){
    return a>b?b:a;
}

int main() {
#ifdef DEBUG
    printf("MyProg(debug version)\n");
#else
    printf("MyProg(production version)\n");
#endif
    printf("debug: line %d in file %s\n", __LINE__, __FILE__);
    double a = 5;
    printf("%f\n", sqrt(a));
    return 0;
}
