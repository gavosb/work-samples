/*
 * A Circular Doubly Linked Queue Service/Manager in C
 * Uses a pointer to a queueNode to be removed as well as the tail pointer,
 * for greater polymorphism. User responsible for memory management. 
 * 
 * Operations:
 * - queueNode *makeEmptyQueue();
 * - int queueEmpty (queueNode * tp);
 * - void enqueue (queueNode **tp, queueNode *p);
 * - queueNode *dequeue (queueNode **tp);
 * - queueNode *front (queueNode *tp);
 * - queueNode *outQueue (queueNode **tp, queueNode *p);
 * - - This will dequeue a specific queueNode
 *
 * Data Structures:
 * - struct queueNode
 * - - Primary node for the linked list.
 * - - Contains next and prev pointers
 * - Circular Doubly-Linked Queue
 * - - Head's prev is tail; tail's next is head.
 * - - Empty queue represented by a NULL tailpointer (and head).
 *
 * by Gavin Osborn 03/20/2023
 */

/*
 * Imports
 */
# include "queue.h"
# include <stdio.h>
# include <stdlib.h>

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
 * tp()
 * Returns the value of the front node in the queue,
 * takes pointer to the queue's tail as a parameter.
 *
 * Parameters:
 * queueNode*: tp - pointer to the queue's tail
 * 
 * Return:
 * NULL: queue is empty
 * queueNode* n: front queueNode of queue
 */
queueNode *front(queueNode *tp){
	if (queueEmpty(tp)){
		return NULL;
	}else{
		return (tp->next);
	}
}

/*
 * queueEmpty()
 * Returns true (>1) if the queue is empty, and false (0) if not.
 *
 * Parameters:
 * queueNode*: top - pointer to the queue
 * 
 * Return:
 * int >0: queue is not empty
 * int 0: queue is empty
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
 * Adds a new node to the queue.
 * Takes pointer to queueNode to be added as a parameter.
 * Queue defined by tp doublepointer parameter.
 * 
 * Parameters:
 * queueNode**: tp - pointer to a pointer to the queue tail
 * queueNode*: p - pointer to a node to be dequeued
 *
 * Return:
 * None
 */
void enqueue(queueNode **tp, queueNode *p){
	
	if (queueEmpty(*tp)){ // first node in queue
		*tp = p;
		p->next = p;
		p->prev = p;
	}else{
		p->next = (*tp)->next; // new tail wraps back to front
		p->next->prev = p;
		p->prev = *tp; // prev of new tail is old tail
		(*tp)->next = p; // old tail points to new tail
		*tp = p; // update tail
	}
}
 
/*
 * dequeue()
 * Remove front node from the queue if queue is not empty.
 *
 * Parameters:
 * queueNode**: tp - pointer to a pointer to the queue tail
 * 
 * Return:
 * NULL: queue is empty
 * queueNode* n: dequeued queueNode
 */
queueNode *dequeue(queueNode **tp){
	
	if (queueEmpty(*tp)){ // empty queue case
		return *tp;
	}else{
		queueNode *returnNode = (*tp)->next;
		(*tp)->next = returnNode->next; // new head
		(*tp)->next->prev = *tp; // head's tail set to p
		
		if (returnNode == *tp){ // dequeueing last item in queue
			*tp = NULL;
		}
		return returnNode;
	}
}

/*
 * outQueue()
 * Removes a specific node from a queue and returns it.
 * Time complexity of O(n).
 *
 * Parameters:
 * queueNode**: tp - pointer to a pointer to the queue tail
 * queueNode*: p - pointer to the node to be dequeued
 * 
 * Return:
 * NULL: queue is empty
 * queueNode* n: dequeued queueNode
 */
queueNode *outQueue (queueNode **tp, queueNode *p){
	
	/* empty queue case */
	if (queueEmpty(*tp)){
		return NULL;
	}
	if (*tp == p){ // p is tail
		if (p->next == p){ // p is last node in queue
			*tp = NULL;
			return p;
		}else{ // p is tail but not head
			p->prev->next = (*tp)->next; // new tail's next points to head
			*tp = p->prev; // set new tail
			(*tp)->next->prev = *tp; // reset head's prev to tail
			return p;
		}
	}
	
	// at this point we know p is not the last node
	queueNode* nodePtr = (*tp)->next;
	while (nodePtr != *tp){
		if (nodePtr == p){ // found p
			if ((*tp)->next == p){ // p is head
				dequeue(tp);
			}else{ // p in middle of queue
				p->next->prev = p->prev;
				p->prev->next = p->next;
			}
			return p;
		}
		nodePtr = nodePtr->next;
		if (nodePtr == *tp){ // p not in queue
			return NULL;
		}
	}
}
