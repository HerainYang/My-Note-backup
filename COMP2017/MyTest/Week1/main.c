#include <stdio.h>
#include <string.h>
int main() {

    int i = 1;
    int *j;
    j = &i;
    printf("%x\n", &i);
    printf("%x\n", j);
    printf("************\n");
    char *word = "POINTERS";
    char *word_array3[] = {"POINTERS"}; //array's name point the first element of the array, this is a array of pointers.
    char **word_array4[] = {&word, word_array3}; // this is a array of address of pointers.
    //word_array4 point to {the address of [the address of (the address of word)]}
    printf("%c\n", *(*word_array3+2)); // read word_array3
    printf("%c\n", *(**word_array4+1)); // read word_array4 -> &word
    printf("%c\n", ***(word_array4+1));
    /*
     * word_array4+1 -> address of word_array3
     * *(word_array4+1) -> word_array3
     * **(word_array4+1) -> address of P
     * ***(word_array4+1) -> P
     */
    printf("%x\n", word_array3);//address of pointer (address) of "POINTERS"
    printf("%x\n", *word_array3);//address of P
    printf("%x\n", **word_array3);//P
    return 0;
}
