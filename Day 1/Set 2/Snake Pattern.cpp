#include <iostream>
using namespace std;

int main() {
    int n;
    cin >> n;
    
    int temp = 0;                      // even row -> ++temp
                                      // odd row ->  temp--
                                      
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(i % 2 == 0){
                cout << ++temp << ' ';
            }
            else{
                cout << temp-- << ' ';
            }
        }
        temp = temp + n;
        cout << endl;
        
    }
}
