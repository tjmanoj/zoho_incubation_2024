#include <iostream>
using namespace std;

void Reverse(int array[], int startInd,int endInd){
	while(startInd < endInd){
		swap(array[startInd],array[endInd]);
		startInd++;
		endInd--;
	}
}

int main(){
	int n = 7,swapCount = 2;
	
	int array[n] = {10,12,13,15,9,5,7};
	
	int startInd = 0;
	
	
	for(int i=swapCount-1;i<n;i += swapCount){
		if(i == n-1){
			Reverse(array,startInd,i);
		}
		
		Reverse(array,startInd,i);
		startInd = i+1;
				
	}
	
	for(int i=0;i<n;i++) cout << array[i] << ' ';
	
}
