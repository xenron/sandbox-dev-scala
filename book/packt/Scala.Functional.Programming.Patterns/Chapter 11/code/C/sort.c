#include <stdio.h>
#include <stdlib.h>

typedef struct record {
	int num;
	const char* str;
} record;

int cmp(const void* e1, const void* e2) {
	record one = *((record *) e1);
	record two = *((record *) e2);

	if (one.num > two.num) 
		return 1;
	if (one.num < two.num)
		return -1;
	return 0;
}

void init_rec(record* r, int n, const char* s) {
	r->num = n;
	/* r->str = s; */
}

int main(int argc, char** argv) {

	record arr[5];
	init_rec(&arr[0], 9, "zeroth");
	init_rec(&arr[1], 4, "first");
	init_rec(&arr[2], 11, "second");
	init_rec(&arr[3], 3, "third");
	init_rec(&arr[4], 22, "fourth");

	int numElems = sizeof(arr)/sizeof(arr[0]);

	qsort(arr, numElems, sizeof(arr[0]), cmp);
	
	for( int i = 0; i < numElems; ++i) {
		printf("rec.num = %d, rec.str = %s\n", arr[i].num, arr[i].str);
	}
}


