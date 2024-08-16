#include <iostream>
using namespace std;

bool isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ;
}

int main() {
    string inputString = "b$6an";
    
    int left = 0, right = inputString.size() - 1;

    while (left < right) {
    	
        if (isAlpha(inputString[left]) == 1 && isAlpha(inputString[right]) == 1) {
            swap(inputString[left], inputString[right]);
            left++;
            right--;
        } 
		else {
            if (isAlpha(inputString[left]) == 0){
            	 left++;
			}
            if (isAlpha(inputString[right]) == 0){
            	 right--;
			}
        }
    }

    cout << inputString;
    return 0;
}

