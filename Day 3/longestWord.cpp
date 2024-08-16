#include <iostream>
#include <string>

using namespace std;

string findLongestWord(const string& str) {
    int maxLength = 0; 
    int currentLength = 0; 
    int start = 0; 
    int maxStart = 0; 

    bool inWord = false; 

    for (int i = 0; i < str.size(); ++i) {
        char ch = str[i];

        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
            if (!inWord) {
                start = i; 
                inWord = true;
            }
            currentLength++; 
        } else {
            if (inWord) {

                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxStart = start;
                }
                currentLength = 0; 
                inWord = false; 
            }
        }
    }

 
    if (inWord && currentLength > maxLength) {
        maxLength = currentLength;
        maxStart = start;
    }

    string longestWord;
    for (int i = maxStart; i < maxStart + maxLength; ++i) {
        longestWord += str[i];
    }

    return longestWord;
}

int main() {
    string input = "I love India";
    

    string longestWord = findLongestWord(input);

    if (!longestWord.empty()) {
        cout << longestWord << endl;
    } else {
        cout <<" no words" << endl;
    }

    return 0;
}

