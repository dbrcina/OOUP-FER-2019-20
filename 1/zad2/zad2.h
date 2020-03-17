typedef struct unaryFunction UnaryFunction;
typedef struct square Square;
typedef struct linear Linear;

typedef double (*PTRFUN)(struct unaryFunction*, double);

void unaryFunctionConstructor(UnaryFunction* f, int lowerBound, int upperBound, PTRFUN* vTable);
double negativeValueAt(UnaryFunction* f, double x);

UnaryFunction* newSquare(int lowerBound, int upperBound);
void squareConstructor(Square* square, int lowerBound, int upperBound);
double valueAtSquare(UnaryFunction* f, double x);

UnaryFunction* newLinear(int lowerBound, int upperBound, double a, double b);
void linearConstructor(Linear* linear, int lowerBound, int upperBound, double a, double b);
double valueAtLinear(UnaryFunction* f, double x);

void tabulate(UnaryFunction* f);