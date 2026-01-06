#include <stdio.h>
#include <time.h>

int Fib1(int n){

     if(n < 2) return n;

     return Fib1(n-1) + Fib1(n-2);    
}

int Fib2(int n){
     int i, F, Fminus1 = 1, Fminus2 = 0;

     if(n < 2) return n;

     for (i=2; i <= n; i++){
          F = Fminus1 + Fminus2;
          Fminus2 = Fminus1;
          Fminus1 = F;
     }

     return Fminus1;     
}

int main(){

     clock_t start, end;
     double time;
     int n, M, result;

     printf("\n============================== The Fibonacci Number ==============================\n\n");

     for(n = 5; n<=30; n+=5){

          printf("n = %d \n\tAlgorythm 1 - ", n);
    
          start = clock();    //Start timer for Fib1
     
          for(int M = 0; M < 1000; M++) {
               result = Fib1(n);        //Run Fib1 1000 times
          }
     
          end = clock();      //End timer
          time = ((double) (end - start)) / CLOCKS_PER_SEC;      //Calculate time it took (divided by M and multiplied by 1000 for ms)
     
          printf("\t%f seconds (ran 1000 times) \t%f ms (ran 1 time) \tresult = %d\n", time, time, result);          //Time it took and result of Fib1


          printf("\tAlgorythm 2 - ");
    
          start = clock();    //Start timer for Fib2
     
          for(int M = 0; M < 1000; M++) {
               result = Fib2(n);        //Run Fib2 1000 times
          }
     
          end = clock();      //End timer
          time = ((double) (end - start)) / CLOCKS_PER_SEC;      //Calculate time it took
     
          printf("\t%f seconds (ran 1000 times) \t%f ms (ran 1 time) \tresult = %d\n", time, time, result);          //Time it took and result of Fib2
     }

     return 0;
}