#include <stdio.h>

struct Loc {
   int x;
   int y;
};

void down(oldLoc)
    struct Loc *oldLoc; {
   oldLoc->x++;
  if (oldLoc->x > 2) oldLoc->x = 2;
}

void up(oldLoc)
    struct Loc *oldLoc; {
   oldLoc->x--;
  if (oldLoc->x < 0) oldLoc->x = 0;
}

void right(oldLoc)
    struct Loc *oldLoc; {
   oldLoc->y++;
  if (oldLoc->y > 2) oldLoc->y = 2;
}

void left(oldLoc)
    struct Loc *oldLoc; {
   oldLoc->y--;
  if (oldLoc->y < 0) oldLoc->y = 0;
}

void display(loc)
     struct  Loc *loc; {
    printf("%dx%d - %d\n", loc->x, loc->y, (loc->x * 3) + loc->y +1);
}

main() {
   struct Loc location;
   location.x = 1;
   location.y = 1;

   FILE *fptr;
   fptr = fopen("c:\\input.txt", "r");
   char input[1000];
   while(fgets(input, 1000, fptr)) {
       int i = 0;
       while(input[i] > 39 || input < 91) {
          if(input[i] == 'U') up(&location);
          else if(input[i] == 'D') down(&location);
          else if(input[i] == 'L') left(&location);
          else right(&location);

          i++;
       }
       display(&location);
   }
   fclose(fptr);

}
