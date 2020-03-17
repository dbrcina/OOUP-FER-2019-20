#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();
typedef struct {
  char* name;
  PTRFUN* vFuncTable;
} Animal;

char const* dogGreet(int a) {
  return "vau!";
}

char const* dogMenu(void) {
  return "kuhanu govedinu";
}

PTRFUN dogVFuncTable[2] = {dogGreet, dogMenu};

void constructDog(Animal* dog, char* name) {
  dog->name = name;
  dog->vFuncTable = dogVFuncTable;
}

Animal* createDog(char* name) {
  Animal* dog = (Animal*)malloc(sizeof(Animal));
  constructDog(dog, name);
  return dog;
}

char const* catGreet(void) {
  return "mijau!";
}

char const* catMenu(void) {
  return "konzerviranu tunjevinu";
}

PTRFUN catVFuncTable[2] = {catGreet, catMenu};

void constructCat(Animal* cat, char* name) {
  cat->name = name;
  cat->vFuncTable = catVFuncTable;
}

Animal* createCat(char* name) {
	Animal* cat = (Animal*)malloc(sizeof(Animal));
	constructCat(cat, name);
  return cat;
}

void animalPrintGreeting(Animal* animal) {
	printf("%s pozdravlja: %s\n", animal->name, animal->vFuncTable[0]());
}

void animalPrintMenu(Animal* animal) {
	printf("%s voli: %s\n", animal->name, animal->vFuncTable[1]());
}

void testAnimals(void) {
  Animal* p1 = createDog("Hamlet");
  Animal* p2 = createCat("Ofelija");
  Animal* p3 = createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); 
  free(p2); 
  free(p3);
}

Animal* createNDogs(int n) {
  int size = sizeof(Animal);
  int offset = 0;
  Animal* dogs = (Animal*)malloc(size * n);
  for (int i = 0; i < n; ++i)
  {
    constructDog(dogs + offset, "Pas");
    offset += size;
  }
  return dogs;
}

int main(int argc, char const *argv[]) {
  printf("Testing on heap\n");
	testAnimals();

  printf("\nTesting on stack\n");
  Animal dog;
  dog.name = "Rex";
  dog.vFuncTable = dogVFuncTable;
  printf("%s voli: %s\n", dog.name, dog.vFuncTable[1]());

  printf("\nCreating 10 dogs using malloc only once\n");
  int size = sizeof(Animal);
  Animal* dogs = createNDogs(10);
  for (int i = 0, offset = 0; i < 10; i++, offset += size)
  {
    printf("%4d.%s\n", (i + 1), (dogs + offset)->name);
  }
	return 0;
}
