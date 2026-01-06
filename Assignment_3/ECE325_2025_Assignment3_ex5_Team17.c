#include <stdio.h>
#include <stdlib.h>

typedef struct que_node {
    char data;
    struct que_node *next;
} QUE_NODE;

typedef struct {
    QUE_NODE *front;
    QUE_NODE *rear;
    int size;
} QUEUE;

void make_empty_queue(QUEUE *list){
    list->front = NULL;
    list->rear = NULL;
    list->size = 0;
}

void enqueue(QUEUE *list, char value){
    QUE_NODE *new_node = malloc(sizeof(QUE_NODE));
    new_node->data = value;
    new_node->next = NULL;
    if(list->rear == NULL){
        list->front = new_node;
        list->rear = new_node;
    }
    else{
        list->rear->next = new_node;
        list->rear = new_node;
    }
    list->size++;
}

void dequeue(QUEUE *list){
    if(list->front == NULL){
        return;
    }
    QUE_NODE *temp = list->front;
    list->front = list->front->next;
    if(list->front == NULL){
        list->rear = NULL;
    }
    free(temp);
    list->size--;
}

void delete_queue(QUEUE *list){
    while(list->front != NULL){
        dequeue(list);
    }
}

int main(){

    printf("\nEnter string to search (end with newline):\n");
    QUEUE input;
    make_empty_queue(&input);
    while(1){
        char ch = getchar();
        if(ch == '\n'){
            break;
        }
        enqueue(&input,ch);
    }

    QUEUE list;
    make_empty_queue(&list);
    printf("\nEnter the long string (end with newline):\n");
    while(1){
        char ch = getchar();
        if(ch == '\n'){
            break;
        }
        enqueue(&list,ch);
    }

    // Early exit check
    if(list.size < input.size){
        printf("The long string is shorter than the input string. No match possible.\n");
        delete_queue(&list);
        delete_queue(&input);
        return 0;
    }

    // Brute force substring search
    int found = 0;
    int pos = 0;  // track starting index in text
    for(QUE_NODE *start = list.front; start != NULL; start = start->next, pos++){
        // optimization: stop if not enough chars left
        if(list.size - pos < input.size){
            break;
        }

        QUE_NODE *list_node = start;
        QUE_NODE *input_node = input.front;
        int match_count = 0;

        while(list_node != NULL && input_node != NULL &&
              list_node->data == input_node->data){
            match_count++;
            list_node = list_node->next;
            input_node = input_node->next;
        }

        if(match_count == input.size){
            found = 1;
            printf("The input string is a substring of the long string.\n");
            break;
        }
    }

    if(!found){
        printf("The input string is NOT a substring of the long string.\n");
    }

    delete_queue(&list);
    delete_queue(&input);
    return 0;
}
