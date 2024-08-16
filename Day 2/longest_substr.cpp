#include <iostream>
using namespace std;

bool isPalindrome(string str){
    int left = 0;
    int right = str.size() - 1;
    
    while(left < right){
        if(str[left] != str[right]){
            return false;
        }
        
        left++;
        right--;
    }
    
    return true;
}


int main() {
    string str  = "babad";    
    int length = str.size();
    
    for(int i=0;i<length;i++){
        for(int j=0;j<=i;j++){
            string curString = "";
            for(int k = 0;k < length-i; k++){
                curString += str[j + k];
            }
            
            if(isPalindrome(curString) == true){
                cout << curString;
                return 0;
            }
        }
    }
}
