#include <stdio.h>
#include <math.h>


void main() {
   int data[8][26];
   int x,y, maxC, maxA;
   for(x = 0; x<8;x++) {
       for(y = 0; y < 26; y++) {
           data[x][y] = 0;
       }
   }

   FILE *fptr;
/*   fptr = fopen("a:\\example.txt", "r"); */
   fptr = fopen("c:\\input.txt", "r");  
   char line1[10];

   while(fgets(line1, 10, fptr)) {
/*      printf(line1);*/
      for(x = 0; x<8; x++) {
         data[x][line1[x] - 'a']++;
         if (line1[x] < 'a' || line1[x] > 'z') {
            printf("boop out %d\n", x);
         }
      }
   }
   fclose(fptr);


   for(x = 0; x<8;x++) {
       maxC = 0;
       maxA = data[x][0];
       for(y = 1; y < 26; y++) {
           if(data[x][y] < maxA) {
               maxC = y;
               maxA = data[x][y];
           }/* else if (data[x][y] == maxA && maxA != 0) {
              printf("boop\n");
           }*/
       }
       printf("%c", 'a' + maxC);
   }

}
