#ifndef NARYTREE
#define NARYTREE

#include <stdint.h>

/************************ NARYTREE.H ******************************
*
*  The externals declaration file for the n-ary Tree Service
*
*  Written by Mikeyg
*/


typedef struct treeNode {
	int value;
	struct treeNode *prnt, *child, *nextSib, *prevSib;
} treeNode;

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern void insertChild (treeNode *prnt, treeNode *p);
extern treeNode *removeChild (treeNode *prnt);
extern int emptyChild (treeNode *prnt);
extern treeNode *firstChild (treeNode *prnt);
extern treeNode *outChild (treeNode *p);

/***************************************************************/

#endif
