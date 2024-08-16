#include <iostream>
using namespace std;

void generateNextPalindrome(int num[], int n, int result[], bool &flag) {
    int leftIndex = n / 2;
    int rightIndex = n / 2;
    
    for (int i = 0; i < n; i++) {
        result[i] = num[i];
    }

    if (n % 2 == 0) {
        leftIndex--;
    }

    // First pass: Make a palindrome
    while (leftIndex >= 0) {
        if (result[rightIndex] > result[leftIndex]) break;
        if (result[leftIndex] > result[rightIndex]) {
            while (leftIndex >= 0) {
                result[rightIndex] = result[leftIndex];
                leftIndex--;
                rightIndex++;
            }
            return;
        }
        leftIndex--;
        rightIndex++;
    }

    // Second pass: Adjust the palindrome
    leftIndex = n / 2;
    rightIndex = n / 2;

    if (n % 2 == 0) {
        leftIndex--;
    }

    while (leftIndex >= 0) {
        if (num[leftIndex] != 9) {
            if (num[rightIndex] >= num[leftIndex]) {
                num[leftIndex]++;
            }
            while (leftIndex >= 0) {
                num[rightIndex] = num[leftIndex];
                rightIndex++;
                leftIndex--;
            }
            for (int x = 0; x < n; x++) {
                result[x] = num[x];
            }
            return;
        } else {
            num[leftIndex] = 0;
            num[rightIndex] = 0;
            leftIndex--;
            rightIndex++;
        }
    }

    // Special case: all digits are 9
    for (int x = 0; x < n + 1; x++) {
        result[x] = 0;
    }
    result[0] = 1;
    result[n] = 1;
    flag = true;
}

int main() {
    int n = 3;
    int num[n] = {9,9,9};
    int result[n + 1];  // Result array should be of size n+1 to handle the all 9's case.
    
    bool flag = false;
    generateNextPalindrome(num, n, result,flag);

    if(flag){           // whether to print or not print last element in result. 
        n++;
    }

    for (int i = 0; i < n ; ++i) {
        cout << result[i] << " ";
    }

    return 0;
}
