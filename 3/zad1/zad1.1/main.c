#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

struct Animal {
  PTRFUN* vtable;
  // vtable entries:
  // 0: char const* name(void* this);
  // 1: char const* greet();
  // 2: char const* menu();
};

// parrots and tigers defined in respective dynamic libraries

// animalPrintGreeting and animalPrintMenu similar as in lab 1

void animalPrintGreeting(struct Animal *animal) {
  printf("%s pozdravlja: %s\n", animal->vtable[0](animal), animal->vtable[1]());
}

void animalPrintMenu(struct Animal *animal) {
  printf("%s voli: %s\n", animal->vtable[0](animal), animal->vtable[2]());
}

int main(int argc, char *argv[]) {
  struct Animal* p1 = (struct Animal*)myfactory("./tiger.dll", "Modrobradi", true);
  struct Animal* p2 = (struct Animal*)myfactory("./parrot.dll", "Svjetlobradi", true);

  if (!p1) {
    printf("Creation of plug-in object %s failed.\n", "tiger.dll");
    return -1;
  }
  if (!p2) {
    printf("Creation of plug-in object %s failed.\n", "parrot.dll");
    return -1;
  }

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintMenu(p1);
  animalPrintMenu(p2);
  free(p1);
  free(p2);
  return 0;
}