#include <iostream>
using namespace std;

int main()
{
    int n;
    cin >> n;
    
    int temp = 1;
    int startValue = 1;
    
    for(int i=1;i<=n;i++){
        temp = startValue;
        int bottom = n;
        for(int j=1;j<=n;j++){
            if(i+j > n+1){
                temp = temp + bottom;
                cout << temp << ' ';
                bottom--;
            }
            else{
                cout << temp << ' ';
                temp = temp + (i + j);
            }
            if(i+j == n+1){
                temp = temp - (i + j);
            }
        }
        startValue = startValue + i;
        bottom = n;
        cout << endl;
    }
}
