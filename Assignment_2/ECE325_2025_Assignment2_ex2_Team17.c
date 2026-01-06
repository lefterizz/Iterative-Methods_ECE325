#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct WORD{
    char word[30];      // Node for words that the user types
    struct WORD *next;
}WORD;

typedef struct{
    struct WORD *head;       // List for words that the user types
    int size;
}WORD_LIST;

typedef struct HASH_NODE{
    char word[30];                               // Store actual word in hash table
    struct HASH_NODE *next;
} HASH_NODE;

typedef struct {
    struct HASH_NODE *head;                         // List for hash table
    int size;
}HASH_TABLE;

void init_hash(HASH_TABLE *list){
    list->head = NULL;
    list->size = 0;
}

void init_list(WORD_LIST *list){
    list->head = NULL;
    list->size = 0;
}

void insert_list(WORD_LIST *list, char *word) {
    if (word == NULL || strlen(word) == 0){
        return;
    }

    WORD *current = list->head;
    while (current){
        if (strcmp(current->word, word) == 0){
            return; // word already exists
        }
        current = current->next;
    }
    WORD *newWORD = malloc(sizeof(WORD));
    strncpy(newWORD->word, word, sizeof(newWORD->word) - 1);
    newWORD->word[sizeof(newWORD->word) - 1] = '\0';
    newWORD->next = list->head;
    list->head = newWORD;
    list->size++;
}

void insert_hash(HASH_TABLE *table, char *word) {
    if (word == NULL || strlen(word) == 0){
        return;
    }

    HASH_NODE *current = table->head;
    while (current){
        if (strcmp(current->word, word) == 0){
            return; // word already exists
        }
        current = current->next;
    }
    
    HASH_NODE *newNode = malloc(sizeof(HASH_NODE));
    strncpy(newNode->word, word, sizeof(newNode->word) - 1);
    newNode->word[sizeof(newNode->word) - 1] = '\0';
    newNode->next = table->head;
    table->head = newNode;
    table->size++;
}

int search_list(WORD_LIST *list, char *word, WORD_LIST *missing_words) {
    WORD *current = list->head;
    while (current) {
        if (strcmp(current->word, word) == 0){
            return 1;
        }
        current = current->next;
    }
    insert_list(missing_words, word);   
    return 0;
}

int search_hash(HASH_TABLE *table, char *word, WORD_LIST *missing_words) {
    HASH_NODE *current = table->head;
    while (current) {
        if (strcmp(current->word, word) == 0){
            return 1;
        }
        current = current->next;
    }
    insert_list(missing_words, word);   
    return 0;
}

void print_list(WORD_LIST *LIST){
    WORD *current = LIST->head;
    while(current != NULL){
        printf("%s ", current->word);
        current = current->next;
    }
    printf("\n");
}

void free_list(WORD_LIST *list) {
    WORD *current = list->head;
    while (current) {
        WORD *tmp = current;
        current = current->next;
        free(tmp);
    }
    list->head = NULL;
    list->size = 0;
}

void free_hash_table(HASH_TABLE *table) {
    HASH_NODE *current = table->head;
    while (current) {
        HASH_NODE *tmp = current;
        current = current->next;
        free(tmp);
    }
    table->head = NULL;
    table->size = 0;
}

int compute_hash(char *word, int mod_value) {
    int hash = 0;
    for (int i = 0; word[i] != '\0'; i++) {
        hash += (int)word[i];
    }
    return hash % mod_value;
}

char *read_word() {
    static int end_of_line = 0;
    if (end_of_line) {
        end_of_line = 0;
        return NULL;
    }

    int c;
    int size = 30;
    int len = 0;
    char *word = malloc(size);
    if (!word) return NULL;

    // skip whitespace, but stop if newline = end of sentence
    while ((c = getchar()) != EOF) {
        if (c == ' ' || c == '\t') continue;
        if (c == '\n') {
            free(word);
            return NULL;
        }
        break;
    }

    if (c == EOF) {
        free(word);
        return NULL;
    }

    // read word until space, punctuation, or newline
    do {
        if (len >= size - 1) {
            size *= 2;
            char *tmp = realloc(word, size);
            if (!tmp) { free(word); return NULL; }
            word = tmp;
        }
        if (c == '\n') {
            end_of_line = 1;
            break;
        }
        word[len++] = tolower(c); // convert to lowercase for consistency
    } while ((c = getchar()) != EOF && !isspace(c) && c != ',' && c != '.');

    word[len] = '\0';
    return word;
}

void run_linked_list() {
    WORD_LIST list;
    init_list(&list);
    WORD_LIST missing_words;
    init_list(&missing_words);

    FILE *file = fopen("dictionary.txt", "r");
    if (!file) {
        perror("Failed to open dictionary.txt");
        return;
    }

    char word[30];
    int words_loaded = 0;
    while (fscanf(file, " %29[^ \t\n,.]", word) == 1) {
        // convert to lowercase for consistency
        for (int i = 0; word[i]; i++) {
            word[i] = tolower(word[i]);
        }
        insert_list(&list, word);
        words_loaded++;
    }
    fclose(file);

    printf("Successfully loaded %d words.\n", words_loaded);

    printf("Enter a sentence: ");
    getchar(); // clear leftover newline from menu input

    int allFound = 1;
    char *user_word;
    while ((user_word = read_word()) != NULL) {
        if (!search_list(&list, user_word, &missing_words)) {
            allFound = 0;
        }
        free(user_word);
    }

    if (allFound) {
        printf("True\n");
    } else {
        printf("False - ");
        print_list(&missing_words);
        printf(" were not present in the dictionary\n");
    }

    free_list(&list);
    free_list(&missing_words);
}

void run_hash_table(int mod_value) {
    HASH_TABLE *hash_tables = malloc(mod_value * sizeof(HASH_TABLE));
    
    // Initialize all hash table buckets
    for (int i = 0; i < mod_value; i++) {
        init_hash(&hash_tables[i]);
    }

    WORD_LIST missing_words;
    init_list(&missing_words);

    FILE *file = fopen("dictionary.txt", "r");
    if (!file) {
        perror("Failed to open dictionary.txt");
        free(hash_tables);
        return;
    }

    char word[30];
    int words_loaded = 0;
    while (fscanf(file, " %29[^ \t\n,.]", word) == 1) {
        // convert to lowercase for consistency
        for (int i = 0; word[i]; i++) {
            word[i] = tolower(word[i]);
        }
        
        int hash_key = compute_hash(word, mod_value);
        insert_hash(&hash_tables[hash_key], word);
        words_loaded++;
    }
    fclose(file);

    printf("Successfully loaded %d words using hash table (mod %d).\n", words_loaded, mod_value);

    printf("Enter a sentence: ");
    getchar(); // clear leftover newline from menu input

    int allFound = 1;
    char *user_word;
    while ((user_word = read_word()) != NULL) {
        int hash_key = compute_hash(user_word, mod_value);
        if (!search_hash(&hash_tables[hash_key], user_word, &missing_words)) {
            allFound = 0;
        }
        free(user_word);
    }

    if (allFound) {
        printf("True\n");
    } else {
        printf("False - ");
        print_list(&missing_words);
        printf(" were not present in the dictionary\n");
    }

    // Free all hash table buckets
    for (int i = 0; i < mod_value; i++) {
        free_hash_table(&hash_tables[i]);
    }
    free(hash_tables);
    free_list(&missing_words);
}

int main() {
    int choice;
    
    printf("Choose data structure to use:\n");
    printf("[1] Hash table (mod 20)\n");
    printf("[2] Hash table (mod 450)\n");
    printf("[3] Linked list\n");
    printf("Enter choice: ");
    scanf("%d", &choice);

    while (choice < 1 || choice > 3) {
        printf("Invalid choice. Please enter 1, 2, or 3: ");
        scanf("%d", &choice);
    }

    switch (choice) {
        case 1:
            printf("Hash table (mod 20) selected.\n");
            run_hash_table(20);
            break;
        case 2:
            printf("Hash table (mod 450) selected.\n");
            run_hash_table(450);
            break;
        case 3:
            printf("Linked list selected.\n");
            run_linked_list();
            break;
        default:
            printf("Invalid choice!\n");
    }

    return 0;
}