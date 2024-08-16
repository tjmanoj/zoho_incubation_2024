#include <iostream>

using namespace std;


int main(){
    int n,base;
    cin >> n >> base;


    string res = "";

    while(n!=0){
        int rem = n % base;
        
        if(rem > 9){
            char temp = rem + 55;
            res = temp + res;
        }
        else{
            res =  to_string(rem) + res;
        }

        n /= base;
    }
    cout << res;
    return 0;
}
