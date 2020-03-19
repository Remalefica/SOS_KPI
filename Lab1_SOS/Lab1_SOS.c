// Lab1_SOS.cpp : This file contains the 'main' function. Program execution begins and ends there.

#include <stdio.h>
#include "allocator.h"
#include "dump.h"

int main() {
	{
		printf("#1. Memory initialization\n");
		mem_init(0);
		mem_dump();
		mem_drop();
	}

	{
		printf("#2. Memory allocation\n");
		mem_init(1024);
		mem_alloc(512);
		mem_alloc(200);
		mem_dump();
		mem_drop();
	}

	{
		printf("#3. Allocation with align size (4 bytes)\n");
		mem_init(1024);
		mem_alloc(1);
		mem_dump();
		mem_drop();
	}

	{
		printf("#4. Memory reallocation (increase)\n");
		mem_init(1024);

		unsigned char* p = mem_alloc(256);
		mem_alloc(256);

		printf("Before:\n");
		mem_dump();

		mem_realloc(p, 300);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}

	{
		printf("#5. Memory reallocation (decrease)\n");
		mem_init(1024);

		unsigned char* p = mem_alloc(256);
		mem_alloc(256);

		printf("Before:\n");
		mem_dump();

		mem_realloc(p, 32);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}

	{
		printf("#6. Memory freeing (no merge)\n");
		mem_init(1024);

		unsigned char* p = mem_alloc(256);
		mem_alloc(256);

		printf("Before:\n");
		mem_dump();
		mem_free(p);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}

	{
		printf("#7. Memory freeing (with previous and next free)\n");
		mem_init(1024);

		unsigned char* p1 = mem_alloc(256);
		unsigned char* p2 = mem_alloc(128);
		mem_free(p1);

		printf("Before:\n");
		mem_dump();

		mem_free(p2);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}

	{
		printf("#8. Memory freeing (firstly merge, then free)\n");
		mem_init(1024);

		mem_alloc(512);
		unsigned char* p = mem_alloc(128);

		printf("Before:\n");
		mem_dump();

		mem_free(p);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}

	{
		printf("#9. Memory freeing (firstly free, than merge)\n");
		mem_init(1024);

		unsigned char* p1 = mem_alloc(128);
		unsigned char* p2 = mem_alloc(512);
		mem_alloc(256);

		mem_free(p1);

		printf("Before:\n");
		mem_dump();

		mem_free(p2);

		printf("After:\n");
		mem_dump();
		mem_drop();
	}
	getchar();
	return 0;
}