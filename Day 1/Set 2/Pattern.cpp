#include <iostream>
using namespace std;

int main() {
    int n;
    cin >> n;
    
    int range = n * 2 - 1;
    
    for(int i=0;i<n;i++){
        for(int j=0;j<range;j++){
            int value = abs(j - (n-1)) + 1;
            
            if(value <= i+1){
                cout << value;
            }
            else{
                cout << ' ';
            }
        }
        
        cout << endl;
    }
}
