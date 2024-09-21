#ifndef SORTEDLIST
#define SORTEDLIST

#include <stdint.h>

/*********************** SORTEDLIST.H **************************
*
*  The externals declaration file for the Sorted List Service 
*
*  Written by Mikeyg
*/


typedef struct listNode {
	int value;
	struct listNode *next;
} listNode;

#define TRUE  1
#define FALSE 0
#define MAXINT INT8_MAX
#define EMPTY -1

extern listNode *makeEmptyList();
extern int listEmpty(listNode *top);
extern int listMinimum (listNode *top);
extern int listMaximum (listNode *top);
extern int listPresent (listNode *top, int value);
extern int listInsert (listNode *top, int value);
extern int listRemove (listNode *top, int value);
listNode *listSearch(listNode *top, int value);

/***************************************************************/

#endif
