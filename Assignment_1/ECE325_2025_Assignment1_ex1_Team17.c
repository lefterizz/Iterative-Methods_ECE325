#include <stdio.h>
#include <stdlib.h>

int main() {

    printf("This program finds the common elements between two sorted lists of integers.\n\n");
    printf ("Please insert the first sorted integer list. Enter '#' to end the list.\n");
    int list1[100];
    int list2[100];
    int common[100];
    char input;
    int indexCommon = 0, comparisons = 0, sumofCommon = 0;
    int i=0;

    while (input != '#') {              //read until user inputs '#'
        scanf("%s", &input);
        if (input == '#') break;
        if (input =='\n') continue;     //if user inputs newline, ignore it
        list1[i] = atoi(&input);
        i++;
        if (i >= 100) {
            printf("List too long. Stopping input.\n");
            break;
        }
    }

    int m = i;          //save size of list1 as m
    input = '\0';       //reset input
    i=0;

    printf ("Please insert the second sorted integer list. Enter '#' to end the list.\n");

    while (input != '#') {              //read until user inputs '#'
        scanf("%s", &input);
        if (input == '#') break;
        if (input =='\n') continue;     //if user inputs newline, ignore it
        list2[i] = atoi(&input);
        i++;
        if (i >= 100) {
            printf("List too long. Stopping input.\n");
            break;
        }
    }

    int n = i;          //save size of list2 as n
    int index1 = 1, index2 = 1;

    while (index1 < m && index2 < n) {                  //checks if list is sorted
        if(list1[index1] < list1[index1-1] || list2[index2] < list2[index2-1]) {
            printf("List was not sorted. Exiting program...\n");
            return 0;
        }
        index1++;
        index2++;
    }

    index1 = 0;
    index2 = 0;

    while (index1 < m && index2 < n) {                  //loop until we reach the end of either list
        if (list1[index1] < list2[index2]) {
            index1++;
            comparisons++;
        } else if (list1[index1] > list2[index2]) {
            index2++;
            comparisons++;
        } else {        //common element found
            common[indexCommon] = list1[index1];
            indexCommon++;
            index1++;
            index2++;
            comparisons++;
        }
    }

    printf("\nThe size of list 1 is: %d\nThe size of list 2 is %d\n", m, n);

    if (indexCommon == 0) {
        printf("No common elements found.\n");
    } else {
        printf("The number of comparisons made is: %d\nThe common elements are:\n", comparisons);
        for (i = 0; i < indexCommon; i++) {
            printf("%d ", common[i]);
            sumofCommon += common[i];
        }
        printf("\nThe sum of the common elements is: %d\n", sumofCommon);
    }

    return 0;

}

