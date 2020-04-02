typedef struct unaryFunction UnaryFunction;
typedef struct square Square;
typedef struct linear Linear;
typedef double (*PTRFUN)(UnaryFunction*, double);

// class UnaryFunction
struct unaryFunction {
	PTRFUN *vTable;
	int lowerBound;
	int upperBound;
	void (*print)(UnaryFunction*);
};
double negativeValueAt(UnaryFunction *f, double x) { return -f->vTable[0](f, x); }
void tabulate(UnaryFunction *f) {
	int lowerBound = f->lowerBound;
	int upperBound = f->upperBound;
	for (int x = lowerBound; x <= upperBound; x++) {
		printf("f(%d)=%.3lf\n", x, f->vTable[0](f, x));
	}
}
void unaryFunctionConstructor(UnaryFunction *f, PTRFUN *vTable, int lowerBound, int upperBound) {
	f->vTable = vTable;
	f->lowerBound = lowerBound;
	f->upperBound = upperBound;
	f->print = tabulate;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

// class Square
struct square {
	UnaryFunction base;
};
double valueAtSquare(UnaryFunction *f, double x) { return x * x; }
PTRFUN squareVTable[2] = {valueAtSquare, negativeValueAt};
void squareConstructor(Square *square, int lowerBound, int upperBound) {
	unaryFunctionConstructor((UnaryFunction*)square, squareVTable, lowerBound, upperBound);
}
UnaryFunction* newSquare(int lowerBound, int upperBound) {
	Square *square = (Square*)malloc(sizeof(Square));
	squareConstructor(square, lowerBound, upperBound);
	return (UnaryFunction*)square;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

// class Linear
struct linear {
	UnaryFunction base;
	double a;
	double b;
};
double valueAtLinear(UnaryFunction *f, double x) {
	Linear *l = (Linear*)f;
	return l->a * x + l->b;
}
PTRFUN linearVTable[2] = {valueAtLinear, negativeValueAt};
void linearConstructor(Linear *linear, int lowerBound, int upperBound, double a, double b) {
	unaryFunctionConstructor((UnaryFunction*)linear, linearVTable, lowerBound, upperBound);
	linear->a = a;
	linear->b = b;
}
UnaryFunction* newLinear(int lowerBound, int upperBound, double a, double b) {
	Linear *linear = (Linear*)malloc(sizeof(Linear));
	linearConstructor(linear, lowerBound, upperBound, a, b);
	return (UnaryFunction*)linear;
}
