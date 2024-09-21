#ifndef STACK
#define STACK

#include <stdint.h>

/************************** STACK.H ******************************
*
*  The externals declaration file for the Stack Manager Module
*
*  Written by Mikeyg, modified by G. C. Osborn
*/


typedef struct stackNode {
	char value[8];
	struct stackNode *next;
} stackNode;

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern stackNode *makeEmptyStack();
extern int stackEmpty(stackNode *top);
extern void push (stackNode **top, char* newValue);
extern char* top (stackNode *top);
extern void pop (stackNode **top, char *token);

/***************************************************************/

#endif
