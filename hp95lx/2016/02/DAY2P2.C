#include <stdio.h>

struct Loc {
   int x;
   int y;
};

void down(oldLoc, map)
    struct Loc *oldLoc;
    char map[7][7]; {
   oldLoc->x++;
   if (map[oldLoc->x][oldLoc->y] == ' ') oldLoc->x--;
}

void up(oldLoc, map)
    struct Loc *oldLoc; 
    char map[7][7]; {
   oldLoc->x--;
   if (map[oldLoc->x][oldLoc->y] == ' ') oldLoc->x++;
}

void right(oldLoc, map)
    struct Loc *oldLoc;
    char map[7][7]; {
   oldLoc->y++;
   if (map[oldLoc->x][oldLoc->y] == ' ') oldLoc->y--;
}

void left(oldLoc, map)
    struct Loc *oldLoc; 
    char map[7][7]; {
   oldLoc->y--;
   if (map[oldLoc->x][oldLoc->y] == ' ') oldLoc->y++;
}

void display(loc, map)
     struct  Loc *loc;
     char map[7][7]; {
    printf("%dx%d - %c\n", loc->x, loc->y, map[loc->x][loc->y]);
}

main() {

   char map[7][7] = { 
 { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
 { ' ', ' ', ' ', '1', ' ', ' ', ' ' },
 { ' ', ' ', '2', '3', '4', ' ', ' ' },
 { ' ', '5', '6', '7', '8', '9', ' ' },
 { ' ', ' ', 'A', 'B', 'C', ' ', ' ' },
 { ' ', ' ', ' ', 'D', ' ', ' ', ' ' },
 { ' ', ' ', ' ', ' ', ' ', ' ', ' ' } };

   struct Loc location;
   location.x = 3;
   location.y = 1;

   FILE *fptr;
   fptr = fopen("c:\\input.txt", "r");
   char input[1000];
   while(fgets(input, 1000, fptr)) {
       int i = 0;
       while(input[i] > 39 || input < 91) {
          if(input[i] == 'U') up(&location,map);
          else if(input[i] == 'D') down(&location,map);
          else if(input[i] == 'L') left(&location, map);
          else right(&location, map);

          i++;
       }
       display(&location, map);
   }
   fclose(fptr);

}
