#ifndef QUEUE
#define QUEUE

#include <stdint.h>

/************************** QUEUE.H ******************************
*
*  The externals declaration file for the Queue Manager Module
*
*  by G. C. Osborn
*/


typedef struct queueNode {
  int value;
  struct queueNode *next, *prev;
} queueNode;

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern queueNode *makeEmptyQueue();
extern int queueEmpty (queueNode * tp);
extern void enqueue (queueNode **tp, queueNode *p);
extern queueNode *dequeue (queueNode **tp);
extern queueNode *front (queueNode *tp);
extern queueNode *outQueue (queueNode **tp, queueNode *p);

/***************************************************************/

#endif
