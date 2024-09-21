/*
 * A Circular Doubly Linked Queue Service/Manager in C
 *
 * Values are character arrays of 8 elements.
 * Handles all memory management.
 *
 * Operations:
 * - makeEmptyQueue()
 * - - creates an empty queue, returning NULL
 * - front()
 * - - returns the value of the front node in the queue
 * - queueEmpty()
 * - - Returns T/F as determined by presence of NULL pointer for tp
 * - enqueue()
 * - - enqueues a given char array
 * - dequeue()
 * - - dequeues a node and modifies the parameter return value with new char array ptr
 *
 * by G. C. Osborn 03/11/2023, modified 04/23/2023
 */

/*
 * Imports
 */
# include "queue.h"
# include <stdio.h>
# include <stdlib.h>
# include <string.h>

/*
 * makeEmptyQueue()
 * Creates an empty queue and returns the pointer to the tail node (NULL).
 * 
 * Parameters:
 * None
 * 
 * Return:
 * queueNode*: NULL as memory address
 */
queueNode *makeEmptyQueue(){
	return NULL;
}

/*
 * front()
 * Returns the value of the front node in the queue.
 *
 * Parameters:
 * queueNode*: tp - pointer to the queue's tail
 * 
 * Return:
 * - NULL: queue is empty
 * - char* value: first element of char array
 */
char* front(queueNode *tp){
	if (queueEmpty(tp)){
		return NULL;
	}else{
		return tp->next->value; // tail pointer's next is front
	}
}

/*
 * queueEmpty()
 * Returns TRUE (1) if the queue is empty, and FALSE (0) if not.
 *
 * Parameters:
 * queueNode*: top - pointer to the queue
 * 
 * Return:
 * TRUE (1): queue is not empty
 * FALSE (0): queue is empty
 */
int queueEmpty(queueNode *tp){
	if (tp == NULL){
	    return TRUE;
	}else{
	    return FALSE;
	}
}

/*
 * enqueue()
 * Adds a new node to the queue, with given char* value.
 * 
 * Parameters:
 * queueNode**: tp - pointer to a pointer to the queue tail
 * char*: value - pointer to first element of char array
 *
 * Return:
 * None
 */
void enqueue(queueNode **tp, char *value){
	queueNode *temp = malloc(sizeof(queueNode));
	strcpy(temp->value, value);
	if (queueEmpty(*tp)){
		*tp = temp;
		temp->next = temp;
		temp->prev = temp;
	}else{
		temp->next = (*tp)->next; // new tail wraps back to front
		temp->prev = *tp; // prev of new tail is old tail
		(*tp)->next = temp; // old tail points to new tail
		*tp = temp; // update tail
	}
}
 
/*
 * dequeue()
 * Remove front node from the queue if queue is not empty.
 *
 * Parameters:
 * queueNode**: tp - pointer to a pointer to the queue tail
 * char*: token - pointer to first element of char array
 *
 * Return:
 * char* token is a parameter return;
 * - NULL: queue is empty
 * - char* value: first element of char array updated w/ strcpy
 */
void dequeue(queueNode **tp, char *token){
	
	//pass the pointer to queueEmpty, not the pointer to the pointer
	if (queueEmpty(*tp)){
	    token = NULL;
		return;
	}else{
		strcpy(token, (*tp)->next->value);
		queueNode *temp = (*tp)->next; // temp = pointer to front
		
		if ((*tp)->next == *tp){ // dequeueing last item in queue
			free(temp);
			*tp = NULL;
			return;
		}
		
		(*tp)->next = temp->next; // new front = oldfront.next
		(*tp)->next->prev = *tp; // new front's prev set to tail
		free(temp); // free memory of old front
		return;
	}
}

