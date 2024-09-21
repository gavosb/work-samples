#ifndef CALCULATOR
#define CALCULATOR

# include "stack.h"
# include "queue.h"
# include <stdint.h>

/************************** CALCULATOR.H ******************************
*
*  The externals declaration file for the Calculator Manager Module
*
*  by G. C. Osborn
*/

#define TRUE  1
#define FALSE 0
#define EMPTY -INT8_MAX


extern int main();
extern void readInput(queueNode **queuePtr);
extern int isOperator(char* token);
extern void sumPostFix(queueNode **queuePtr, stackNode **stackPtr);
extern void convertInfixToPostfix(queueNode **inputQueuePtr, queueNode **postfixQueuePtr, stackNode **stackPtr);
extern int evaluateOperatorPrecedence(char *token);
extern int topOperatorPrecedence(stackNode *stackPtr, char *token);
extern int askInputType();

/***************************************************************/

#endif
