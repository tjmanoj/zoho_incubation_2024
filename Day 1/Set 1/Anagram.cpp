#include <iostream>
using namespace std;

bool isAnagram(string s1, string s2){
    if(s1.size() != s2.size()) return false;
    
    int hash[126] = {0};
    
    for(int i=0;i<s1.size();i++){
        hash[s1[i]]++;
        hash[s2[i]]--;
    }
    
    for(int i=0;i<126;i++){
        if(hash[i] != 0) return false;
    }
    
    return true;  
    
}
int main()
{
    string s1, s2;
    cin >> s1 >> s2;
    
    cout << isAnagram(s1,s2);
}
