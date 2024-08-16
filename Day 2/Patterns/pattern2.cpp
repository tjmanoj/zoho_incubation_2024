#include<iostream>
using namespace std;

int main(){
    int n = 5;

    for(int i=1;i<=n;i++){
        int startValue = i;
        int diff = n-1;
        for(int j=1;j<=i;j++){
            cout << startValue << ' ';
            startValue += diff;
            diff--;
        }
        cout << endl;
    }
    return 0;
}

// 1 
// 2 6
// 3 7 10
// 4 8 11 13
// 5 9 12 14 15