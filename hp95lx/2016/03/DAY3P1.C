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
   char line[20];
   while(fgets(line1, 20, fptr)) {

       nums[0] = atoi(line);
       nums[1] = atoi(&line[5]);
       nums[2] = atoi(&line[10]);
       qsort(nums, 3, sizeof(int), compare);

       if( nums[0] + nums[1] > nums[2] ) {
          count++;
       }
   }
   fclose(fptr);


   printf("%d\n", count);
}
