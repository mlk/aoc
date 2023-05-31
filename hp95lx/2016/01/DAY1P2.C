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
   int x = 500;
   int y = 500;
   char visited[1000][1000];
   FILE *fptr;
   char my[900];
   fptr = fopen("c:\\input.txt", "r");
   fgets(my, 900, fptr);
   fclose(fptr);

   int ci = 0;
   int i = 0;
   char t[10];
   int cur;
   t[1] = 0;
   outer: while(i < 900) {
     t[ci] = my[i];
     if(my[i] == ',' || my[i] == '\n' || my[i] == '\r') {
        t[ci] = 0; 
        cur = toInt(t, ci);
        ci = 0;
        if(d == N) {
            int nx = x + cur;
            for(; x < nx; x++) {
                if(visited[x][y] == 'v') break;
                visited[x][y] = 'v';
            }
        } else if(d == S) {
            int nx = x - cur;
            for(; x > nx; x--) {
                if(visited[x][y] == 'v') break;
                visited[x][y] = 'v';
            }
        } else if(d == E) {
            int nx = y + cur;
            for(; y < nx; y++) {
                if(visited[x][y] == 'v') break;
                visited[x][y] = 'v';
            }
        } else {
            int nx = y - cur;
            for(; y > nx; y--) {
                if(visited[x][y] == 'v') break;
                visited[x][y] = 'v';
            }
        }
        if(visited[x][y] == 'v') break;

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
   printf("%d x %d = %d", abs(x - 500), abs(y - 500), abs(x - 500)+abs(y - 500));
}
