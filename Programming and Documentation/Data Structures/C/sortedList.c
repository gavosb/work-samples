/*
 * sortedList.c
 *
 * An automatically sorted singly linked linear list of positive integers,
 * which uses sentinel nodes at the ends.
 * 
 * Operations:
 * listNode *makeEmptyList();
 * int listEmpty(listNode *top);
 * int listMinimum (listNode *top);
 * int listMaximum (listNode *top);
 * int listPresent (listNode *top, int value);
 * int listInsert (listNode *top, int value);
 * int listRemove (listNode *top, int value);
 *
 * Data Structures:
 * - struct listNode
 * - - Primary node for the linked list.
 * - - Contains next pointer
 * - - int value > EMPTY (-1)
 * - Auto-sorted List with two sentinels
 * - - Head's prev is tail; tail's next is head.
 * - - Empty list has a sentinel head with a value of 0,
 * - - - and a sentinel tail with a value of INT_MAX
 *
 * by Gavin Osborn 03/27/2023
 */

/*
 * Imports
 */
# include "sortedList.h"
# include <stdio.h>
# include <stdlib.h>

/*
 * makeEmptyList()
 * Creates an empty list and returns the pointer to the first node.
 * 
 * Parameters:
 * None
 * 
 * Return:
 * listNode*: memory address to head node
 */
listNode *makeEmptyList(){
	// allocate head (top) and tail
	listNode *head = malloc(sizeof(listNode));
	listNode *tail = malloc(sizeof(listNode));
	head->value = 0;
	head->next = tail;
	tail->value = MAXINT;
	tail->next = NULL; // should this point to top?
	return head;
}

/*
 * listEmpty()
 * Returns boolean of whether list is empty or not.
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * 
 * Return:
 * int: true (not zero) and false (zero) as defined in header file
 *      represents empty list state boolean
 */
int listEmpty(listNode *top) {
	if (top->next->value != MAXINT){
		return FALSE;
	}
	return TRUE;
}

/*
 * listMinimum()
 * Returns the minimum value in the list
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * 
 * Return:
 * int: minimum value in list, or EMPTY (-1) if list empty
 */
int listMinimum (listNode *top){
	if (listEmpty(top)){
		return EMPTY;
	}else{
		return top->next->value;
	}
}

/*
 * listMaximum()
 * Returns the maximum value in the list
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * 
 * Return:
 * int: maximum value in list, or EMPTY (-1) if list empty
 */
int listMaximum (listNode *top){
	if (listEmpty(top)){
		return EMPTY;
	}else{
		listNode *node = listSearch(top, MAXINT);
		return node->value;
	}
}

/*
 * listPresent()
 * Checks if a value is in the list or not.
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * int value: value to check for
 * 
 * Return:
 * int: true (not zero, is in list) and false (zero, not in list) as defined in header file
 */
int listPresent (listNode *top, int value){
	listNode *node = listSearch(top, value);
	if (node->next->value != value){
		return FALSE;
	}else{
		return TRUE;
	}
}

/*
 * listInsert()
 * Attempts to insert value in the list and returns TRUE or FALSE depending
 * on success of the operation.
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * int value: value to insert
 * 
 * Return:
 * int: true (not zero, added to list) and false (zero, already in list)
        as defined in header file
 */
int listInsert (listNode *top, int value){
	
	listNode *prevNode = listSearch(top, value);
	// check if in list already
	if (prevNode->next->value == value){ // dont want to run search again w/ listPresent()
		return FALSE;
	}
	
	listNode *nextNode = prevNode->next;
	
	listNode *newNode = malloc(sizeof(listNode));
	prevNode->next = newNode;
	newNode->value = value;
	newNode->next = nextNode;
	
	return TRUE;
	
}

/*
 * listRemove()
 * Attempts to remove value from the list and returns TRUE or FALSE depending
 * on success of the operation.
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * int value: value to remove
 * 
 * Return:
 * int: value (removed from list) or EMPTY (-1) (not in list)
 */
int listRemove (listNode *top, int value){
	
	listNode *prevNode = listSearch(top, value);
	listNode *temp = prevNode->next;
	
	// check if in list
	if (temp->value == value){ // dont want to run search again w/ listPresent()
		int returnValue = temp->value;
		prevNode->next = temp->next;
		free(temp);
		return returnValue;
	
	}else{
		return EMPTY; // not in list
	}
	
}

/*
 * listSearch()
 * Searches list for a value and, if present, returns a pointer to the node previous to it.
 * If not present, returns the pointer previous to where the value should be in the list.
 *
 * Parameters:
 * listNode* top: pointer to the head sentinel node of the list
 * int value: value to search for
 * 
 * Return:
 * listNode* ptr: pointer to node previous to the node with the value we searched for,
             Or where it should be.
 */
listNode *listSearch(listNode *top, int value){
	
	listNode *current = top;
	while (current->next->value < value){
		current = current->next;
	}
	return current;
}