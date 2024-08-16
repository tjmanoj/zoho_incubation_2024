#include <iostream>
using namespace std;

void mergeSortedArrays(const int firstArray[], int firstArraySize, const int secondArray[], int secondArraySize, int mergedArray[]) {
    int firstIndex = 0;
    int secondIndex = 0;
    int mergedIndex = 0;

    while (firstIndex < firstArraySize && secondIndex < secondArraySize) {
        
        if (firstArray[firstIndex] < secondArray[secondIndex]) {
            mergedArray[mergedIndex++] = firstArray[firstIndex++];
        } 
        
        else {
            mergedArray[mergedIndex++] = secondArray[secondIndex++];
        }
    }


    while (firstIndex < firstArraySize) {                                // Store remaining elements of the first array
        mergedArray[mergedIndex++] = firstArray[firstIndex++];
    }


    while (secondIndex < secondArraySize) {                             // Store remaining elements of the second array
        mergedArray[mergedIndex++] = secondArray[secondIndex++];
    }
}

int main() {
    int firstArraySize = 4;
    int secondArraySize = 4;
    int mergedArraySize = firstArraySize + secondArraySize;\

    int firstArray[] = {1, 3, 4, 5};
    int secondArray[] = {2, 4, 6, 8};
    int mergedArray[mergedArraySize];

    mergeSortedArrays(firstArray, firstArraySize, secondArray, secondArraySize, mergedArray);

    for (int i = 0; i < mergedArraySize; i++) {
        cout << mergedArray[i] << " ";
    }

    return 0;
}
