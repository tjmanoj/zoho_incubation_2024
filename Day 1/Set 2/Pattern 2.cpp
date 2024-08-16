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
        for(int j=1;j<=n-i+1;j++){
            cout << temp << ' ';
            temp = temp + (i + j);
        }
        startValue = startValue + i;
        cout << endl;
    }
}
