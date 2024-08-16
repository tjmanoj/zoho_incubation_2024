#include<iostream>
using namespace std;

int find_second_max(int arr[],int n){
    int max1 = arr[0];
    int max2 = arr[1];
    for(int i=1;i<n;i++){
        if(arr[i] > max1 ){
            max2 = max1;
            max1 = arr[i];
        }
        if(arr[i] > max2 && arr[i] != max1) max2 = arr[i];
    }
    return max2;
}

int main(){
	int n = 5;
    int arr[n] = {1,2,3,5,5};
    
    cout << find_second_max(arr,n);
	
}



