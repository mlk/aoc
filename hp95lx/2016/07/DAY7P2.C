#include <stdio.h>
#include <math.h>

#define LINE_LEN 200
#define ABAS 100

struct ababab {
    char a;
    char b;
};

int any_match(abas, abaCount, babs, babCount)
    struct ababab *abas;
    int abaCount;
    struct ababab *babs;
    int babCount; {
    int a, b;
    for(a=0;a<abaCount;a++) {
        for(b=0;b<babCount;b++) {
            if(abas[a].a == babs[b].b && abas[a].b == babs[b].a) {
               return 1;
            }
        }
    }
    return 0;
}


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

int find_aba(string, len, abas, abaCount) 
    char *string;
    int len;
    struct ababab *abas;
    int abaCount; {


    if(len < 3) {
        return;
    }
    int i;
    for(i = 0; i < len - 2; i++) {
/*      printf("%c-%c-%c\n", string[i], string[i+1], string[i + 2]);*/
      if(string[i+2] < 'a' || string[i+2] > 'z') return abaCount;
      if(string[i] != string[i+1] && string[i] == string[i + 2]) {
/*      printf("yeay\n");*/
          abas[abaCount].a = string[i];
          abas[abaCount].b = string[i+1];
          abaCount++;
        }
    }
    return abaCount;

}

/*
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
*/

void reset(aba)
     struct ababab *aba; {
    int i;
    for(i=0;i<ABAS; i++) {
        aba->a = 0;
        aba->b = 0;
        aba++;
    }
}

main(argc, argv)
   int argc;
   char *argv[]; {

   struct ababab abas[ABAS];
   struct ababab babs[ABAS];

   int abaCount, babCount;


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
       reset(abas);   reset(babs);
       abaCount = 0;
       babCount = 0;
       char* data = line1;
       again:
       char* remainder = strchr(data, '[');
       if( remainder != 0) {
           *remainder = 0;
           remainder++;
       }

       abaCount = find_aba(data, remainder - data - 1, abas, abaCount);
       if(remainder != 0) {
           data = strchr(remainder, ']');
           if(data == 0) {
               printf("huh\n%s\n", remainder);
               return;
           }
           *data = 0;
           data++;
           babCount = find_aba(remainder, data - remainder -1, babs, babCount);
           goto again;
       }
/*       printf("%d %d\n", abaCount, babCount);*/
       if(any_match(abas, abaCount, babs, babCount)) {
           counter++;
       }
   }
   fclose(fptr);
   printf("\n%d\n", counter);
}
