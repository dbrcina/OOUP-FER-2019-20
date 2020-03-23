#include <cstdio>

typedef int (*prvaFun)(void*);
typedef int (*drugaFun)(void*, int);

class B {
public:
  virtual int prva()=0;
  virtual int druga(int x)=0;
};

class D: public B {
public:
  virtual int prva(){return 42;}
  virtual int druga(int x){return prva()+x;}
};

void executeMethods(B* pb) {
	unsigned long* vtable =  *(unsigned long**)pb;
	printf("%d\n", ((prvaFun)vtable[0])(pb));
	printf("%d\n", ((drugaFun)vtable[1])(pb, 10));
}

int main(int argc, char const *argv[]) {
	B* d = new D();
	executeMethods(d);
	delete d;
	return 0;
}