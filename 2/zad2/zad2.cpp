#include <cstring>
#include <iostream>
#include <vector>
#include <set>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator cur, Iterator last, Predicate pred) {
	Iterator max = cur;
	cur++;
	while (cur != last) {
		if (pred(cur, max) == 1) max = cur;
		cur++;
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
	const int *maxint = mymax(&arr_int[0], &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
	std::cout <<*maxint <<"\n";

	char arr_char[] = "Suncana strana ulice";
	const char *maxchar = mymax(&arr_char[0], &arr_char[sizeof(arr_char)/sizeof(*arr_char)], gt_char);
	std::cout <<*maxchar <<"\n";

	const char *arr_str[] = {
		"Gle", "malu", "vocku", "poslije", "kise",
   		"Puna", "je", "kapi", "pa", "ih", "njise"
	};
	const char **maxstring = mymax(&arr_str[0], &arr_str[sizeof(arr_str)/sizeof(*arr_str)], gt_str);
	std::cout <<*maxstring <<"\n";

	std::vector<int> vectorInts(arr_int, arr_int + sizeof(arr_int) / sizeof(int));
	const int *maxintV = mymax(&vectorInts.front(), &vectorInts.back(), gt_int);
	std::cout <<*maxintV <<"\n";

	std::vector<char> vectorChars(arr_char, arr_char + sizeof(arr_char) / sizeof(char));
	const char *maxcharV = mymax(&vectorChars.front(), &vectorChars.back(), gt_char);
	std::cout <<*maxcharV <<"\n";

	std::vector<const char*> vectorStrings(arr_str, arr_str + sizeof(arr_str) / sizeof(char*));
	const char **maxstringV = mymax(&vectorStrings.front(), &vectorStrings.back(), gt_str);
	std::cout <<*maxstringV <<"\n";

	std::set<int> setInts(arr_int, arr_int + sizeof(arr_int) / sizeof(int));
	const int *maxintS = mymax(setInts.begin(), setInts.end(), gt_int);
	std::cout <<*maxintS <<"\n";
}