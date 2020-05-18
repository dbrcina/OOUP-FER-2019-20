#include <stdlib.h> 

typedef char const* (*PTRFUN)();

typedef struct {
	PTRFUN* vtable;
	char const* name;
} Tiger;

char const* name(Tiger* t) { return t->name; }
char const* greet(void) { return "rawr!"; }
char const* menu(void) { return "divljac"; }

PTRFUN vtable[] = {(PTRFUN)name, greet, menu};

void* create(char const* name) {
	Tiger* t = (Tiger*)malloc(sizeof(Tiger));
	t->vtable = vtable;
	t->name = name;
	return t;
}

void* createStack(char const* name) {
	Tiger t;
	t.vtable = vtable;
	t.name = name;
	return &t;
}
