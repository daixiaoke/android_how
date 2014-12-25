#include <stdio.h>

void save_data(char *inputText) {
	FILE * fp = NULL;

	fp = fopen("/data/data/com.example.jnidemo/files/data_save.txt", "w");
	if (NULL == fp) {
		printf("fopen failed!\n");
		return;
	}

	fwrite(inputText, sizeof(inputText), 1, fp);

	fclose(fp);
}
