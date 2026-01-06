#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int binary_search(int arr[], int n, int target) {
    int low = 0, high = n - 1;
    while (low <= high) {
        int mid = (low + high) / 2;
        if (arr[mid] == target)
            return 1; // found
        else if (arr[mid] < target)
            low = mid + 1;
        else
            high = mid - 1;
    }
    return 0;
}

long long sum_array(int arr[], int n) {
    long long total = 0;
    for (int i = 0; i < n; i++) {
        total += arr[i];
    }
    return total;
}

void bubble_sort(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void permute(int *arr, int l, int r) {
    if (l == r) {
        return;
    } else {
        for (int i = l; i <= r; i++) {
            swap(&arr[l], &arr[i]);
            permute(arr, l + 1, r);
            swap(&arr[l], &arr[i]);
        }
    }
}

// Fill array with random numbers
void fill_array(int *arr, int n) {
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 1000000; // random numbers
    }
}

int main() {
    int sizes[] = {10, 100, 1000, 10000, 100000, 1000000};
    int num_sizes = sizeof(sizes) / sizeof(sizes[0]);

    srand(time(NULL));

    printf(" n\tBinarySearch\tSumArray\tBubbleSort\tPermutations\n");

    for (int s = 0; s < num_sizes; s++) {
        int n = sizes[s];
        int *arr = (int *)malloc(n * sizeof(int));
        fill_array(arr, n);

        clock_t start, end;
        double time_taken;

        start = clock();
        binary_search(arr, n, arr[n/2]); // search for middle element
        end = clock();
        double t_binary = ((double)(end - start)) / CLOCKS_PER_SEC;

        start = clock();
        sum_array(arr, n);
        end = clock();
        double t_sum = ((double)(end - start)) / CLOCKS_PER_SEC;

        double t_bubble = 0.0;
        if (n <= 10000) { // only run if n <= 10000, otherwise too slow
            int *copy = (int *)malloc(n * sizeof(int));
            for (int i = 0; i < n; i++) copy[i] = arr[i];

            start = clock();
            bubble_sort(copy, n);
            end = clock();
            t_bubble = ((double)(end - start)) / CLOCKS_PER_SEC;
            free(copy);
        }

        double t_perm = 0.0;
        if (n <= 10) { // only run for n <= 10, otherwise too slow
            int *permArr = (int *)malloc(n * sizeof(int));
            for (int i = 0; i < n; i++) permArr[i] = i;

            start = clock();
            permute(permArr, 0, n - 1);
            end = clock();
            t_perm = ((double)(end - start)) / CLOCKS_PER_SEC;

            free(permArr);
        }

        printf("%d\t%.6f\t%.6f\t%.6f\t%.6f\n", n, t_binary, t_sum, t_bubble, t_perm);

        free(arr);
    }

    return 0;
}
