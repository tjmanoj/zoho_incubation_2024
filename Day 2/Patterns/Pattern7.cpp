#include<iostream>
using namespace std;

int main(){
    int n = 6;

    for(int i=1;i<=n;i++){
        int startValue = i;
        for(int j=1;j<=n;j++){
            cout << (startValue++) << ' ';

            if(startValue > n){
                startValue = 1;
            }   
        }
        cout << endl;
    }
}

// 1 2 3 4 5 6 
// 2 3 4 5 6 1
// 3 4 5 6 1 2
// 4 5 6 1 2 3
// 5 6 1 2 3 4
// 6 1 2 3 4 5