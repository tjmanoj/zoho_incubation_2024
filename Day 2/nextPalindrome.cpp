#include<iostream>
using namespace std;

bool isPalindrome(long long number){
	long long originalNum = number;
	long long reversedNum = 0;

	while(number != 0){

		int lastDigit = number % 10;
		reversedNum = (reversedNum * 10) + lastDigit;

		number = number / 10;
	}
	return originalNum == reversedNum;
}
	

void nextPalindrome(int inputNumbers[],int n){
	long long number = 0;

	for(int i=0;i<n;i++){									// Array of numbers to integer.
		number = (number * 10) + inputNumbers[i];
	}
	
	number = number + 1;									// We need next palindrome number not the given number.

	while(true){
		if(isPalindrome(number)){
			cout << number;
			break;
		}

		number = number + 1;
	}
}

int main(){
	int n = 11;
	int inputNumbers[n] = {9, 4,1, 8, 7, 9 ,7 ,8 ,3, 2, 2};
	
	nextPalindrome(inputNumbers,n);

}

