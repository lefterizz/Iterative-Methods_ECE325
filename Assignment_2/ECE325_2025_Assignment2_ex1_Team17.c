/*assignment2 325*/
# include<stdio.h>
# include<stdlib.h>


typedef struct NODE{
char data;
struct NODE *next;
} NODE;

typedef struct {
    NODE *front;
    NODE *rear;
    int size;
} QUEUE;

typedef struct {
NODE *head;
int size;
} STACK;

void make_empty_stack(STACK *stack){
    stack->head=NULL;
    stack->size=0;
}

void make_empty_queue(QUEUE *queue){
    queue->front=NULL;
    queue->rear=NULL;
    queue->size=0;
}
void enqueue(QUEUE *queue, char value){         //insert new node in queue
    NODE *new_node=malloc(sizeof(NODE));
    new_node->data=value;
    new_node->next=NULL;
    if(queue->rear==NULL){          //if queue was empty, connect new node to front and rear
        queue->front=new_node;
        queue->rear=new_node;
    }
    else{
        queue->rear->next=new_node;     //if not, insert node at the end of queue
        queue->rear=new_node;
    }
    queue->size++;
}

void dequeue(QUEUE *queue){         //remove from queue
    if(queue->front==NULL){
        printf("Queue is empty\n");
        return;
    }
    NODE *temp=queue->front;            //remove the first node of the queue
    queue->front=queue->front->next;
    if(queue->front==NULL){
        queue->rear=NULL;
    }
    free(temp);
    queue->size--;
}

char front(QUEUE *queue){
    if(queue->front==NULL){
        printf("Queue is empty\n");
        return -1;
    }
    return queue->front->data;
}

void delete_queue(QUEUE *queue){
    while(queue->front!=NULL){
        dequeue(queue);
    }
}

void pop(STACK *stack){             //remove from stack
    if(stack->head==NULL){ 
        printf("Stack is empty\n");
        return;
    }
    NODE *temp=stack->head;         //remove the last node of the stack
    stack->head=stack->head->next;
    free(temp);
    stack->size--;
}

void delete_stack(STACK *stack){
    while(stack->head!=NULL){
        pop(stack);
    }
}

void push(STACK *stack, char value){        //insert new node in stack
    NODE *new_node=malloc(sizeof(NODE));
    new_node->data=value;
    new_node->next=stack->head;
    stack->head=new_node;
    stack->size++;
}

char top(STACK *stack){
    if(stack->head==NULL){
        printf("Stack is empty\n");
        return -1;
    }
    return stack->head->data;
}

void print_stack(STACK *stack){
    NODE *current=stack->head;
    while(current!=NULL){
        printf("%c ",current->data);
        current=current->next;
    }
    printf("\n");
}

void print_queue(QUEUE *queue){
    NODE *current=queue->front;
    while(current!=NULL){
        printf("%c ",current->data);
        current=current->next;
    }
    printf("\n");
}

int Array_implementation(){
    
    int SIZE = 0, M, sel, i = 0, j = 0;
    char input;

    printf("Enter the size of the arrays\n");
    scanf("%d", &SIZE);
    
    char *arrayStack = malloc(SIZE * sizeof(char));     //allocate appropriate memory for Stack and Queue arrays
    char *arrayQueue = malloc(SIZE * sizeof(char));
    
    int sizeQ = 0, sizeS = 0;
    
    if (arrayStack == NULL || arrayQueue == NULL) {
        printf("Memory allocation failed\n");
        return 1;
    }

    printf("\nEnter the contents of the Stack and Queue:\n");

    while(sizeQ < SIZE && sizeS < SIZE){
        
        scanf(" %c", &input);

        if(input == '#'){
            printf("Please input the number of elements you want to remove from both the Queue and the Stack:\n");
            scanf("%d", &M);

            if (M > sizeQ || M > sizeS) {
                printf("Error: not enough elements to remove\n");
            } else {                
                printf("\nQueue elements removed: ");
                for(j=0; j < sizeQ-M; j++){            //remove M elements from front of queue
                    printf("%c, ", arrayQueue[j]);      //print the removed elements
                    arrayQueue[j] = arrayQueue[j+M];
                }
                printf("\nStack elements removed: ");
                for(j = sizeS-1; j>= sizeS-M; j--){     //remove M elements from front of stack
                    printf("%c, ", arrayStack[j]);      //print the removed elements
                    arrayStack[j] = '\0';
                }
            }            
            sizeQ -= M;
            sizeS -= M;

            printf("\n%d elements removed from queue and stack...\nKeep entering the contents of the Stack and List:\n", M);
        }
        else if(input == '?'){
            printf("\nChoose between:\n1) Move one element from the queue to the stack\n2) Move one element from the stack to the queue\n");
            scanf("%d", &sel);
            while(sel!=1 && sel!=2){
                printf("Invalid input, please select 1 or 2\n");
                scanf("%d", &sel);
            }
            if (sel == 1){
                char charTransfer = arrayQueue[0];      //transfer first element of queue 
                arrayStack[sizeS] = charTransfer;       //to the top of the stack
                sizeS++;
                sizeQ--;

                for (int j=0; j<(sizeQ); j++){          //move all elements one place before 
                    arrayQueue[j] = arrayQueue[j+1];
                }
                printf("\nElement %c successfully transfered from queue to stack...\nKeep entering the contents of the Stack and List:\n", charTransfer);
            }
            else {
                char charTransfer = arrayStack[sizeS-1];      //transfer last element of stack
                arrayQueue[sizeQ] = charTransfer;           //to the end of the queue
                arrayStack[sizeS-1] = '\0';                   //delete transfered element
                sizeS--;
                sizeQ++;

                printf("\nElement %c successfully transfered from stack to queue...\nKeep entering the contents of the Stack and List:\n", charTransfer);
            }
        }
        else if(input == ';'){
            printf("\nContents of Queue:\t");       //print conternts of queue
            for(j = 0; j < sizeQ; j++){
                printf("%c ", arrayQueue[j]);
            }
            printf("\nContents of Stack:\t");       //print contents of stack
            for(j = sizeS; j >= 0; j--){
                printf("%c ", arrayStack[j]);
            }
            for(j = 0; j < SIZE; j++){          //delete stack and array
                arrayQueue[j] = '\0';
                arrayStack[j] = '\0';
            }
            sizeQ = 0;
            sizeS = 0;
            i = -1;

            printf("\nReturning to main menu...\n\n");
            return 1;
        }
        else if(input == '*'){
            printf("\nExiting Program...\n");
            return 0;
        }
        else{
            arrayQueue[sizeQ] = input;      //insert input to queue and stack
            sizeQ++;
            arrayStack[sizeS] = input;
            sizeS++;
        }
    }
    printf("\nNo more space in array. Returning to Menu...\n");
}

int Linked_list_implementation() {
    STACK Stack;
    QUEUE Queue;

    make_empty_stack(&Stack);
    make_empty_queue(&Queue);

    char input;
    int M;
    printf("Enter the contents of the Stack and Queue:\n");

    while (1) {
        scanf(" %c", &input);  // Moved inside the loop

        if (input == '*') {
            printf("\nExiting Program...\n");
            return 0;
        }

        switch (input) {
            case '#':
                printf("Please enter number of elements to remove from stack and queue\n");
                scanf("%d", &M);
                
                printf("\nStack elements removed: ");
                for (int i = 0; i < M; i++){         //remove M elements from front of stack
                    if (Stack.head != NULL) {
                        char val = top(&Stack);
                        printf("%c, ", val);        //print the removed elements
                        pop(&Stack);
                    } else {
                        printf("Stack is empty...No elements removed.\n");
                    }
                }

                printf("\nQueue elements removed: ");
                for (int i = 0; i < M; i++) {       //remove M elements from front of queue
                    if (Queue.front != NULL) {
                        char val = front(&Queue);
                        printf("%c, ", val);        //print the removed elements
                        dequeue(&Queue);
                    } else {
                        printf("Queue is empty... No elements removed.\n");
                    }
                }
                printf("\n%d elements removed from queue and stack...\nKeep entering the contents of the Stack and List:\n", M);
                break;

            case '?': {
                printf("\nChoose between:\n1) Move one element from the queue to the stack\n2) Move one element from the stack to the queue\n");
                int sel;
                scanf("%d", &sel);
                while (sel != 1 && sel != 2) {
                    printf("Invalid input, please select 1 or 2\n");
                    scanf("%d", &sel);
                }
                if (sel == 1) {
                    if (Queue.front != NULL) {
                        char val = front(&Queue);
                        dequeue(&Queue);
                        push(&Stack, val);
                        printf("\nElement %c successfully transfered from queue to stack...\nKeep entering the contents of the Stack and List:\n", val);
                    } else {
                        printf("Queue is empty, cannot move element.\n");
                    }
                } else {
                    if (Stack.head != NULL) {
                        char val = top(&Stack);
                        pop(&Stack);
                        enqueue(&Queue, val);
                        printf("\nElement %c successfully transfered from stack to queue...\nKeep entering the contents of the Stack and List:\n", val);
                    } else {
                        printf("Stack is empty, cannot move element.\n");
                    }
                }
                break;
            }

            case ';':
                printf("Contents of Queue:\t");
                print_queue(&Queue);
                printf("Contents of Stack:\t");
                print_stack(&Stack);
                delete_queue(&Queue);
                delete_stack(&Stack);
                printf("\nReturning to main menu...\n\n");
                return 1;

            default:
                enqueue(&Queue, input);
                push(&Stack, input);
                break;
        }
    }
}

int main(){

    int selection;
    int running = 1;

    while(running){

        selection = 0;
        printf("\n=============== Menu ===============\n");
        printf("Select 1 for array or 2 for linked list\n");
        scanf("%d",&selection);

        while(selection!=1 && selection!=2){
        printf("Invalid input, please select 1 or 2\n");
            scanf("%d",&selection);
        }

        switch(selection){
            case 1: 
                running = Array_implementation(); 
                break;        

            case 2:
                running =Linked_list_implementation();
                break;
        }
    }

    return 0;
}
