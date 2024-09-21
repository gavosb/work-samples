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
  char value[8];
  struct queueNode *next, *prev;
} queueNode;

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern queueNode *makeEmptyQueue();
extern int queueEmpty (queueNode * tp);
extern void enqueue (queueNode **tp, char* value);
extern void dequeue(queueNode **tp, char *token);
extern char* front (queueNode *tp);

/***************************************************************/

#endif
