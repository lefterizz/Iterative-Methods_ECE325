#include <stdio.h>

int TowerofHanoi(int n, int moves, char start, char end, char middle){

     if (n == 1){
          printf("Move disc from column %c to column %c\n", start, end);        //Move remaining disc from start to end
          moves++;
          return moves;
     }

     moves = TowerofHanoi(n-1, moves, start, middle, end);       //Move n-1 discs from start to middle
     printf("Move disc from column %c to column %c\n", start, end);
     moves++;

     moves = TowerofHanoi(n-1, moves, middle, end, start);       //Move n-1 discs from middle to end

}

int main(){

     int n;
     int moves = 0;

     printf("\n======== Tower of Hanoi ========\n");
     printf("Please enter the number of disks: ");
     scanf("%d", &n);

     printf("\nMaking moves...\n");
     moves = TowerofHanoi(n, 0, 'A', 'C', 'B');

     printf("\nThe program took %d moves to solve the puzzle.\n", moves);

     return 0;
}