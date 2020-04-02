#include <stdio.h>
#include <stdlib.h>

// define a pointer to the table with virtual functions
typedef char const* (*PTRFUN)();

// define abstract class Animal
typedef struct {
  PTRFUN *vTable;
  char const *name;
} Animal;

///////////////////////////////////////////////////

// define class Dog
Animal* createDog(char const *name);
void constructDog(Animal *animal, char const *name);
char const* dogGreet(void) { return "vau!"; }
char const* dogMenu(void) { return "kuhanu govedinu"; }
PTRFUN dogVTable[2] = {dogGreet, dogMenu};

Animal* createDog(char const *name) {
  Animal *dog = (Animal *)malloc(sizeof(Animal));
  constructDog(dog, name);
  return dog;
}

void constructDog(Animal *animal, char const *name) {
  animal->vTable = dogVTable;
  animal->name = name;
}

///////////////////////////////////////////////////

// define class Cat
Animal* createCat(char const *name);
void constructCat(Animal *animal, char const *name);
char const* catGreet(void) { return "mijau!"; }
char const* catMenu(void) { return "konzerviranu tunjevinu"; }
PTRFUN catVTable[2] = {catGreet, catMenu};

Animal* createCat(char const *name) {
  Animal *cat = (Animal *)malloc(sizeof(Animal));
  constructCat(cat, name);
  return cat;
}

void constructCat(Animal *animal, char const *name) {
  animal->vTable = catVTable;
  animal->name = name;
}

///////////////////////////////////////////////////

void animalPrintGreeting(Animal *animal) {
  printf("%s pozdravlja: %s\n", animal->name, animal->vTable[0]());
}

void animalPrintMenu(Animal *animal) {
  printf("%s voli: %s\n", animal->name, animal->vTable[1]());
}

void testAnimals(void) {
  Animal *p1 = createDog("Hamlet");
  Animal *p2 = createCat("Ofelija");
  Animal *p3 = createDog("Polonije");

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
  Animal *dogs = (Animal *)malloc(size * n);
  for (int i = 0; i < n; ++i) {
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
  dog.vTable = dogVTable;
  printf("%s voli: %s\n", dog.name, dog.vTable[1]());

  int n;
  printf("\nEnter number of dogs: ");
  scanf("%d", &n);
  printf("\nCreating %d dogs using malloc only once\n", n);
  int size = sizeof(Animal);
  Animal *dogs = createNDogs(n);
  for (int i = 0, offset = 0; i < n; i++, offset += size) {
    printf("%4d.%s\n", (i + 1), (dogs + offset)->name);
  }
  free(dogs);
  return 0;
}
