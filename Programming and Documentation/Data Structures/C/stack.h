#ifndef STACK
#define STACK

#include <stdint.h>

/************************** STACK.H ******************************
*
*  The externals declaration file for the Stack Manager Module
*
*  Written by Mikeyg
*/


typedef struct stackNode {
	int value;
	struct stackNode *next;
} stackNode;

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern stackNode *makeEmptyStack();
extern int stackEmpty(stackNode *top);
extern void push (stackNode **top, int newValue);
extern int top (stackNode *top);
extern int pop (stackNode **top);

/***************************************************************/

#endif
