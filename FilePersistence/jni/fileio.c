#include <stdio.h>
#include <string.h>
#include <jni.h>

#define PROJECT_PATH	"/data/data/com.example.filepersistence/files/"
#define SAVE_DATA_FILE	"/data/data/com.example.filepersistence/files/data_save.txt"

/*
 * Class:     com_example_filepersistence_MainActivity
 * Method:    loadData
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_filepersistence_MainActivity_loadData
  (JNIEnv *env, jobject obj) {
	FILE * fp = NULL;
	char line[512] = {0};
	char data[1024] = {0};

	fp = fopen(SAVE_DATA_FILE, "r");
	if (NULL == fp) {
		printf("fopen failed!\n");
		return (*env)->NewStringUTF(env, "");
	}

	while (NULL != fgets(line,sizeof(line),fp)) {
		strcat(data, line);
	}

	fclose(fp);

	//return (*env)->NewStringUTF(env, "hello, world");
	return (*env)->NewStringUTF(env, data);
}

JNIEXPORT void JNICALL Java_com_example_filepersistence_MainActivity_saveData
  (JNIEnv *env, jobject obj, jstring inputText)
{
	FILE * fp = NULL;
	char data[512] = {0};

	sprintf(data, "mkdir -p %s", PROJECT_PATH);

	system(data);
	memset(data, 0, sizeof(data));

	strcpy(data, (*env)->GetStringUTFChars(env,inputText,NULL) );
	//strcat(data, "##123\n");

	fp = fopen(SAVE_DATA_FILE, "w");
	if (NULL == fp) {
		printf("fopen failed!\n");
		return;
	}

	fwrite(data, sizeof(data), 1, fp);
	fclose(fp);
	//return 0;
}
