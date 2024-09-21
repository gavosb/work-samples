/*
 * A Stack Service in C, with implementation.
 * Intended to be compiled with stack.h and stackServiceTest.c
 * by Gavin Osborn 02/27/2023
 */

/*
 * Imports
 */
# include "stack.h"
# include <stdio.h>
# include <stdlib.h>

/*
 * makeEmptyStack()
 * Creates an empty stack and returns the pointer to the top node.
 * 
 * Parameters:
 * None
 * 
 * Return:
 * stackNode*: NULL as memory address
 */
stackNode *makeEmptyStack(){
	return NULL;
}

/*
 * top()
 * Returns the value of the top node in the stack.
 *
 * Parameters:
 * stackNode*: top - pointer to the stack
 * 
 * Return:
 * EMPTY(-INT8_MAX): stack is empty
 */
int top(stackNode *top){
	if (stackEmpty(top)){
		return EMPTY;
	}else{
		return top->value;
	}
}

/*
 * stackEmpty()
 * Returns true (>1) if the stack is empty, and false (0) if not.
 *
 * Parameters:
 * stackNode*: top - pointer to the stack
 * 
 * Return:
 * int >0: stack is not empty
 * int 0: stack is empty
 */
int stackEmpty(stackNode *top){
	return !top;
}

/*
 * push()
 * Adds a new node to the stack, with given value.
 * 
 * Parameters:
 * stackNode**: top - pointer to a pointer to the stack top
 * int: newValue - value of new stackNode
 *
 * Return:
 * None
 */
void push(stackNode **top, int newValue){
	stackNode *temp = malloc(sizeof(stackNode));
	temp->value = newValue;
	temp->next = *top; // assign temp's next to be the top struct itself
	*top = temp; // pointer to top's pointer becomes address of temp (temp is a pointer data type)
}
 
/*
 * pop()
 * Remove a node from the stack if stack is not empty.
 *
 * Parameters:
 * stackNode**: top - address of a pointer to the stack
 * 
 * Return:
 * -MAX_INT: stack is empty
 * int: any int value otherwise
 */
int pop (stackNode **top){
	
	//pass the pointer to stackEmpty, not the pointer to the pointer
	if (stackEmpty(*top)){
		return EMPTY;
	}else{
		int returnValue = (*top)->value; // save return value (-> dereferences pointer)
		stackNode *temp = *top; // temp = pointer to top
		*top = (*top)->next; // top = (*top)->next
		free(temp); // free memory of old top
		return returnValue;
	}
}

