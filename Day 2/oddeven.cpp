#include <iostream>
using namespace std;
const int MAX_SIZE = 100;  

void separateOddEven(const int inputArray[], int size, int oddArray[], int& oddCount, int evenArray[], int& evenCount) {
    oddCount = 0;
    evenCount = 0;
    
    for (int i = 0; i < size; ++i) {
        if (inputArray[i] % 2 == 0) {
            evenArray[evenCount++] = inputArray[i];
        } else {
            oddArray[oddCount++] = inputArray[i];
        }
    }
}

void printArray(const int array[], int size) {
    for (int i = 0; i < size; ++i) {
        cout << array[i] << " ";
    }
    cout << endl;
}

int main() {
	int n = 4;
    int inputArray[] = {1,2,3,4};

    int oddArray[MAX_SIZE];
    int evenArray[MAX_SIZE];
    int oddCount, evenCount;
    

    separateOddEven(inputArray, size, oddArray, oddCount, evenArray, evenCount);


	cout << oddCount << evenCount;
    printArray(oddArray, oddCount);
    printArray(evenArray, evenCount);
    

    return 0;
}

