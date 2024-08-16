#include <iostream>
#include <climits>

using namespace std;

int calculateFrequency(int arr[], int n, int target) {
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (arr[i] == target) count++;
    }
    return count;
}

int main() {
    int count = 3, k = 0;
    int n = 15;
    int arr[n] = {7, 6, 5, 3, 1, 6, 7, 13, 25, 3, 7, 19, 9, 6, 10};
    
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int i_factor = calculateFrequency(arr, n, arr[i]); // frequency of ith element
            int j_factor = calculateFrequency(arr, n, arr[j]); // frequency of jth element
            
            if (i_factor < j_factor || (i_factor == j_factor && arr[i] > arr[j])) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    
    int result[count];
    for (int i = 0; i < count; i++) result[i] = INT_MAX;
    
    result[0] = arr[0];
    k = 1;
    
    for (int i = 1; i < n && k < count; i++) {
        if (arr[i] != result[k-1]) {
            result[k++] = arr[i];
        }
    }
    
    for (int i = 0; i < count; i++) {
        if (result[i] != INT_MAX) {
            cout << result[i] << ' ';
        }
    }
    cout << endl;

    return 0;
}

