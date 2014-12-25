#include <stdio.h>
#include <string.h>
#include <jni.h>


JNIEXPORT void JNICALL Java_com_example_filepersistencetest_MainActivity_saveData
  (JNIEnv *env, jobject obj, jstring inputText)
{
	FILE * fp = NULL;
	char data[512] = {0};

	strcpy(data, (*env)->GetStringUTFChars(env,inputText,NULL) );
	strcat(data, "##123\n");

	fp = fopen("/data/data/com.example.filepersistencetest/files/data_save.txt", "w");
	if (NULL == fp) {
		printf("fopen failed!\n");
		return;
	}

	fwrite(data, sizeof(data), 1, fp);
	fclose(fp);
	//return 0;
}
