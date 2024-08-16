#include<iostream>
using namespace std;

int main(){
    int n = 4;
    int range = n * 2 - 1;

    int startInd = n;
    int toggle = 1;

    for(int i=1;i<=n;i++){
        int startValue = 1;
        for(int j=1;j<=range;j++){
            if(j >= startInd && j < n+i){
                cout << startValue;
                if(j >= n){
                    startValue--;
                }
                else{
                    startValue++;
                }
            }
            else{
                cout << ' ';
            }
        }
        cout << endl;
        startInd--;
    }
    
    return 0;

}

//    1   
//   121
//  12321
// 1234321