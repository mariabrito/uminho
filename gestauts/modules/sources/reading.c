#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include "strutil.h"
#include "author_index.h"

static char* fileread;

static void extract_year_info(char* year_str) {
	/* Function that should use year to complete statistics */
	int year = atoi(year_str);
	checkForYear(year);
}

static void extract_author_info(char* author) {
	/* Function that should:
	 * add author to author_indexe
	 * use author info to calculate statistics
	 */
	addToLength( strlen(author) );
	checkForLength(author);
	insertAuthor(author);
}

static int isAuthor(char* str) { return !isdigit(str[0]); }

static void tokenize(char* buffer) {
	char *token = strtrim( strtok(buffer, ",") );

	while (token) {
#ifdef DEBUG
		printf("%s ", token);
#endif
		/* use !isdigit() instead of isalpha because of names started with special characters */
		/* use a function for this line */
		isAuthor(token) ? extract_author_info(token) : extract_year_info(token);
		
		/* strtrim is allocating mem
		 * since we're making copies into the tree, no need for dupmem
		 */
		free(token);
		token = strtrim( strtok(NULL, ",") );

	}

}

static void set_filename(char* filename) {
	fileread = (char*)malloc(sizeof(char) * ( strlen(filename) + 1 ) );
	strncpy(fileread, filename, sizeof(char) * ( strlen(filename) + 1 ) );
}


int read_file(char* filename) {
	char buffer[1024];
	FILE *file = fopen(filename, "r");
	
	set_filename(filename);

	/* ERROR HANDLING */
	if (!file)
		return -1;

	while( fgets(buffer, 1024, file) ) {
#ifdef DEBUG
		printf("#%d	%s\n", nr_publications + 1, buffer);
#endif
		tokenize(buffer);
	}

	return 0;
}

char* getReadStats() {
	char* stats = (char*)malloc(sizeof(char) * 1024);
	
	sprintf( stats, "FILE READ: %s\nTOTAL NUMBER OF PUBLICATIONS: %d\nTOTAL NUMBER OF AUTHORS:%d\n", fileread, getNrPublications(), (int)getNumberAuthors() );
	sprintf( stats + strlen(stats), "LONGEST NAME: %s with length %ld\n", getLongestAuthorName(), strlen( getLongestAuthorName() ) );
	sprintf( stats + strlen(stats), "SHORTEST NAME: %s with length %ld\n", getShortestAuthorName(), strlen( getShortestAuthorName() ) );
	sprintf( stats + strlen(stats), "AVERAGE LENGTH: %f\n", getAverage());
	sprintf( stats + strlen(stats), "MIN YEAR:%d\nMAX YEAR:%d\n", getMinYear(), getMaxYear() );

	return stats;
}

/*
	int i;
	int number;
	char* author_list[24];

	if ( result == -1 )
		return result;

	number = getListOfAuthorsBy('A', author_list, 24);
	printf("%d\n", number);
	for (i = 0; i < number; i++) {
		printf("%s\n", author_list[i]);
	}

	return result;
}*/
