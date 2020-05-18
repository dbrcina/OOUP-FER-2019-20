#include "myfactory.h"
#include <windows.h>

typedef void* (*FUNC)(char const*);

void* myfactory(char const* libname, char const* ctorarg, bool heap) {
	HMODULE moduleId = LoadLibrary(libname);
	if (moduleId == NULL) {
		return NULL;
	}
	if (heap) {
		return ((FUNC)GetProcAddress(moduleId, "create"))(ctorarg);
	} else {
		return ((FUNC)GetProcAddress(moduleId, "createStack"))(ctorarg);
	}
}
