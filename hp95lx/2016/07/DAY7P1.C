#include <stdio.h>
#include <math.h>

#define LINE_LEN 200

char* strchr(string, look) 
    char *string;
    char look; {
    while(*string != 0) {
       if(*string == look) {
           return string;
       }
       string++;
    }
    return 0;
}

int s_is_abba(string, len) 
    char *string;
    int len; {

/*    printf("\n%s (%d)\n", string, len);*/

    if(len < 4) {
        return 0;
    }
    int i;
    for(i = 0; i < len - 2; i++) {
        if(string[i] < 'a' || string[i] > 'z') {
            printf("%s\n", string);
        }

        if(string[i+3] == 0) return 0;
        if(string[i] != string[i+1] && string[i] == string[i + 3] && string[i+1] == string[i+2]) {
/*            printf("%c %c / %c %c; %d/%d", string[i], string[i+3], string[i+1], string[i+2], i, len);*/
            return 1;
        }
    }
    return 0;

}

int s_count(string, look)
    char *string;
    char look; {
    int count = 0;
    int idx = 0;
    while(string[idx] != 0) {
        if(string[idx] == look) count++;
        idx++;
    }
    return count;
}

main(argc, argv)
   int argc;
   char *argv[]; {

   char fileName[15];
   int counter = 0;
   if(argc >= 2 && strcmp(argv[1], "e") == 0) {
       strcpy(fileName, "c:\\example.txt");
   } else {
       strcpy(fileName, "c:\\input.txt");
   }


   FILE *fptr;
   fptr = fopen(fileName, "r");
   if(!fptr) {
       printf("No file: %s\n", fileName);
       return;
   }
   char line1[LINE_LEN];

   while(fgets(line1, LINE_LEN, fptr)) {
/*       printf(line1);*/
       int goodAbba, badAbba;
       goodAbba = 0;
       badAbba = 0;
       char* data = line1;
       again:
       char* remainder = strchr(data, '[');
       if( remainder != 0) {
           *remainder = 0;
           remainder++;
       }

       goodAbba += s_is_abba(data, remainder - data - 1);
       if(remainder != 0) {
           data = strchr(remainder, ']');
           if(data == 0) {
               printf("huh\n%s\n", remainder);
               return;
           }
           *data = 0;
           data++;
           badAbba += s_is_abba(remainder, data - remainder -1);
           goto again;
       }
       if(goodAbba > 0 && badAbba == 0) {
/*           printf("  >  match\n");*/
           counter++;
       }
   }
   fclose(fptr);
   printf("\n%d\n", counter);
}
