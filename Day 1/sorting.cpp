#include<iostream>
using namespace std;

int countFactors(int n){
    int count = 0;
    for(int i=1;i<=n/2;i++){
        if(n % i == 0) count++;
    }
    
    return count;
}

int main(){
	int n = 5;
    int arr[n] = {10,12,13,9,6};
    
    for(int i=0;i<n;i++){
    	for(int j=0;j<n-i-1;j++){
    		
    		int i_factor = countFactors(arr[j]);          // factors of ith element
    		int j_factor = countFactors(arr[j+1]);         // factors of jth element
    		
    		if(i_factor > j_factor){
    			int temp = arr[j];
    			arr[j] = arr[j+1];
    			arr[j+1] = temp;	
			}
		}
	}
    
    for(int i=0;i<n;i++) cout << arr[i] << ' ';
}

