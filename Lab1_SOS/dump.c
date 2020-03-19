#include <stdio.h>
#include "allocator.h"
#include "dump.h"

void mem_dump() {
	printf("Heap size: \t\t");
	printf("%d\n", mem_heap.size);
	printf("Number of blocks: \t");
	printf("%d\n", mem_heap.blocks_count);
	printf("Block information:\n");
	mem_block_header_t* block = mem_blocks.start;

	for (int i = 1; i <= mem_heap.blocks_count; ++i) {
		printf("    %d) Free: ", i);
		printf("%s", block->is_free ? "true" : "false");
		printf(", Size: ");
		printf("%d", block->size);
		printf(", Address: ");
		printf("%d", block->addr - mem_heap.bytes);
		printf("\n");
		block = block->next;
	}
	printf("\n\n");
};