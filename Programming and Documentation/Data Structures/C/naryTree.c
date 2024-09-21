/*
 * naryTree.c
 *
 * An implementation of a tree service,
 * in which each node can have an unlimited number of child nodes.
 * Each node holds an integer value and keeps its children in a stack.
 *
 * Operations:
 * void insertChild (treeNode *prnt, treeNode *p);
 * treeNode *removeChild (treeNode *prnt);
 * int emptyChild (treeNode *prnt);
 * - returns whether parent has children, T/F.
 * treeNode *firstChild (treeNode *prnt);
 * - peeks the top child in the stack.
 * treeNode *outChild (treeNode *p);
 * - removes a child from the middle of the child stack.
 *
 * Data Structures:
 * - struct treeNode
 * - - Node within the tree structure
 * - - Holds a double pointer to parent, and pointers to
 * - - - its own child list, and then next and prev pointers to its siblings.
 * - - - the child list pointer is a tail pointer.
 *
 * - n-ary tree
 * - - Children are kept in a doubly linked list.
 * - - - This list implements a stack structure.
 * - - Empty tree has a NULL pointer as a child list.
 * - - A tree root has a NULL pointer as its parent.
 *
 * by Gavin Osborn 04/03/2023
 */

/*
 * Imports
 */
# include "naryTree.h"
# include <stdio.h>

/*
 * insertChild()
 * Pushes a child node to a given parent node's child stack.
 * 
 * Parameters:
 * treeNode* prnt - to adopt child
 * treeNode* p - to be pushed
 * 
 * Return:
 * None
 */
void insertChild (treeNode *prnt, treeNode *p){
	if (emptyChild(prnt)){
		prnt->child = p;
		p->prnt = prnt;
		return;
	}
	treeNode *top = prnt->child;
	p->nextSib = top;
	top->prevSib = p;
	p->prevSib = NULL;
	prnt->child = p;
	p->prnt = prnt;
	return;
}

/*
 * removeChild()
 * Pops a child node from the parent's child stack.
 * 
 * Parameters:
 * treeNode* prnt - parent which will have a child removed
 * 
 * Return:
 * treeNode* - popped child
 */   
treeNode* removeChild (treeNode *prnt){
	if (emptyChild(prnt)){ // no children
		return NULL;
	}
	treeNode *top = prnt->child;
	
	if (top->nextSib == NULL){ // p is last node
		prnt->child = NULL;
	}else{
		prnt->child = top->nextSib;
	}
	top->prnt = NULL;
	top->nextSib = NULL;
	top->prevSib = NULL;
	return top;
}

/*
 * emptyChild()
 * Returns "boolean" predicate as to whether a parent has children.
 * 
 * Parameters:
 * treeNode* prnt - parent which will be investigated
 * 
 * Return:
 * TRUE (1): parent has >0 children
 * FALSE (0): parent has no children
 */   
int emptyChild (treeNode *prnt){
	if (prnt->child == NULL){
		return TRUE;
	}else{
		return FALSE;
	}
}

/*
 * firstChild()
 * Peeks a parent's child stack and returns the top child.
 * 
 * Parameters:
 * treeNode* prnt - parent which will be peeked
 * 
 * Return:
 * treeNode* - top child
 */       
treeNode *firstChild (treeNode *prnt){
	if (emptyChild(prnt)){
		return NULL;
	}else{
		return prnt->child;
	}
}

/*
 * outChild()
 * Removes a child (and thus a subtree) from a parent's child stack,
 * in an arbitrary location. This will make the child the root of its own tree.
 * 
 * Parameters:
 * treeNode* prnt - parent which will have a child removed
 * 
 * Return:
 * treeNode* - child (subtree) that has been removed
 */       
treeNode *outChild (treeNode *p){
	
	treeNode *parent = p->prnt;
	
	/* p already root node */
	if (parent == NULL){
		return NULL;
	}
	treeNode *top = parent->child;
	/*p is top node*/
	// p <- ... <- head OR p
	if (top == p){
		return removeChild(parent);
	}
	
	/*p is not last node, nor tailpointer*/
	//tp <- ... <- p OR tp <- ... <- p <- ... <- head
	
	if (p->nextSib == NULL){ // p is head
		p->prevSib->nextSib = NULL;
		p->prnt = NULL;
		p->nextSib = NULL;
		p->prevSib = NULL;
		return p;
	}
	// n+1 <- n <- n-1
	p->nextSib->prevSib = p->prevSib; // n+1's prev is n-1
	p->prevSib->nextSib = p->nextSib; // n-1's next is n+1
	p->prnt = NULL;
	p->nextSib = NULL;
	p->prevSib = NULL;
	return p;
}
