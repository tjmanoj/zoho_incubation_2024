#include<iostream>
using namespace std;

int calculateFrequency(int arr[],int n,int target){
    int count = 0;
    for(int i=0;i<=n;i++){
        if(arr[i] == target) count++;
    }
    
    return count;
}

int main(){
	int count = 3,k = 0;
	int n = 9;
    int arr[n] = {5,11,13,12,11,15,12,11,16};
    
    for(int i=0;i<n;i++){
    	for(int j=0;j<n-i-1;j++){
    		
    		int i_factor = calculateFrequency(arr,n,arr[i]);          // frequency of ith element
    		int j_factor = calculateFrequency(arr,n,arr[j]);         // frequency of jth element
    		
    		if(i_factor > j_factor){
    			int temp = arr[i];
    			arr[i] = arr[j];
    			arr[j] = temp;	
			}
		}
	}
    
    int result[count];
    for(int i=1;i<count;i++) result[i] = INT_MAX;
    
    result[0] = arr[0];
    
    for(int i=1;i<n;i++){
    	
    	if(result[k] != arr[i]){
    		result[++k] = arr[i];
		}
    	
    	if(count == k) break;
	}
	
//	for(int i=0;i<n;i++) cout << arr[i] << ' ';
	for(int i=0;i<count;i++) cout << result[i] << ' ';
}

