#include <iostream>
using namespace std;

// Function to merge two halves
void merge(pair<int, int> arr[], int low, int mid, int high) {
    int n = high - low + 1;
    pair<int, int> temp[n];
    int i = low;
    int j = mid + 1;
    int k = 0;

    while (i <= mid && j <= high) {
        if (arr[i].first < arr[j].first) {
            temp[k++] = arr[i++];
        } else {
            temp[k++] = arr[j++];
        }
    }

    while (i <= mid) {
        temp[k++] = arr[i++];
    }

    while (j <= high) {
        temp[k++] = arr[j++];
    }

    for (int i = low; i <= high; i++) {
        arr[i] = temp[i - low];
    }
}

// Function to perform merge sort
void mergeSort(pair<int, int> arr[], int low, int high) {
    if (low < high) {
        int mid = (low + high) / 2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
    }
}

int main() {
    int n;
    cin >> n;

    pair<int, int> intervals[n];

    for (int i = 0; i < n; ++i) {
        cin >> intervals[i].first >> intervals[i].second;
    }

    // Sort the intervals based on the starting value using merge sort
    mergeSort(intervals, 0, n - 1);

    pair<int, int> result[n];
    int k = 0;
    result[k] = intervals[0];

    for (int i = 1; i < n; ++i) {
        if (result[k].second >= intervals[i].first) {
            result[k].second = max(result[k].second, intervals[i].second);
        } else {
            k++;
            result[k] = intervals[i];
        }
    }

    for (int i = 0; i <= k; ++i) {
        cout << result[i].first << " " << result[i].second << endl;
    }

    return 0;
}
