#include <cstring>
#include <iostream>
#include <vector>
#include <set>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator cur, Iterator last, Predicate pred) {
	Iterator max = cur;
	cur++;
	while (cur != last) {
		if (pred(*cur, *max) == 1) max = cur;
		cur++;
	}
	return max;
}

int gt_int(const int a, const int b) {
	return a > b ? 1 : 0;
}
int gt_char(const char c1, const char c2) {
	return c1 > c2 ? 1 : 0;
}
int gt_str(const char *s1, const char *s2) {
	return strcmp(s1, s2) > 0 ? 1 : 0;
}

int main() {
	int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
	int n_arr_int = sizeof(arr_int) / sizeof(*arr_int);
	std::vector<int> vec_int(arr_int, arr_int + n_arr_int);
	std::set<int> set_int(vec_int.begin(), vec_int.end());

	char arr_char[] = "Suncana strana ulice";
	int n_arr_char = sizeof(arr_char) / sizeof(*arr_char);
	std::vector<char> vec_char(arr_char, arr_char + n_arr_char);
	std::set<char> set_char(vec_char.begin(), vec_char.end());

	const char *arr_str[] = {
		"Gle", "malu", "vocku", "poslije", "kise",
   		"Puna", "je", "kapi", "pa", "ih", "njise"
	};
	int n_arr_str = sizeof(arr_str) / sizeof(*arr_str);
	std::vector<const char*> vec_str(arr_str, arr_str + n_arr_str);
	std::set<const char*> set_str(vec_str.begin(), vec_str.end());

	// ARRAYS
	std::cout << "Arrays:\n";
	std::cout << *mymax(&arr_int[0], &arr_int[n_arr_int], gt_int) << "\n";
	std::cout << *mymax(&arr_char[0], &arr_char[n_arr_char], gt_char) << "\n";
	std::cout << *mymax(&arr_str[0], &arr_str[n_arr_str], gt_str) <<"\n";

	// VECTORS
	std::cout << "\nVectors:\n";
	std::cout << *mymax(vec_int.begin(), vec_int.end(), gt_int) << "\n";
	std::cout << *mymax(vec_char.begin(), vec_char.end(), gt_char) << "\n";
	std::cout << *mymax(vec_str.begin(), vec_str.end(), gt_str) << "\n";

	// SETS
	std::cout << "\nSets:\n";
	std::cout << *mymax(set_int.begin(), set_int.end(), gt_int) << "\n";
	std::cout << *mymax(set_char.begin(), set_char.end(), gt_char) << "\n";
	std::cout << *mymax(set_str.begin(), set_str.end(), gt_str) << "\n";
}