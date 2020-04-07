#include <stdio.h>
#include <string.h>

const void* mymax(
	const void *base, size_t nmemb, size_t size,
	int (*compar)(const void *, const void *)) {
	const unsigned long *max = base;
	for (size_t i = 1, offset = size; i < nmemb; ++i, offset += size) {
		const unsigned long *temp = base + offset;
		if (compar(temp, max) == 1) {
			max = temp;

		}
	}
	return max;
}

int gt_int(const void *a, const void *b) {
	return *(int*)a > *(int*)b ? 1 : 0;
}
int gt_char(const void *c1, const void *c2) {
	return *(char*)c1 > *(char*)c2 ? 1 : 0;
}
int gt_str(const void *s1, const void *s2) {
	return strcmp(*(char**)s1, *(char**)s2) > 0 ? 1 : 0;
}

int main() {
	int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
	for (int i = 0; i < 9; ++i) {
		if (i == 0) printf("Max (");
		printf("%d", arr_int[i]);
		if (i != 8) printf(", ");
		else printf(") = %d\n", *(int*)mymax(arr_int, 9, sizeof(int), gt_int));
	}

	char arr_char[] = "Suncana strana ulice";
	for (int i = 0; i < 20; ++i) {
		if (i == 0) printf("Max (");
		printf("%c", arr_char[i]);
		if (i != 19) printf(", ");
		else printf(") = %c\n", *(char*)mymax(arr_char, 20, sizeof(char), gt_char));
	}

	const char* arr_str[] = {
   		"Gle", "malu", "vocku", "poslije", "kise",
   		"Puna", "je", "kapi", "pa", "ih", "njise"
	};
	for (int i = 0; i < 11; ++i) {
		if (i == 0) printf("Max (");
		printf("%s", arr_str[i]);
		if (i != 10) printf(", ");
		else printf(") = %s\n", *(char**)mymax(arr_str, 11, sizeof(char*), gt_str));
	}

	return 0;
}