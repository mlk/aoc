#include <stdio.h>
#include <math.h>

int compare(a, b)
   int *a;
   int *b; {
   return *a - *b;
}

void main() {

   FILE *fptr;
   int nums[3];
   int count = 0;
   int idx;
   fptr = fopen("c:\\input.txt", "r");
   char line1[20];
   char line2[20];
   char line3[20];
   while(fgets(line1, 20, fptr)) {
       fgets(line2, 20, fptr);
       fgets(line3, 20, fptr);

       for(idx = 0; idx < 3; idx++) {
           nums[0] = atoi(&line1[idx * 5]);
           nums[1] = atoi(&line2[idx * 5]);
           nums[2] = atoi(&line3[idx * 5]);
           qsort(nums, 3, sizeof(int), compare);

           if( nums[0] + nums[1] > nums[2] ) {
              count++;
           }
       }
   }
   fclose(fptr);


   printf("%d\n", count);
}
