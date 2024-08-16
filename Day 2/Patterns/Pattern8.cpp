#include <iostream>
using namespace std;

int main()
{
    int n = 5;
    
    int temp = 1;
    int startValue = 1;
    
    for(int i=1;i<=n;i++){
        temp = startValue;
        for(int j=1;j<=n-i+1;j++){
            cout << temp << ' ';
            temp = temp + (i + j);
        }
        startValue = startValue + i;
        cout << endl;
    }
}

// 1 3 6 10 15 
// 2 5 9 14 
// 4 8 13 
// 7 12 
// 11 