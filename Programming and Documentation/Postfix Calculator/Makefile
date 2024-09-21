CC = gcc

all: calculatorStuff

calculatorStuff: calculator.o queue.o stack.o
	$(CC) stack.o queue.o calculator.o -o TheCalculator

clean:
	rm *.o
    
.c.o:
	$(CC) -c $< -o $@

