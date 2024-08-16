#include<iostream>
using namespace std;

int main(){
    int n = 5;

    int range = n*2-1;
    for(int i=1;i<=n;i++){
        for(int j=1;j<=range;j++){
            if(i+j >= n+1 && i >= j+1-n){
                cout << abs(n-j);
            }
            else{
                cout << ' ';
            }
        }
        cout << endl;
    }
}

//     0    
//    101
//   21012
//  3210123
// 432101234