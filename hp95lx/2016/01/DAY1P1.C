#include <stdio.h>


enum Dir { N = 0, E, S, W };


int toInt(s, l) 
       char s[];
       int l; {
   int val = 0;
   int i = 0;

   int mult = 1;
   for(i = l -1; i >= 0; i--) {
      val = val + (s[i] - '0') * mult;
      mult = mult * 10;
   }

   return val;
}

main() {
   enum Dir d = W;
   int x = 0;
   int y = 0;
   FILE *fptr;
   char my[900];
   fptr = fopen("c:\input.txt", "r");
   fgets(my, 900, fptr);
   fclose(fptr);

   int ci = 0;
   int i = 0;
   char t[10];
   int cur;
   t[1] = 0;
   while(i < 900) {
     t[ci] = my[i];
     if(my[i] == ',' || my[i] == '\n' || my[i] == '\r') {
        t[ci] = 0; 
        cur = toInt(t, ci);
        ci = 0;

        if(d == N) {
            x += cur;
        } else if(d == S) {
            x -= cur;
        } else if(d == E) {
            y += cur;
        } else {
            y -= cur;
        }
        if ( my[i] == 0 || my[i] == '\n' || my[i] == '\r') {
            break;
        }
        i++;
     } else if( my[i] == 'R' ) {
        d++;
        if ( d > 3 ) d = 0;
     } else if ( my[i] == 'L' ) {
        d--;
        if ( d < 0 ) d = 3;
     } else {
        ci++;
     }
     i++;
   }
   printf("%d x %d = %d", abs(x), abs(y), abs(x)+abs(y));
}