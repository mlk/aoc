#include <stdio.h>
#include <math.h>

struct Item {
   char c;
   int count;
};

int compare(a,b) 
     struct Item *a;
     struct Item *b; {
    if(a->count != b->count) {
        return b->count - a->count;
    } else {
        return ((int)a->c) - b->c;
    }
}

void reset(items)
      struct Item *items; {
    int i;
    for(i = 0; i < 26; i++) {
      items->c = 'a' + i;
      items->count = 0;

     items++;
    }

}

char shift(c, r) 
    char c;
    int r; {
    if(c == '-') {
       return ' ';
    } else
    return (((c - 'a') + (r%26)) % 26) + 'a';
}


void main() {

   struct Item items[26];
   int iSize = sizeof(struct Item);
   reset(&items);

   FILE *fptr;
/*   fptr = fopen("a:\\example.txt", "r"); */
   fptr = fopen("c:\\input.txt", "r"); 
   char line1[150];

   while(fgets(line1, 150, fptr)) {
       int idx, i2, found, i3, id;
       for(idx = 0; idx < 150; idx++) {
           if (line1[idx] >= 'a' && line1[idx] <= 'z' ) {
               items[line1[idx] - 'a'].count++;
           } else if ( line1[idx] >= '0' && line1[idx] <= '9') {
               id = atoi(&line1[idx]);

               idx+=3;
               qsort(items, 26, iSize, compare);
               found = 0;
               for(i2 = 0; i2<5;i2++) {
/*printf("%d %c v %c\n", i2, items[i2].c, line1[idx + 1 + i2]);*/
                  if(items[i2].c != line1[idx + 1 + i2]) {
                      found = 1;
                      i2 = 5;
                  }
               }
               if( found == 0) {
                   for(i3 = 0; i3 < idx-3; i3++) {
                      line1[i3] = shift(line1[i3], id);
                   }
                   if(line1[0] == 'n') {
                      printf(line1);
                   }
               }
               reset(&items);
               idx = 150;
           }

       }
   }
   fclose(fptr);

}
