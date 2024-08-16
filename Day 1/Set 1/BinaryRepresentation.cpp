#include <iostream>
using namespace std;

int main()
{
    int n;
    cin >> n;
    
    int temp[9] = {0};
    
    int multiple = 1;
    
    while(n > 0){
        int count = n % 10;
        
        for(int i=0;i<count;i++) temp[i] += multiple;
        
        multiple *= 10;
        
        n /= 10;
        
    }
    
    for(int i=0;i<10;i++){
        if(temp[i] == 0) break;
        cout << temp[i] << endl;
    }
    
}
