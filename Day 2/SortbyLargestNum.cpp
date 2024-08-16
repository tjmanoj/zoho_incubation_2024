#include <iostream>
using namespace std;

void bubbleSort(int arr[],int n){

    for(int i=0;i<n-1;i++){
        for(int j=0;j<n-i-1;j++){
            if(arr[j] < arr[j+1]){
                swap(arr[j],arr[j+1]);
            }
        }
    }
}

int main(){
    int n = 9;
    int inputArray[n] = {10,50,20,60,80,30,40,70,90};

    bubbleSort(inputArray,n);

    int result[n];
    int resultInd = 0;

    for(int i=0;i<n;i+=2){
        result[resultInd] = inputArray[i];
        result[n - resultInd - 1] = inputArray[i+1];
        resultInd++;
    }
    
    if(n % 2 == 1){
         result[n/2] = inputArray[n-1];
    }

    for(int i=0;i<n;i++) cout << result[i] << ' ';
}