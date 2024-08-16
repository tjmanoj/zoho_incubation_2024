#include <iostream>
using namespace std;

// Function to swap two pairs
void swap(pair<int, int>& a, pair<int, int>& b) {
    pair<int, int> temp = a;
    a = b;
    b = temp;
}

// Function to sort pairs using bubble sort
void bubbleSort(pair<int, int> arr[], int n) {
    for (int i = 0; i < n - 1; ++i) {
        for (int j = 0; j < n - i - 1; ++j) {
            if (arr[j].first > arr[j + 1].first) {
                swap(arr[j], arr[j + 1]);
            }
        }
    }
}

int main() {
    int numberOfIntervals = 3;
    int mergedIndex = 0;

    pair<int, int> intervals[numberOfIntervals] = {{1,2},{2,4},{6,7}};

    bubbleSort(intervals, numberOfIntervals);

    pair<int, int> mergedIntervals[numberOfIntervals];
    mergedIntervals[mergedIndex] = intervals[0];

    for (int i = 1; i < numberOfIntervals; ++i) {
        if (mergedIntervals[mergedIndex].second >= intervals[i].first) {
            mergedIntervals[mergedIndex].second = max(mergedIntervals[mergedIndex].second, intervals[i].second);
        } 
        
        else {
            ++mergedIndex;
            mergedIntervals[mergedIndex] = intervals[i];
        }
    }

    for (int i = 0; i <= mergedIndex; ++i) {
        cout << mergedIntervals[i].first << " " << mergedIntervals[i].second << endl;
    }

    return 0;
}
