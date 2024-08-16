#include <iostream>
using namespace std;

int main()
{
    int n,x;
    cin >> n >> x;
    int a[n];
    
    for(int i=0;i<n;i++) cin >> a[i];
    
    int Floor = -1;
    int Ceil = -1;
    
    for(int i=0;i<n;i++){
        if(Ceil != -1) break;
        
        if(a[i] <= x) Floor = a[i];
        if(a[i] >= x) Ceil = a[i];
    }
    
    cout << Floor << ' ' << Ceil;
}
