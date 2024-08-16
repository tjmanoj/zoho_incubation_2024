#include <iostream>
using namespace std;

void UnmatchSubstring(string str1, string str2){
    string unmatchedInString1 = "";
    string unmatchedInString2 = "";

    for(int i=0;i<str1.size();i++){
        if(str1[i] != str2[i]){
            unmatchedInString1 += str1[i];
            unmatchedInString2 += str2[i];
        }
        else{
            if(unmatchedInString1.size() > 0){
                cout << unmatchedInString1 << ' ' << unmatchedInString2 << endl;
                unmatchedInString1 = "";
                unmatchedInString2 = "";
            }
        }
    }
}

int main(){
    string str1 = "abdefghjk";
    string str2 = "abjufgikk";

    UnmatchSubstring(str1,str2);

    return 0;
}