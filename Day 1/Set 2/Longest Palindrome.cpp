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
    string str;
    cin >> str;
    
    int length = str.size();
    
    for(int i=0;i<length;i++){
        for(int j=0;j<=i;j++){
            string temp = "";
            for(int k = 0;k < length-i; k++){
                temp += str[j + k];
            }
            
            if(isPalindrome(temp) == true){
                cout << temp;
                return 0;
            }
        }
    }
}
