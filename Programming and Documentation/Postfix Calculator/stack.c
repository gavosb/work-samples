/*
 * A Stack Service in C.
 * Intended to be compiled with calculator.c
 *
 * Values are character arrays of 8 elements.
 * Handles all memory management.
 *
 * Operations:
 * - makeEmptyStack()
 * - - creates an empty stack, by returning null.
 * - stackEmpty()
 * - - returns T/F dependening on whether stack is empty (NULL).
 * - top()
 * - - returns the value of the top node in the stack.
 * - push()
 * - - pushes a given value onto the stack by creating a new node.
 * - pop()
 * - - pops a node off the stack and returns the value by altering a return parameter.
 *
 * by Gavin Osborn 02/27/2023, modified 04/23/2023
 */

/*
 * Imports
 */
# include "stack.h"
# include <stdio.h>
# include <stdlib.h>
# include <string.h>

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
 * - Returns the value of the top node in the stack.
 *
 * Parameters:
 * - stackNode*: top - pointer to the stack
 * 
 * Return:
 * - NULL: stack is empty
 * - char: top's value
 */
char* top(stackNode *top){
	if (stackEmpty(top)){
		return NULL;
	}else{
		return top->value;
	}
}

/*
 * stackEmpty()
 * - Returns true (1) if the stack is empty, and false (0) if not.
 *
 * Parameters:
 * - stackNode*: top - pointer to the stack
 * 
 * Return:
 * - TRUE (1): stack is not empty
 * - FALSE (0): stack is empty
 */
int stackEmpty(stackNode *top){
	if (top == NULL){
		return TRUE;
	}else{
		return FALSE;
	}
}

/*
 * push()
 * - Adds a new node to the stack, with given value.
 * 
 * Parameters:
 * - stackNode**: top - pointer to a pointer to the stack top
 * - char*: newValue - reference to the first element of a char array, the value of new stackNode
 *
 * Return:
 * - None
 */
void push(stackNode **top, char* newValue){
	stackNode *temp = malloc(sizeof(stackNode));
	strncpy(temp->value, newValue, 8);
	temp->next = *top; // assign temp's next to be the top struct itself
	*top = temp; // pointer to top's pointer becomes address of temp (temp is a pointer data type)
}
 
/*
 * pop()
 * Remove a node from the stack if stack is not empty.
 * Uses a parameter return.
 *
 * Parameters:
 * stackNode**: top - address of a pointer to the stack
 * 
 * Return:
 * - Because we use token as a parameter return, it is modified.
 * - Token is NULL: stack is empty.
 * - Token is first element of char array: stack not empty, returned value.
 */
void pop (stackNode **top, char *token){
	
	//pass the pointer to stackEmpty, not the pointer to the pointer
	if (stackEmpty(*top)){
		token = NULL;
	}else{
		strcpy(token, (*top)->value);
		stackNode *temp = *top; // temp = pointer to top
		*top = (*top)->next; // top = (*top)->next
		free(temp); // free memory of old top
		return;
	}
}

