#include <iostream>
#include <cmath>

// Function to extract digits of a number and compare them manually
bool compareNumbers(int a, int b) {
    // Convert numbers to arrays of digits
    int digitsA[10], digitsB[10];
    int lenA = 0, lenB = 0;
    
    // Extract digits of 'a'
    while (a > 0) {
        digitsA[lenA++] = a % 10;
        a /= 10;
    }
    
    // Extract digits of 'b'
    while (b > 0) {
        digitsB[lenB++] = b % 10;
        b /= 10;
    }
    
    // Compare digits from most significant to least significant
    for (int i = std::max(lenA, lenB) - 1; i >= 0; --i) {
        int digitA = (i < lenA) ? digitsA[i] : -1; // Use -1 if the digit does not exist
        int digitB = (i < lenB) ? digitsB[i] : -1; // Use -1 if the digit does not exist
        
        if (digitA > digitB) return true;
        if (digitA < digitB) return false;
    }
    
    return false;
}

// Function to perform bubble sort based on custom comparator
void bubbleSort(int arr[], int size) {
    for (int i = 0; i < size - 1; ++i) {
        for (int j = 0; j < size - i - 1; ++j) {
            if (!compareNumbers(arr[j], arr[j + 1])) {
                // Swap elements
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// Function to print the array
void printArray(int arr[], int size) {
    for (int i = 0; i < size; ++i) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
}

int main() {
    int arr[] = {7, 1, 19, 30};
    int size = sizeof(arr) / sizeof(arr[0]);

    bubbleSort(arr, size);

    std::cout << "Sorted array in descending order based on digits: ";
    printArray(arr, size);

    return 0;
}

