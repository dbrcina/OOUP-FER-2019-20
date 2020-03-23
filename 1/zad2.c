#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "zad2.h"

struct unaryFunction {
	int lowerBound;
	int upperBound;
	void (*print)(struct unaryFunction*);
	PTRFUN* vTable;
};
void unaryFunctionConstructor(UnaryFunction* f, int lowerBound, int upperBound, PTRFUN* vTable) {
	f->lowerBound = lowerBound;
	f->upperBound = upperBound;
	f->print = tabulate;
	f->vTable = vTable;
}
double negativeValueAt(UnaryFunction* f, double x) {
	return -f->vTable[0](f, x);
}

struct square {
	UnaryFunction base;
};
PTRFUN squareVTable[2] = {valueAtSquare, negativeValueAt};
void squareConstructor(Square* square, int lowerBound, int upperBound) {
	unaryFunctionConstructor((UnaryFunction*)square, lowerBound, upperBound, squareVTable);
}
UnaryFunction* newSquare(int lowerBound, int upperBound) {
	Square* square = (Square*)malloc(sizeof(Square));
	squareConstructor(square, lowerBound, upperBound);
	return (UnaryFunction*)square;
}
double valueAtSquare(UnaryFunction* f, double x) {
	return x * x;
}

struct linear {
	UnaryFunction base;
	double a;
	double b;
};
PTRFUN linearVTable[2] = {valueAtLinear, negativeValueAt};
void linearConstructor(Linear* linear, int lowerBound, int upperBound, double a, double b) {
	unaryFunctionConstructor((UnaryFunction*)linear, lowerBound, upperBound, linearVTable);
	linear->a = a;
	linear->b = b;
}
UnaryFunction* newLinear(int lowerBound, int upperBound, double a, double b) {
	Linear* linear = (Linear*)malloc(sizeof(Linear));
	linearConstructor(linear, lowerBound, upperBound, a, b);
	return (UnaryFunction*)linear;
}
double valueAtLinear(UnaryFunction* f, double x) {
	Linear* l = (Linear*)f;
	return l->a * x + l->b;
}

void tabulate(UnaryFunction* f) {
	int lowerBound = f->lowerBound;
	int upperBound = f->upperBound;
	for (int x = lowerBound; x <= upperBound; x++) {
		printf("f(%d)=%.3lf\n", x, f->vTable[0](f, x));
	}
}

bool sameFunctionsForInts(UnaryFunction* f1, UnaryFunction* f2, double tolerance) {
	if (f1->lowerBound != f2->lowerBound) return false;
	if (f1->upperBound != f2->upperBound) return false;
	for (int x = f1->lowerBound; x <= f1->upperBound; x++) {
		double delta = f1->vTable[0](f1, x) - f2->vTable[0](f2, x);
		if (delta < 0) delta = -delta;
		if (delta > tolerance) return false;
	}
	return true;
}

int main(int argc, char const *argv[]) {
	UnaryFunction* f1 = newSquare(-2, 2);
	printf("Square function: f1(x) = x * x\n");
	f1->print(f1);
	printf("\n");

	printf("Linear function: f2(x) = 5 * x - 2\n");
	UnaryFunction* f2 = newLinear(-2, 2, 5, -2);
	f2->print(f2);
	printf("\n");

	printf("f1==f2: %s\n", sameFunctionsForInts(f1, f2, 1E-6) ? "DA" : "NE");
	printf("neg_val f1(1) = %.3lf\n", f1->vTable[1](f1, 1.0));
  	printf("neg_val f2(1) = %.3lf\n", f2->vTable[1](f2, 1.0));

	free(f1);
	free(f2);
	return 0;
}
