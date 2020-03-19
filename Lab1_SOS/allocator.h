#ifndef ALLOCATOR_H
#define ALLOCATOR_H

// 1 MB Memory Definition
#define DEFAULT_MEM_SIZE 1024 
#define FROM_START 1

#include <stdlib.h>
#include <stdbool.h>

// Memory heap structure
struct {
    unsigned char* bytes;
    size_t size;
    size_t blocks_count;
} mem_heap;

// Memory block header type
typedef struct mem_block_header_t mem_block_header_t;
struct mem_block_header_t {
    bool is_free;
    size_t size;
    unsigned char* addr;
    mem_block_header_t* next;
    mem_block_header_t* prev;
};

// List of memory block headers
struct {
    char order;
    mem_block_header_t* start;
    mem_block_header_t* end;
} mem_blocks;

// Initialize heap
int mem_init(size_t);

// Block existence check
bool isExist(unsigned char*);

// Allocate memory
unsigned char* mem_alloc(size_t);

// Reallocate memory
unsigned char* mem_realloc(unsigned char*, size_t);

// Free previously allocated memory block
void mem_free(unsigned char*);

// Free heap
void mem_drop();

// Align memory addresses by size 4 B
size_t align(size_t);

// Find free block with appropriate size
mem_block_header_t* find_free_block(size_t);

// Find block with the given address
mem_block_header_t* find_block(unsigned char*);

// Get new address for given address with given size reallocation
unsigned char* get_realloc_addr(mem_block_header_t*, size_t);

// Split memory block in two parts where one has given size
void split_mem_block(mem_block_header_t*, size_t);

// Merge memory blocks with given headers while second one will be merged into the first one
void merge_mem_blocks(mem_block_header_t*, mem_block_header_t*);

// Mark block header as free and merge it with neighbours whether it is possible
void free_block(mem_block_header_t*);

#endif