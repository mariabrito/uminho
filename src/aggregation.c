#include "aggregation.h"

typedef struct bucket_node Node, *Bucket;

struct bucket_node {
  Aggregate content;
  struct bucket_node* next;
};

struct aggregation {
  int size;
  Bucket* table;
};

/** Creates a hash bucket with an aggregation of given name and count total to 0
  * It shall not contain subaggregations
  */
static Bucket newBucket(char* name) {
    Bucket new = (Bucket)malloc( sizeof (struct bucket_node) );
    new -> content = newAggregateWith(name, 0);
    new -> next = NULL;

    return new;
}


/* djb2 hash function created by Dan Bernstein */
static unsigned int hash( char *str ) {
   unsigned int hash = 5381;
    int c;

        while ( (c = *str++) )
            hash = ((hash << 5) + hash) ^ c;

        return hash;
}


/** Iterates the table looking for the aggregate with the given name
  * ret will contain the address of the bucket where it should be (whether or not it in fact is)
  * Return will be 1 or 0 depending on existance
  */
static int get_bucket_address(Aggregation a, char* name, Bucket** ret) {
    unsigned int index = hash(name) % a -> size;                    // Get table index
    int found = 0;                                                  // Control variable
    Bucket *it = &(a -> table)[index];                              // Iterator for that bucket
    Bucket *head = it;                                              // Saving the head of the bucket

    while (*it && !found) {                                         // Scanning the bucket for agregation
        if ( strcmp(name, getAggregateName( (*it) -> content) ) == 0 )
            found = 1;
        else
            it = &( (*it) -> next );                                // Saving the new bucket address
    }

    if (! *it)
        *ret = head;                                                // Returning the position where the new bucket should be inserted
    else
        *ret = it;                                                  // Returning the position where the bucket is

    return found;                                                   // This allows us to know if we should create or update what's in ret
}

/** Tries to find the aggregate node
  * If found, returns its address
  * Otherwise adds it to the table
  */
static Bucket* get_aggregate_ptr(Aggregation a, char* name) {
    Bucket new;
    Bucket* it;

    if (! get_bucket_address(a, name, &it) ) {
        new = newBucket(name);
        new -> next = *it;
        *it = new;
    }

    return it;
}

static void deleteBucket (Bucket b) {
    Bucket bird;                                // Auxiliary iterator that deletes everything behind
    Bucket it = b;                              // Main iterator that leads the way
    while (it) {
        bird = it;
        it = it -> next;
        deleteAggregate(bird -> content);
        free(bird);                             // badumm tss!
    }
}

void deleteAggregation(Aggregation a) {
  if (a) {
    for (int i = 0; i < a -> size; i++)
        deleteBucket( a -> table[i] );

    free(a -> table);
    free(a);
  }
}

Aggregation newAggregation(int size) {
    Aggregation a = (Aggregation)malloc(sizeof (struct aggregation) );
    a -> size = size;
    a -> table = (Bucket*)calloc( size, sizeof(Bucket) );

    return a;
}

int updateAggregation(Aggregation a, char name*[], int count) {

    if (*name) {
        Aggregate curr = get_aggregate_ptr(a, *name) -> content
        incCount(curr, count);
        int res; = createSubAggregate(curr);
        updateAggregation( getSubAggregate(curr), name + 1, count);
        return res;
    }

    return -1;
}
