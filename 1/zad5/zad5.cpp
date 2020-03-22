#include <cstdio>

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
	int* vptr =  *(int**)pb;
	printf("%d\n", ((int (*)()) vptr[0])());
	printf("%d\n", ((int (*)(int)) vptr[4])(10));
}

int main(int argc, char const *argv[]) {
	executeMethods(new D());
	return 0;
}