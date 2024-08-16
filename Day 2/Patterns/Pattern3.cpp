#include<iostream>
using namespace std;

int main(){
    int n = 5;

    for(int i=1;i<=n;i++){
        for(int j=1;j<=n;j++){
            int value = (n+1) - j;
            if(value <= i){
                cout << value;
            }
            else{
                cout << ' ';
            }
        }
        cout << endl;
    }
}

//     1
//    21
//   321
//  4321
// 54321