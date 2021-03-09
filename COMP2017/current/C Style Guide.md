# COMP2017 C Style Guide

## Naming and variables

Naming convention should follow snake case (snake_case). The variable names should contain an underscore where it will normally have a space. Do not use camelCase

Variable names in C should be short and to the point. However, global variables should be more descriptive to ensure they are correctly identified and contain uniqueness. Be careful about mangling names that may be represented in other files. 

Only one variable per a line, do not use multi-variable declaration.
Try to initialise variables to a default value (typically 0 or NULL, depending on the type), this avoids accidental usage of garbage data.

Acceptable:
```
char y = 'y';
int x = 10;
int* tmp_v = malloc(sizeof(int));
```

Awful:
```
char y, x = '1';
int someHiddenKey = 10;
```

### Pointers

Pointer type representation should be compact. The following shows two distinct styles that makes it easily identifiable as a pointer type.

```
int* i = NULL;
```

or

```
int *i = NULL;
```

The above pointer type declarations are acceptable, the latter providing clarity when initialising with multiple variables on one line (although this is discouraged). The former is a little more succinct in its representation of the type.

## File extensions

Use .h for header files and .c for source files. 

## Header file guards

You should always eliminate multiple inclusion through the use of a header guard, typical convention is to use the header file name in uppercase with _H suffix. Do acknowledge when header files and header file guard names can clash with any library or standard library header file.

```
#ifndef MY_HEADER_FILE_H
#define MY_HEADER_FILE_H

...

#endif
```

When working with a large project, you may want to provide a unique identifier with your header guard to attempt to eliminate the possibility of two header files having the same directive name.

## Lines should not exceed 80 characters

* Even with wide screen monitors, default number of columns for terminals is 80 columns.

* The wider the window the fewer windows we can have on a screen. More windows is better than wider windows.


## Constants and Enums

Preprocessor constants, enumerator values and variable constants should be clearly named in UPPER CASE (and snake case!).

Acceptable:
```
#define THIS_IS_A_CONSTANT (42)

enum traffic {
	GREEN = 0,
	YELLOW = 1,
	RED = 2
};
```

Awful:
```
#define x 30
enum traffic {
	green = 0,
	yellow = 1,
	red = 2
};
```

Be careful with defining multiple enum types or including enum types that names may clash with your own definition.

This helps distinguish between lower case variable names and globals/constants/enum types.

## Indentation

Indentation is 4 characters long. (4 spaces, not 4 \t). Your text editor should support soft tabs with the ability to specify the number of whitespace characters. However when working with on other projects, you should adapt to the style of the project.

Rationale: Although not as heretical as other decisions in this document, it appears to be a common standard to use 4 space indentation.

## Braces and Spaces

Containing a space after a keyword is acceptable however it is desirable to not do this for sizeof, alignof or __attribute__.

Keywords such as if, switch, case, for, do, while braces should have a space between the opening brace and the last parenthesis.

Acceptable:
```
if(condition) {

}
```

Acceptable:
```
if (condition) {

}
```

Acceptable:
```
v = sizeof(type)
```

Wrong:
```
v = sizeof( type )
```

Functions follow a space and a curly brace after the final parenthesis however a new line and curly brace after the final parenthesis is acceptable.

Acceptable:
```
void f(int j) {

}
```

Acceptable:
```
void f(int j)
{

}
```

## Typedefs

Typedefs are generally allowed if you feel that it helps with readability of your code, however a few recommendations when using typedef.

Never typedef:
* Pointers (exception being, complex function pointers)
* Other typedefs
* typedef other library structures or typedefs

Acceptable:
```
typedef struct {
	void* data;
	int count;
} container;

...

container c = { NULL, 0 };

```

Never:
```
typedef struct {
	void* data;
	int count;
} container;

typedef container lib_container;

...

container x = ...
lib_container c = x; //Ack! What is this?
```
Why:
This results in confusion between the original type and normal typedef, anyone reading your code may assume that lib_container and container are separate types.

Never:
```
typedef int* IntPtr
```
Why:
This typedef is inflexible and does not improve readability, it's inflexibility is outlined easily when we have a nested pointer ( `IntPtr*` ) effectively making the original typedef useless.

Never:
```
typedef uint32_t u32_t
```
Why:
This `uint32_t` is already a typedef, the name is already short enough to convey meaning of the type and creating a new typedef will result in confusion and unnecessary code.

## Functions

Functions declarations should be made in the header file or at the top of the file (because C's top-down parsing)

```
void my_function();

void my_function() {
	//Your code
}
```

Please do not arbitrarily scatter your function declarations and definitions through out your code. Keep it consistant and easy to manage. Declarations at the top or in a header file and definitions below them.

## Use of goto

Goto should not be used in your code and careless programmers can create problematic bugs with this method. There are instances where goto can be elegant when exiting and cleaning up functions, however, goto lends itself well to creating spaghetti code which goes against what C is trying to achieve.

Simply, just don't do it.

## Magic numbers, Macros and Enums

### Magic Numbers

With the exception of very small programs and cleanly initialising your variables (set to 0, 1 or NULL), variables should not use magic numbers.

In the case where the problem requires a specific number, a macro should be in its place to make your life easier as a programmer and anyone else reading your code.

No:
```
int x = 50;
if (50 < x) {
```

Acceptable:
```
#define OFFSET (50)
int x = 0;
if (OFFSET < x) { 
```


### Macros

Macros should be short, sweet and sanitary. Sanitising your macro is necessary to prevent logic errors in your code. Any variable that you use within a macro should be wrapped around parenthesis to ensure that any placement of multiple variables in its place are cleanly resolved.

Macro names should be in upper snakecase.

Acceptable:
```
#define ADD(x,y) (x) + (y)
```

Awful:
```
#define ADD(x,y) x + y
```

#### Multi-line Macros

It is good advice to avoid writing multi-line macros, however in the event that you will need to write such a macro it is advisable to write them in their own scope and variable names to start with the macro's name.

Acceptable:
```
#define PIN_SET()
TODO: Finish this section
```

### Enums

Enumerator types can be used in place of constants when the constants are related or in effect, represent states or flags.

The trade off between enums and macros is in relation to size of the executable/memory.

## Casting malloc, calloc, realloc

It is preferred when writing portable code to cast malloc, calloc or realloc. This is in desire for your C code to be compatible with C++.

Acceptable:
```
int* p = (int*) malloc(sizeof(int));
```

However, there is a valid argument against casting malloc, calloc or realloc. C implicitly promotes void* to the binding's type, therefore this is a redundant operation.

## No dependent data in conditional expressions

To avoid some sneaky short circuiting bugs, please avoid the use of variable updates conditional expressions.

Problem:
```
int s = 1;
int* p;
//assume f sets p
if(s || f(&p)) {
	int x = *p; //Potential seg fault if short circuit occurs
}
```

Not ideal:
```
int s = 1;
int* p;
//assume f sets p
if(f(&p) || s) {
	int x = *p; //Works but is not good practice
}
```

Ideal:
```
int s = 1;
int* p;
//assume f sets p
if(s) {
	if(f(&p)) {
		int x = *p; //Works but is not good practice
	}
}
```

## Thou shall not #ifdef, #if is all you need

When checking a macro existence or value, you can just use `#if` as an acceptable way of checking. This is flexible enough to detect the value or if the macro has been set.


## End Comment

We are aiming for programmers to maintain consistency, if a section specifies multiple acceptable styles it is implied that you maintain one style or the other, not both.

## References

This style guide was influenced heavily from:

* C Coding Standard - https://users.ece.cmu.edu/~eno/coding/CCodingStandard.html
* Linux Kernel coding style - https://www.kernel.org/doc/html/v4.10/process/coding-style.html

