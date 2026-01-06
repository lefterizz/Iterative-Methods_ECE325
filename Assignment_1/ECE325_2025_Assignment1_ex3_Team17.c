#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_STUDENTS 100
#define MAX_SCORE 10.0
#define MIN_SCORE 0.0

typedef struct {
    char id[20];
    char name[50];
    int participations;
    float proj1;
    float proj2;
    float total;
} Student;

Student students[MAX_STUDENTS]; // array to hold student records
int studentCount = 0; // current number of students

void calculateTotal(Student *s) {
    s->total = (s->proj1 * 0.6f) + (s->proj2 * 0.4f); // weighted total
}

void addParticipant() {
    if (studentCount >= MAX_STUDENTS) { // if array is full
        printf("Cannot add more participants.\n");
        return;
    }

    Student s;
    printf("Enter ID: ");
    scanf("%s", s.id);
    printf("Enter Name: ");
    scanf(" %[^\n]", s.name);

    printf("Enter Number of previous participations: ");
    scanf("%d", &s.participations);
    while(s.participations < 0 ) { // validate participations
        printf("Invalid number. Enter Number of previous participations (>=0): ");
        scanf("%d", &s.participations);
    }

    printf("Enter Project 1 score: ");
    scanf("%f", &s.proj1);
    while(s.proj1 < MIN_SCORE || s.proj1 > MAX_SCORE) { // validate proj1 scores
        printf("Invalid score. Enter Project 1 score (0-10): ");
        scanf("%f", &s.proj1);
    }
    printf("Enter Project 2 score: ");
    scanf("%f", &s.proj2);
    while(s.proj2 < MIN_SCORE || s.proj2 > MAX_SCORE) { // validate proj2 scores
        printf("Invalid score. Enter Project 2 score (0-10): ");
        scanf("%f", &s.proj2);
    }
    calculateTotal(&s); // calculate total score
    students[studentCount++] = s; // add student to array

    printf("Participant added successfully.\n");
}

void deleteParticipant() {
    char id[20];
    printf("Enter ID of participant to delete: "); // find by ID
    scanf("%s", id);

    for (int i = 0; i < studentCount; i++) { // loop to find student
        if (strcmp(students[i].id, id) == 0) { // if found
            // shift remaining students left
            for (int j = i; j < studentCount - 1; j++) {
                students[j] = students[j + 1];
            }
            studentCount--;
            printf("Participant deleted successfully.\n");
            return;
        }
    }
    printf("Participant not found.\n");
}

void updateParticipant() {
    char id[20];
    printf("Enter ID of participant to update: "); // find by ID
    scanf("%s", id);

    for (int i = 0; i < studentCount; i++) {
        if (strcmp(students[i].id, id) == 0) {
            printf("Enter new Name: ");
            scanf(" %[^\n]", students[i].name);

            printf("Enter new Number of previous participations: ");
            scanf("%d", &students[i].participations);
            while(students[i].participations < 0) { // validate participations
                printf("Invalid number. Enter Number of previous participations (>=0): ");
                scanf("%d", &students[i].participations);
            }
            printf("Enter new Project 1 score: "); 
            scanf("%f", &students[i].proj1);
            while(students[i].proj1 < MIN_SCORE || students[i].proj1 > MAX_SCORE) { // validate proj1 scores
                printf("Invalid score. Enter Project 1 score (0-10): ");
                scanf("%f", &students[i].proj1);
            }
            printf("Enter new Project 2 score: ");
            scanf("%f", &students[i].proj2);
            while(students[i].proj2 < MIN_SCORE || students[i].proj2 > MAX_SCORE) { // validate proj2 scores
                printf("Invalid score. Enter Project 2 score (0-10): ");
                scanf("%f", &students[i].proj2);
            }

            calculateTotal(&students[i]); // recalculate total
            printf("Participant updated successfully.\n");
            return;
        }
    }
    printf("Participant not found.\n");
}

void saveToFile() {
    FILE *f = fopen("participants.txt", "w"); // open file for writing
    if (!f) { // check if file opened successfully
        printf("Error opening file.\n");
        return;
    }

    // format of each line to be saved id,name,participations,proj1,proj2,total
    for (int i = 0; i < studentCount; i++) {
        fprintf(f, "%s,%s,%d,%.2f,%.2f,%.2f\n",
                students[i].id, students[i].name,
                students[i].participations,
                students[i].proj1, students[i].proj2,
                students[i].total);
    }
    fclose(f);
    printf("Records saved successfully.\n");
}


void loadFromFile() {
    FILE *f = fopen("participants.txt", "r"); // open and read file
    if (!f) { // check if file opened successfully
        printf("Error opening file.\n");
        return;
    }

    studentCount = 0;
    // each line format of file to be loaded is id,name,participations,proj1,proj2,total
        while (fscanf(f, "%19[^,],%49[^,],%d,%f,%f,%f\n",
                      students[studentCount].id,
                      students[studentCount].name,
                      &students[studentCount].participations,
                      &students[studentCount].proj1,
                      &students[studentCount].proj2,
                      &students[studentCount].total) == 6) {
            studentCount++;
            if (studentCount >= MAX_STUDENTS) break;
        }
    fclose(f);
    printf("Records loaded successfully.\n");
}

void viewAll() {
    if (studentCount == 0) {
        printf("No participants available.\n");
        return;
    }

    for (int i = 0; i < studentCount; i++) { // loop to print all students
        printf("ID: %s, Name: %s, Participations: %d, Project 1: %.2f, Project 2: %.2f, Total: %.2f\n",
               students[i].id, students[i].name, students[i].participations,
               students[i].proj1, students[i].proj2, students[i].total);
    }
}

void viewByParticipations() { // Prints students by number of participations
    if (studentCount == 0) {
        printf("No participants available.\n");
        return;
    }

    int num;
    printf("Enter number of previous participations: ");
    scanf("%d", &num);

    int found = 0; // flag to check if any student found
    printf("Participants with %d previous participations:\n", num);
    for (int i = 0; i < studentCount; i++) {
        if (students[i].participations == num) {
            printf("ID: %s, Name: %s, Project 1: %.2f, Project 2: %.2f, Total: %.2f\n",
                   students[i].id, students[i].name, students[i].proj1,
                   students[i].proj2, students[i].total);
            found = 1;
        }
    }
    if (!found) {
        printf("No participants found with %d participations.\n", num);
    }
}


void viewHighestScore() {
    if (studentCount == 0) {
        printf("No participants available.\n");
        return;
    }

    int maxIndex = 0;
    for (int i = 1; i < studentCount; i++) { // loop and find highest score index
        if (students[i].total > students[maxIndex].total) { // compare current to max
            maxIndex = i;
        }
    }

    Student s = students[maxIndex]; // get student with highest score
    printf("\nParticipant with Highest Score:\n");
    printf("ID: %s, Name: %s, Participations: %d, Project 1: %.2f, Project 2: %.2f, Total: %.2f\n",
           s.id, s.name, s.participations, s.proj1, s.proj2, s.total);
}

void averageScore() {
    if (studentCount == 0) {
        printf("No participants available.\n");
        return;
    }

    float sum = 0;
    for (int i = 0; i < studentCount; i++) { // get total score sum
        sum += students[i].total;
    }
    printf("Average total score: %.2f\n", sum / studentCount); // divide by number of students
}

int main() {
    int choice;
    do {
        printf("\n===============Menu===============\n");
        printf("1. Add participant record\n");
        printf("2. Delete participant record\n");
        printf("3. Update participant record\n");
        printf("4. Save all participant records in a .txt file\n");
        printf("5. Load all participant records from a .txt file\n");
        printf("6. View all participant records\n");
        printf("7. View participants by number of previous participations\n");
        printf("8. View participant with highest total score\n");
        printf("9. Calculate average total score over all participants\n");
        printf("10. Exit program\n");
        printf("===================================\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1: addParticipant(); break;
            case 2: deleteParticipant(); break;
            case 3: updateParticipant(); break;
            case 4: saveToFile(); break;
            case 5: loadFromFile(); break;
            case 6: viewAll(); break;
            case 7: viewByParticipations(); break;
            case 8: viewHighestScore(); break;
            case 9: averageScore(); break;
            case 10: printf("Exiting program.\n"); break;
            default: printf("Invalid choice. Try again.\n");
        }
    } while (choice != 10);
    return 0;
}