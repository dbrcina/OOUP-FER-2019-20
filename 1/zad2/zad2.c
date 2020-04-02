#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "zad2.h"

bool sameFunctionsForInts(UnaryFunction *f1, UnaryFunction *f2, double tolerance) {
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
	UnaryFunction *f1 = newSquare(-2, 2);
	printf("Square function: f1(x) = x * x\n");
	f1->print(f1);

	printf("\nLinear function: f2(x) = 5 * x - 2\n");
	UnaryFunction *f2 = newLinear(-2, 2, 5, -2);
	f2->print(f2);

	printf("\nf1==f2: %s\n", sameFunctionsForInts(f1, f2, 1E-6) ? "DA" : "NE");
	printf("neg_val f1(1) = %.3lf\n", f1->vTable[1](f1, 1.0));
  	printf("neg_val f2(1) = %.3lf\n", f2->vTable[1](f2, 1.0));
	free(f1);
	free(f2);
	return 0;
}
