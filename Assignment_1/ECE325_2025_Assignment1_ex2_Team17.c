#include<stdio.h>
#include <limits.h>

/* calculates average float because of the decimal points*/
float average (int *array){
    float av;
    int sum=0;
    for(int i=0; i<10; i++){
        sum+=array[i];
    }
    av = sum/10.0;
    return av;
}

int diff_between_max_min(int *array){
    int max=INT_MIN, min=INT_MAX;
/*finds max and min*/
    for(int i=0; i<10; i++){
        if(max<array[i])
            max=array[i];

        if(min>array[i])
            min=array[i];
    }
    /*returns directly the subtraction*/
    return (max-min);
}

int index_of_max(int *array){
    int max=INT_MIN, index_max;

    for(int i=0; i<10; i++){
        if(max<array[i]){
            max=array[i];
            index_max=i;
        }
    }
    index_max++;
    return index_max;
} 
/*function fills the new array with the unique elements */
void unique_array(int *array, int *unique, int *size_unique){
    *size_unique = 0;
    
    for(int i = 0; i < 10; i++) {
        int is_unique = 1;  /*this flag checks if the element is unique*/

        for(int j = 0; j < *size_unique; j++) {
            if(array[i] == unique[j]) {
                is_unique = 0;  /*if the element is found, it is not unique*/
                break;
            }
        }
        if(is_unique==1) {
            unique[*size_unique] = array[i];
            (*size_unique)++;
        }
    }
}

void squared_function(int *array){
    for(int i=0; i<10; i++){
        array[i]=array[i]*array[i];
    }
}

void insertion_sort(int *array){
    int i, j, index;
    for (i=1; i < 10; i++) {
        index = array[i];
        for (j=i; j>0; j--) {
            if (array[j-1] <= index) {
                break;
            }
        array[j] = array[j-1];
        }
        array[j] = index;
    }
}

int main(){
    int array[10];
    int arraySq[10];
    int unique[10];
    int size_unique=0, i=0;

    printf("Please enter 10 integers:\n");
    for(i=0; i<10; i++){
        scanf("%d", &array[i]);
    }

    for (i=0; i<10; i++){
        arraySq[i] = array[i];
    }

    printf("The average is: %.2f\n", average(array));
    printf("The difference between the maximum and minimum is: %d\n", diff_between_max_min(array));
    printf("The index of the maximum element is: %d\n", index_of_max(array));

    unique_array(array, unique, &size_unique);
    printf("The unique elements are: ");
    for(i=0; i<size_unique; i++){
        printf("%d ", unique[i]);
    }
    printf("\n");

    squared_function(arraySq);
    printf("The squared elements are: ");
    for(i=0; i<10; i++){
        printf("%d ", arraySq[i]);
    }
    printf("\n");

    insertion_sort(array);
    printf("The sorted elements are: \n");
    for(i=0; i<10; i++){
        printf("%d ", array[i]);
    }
    printf("\n\n");
    return 0;
}